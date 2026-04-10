package com.bms.app.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bms.app.domain.model.UserProfile
import com.bms.app.domain.model.UserRoleRow
import com.bms.app.domain.util.NameUtils
import com.bms.app.domain.repository.AppointmentRepository
import com.bms.app.domain.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

sealed class AdminUiState {
    object Loading : AdminUiState()
    data class Success(
        val totalUsers: Int,
        val totalAppointments: Int,
        val newUsersThisWeek: Int,
        val totalRevenue: Double,
        val pendingProviders: Int,
        val completedSessions: Int,
        val pendingBookings: Int,
        val users: List<UserProfile>,
        val appointments: List<com.bms.app.domain.model.Appointment>,
        val transactions: List<com.bms.app.domain.model.PaymentTransaction>,
        val adminInitials: String
    ) : AdminUiState()
    data class Error(val message: String, val isNetwork: Boolean = false) : AdminUiState()
}

sealed class ExportState {
    object Idle : ExportState()
    object Processing : ExportState()
    data class Success(val json: String, val timestamp: Long) : ExportState()
    data class Error(val message: String) : ExportState()
}

@HiltViewModel
class AdminViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val appointmentRepository: AppointmentRepository,
    private val auth: Auth,
    private val postgrest: Postgrest
) : ViewModel() {

    private val _uiState = MutableStateFlow<AdminUiState>(AdminUiState.Loading)
    val uiState: StateFlow<AdminUiState> = _uiState.asStateFlow()

    private val _exportState = MutableStateFlow<ExportState>(ExportState.Idle)
    val exportState: StateFlow<ExportState> = _exportState.asStateFlow()

    init {
        loadAdminDashboard()
    }

    fun loadAdminDashboard() {
        viewModelScope.launch {
            _uiState.update { AdminUiState.Loading }

            // Wait for Supabase to restore the session (fixes cold-start blank screen)
            try { auth.awaitInitialization() } catch (_: Exception) {}

            // Retry up to 3 times with exponential backoff for transient network errors
            var lastError: String = "Unknown error"
            var isNetworkError = false
            repeat(3) { attempt ->
                val result = runCatching { fetchDashboardData() }
                if (result.isSuccess) return@launch  // success — stop retrying
                val ex = result.exceptionOrNull()
                lastError = ex?.message ?: "Unknown error"
                isNetworkError = isNetworkException(ex)
                if (attempt < 2) {
                    // Wait before retrying: 1s, then 3s
                    delay(if (attempt == 0) 1_000L else 3_000L)
                }
            }

            // All retries exhausted
            val friendlyMsg = if (isNetworkError)
                "No internet connection. Please check your network and tap Retry."
            else
                "Failed to load admin data: $lastError"
            _uiState.update { AdminUiState.Error(friendlyMsg, isNetworkError) }
        }
    }

    private fun isNetworkException(e: Throwable?): Boolean {
        if (e == null) return false
        val msg = e.message?.lowercase() ?: ""
        return msg.contains("unable to resolve host") ||
               msg.contains("failed to connect") ||
               msg.contains("no address associated") ||
               msg.contains("network") ||
               msg.contains("timeout") ||
               msg.contains("connection") ||
               msg.contains("localhost")
    }

    private suspend fun fetchDashboardData() {
        val currentUser = auth.currentSessionOrNull()?.user

        val profilesResult = profileRepository.getAllProfiles()
        val providersResult = profileRepository.getAllProviderProfiles()
        val appointmentsResult = appointmentRepository.getAllAppointments()

        if (profilesResult.isFailure) {
            throw profilesResult.exceptionOrNull() ?: Exception("Failed to load profiles")
        }

        val rawUsers = profilesResult.getOrThrow()
        val providerProfiles = providersResult.getOrElse { emptyList() }
        val rawAppointments = appointmentsResult.getOrElse { emptyList() }

        // Fetch authoritative roles from user_roles table
        val allUserIds = rawUsers.map { it.userId }
        val userRolesMap: Map<String, String> = try {
            postgrest["user_roles"].select {
                filter { isIn("user_id", allUserIds) }
            }.decodeList<UserRoleRow>().associate { it.user_id to it.role }
        } catch (_: Exception) { emptyMap() }

        // Map providerProfileId -> userId for lookup
        val providerProfileToUserId = providerProfiles.associate { it.id to it.userId }
        
        // Enrich appointments with actual userIds for both patient and provider
        val appointments = rawAppointments.map { appt ->
            appt.copy(
                // providerId in DB is usually the provider_profile UUID, convert to userId for UI lookup
                providerId = providerProfileToUserId[appt.providerId] ?: appt.providerId
            )
        }

        val providerUserIds = providerProfiles.map { it.userId }.toSet()
        val users = rawUsers.map { user ->
            // Priority: user_roles table > provider_profiles presence > profiles.role
            val authoritativeRole = userRolesMap[user.userId]
            val p = providerProfiles.find { it.userId == user.userId }
            val resolvedRole = when {
                authoritativeRole != null -> authoritativeRole
                providerUserIds.contains(user.userId) -> "provider"
                else -> user.role ?: "user"
            }
            user.copy(
                role = resolvedRole,
                status = if (p?.isApproved == false && p.isActive) "pending" else user.status
            )
        }

        val currentUserId = currentUser?.id ?: ""
        val currentUserProfile = users.find { it.userId == currentUserId }
        val adminInitials = NameUtils.getInitials(currentUserProfile?.fullName)
            .ifBlank { 
                NameUtils.getInitials(currentUser?.email?.split("@")?.firstOrNull())
            }.ifBlank { "AD" }

        val completedAppointments = appointments.filter { it.status.lowercase() == "completed" }
        val completedCount = completedAppointments.size
        val pendingCount = appointments.count { it.status.lowercase() == "pending" }
        val pendingProvidersCount = users.count { it.role == "provider" && it.status?.lowercase() == "pending" }

        // Calculate Revenue and Transactions
        var totalRevenue = 0.0
        val recentTransactions = mutableListOf<com.bms.app.domain.model.PaymentTransaction>()

        completedAppointments.forEach { appt ->
            // Find provider profiles for this appointment to get the fee
            val provider = providerProfiles.find { it.id == rawAppointments.find { ra -> ra.id == appt.id }?.providerId }
            val patient = users.find { it.userId == appt.userId }
            val fee = provider?.consultationFee ?: 150.0 // fallback
            
            totalRevenue += fee
            
            recentTransactions.add(
                com.bms.app.domain.model.PaymentTransaction(
                    id = appt.id,
                    title = "Consultation Fee - ${patient?.fullName ?: "Unknown User"}",
                    subtitle = appt.appointmentDate,
                    amount = fee,
                    type = "credit",
                    date = appt.appointmentDate,
                    isIncoming = true
                )
            )
        }

        _uiState.update {
            AdminUiState.Success(
                totalUsers = users.size,
                totalAppointments = appointments.size,
                newUsersThisWeek = users.size,
                totalRevenue = totalRevenue,
                pendingProviders = pendingProvidersCount,
                completedSessions = completedCount,
                pendingBookings = pendingCount,
                users = users,
                appointments = appointments,
                transactions = recentTransactions.sortedByDescending { it.date }.take(20),
                adminInitials = adminInitials
            )
        }
    }

    fun deactivateUser(userId: String) {
        viewModelScope.launch {
            val result = profileRepository.deactivateUser(userId)
            if (result.isSuccess) loadAdminDashboard()
        }
    }

    fun suspendUser(userId: String) {
        viewModelScope.launch {
            val result = profileRepository.suspendUser(userId)
            if (result.isSuccess) loadAdminDashboard()
        }
    }

    fun banUser(userId: String) {
        viewModelScope.launch {
            val result = profileRepository.banUser(userId)
            if (result.isSuccess) loadAdminDashboard()
        }
    }

    fun addUser(name: String, email: String, role: String) {
        viewModelScope.launch {
            _uiState.update { AdminUiState.Loading }
            // Simulation: In a real app, this would call an Edge Function or auth.admin.createUser
            delay(1500) 
            loadAdminDashboard()
        }
    }

    fun changeUserRole(userId: String, newRole: String) {
        viewModelScope.launch {
            val result = profileRepository.changeUserRole(userId, newRole)
            if (result.isSuccess) loadAdminDashboard()
        }
    }

    fun exportToJSON(tableName: String = "all") {
        viewModelScope.launch {
            _exportState.value = ExportState.Processing
            delay(1500) // Simulate processing
            
            val currentState = _uiState.value
            if (currentState is AdminUiState.Success) {
                try {
                    val exportData = when (tableName) {
                        "appointments" -> Json.encodeToString(currentState.appointments)
                        "users" -> Json.encodeToString(currentState.users)
                        "transactions" -> Json.encodeToString(currentState.transactions)
                        else -> {
                            val all = mapOf(
                                "users" to currentState.users,
                                "appointments" to currentState.appointments,
                                "transactions" to currentState.transactions
                            )
                            Json.encodeToString(all)
                        }
                    }
                    _exportState.value = ExportState.Success(exportData, System.currentTimeMillis())
                } catch (e: Exception) {
                    _exportState.value = ExportState.Error(e.message ?: "Failed to serialize data")
                }
            } else {
                _exportState.value = ExportState.Error("Data not loaded yet")
            }
        }
    }

    fun resetExportState() {
        _exportState.value = ExportState.Idle
    }
}
