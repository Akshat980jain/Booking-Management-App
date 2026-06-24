package com.bms.app.ui.dashboard

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bms.app.domain.model.Appointment
import com.bms.app.domain.model.UserProfile
import com.bms.app.domain.model.UserRoleRow
import com.bms.app.domain.repository.AppointmentRepository
import com.bms.app.domain.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.gotrue.SessionStatus
import io.github.jan.supabase.gotrue.auth
import com.bms.app.data.local.SupabaseSessionManager
import com.bms.app.util.NotificationHelper
import io.github.jan.supabase.postgrest.Postgrest
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class DashboardUiState {
    object Loading : DashboardUiState()
    data class Success(
        val userProfile: UserProfile,
        val totalAppointments: Int,
        val newPatients: Int,
        val totalRevenue: Double,
        val pendingRequests: Int,
        val todayAppointments: List<Appointment>,
        val allUpcomingAppointments: List<Appointment> = emptyList(),
        val pendingAppointments: List<Appointment> = emptyList(),
        val pendingUserProfiles: Map<String, UserProfile> = emptyMap(),
        val patientNames: Map<String, String> = emptyMap(),
        val patientRoles: Map<String, String> = emptyMap(),
        val userInitials: String = "PR",
        val currencySymbol: String = "₹",
        val rescheduleRequests: List<Appointment> = emptyList(),
        val isActionLoading: String? = null,
        val errorMessage: String? = null,
        /** Provider online/offline status */
        val isOnline: Boolean = true,
        val isStatusLoading: Boolean = false,
        /** Stores the provider profile id for status updates */
        val providerProfileId: String = ""
    ) : DashboardUiState()
    object ProfileIncomplete : DashboardUiState()
    data class Error(val message: String) : DashboardUiState()
}

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val application: Application,
    private val profileRepository: ProfileRepository,
    private val appointmentRepository: AppointmentRepository,
    private val auth: Auth,
    private val sessionManager: SupabaseSessionManager,
    private val postgrest: Postgrest,
    private val notificationRepository: com.bms.app.domain.repository.NotificationRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<DashboardUiState>(DashboardUiState.Loading)
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    // seenNotificationIds lives in notificationRepository (singleton) — shared across all dashboards.

    init {
        loadDashboard()
        startPolling()
    }

    private fun startPolling() {
        viewModelScope.launch {
            while (isActive) {
                delay(10_000L) // Poll every 10 seconds for "dynamic" feel
                loadDashboard(isRefresh = true)
            }
        }
    }

    fun loadDashboard(isRefresh: Boolean = false) {
        viewModelScope.launch {
            if (!isRefresh) {
                _uiState.update { DashboardUiState.Loading }
            }
            
            // 1. Wait for Supabase to initialize
            try { 
                auth.awaitInitialization() 
                if (auth.sessionStatus.value is SessionStatus.LoadingFromStorage) {
                    kotlinx.coroutines.withTimeoutOrNull(5000) {
                        auth.sessionStatus.filter { it !is SessionStatus.LoadingFromStorage }.first()
                    }
                }
            } catch (_: Exception) {}

            // 2. Resolve User ID with Rescue 2.0
            var userId = auth.currentSessionOrNull()?.user?.id
            
            // If identity missing, try rescue from local storage
            if (userId == null) {
                try {
                    val manualSession = sessionManager.loadSession()
                    if (manualSession != null) {
                        auth.importSession(manualSession)
                        userId = manualSession.user?.id ?: auth.currentUserOrNull()?.id
                    }
                } catch (_: Exception) {}
            }

            // If still null, check authenticated stream directly (emergency bypass)
            if (userId == null) {
                val status = auth.sessionStatus.value
                if (status is SessionStatus.Authenticated) {
                    userId = status.session.user?.id
                }
            }

            if (userId == null) {
                _uiState.update { DashboardUiState.Error("Session Expired: Please login again.") }
                return@launch
            }

            // 3. Robust Profile Fetch with Retry Logic
            var profileResult: Result<UserProfile>? = null
            var lastError: String = "Failed to load profile"
            
            // Try fetching up to 3 times to handle temporary network blips or cold start delays
            for (attempt in 1..3) {
                profileResult = profileRepository.getCurrentUserProfile(userId)
                if (profileResult.isSuccess) break
                
                val error = profileResult.exceptionOrNull()?.message ?: ""
                lastError = if (error.contains("network", ignoreCase = true)) "Network Error: Check your connection" else "Failed to load profile (Attempt $attempt)"
                
                if (attempt < 3) delay(1000L * attempt) // Exponential backoff
            }

            if (profileResult == null || profileResult.isFailure) {
                _uiState.update { DashboardUiState.Error(lastError) }
                return@launch
            }
            val userProfile = profileResult.getOrThrow()

            // 4. Load Data Blocks
            // Fetch provider profile to get provider ID
            val providerResult = profileRepository.getProviderProfile(userId)
            if (providerResult.isFailure) {
                val exception = providerResult.exceptionOrNull()
                if (exception?.message == "PROVIDER_NOT_FOUND") {
                    _uiState.update { DashboardUiState.ProfileIncomplete }
                } else {
                    _uiState.update { DashboardUiState.Error("Failed to load provider details") }
                }
                return@launch
            }
            val providerProfile = providerResult.getOrThrow()
            
            val appointmentsResult = appointmentRepository.getAppointmentsForProvider(providerProfile.id)

            if (appointmentsResult.isSuccess) {
                val today = java.time.LocalDate.now()
                val allAppointments = appointmentsResult.getOrThrow()
                
                val currencyCode = userProfile.preferredCurrency ?: "INR"
                val currencySymbol = try {
                    java.util.Currency.getInstance(currencyCode).symbol
                } catch (e: Exception) {
                    "₹"
                }

                val pendingAppointments = allAppointments.filter { it.status == "pending" }

                // All upcoming = confirmed/approved/pending, today or future
                val allUpcomingAppointments = allAppointments
                    .filter { it.status.lowercase() in listOf("confirmed", "approved", "pending") }
                    .filter {
                        runCatching { java.time.LocalDate.parse(it.appointmentDate) >= today }.getOrElse { true }
                    }
                    .sortedWith(compareBy({ it.appointmentDate }, { it.startTime }))

                val todayStr = today.toString()
                val todayAppointments = allUpcomingAppointments
                    .filter { it.status.lowercase() != "pending" }
                    .filter { it.appointmentDate == todayStr }

                val pendingUserIds = pendingAppointments.map { it.userId }.distinct()
                val pendingProfilesResult = profileRepository.getProfilesByIds(pendingUserIds)
                val pendingProfilesMap = if (pendingProfilesResult.isSuccess) {
                    pendingProfilesResult.getOrThrow().associateBy { it.userId }
                } else {
                    emptyMap()
                }

                // Fetch patient names for all upcoming + today
                val patientIds = (allUpcomingAppointments.map { it.userId } + todayAppointments.map { it.userId } + pendingUserIds).distinct()
                val patientProfilesResult = profileRepository.getProfilesByIds(patientIds)
                val patientNamesMap = if (patientProfilesResult.isSuccess) {
                    patientProfilesResult.getOrThrow().associate { it.userId to it.fullName }
                } else {
                    emptyMap()
                }
                // Fetch patient roles from user_roles table
                val patientRolesMap: Map<String, String> = try {
                    if (patientIds.isNotEmpty()) {
                        postgrest["user_roles"].select {
                            filter { isIn("user_id", patientIds) }
                        }.decodeList<UserRoleRow>().associate { it.user_id to it.role }
                    } else emptyMap()
                } catch (_: Exception) { emptyMap() }

                val totalAppointments = allAppointments.size
                val pendingRequests = allAppointments.count { it.status == "pending" || it.status == "rescheduling" }
                val newPatients = allAppointments.map { it.userId }.distinct().size
                
                // Estimate revenue based on consultation fees
                val totalRevenue = allAppointments
                    .filter { it.status == "completed" || it.status == "confirmed" }
                    .sumOf { appt -> 
                        if (appt.isVideoConsultation == true) providerProfile.videoConsultationFee ?: providerProfile.consultationFee
                        else providerProfile.consultationFee 
                    }

                    _uiState.update {
                        DashboardUiState.Success(
                            userProfile = userProfile,
                            totalAppointments = totalAppointments,
                            newPatients = newPatients,
                            totalRevenue = totalRevenue,
                            pendingRequests = pendingRequests,
                            todayAppointments = todayAppointments,
                            allUpcomingAppointments = allUpcomingAppointments,
                            pendingAppointments = pendingAppointments,
                            pendingUserProfiles = pendingProfilesMap,
                            patientNames = patientNamesMap,
                            patientRoles = patientRolesMap,
                            userInitials = com.bms.app.domain.util.NameUtils.getInitials(userProfile.fullName),
                            currencySymbol = currencySymbol,
                            rescheduleRequests = allAppointments.filter { it.status == "rescheduling" },
                            isOnline = providerProfile.isActive,
                            providerProfileId = providerProfile.id
                        )
                    }
            } else {
                _uiState.update { DashboardUiState.Error("Connectivity Issue: Failed to load appointments") }
            }

            // Start notification listener for Provider
            startNotificationsListener(userId)
        }
    }

    private fun startNotificationsListener(userId: String) {
        viewModelScope.launch {
            notificationRepository.getNotificationsFlow(userId).collect { list ->
                // Show Android system notifications only for NEW, RECENT, UNREAD contact_message items.
                // Grouping by sender name ensures the notification shade shows at most 1 card per sender.
                val notificationsToShow = list.filter { notification ->
                    notification.type == "contact_message"
                            && !notification.isRead
                            && notification.id.isNotBlank()
                            // 1. Skip if already shown in this app session
                            && !notificationRepository.hasBeenSeen(notification.id)
                            // 2. Skip if older than 24 hours (prevents backlog re-surfacing)
                            && (notificationRepository as? com.bms.app.data.repository.NotificationRepositoryImpl)
                                ?.isRecentNotification(notification.createdAt) != false
                }
                for (notification in notificationsToShow) {
                    notificationRepository.markAsSeen(notification.id)
                    val senderGroupKey = notification.title.removePrefix("💬 ").trim().lowercase()
                    NotificationHelper.showChatNotification(
                        context = application,
                        senderName = notification.title,
                        messagePreview = notification.message,
                        senderId = senderGroupKey
                    )
                }
            }
        }
    }

    fun confirmAppointment(appointmentId: String) {
        val currentState = _uiState.value
        if (currentState !is DashboardUiState.Success) return
        
        viewModelScope.launch {
            _uiState.update { currentState.copy(isActionLoading = appointmentId) }
            val result = appointmentRepository.confirmAppointment(appointmentId)
            if (result.isSuccess) {
                loadDashboard(isRefresh = true)
            } else {
                val errorMsg = result.exceptionOrNull()?.message ?: "Unknown error"
                val displayMsg = if (errorMsg.contains("{")) "Database Update Failed (Check Permissions)" else errorMsg.take(50)
                _uiState.update { currentState.copy(
                    isActionLoading = null,
                    errorMessage = "Failed to confirm: $displayMsg"
                ) }
            }
        }
    }

    fun rejectAppointment(appointmentId: String, reason: String = "Declined by provider") {
        val currentState = _uiState.value
        if (currentState !is DashboardUiState.Success) return
        
        viewModelScope.launch {
            _uiState.update { currentState.copy(isActionLoading = appointmentId) }
            val result = appointmentRepository.rejectAppointment(appointmentId, reason)
            if (result.isSuccess) {
                loadDashboard(isRefresh = true)
            } else {
                val errorMsg = result.exceptionOrNull()?.message ?: "Unknown error"
                val displayMsg = if (errorMsg.contains("{")) "Database Update Failed (Check Permissions)" else errorMsg.take(50)
                _uiState.update { currentState.copy(
                    isActionLoading = null,
                    errorMessage = "Failed to decline: $displayMsg"
                ) }
            }
        }
    }

    fun suggestReschedule(appointmentId: String, customMessage: String) {
        val reason = "Reschedule Requested: $customMessage"
        rejectAppointment(appointmentId, reason)
    }

    fun acceptReschedule(appointmentId: String) {
        val currentState = _uiState.value
        if (currentState !is DashboardUiState.Success) return
        
        viewModelScope.launch {
            _uiState.update { currentState.copy(isActionLoading = appointmentId) }
            val result = appointmentRepository.acceptReschedule(appointmentId)
            if (result.isSuccess) {
                loadDashboard(isRefresh = true)
            } else {
                _uiState.update { currentState.copy(
                    isActionLoading = null,
                    errorMessage = "Failed to accept: ${result.exceptionOrNull()?.message}"
                ) }
            }
        }
    }

    fun declineReschedule(appointmentId: String) {
        val currentState = _uiState.value
        if (currentState !is DashboardUiState.Success) return
        
        viewModelScope.launch {
            _uiState.update { currentState.copy(isActionLoading = appointmentId) }
            val result = appointmentRepository.declineReschedule(appointmentId)
            if (result.isSuccess) {
                loadDashboard(isRefresh = true)
            } else {
                _uiState.update { currentState.copy(
                    isActionLoading = null,
                    errorMessage = "Failed to decline: ${result.exceptionOrNull()?.message}"
                ) }
            }
        }
    }

    fun clearError() {
        val currentState = _uiState.value
        if (currentState is DashboardUiState.Success) {
            _uiState.update { currentState.copy(errorMessage = null) }
        }
    }

    /**
     * Toggles the provider's Online/Offline status.
     * Uses optimistic update: the UI reflects the change immediately, and the database
     * is updated asynchronously. On failure, state is reverted.
     */
    fun toggleStatus(active: Boolean) {
        val currentState = _uiState.value as? DashboardUiState.Success ?: return

        // 1. Optimistic update — UI responds instantly
        _uiState.update { currentState.copy(isOnline = active, isStatusLoading = true) }

        viewModelScope.launch {
            try {
                // Fetch the current provider profile to do a safe upsert
                val userId = auth.currentSessionOrNull()?.user?.id ?: return@launch
                val profileResult = profileRepository.getProviderProfile(userId)
                val providerProfile = profileResult.getOrNull() ?: return@launch

                // 2. Persist to database
                val updatedProfile = providerProfile.copy(isActive = active)
                val result = profileRepository.updateProviderProfile(updatedProfile)

                if (result.isSuccess) {
                    // 3. Confirm state, clear loading.
                    // Small delay to avoid reading stale data from Supabase replication lag
                    delay(800)
                    _uiState.update { state ->
                        if (state is DashboardUiState.Success) {
                            state.copy(isOnline = active, isStatusLoading = false)
                        } else state
                    }
                } else {
                    // 4. Revert on failure
                    _uiState.update { state ->
                        if (state is DashboardUiState.Success) {
                            state.copy(isOnline = !active, isStatusLoading = false,
                                errorMessage = "Failed to update status. Try again.")
                        } else state
                    }
                }
            } catch (e: Exception) {
                // 5. Revert on exception
                _uiState.update { state ->
                    if (state is DashboardUiState.Success) {
                        state.copy(isOnline = !active, isStatusLoading = false,
                            errorMessage = "Network error: ${e.message?.take(40)}")
                    } else state
                }
            }
        }
    }
}
