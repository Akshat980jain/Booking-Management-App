package com.bms.app.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bms.app.domain.model.Appointment
import com.bms.app.domain.model.ProviderProfile
import com.bms.app.domain.model.UserProfile
import com.bms.app.domain.repository.AppointmentRepository
import com.bms.app.domain.repository.ProfileRepository
import com.bms.app.data.local.SupabaseSessionManager
import com.bms.app.domain.util.NameUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.gotrue.SessionStatus
import io.github.jan.supabase.gotrue.auth
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

// ── UI State ──────────────────────────────────────────────────────────────────

sealed class UserDashboardUiState {
    object Loading : UserDashboardUiState()

    data class Success(
        val userProfile: UserProfile,
        val userInitials: String,
        val nextAppointment: Appointment?,
        val upcomingBookings: List<Appointment>,
        val pastBookings: List<Appointment>,
        /** Maps providerId (provider_profiles.id) -> ProviderProfile */
        val providerMap: Map<String, ProviderProfile>,
        /** Maps userId (profiles.user_id) -> UserProfile — so we can show real names */
        val userProfileMap: Map<String, UserProfile>,
        val totalBookings: Int,
        val upcomingCount: Int,
        val completedCount: Int,
        val cancelledCount: Int
    ) : UserDashboardUiState()

    data class Error(val message: String) : UserDashboardUiState()
}

// ── ViewModel ─────────────────────────────────────────────────────────────────

@HiltViewModel
class UserDashboardViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val appointmentRepository: AppointmentRepository,
    private val auth: Auth,
    private val sessionManager: SupabaseSessionManager
) : ViewModel() {

    private val _uiState = MutableStateFlow<UserDashboardUiState>(UserDashboardUiState.Loading)
    val uiState: StateFlow<UserDashboardUiState> = _uiState.asStateFlow()

    init {
        loadDashboard()
    }

    fun loadDashboard() {
        viewModelScope.launch {
            _uiState.update { UserDashboardUiState.Loading }

            // ── 1. Wait for Supabase session ─────────────────────────────
            try {
                auth.awaitInitialization()
                if (auth.sessionStatus.value is SessionStatus.LoadingFromStorage) {
                    kotlinx.coroutines.withTimeoutOrNull(5_000) {
                        auth.sessionStatus.filter { it !is SessionStatus.LoadingFromStorage }.first()
                    }
                }
            } catch (_: Exception) {}

            // ── 2. Resolve userId (with local session rescue) ─────────────
            var userId = auth.currentSessionOrNull()?.user?.id
            if (userId == null) {
                try {
                    val manualSession = sessionManager.loadSession()
                    if (manualSession != null) {
                        auth.importSession(manualSession)
                        userId = manualSession.user?.id ?: auth.currentUserOrNull()?.id
                    }
                } catch (_: Exception) {}
            }
            if (userId == null) {
                val status = auth.sessionStatus.value
                if (status is SessionStatus.Authenticated) userId = status.session.user?.id
            }
            if (userId == null) {
                _uiState.update { UserDashboardUiState.Error("Session expired. Please log in again.") }
                return@launch
            }

            // ── 3. Fetch user profile (with retry) ────────────────────────
            var profileResult: Result<UserProfile>? = null
            for (attempt in 1..3) {
                profileResult = profileRepository.getCurrentUserProfile(userId)
                if (profileResult.isSuccess) break
                if (attempt < 3) delay(1_000L * attempt)
            }
            val userProfile = profileResult?.getOrNull()
                ?: run {
                    _uiState.update { UserDashboardUiState.Error("Failed to load your profile.") }
                    return@launch
                }

            // ── 4. Fetch all appointments for this user ───────────────────
            val appointmentsResult = appointmentRepository.getAppointmentsForUser(userId)
            val allAppointments = appointmentsResult.getOrNull() ?: emptyList()

            // ── 5. Fetch all provider profiles ────────────────────────────
            val providerProfiles: List<ProviderProfile> = try {
                profileRepository.getAllProviderProfiles().getOrNull() ?: emptyList()
            } catch (_: Exception) { emptyList() }

            // providerMap: provider_profiles.id -> ProviderProfile
            val providerMap = providerProfiles.associateBy { it.id }

            // ── 6. Fetch real names for each provider from profiles table ─
            val providerUserIds = providerProfiles.map { it.userId }.distinct()
            val userProfileMap: Map<String, UserProfile> = try {
                if (providerUserIds.isEmpty()) emptyMap()
                else profileRepository.getProfilesByIds(providerUserIds)
                    .getOrNull()
                    .orEmpty()
                    .associateBy { it.userId }
            } catch (_: Exception) { emptyMap() }

            // ── 7. Derive stats ───────────────────────────────────────────
            val today = java.time.LocalDate.now()

            val upcomingBookings = allAppointments
                .filter { it.status == "confirmed" || it.status == "approved" || it.status == "pending" }
                .sortedWith(compareBy({ it.appointmentDate }, { it.startTime }))

            val pastBookings = allAppointments
                .filter { it.status == "completed" || it.status == "cancelled" || it.status == "rejected" }
                .sortedByDescending { it.appointmentDate }

            val nextAppointment = upcomingBookings.firstOrNull {
                runCatching {
                    java.time.LocalDate.parse(it.appointmentDate) >= today
                }.getOrElse { true }
            }

            _uiState.update {
                UserDashboardUiState.Success(
                    userProfile       = userProfile,
                    userInitials      = NameUtils.getInitials(userProfile.fullName),
                    nextAppointment   = nextAppointment,
                    upcomingBookings  = upcomingBookings,
                    pastBookings      = pastBookings,
                    providerMap       = providerMap,
                    userProfileMap    = userProfileMap,
                    totalBookings     = allAppointments.size,
                    upcomingCount     = upcomingBookings.size,
                    completedCount    = allAppointments.count { it.status == "completed" },
                    cancelledCount    = allAppointments.count { it.status == "cancelled" || it.status == "rejected" }
                )
            }
        }
    }

    /** Cancel an appointment and reload the dashboard. */
    fun cancelBooking(appointmentId: String, reason: String = "Cancelled by user") {
        viewModelScope.launch {
            appointmentRepository.cancelAppointment(appointmentId, reason)
            loadDashboard()
        }
    }
}
