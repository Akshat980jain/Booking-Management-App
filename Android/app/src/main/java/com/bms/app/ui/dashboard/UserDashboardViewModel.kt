package com.bms.app.ui.dashboard

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bms.app.domain.model.Appointment
import com.bms.app.domain.model.ProviderProfile
import com.bms.app.domain.model.UserProfile
import com.bms.app.domain.repository.AppointmentRepository
import com.bms.app.domain.repository.ProfileRepository
import com.bms.app.data.local.SupabaseSessionManager
import com.bms.app.domain.util.NameUtils
import com.bms.app.util.NotificationHelper
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
        val cancelledBookings: List<Appointment>,
        val paidAppointments: List<Appointment>,
        val allAppointments: List<Appointment>,
        /** Maps providerId (provider_profiles.id) -> ProviderProfile */
        val providerMap: Map<String, ProviderProfile>,
        val userProfileMap: Map<String, UserProfile>,
        val totalBookings: Int,
        val upcomingCount: Int,
        val completedCount: Int,
        val cancelledCount: Int,
        val rescheduleRequests: List<Appointment> = emptyList(),
        /** List of provider ids (provider_profiles.id) */
        val favoriteProviderIds: Set<String> = emptySet(),
        /** List of provider ids currently selected for comparison */
        val selectedComparisonIds: Set<String> = emptySet(),
        /** Filtering */
        val selectedProfession: String? = null,
        val minRating: Float = 0f,
        val showVideoOnly: Boolean = false,
        /** Real-time Notifications */
        val notifications: List<com.bms.app.domain.model.Notification> = emptyList(),
        val unreadNotificationCount: Int = 0
    ) : UserDashboardUiState()

    data class Error(val message: String) : UserDashboardUiState()
}

// ── ViewModel ─────────────────────────────────────────────────────────────────

@HiltViewModel
class UserDashboardViewModel @Inject constructor(
    private val application: Application,
    private val profileRepository: ProfileRepository,
    private val appointmentRepository: AppointmentRepository,
    private val auth: Auth,
    private val sessionManager: SupabaseSessionManager,
    private val notificationRepository: com.bms.app.domain.repository.NotificationRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UserDashboardUiState>(UserDashboardUiState.Loading)
    val uiState: StateFlow<UserDashboardUiState> = _uiState.asStateFlow()

    // Track which notification IDs we've already shown system notifications for
    private val seenNotificationIds = mutableSetOf<String>()

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
            val providerByUserIdMap = providerProfiles.associateBy { it.userId }

            // ── 6. Fetch real names for all involved users ────────────────
            // Collect all unique user IDs we might need (all provider user IDs + any user ids from appointments)
            val allProviderUserIds = providerProfiles.map { it.userId }.toSet()
            val appointmentProviderIds = allAppointments.map { it.providerId }.toSet()
            
            // Some provider_id values in appointments might actually be user IDs
            val idsToFetch = (allProviderUserIds + appointmentProviderIds).toList()

            val userProfileMap: Map<String, UserProfile> = try {
                if (idsToFetch.isEmpty()) emptyMap()
                else profileRepository.getProfilesByIds(idsToFetch)
                    .getOrNull()
                    .orEmpty()
                    .associateBy { it.userId }
            } catch (_: Exception) { emptyMap() }

            // ── 7. Fetch favorites ────────────────────────────────────────
            val favoriteProviderIds = profileRepository.getFavorites(userId).getOrNull()?.toSet() ?: emptySet()

            // ── 8. Derive stats ───────────────────────────────────────────
            val today = java.time.LocalDate.now()

            val upcomingBookings = allAppointments
                .filter { it.status == "confirmed" || it.status == "approved" || it.status == "pending" || it.status == "rescheduling" }
                .sortedWith(compareBy({ it.appointmentDate }, { it.startTime }))

            val cancelledBookings = allAppointments
                .filter { it.status == "cancelled" || it.status == "rejected" }
                .sortedByDescending { it.appointmentDate }

            val pastBookings = allAppointments
                .filter { it.status == "completed" }
                .sortedByDescending { it.appointmentDate }

            val paidBookings = allAppointments
                .filter { it.paymentStatus == "paid" || it.paymentStatus == "waived" }
                .sortedByDescending { it.appointmentDate }

            val nextAppointment = upcomingBookings.firstOrNull {
                runCatching {
                    java.time.LocalDate.parse(it.appointmentDate) >= today
                }.getOrElse { true }
            }

            // Identify active reschedule requests: status is 'rescheduling' AND requester is NOT the current user
            val rescheduleRequests = allAppointments.filter { appt ->
                appt.status == "rescheduling" && appt.rescheduleRequesterId != userId
            }.sortedByDescending { it.appointmentDate }

                // ── 8.5. Proximity Sort (Simulated) ─────────────────────────
                // We sort providers who share the same city as the user to the top
                val sortedProviders = providerProfiles.sortedByDescending { 
                    it.location?.contains(userProfile.city ?: "", ignoreCase = true) == true
                }
                
                // Create a robust map that supports lookup by BOTH profile.id and profile.userId
                val robustProviderMap = mutableMapOf<String, ProviderProfile>()
                sortedProviders.forEach { profile ->
                    robustProviderMap[profile.id] = profile
                    robustProviderMap[profile.userId] = profile
                }

                _uiState.update {
                    UserDashboardUiState.Success(
                        userProfile       = userProfile,
                        userInitials      = NameUtils.getInitials(userProfile.fullName),
                        nextAppointment   = nextAppointment,
                        upcomingBookings  = upcomingBookings,
                        pastBookings      = pastBookings,
                        cancelledBookings = cancelledBookings,
                        paidAppointments  = paidBookings,
                        allAppointments   = allAppointments,
                        providerMap       = robustProviderMap,
                        userProfileMap    = userProfileMap,
                        totalBookings     = allAppointments.size,
                        upcomingCount     = upcomingBookings.size,
                        completedCount    = allAppointments.count { it.status == "completed" },
                        cancelledCount    = cancelledBookings.size,
                        rescheduleRequests = rescheduleRequests,
                        favoriteProviderIds = favoriteProviderIds,
                        selectedComparisonIds = emptySet()
                    )
                }
            
            // ── 9. Start Notifications Listener ──────────────────────────
            startNotificationsListener(userId)
        }
    }

    private fun startNotificationsListener(userId: String) {
        viewModelScope.launch {
            notificationRepository.getNotificationsFlow(userId).collect { list ->
                val previousCount = (_uiState.value as? UserDashboardUiState.Success)
                    ?.unreadNotificationCount ?: 0

                _uiState.update { state ->
                    if (state is UserDashboardUiState.Success) {
                        state.copy(
                            notifications = list,
                            unreadNotificationCount = list.count { !it.isRead }
                        )
                    } else state
                }

                // Show Android system notifications for NEW contact_message items
                val newContactMessages = list.filter { notification ->
                    notification.type == "contact_message"
                            && !notification.isRead
                            && notification.id.isNotBlank()
                            && notification.id !in seenNotificationIds
                }

                for (notification in newContactMessages) {
                    seenNotificationIds.add(notification.id)
                    NotificationHelper.showChatNotification(
                        context = application,
                        senderName = notification.title,
                        messagePreview = notification.message,
                        senderId = notification.id // Use notification ID for unique system notif
                    )
                }
            }
        }
    }

    fun markNotificationAsRead(notificationId: String) {
        viewModelScope.launch {
            notificationRepository.markAsRead(notificationId)
        }
    }

    /** Toggles favorite status for a provider and updates local state. */
    fun toggleFavorite(providerProfileId: String) {
        val currentState = uiState.value
        if (currentState !is UserDashboardUiState.Success) return

        val userId = auth.currentSessionOrNull()?.user?.id ?: return

        viewModelScope.launch {
            val result = profileRepository.toggleFavorite(userId, providerProfileId)
            if (result.isSuccess) {
                val isNowFavorite = result.getOrThrow()
                _uiState.update { state ->
                    if (state is UserDashboardUiState.Success) {
                        val newFavorites = if (isNowFavorite) {
                            state.favoriteProviderIds + providerProfileId
                        } else {
                            state.favoriteProviderIds - providerProfileId
                        }
                        state.copy(favoriteProviderIds = newFavorites)
                    } else state
                }
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

    /** Toggles selection for comparison (max 3) */
    fun toggleComparisonSelection(providerId: String) {
        _uiState.update { state ->
            if (state is UserDashboardUiState.Success) {
                val current = state.selectedComparisonIds
                val newSelection = if (current.contains(providerId)) {
                    current - providerId
                } else {
                    if (current.size >= 3) current else current + providerId
                }
                state.copy(selectedComparisonIds = newSelection)
            } else state
        }
    }

    fun clearComparison() {
        _uiState.update { state ->
            if (state is UserDashboardUiState.Success) {
                state.copy(selectedComparisonIds = emptySet())
            } else state
        }
    }

    fun updateFilters(
        profession: String?,
        minRating: Float,
        showVideoOnly: Boolean
    ) {
        _uiState.update { state ->
            if (state is UserDashboardUiState.Success) {
                state.copy(
                    selectedProfession = profession,
                    minRating = minRating,
                    showVideoOnly = showVideoOnly
                )
            } else state
        }
    }

    fun acceptReschedule(appointmentId: String) {
        viewModelScope.launch {
            appointmentRepository.acceptReschedule(appointmentId)
            loadDashboard()
        }
    }

    fun declineReschedule(appointmentId: String) {
        viewModelScope.launch {
            appointmentRepository.declineReschedule(appointmentId)
            loadDashboard()
        }
    }

    fun requestReschedule(
        appointmentId: String,
        newDate: String,
        newStartTime: String,
        newEndTime: String,
        reason: String
    ) {
        viewModelScope.launch {
            appointmentRepository.rescheduleAppointment(
                appointmentId, newDate, newStartTime, newEndTime, reason
            )
            loadDashboard()
        }
    }
}
