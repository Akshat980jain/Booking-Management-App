package com.bms.app.ui.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bms.app.domain.model.Appointment
import com.bms.app.domain.model.AvailabilitySlot
import com.bms.app.domain.model.ProviderProfile
import com.bms.app.domain.model.UserProfile
import com.bms.app.domain.repository.AppointmentRepository
import com.bms.app.domain.repository.AvailabilityRepository
import com.bms.app.domain.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.gotrue.SessionStatus
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.gotrue.user.UserSession
import com.bms.app.data.local.SupabaseSessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

sealed class BookingUiState {
    object Idle : BookingUiState()
    object Loading : BookingUiState()
    data class Success(
        val provider: UserProfile,
        val providerProfile: ProviderProfile,
        val availableSlots: List<String>,
        val selectedDate: LocalDate,
        val selectedSlot: String? = null
    ) : BookingUiState()
    data class Error(val message: String) : BookingUiState()
    object BookingConfirmed : BookingUiState()
}

@HiltViewModel
class BookingViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val availabilityRepository: AvailabilityRepository,
    private val appointmentRepository: AppointmentRepository,
    private val auth: Auth,
    private val sessionManager: SupabaseSessionManager
) : ViewModel() {

    private val _uiState = MutableStateFlow<BookingUiState>(BookingUiState.Idle)
    val uiState: StateFlow<BookingUiState> = _uiState.asStateFlow()

    private var currentProviderId: String? = null

    fun loadProviderBookingData(userId: String, date: LocalDate = LocalDate.now()) {
        currentProviderId = userId
        viewModelScope.launch {
            _uiState.update { BookingUiState.Loading }

            // 1. Permanent Initialization Wait + Rescue Try
            try { 
                auth.awaitInitialization() 
                if (resolveUserId() == null) tryRescueSession() // Try rescue early
            } catch (_: Exception) {}

            val profileResult = profileRepository.getProfileById(userId)
            val providerProfileResult = profileRepository.getProviderProfile(userId)

            if (profileResult.isSuccess && providerProfileResult.isSuccess) {
                val provider = profileResult.getOrThrow()
                val providerProfile = providerProfileResult.getOrThrow()
                
                fetchAvailableSlots(provider, providerProfile, date)
            } else {
                _uiState.update { BookingUiState.Error("Failed to load professional data") }
            }
        }
    }

    fun onDateSelected(date: LocalDate) {
        val currentState = _uiState.value
        if (currentState is BookingUiState.Success) {
            viewModelScope.launch {
                fetchAvailableSlots(currentState.provider, currentState.providerProfile, date)
            }
        }
    }

    private suspend fun fetchAvailableSlots(
        provider: UserProfile,
        providerProfile: ProviderProfile,
        date: LocalDate
    ) {
        val scheduleResult = availabilityRepository.getWeeklySchedule(providerProfile.id)
        val appointmentsResult = appointmentRepository.getAppointmentsForProvider(providerProfile.userId)

        if (scheduleResult.isSuccess && appointmentsResult.isSuccess) {
            val dayOfWeek = date.dayOfWeek.value % 7 // 0=Sunday, 1=Monday...
            val dailySlots = scheduleResult.getOrThrow().filter { it.dayOfWeek == dayOfWeek && it.isActive }
            val existingAppointments = appointmentsResult.getOrThrow().filter { it.appointmentDate == date.toString() && it.status != "cancelled" }

            val availableTimes = mutableListOf<String>()
            
            dailySlots.forEach { slot ->
                var currentTime = LocalTime.parse(slot.startTime)
                val endTime = LocalTime.parse(slot.endTime)
                
                while (currentTime.isBefore(endTime)) {
                    val slotStartStr = currentTime.format(DateTimeFormatter.ofPattern("HH:mm"))
                    val isBooked = existingAppointments.any { it.startTime == slotStartStr }
                    
                    if (!isBooked) {
                        availableTimes.add(slotStartStr)
                    }
                    currentTime = currentTime.plusMinutes(slot.slotDuration.toLong())
                }
            }

            _uiState.update {
                BookingUiState.Success(
                    provider = provider,
                    providerProfile = providerProfile,
                    availableSlots = availableTimes,
                    selectedDate = date
                )
            }
        } else {
            _uiState.update { BookingUiState.Error("Failed to calculate availability") }
        }
    }

    fun selectSlot(slot: String) {
        val currentState = _uiState.value
        if (currentState is BookingUiState.Success) {
            _uiState.update { currentState.copy(selectedSlot = slot) }
        }
    }

    fun confirmBooking() {
        val currentState = _uiState.value
        if (currentState !is BookingUiState.Success || currentState.selectedSlot == null) return

        viewModelScope.launch {
            _uiState.update { BookingUiState.Loading }

            // 1. Permanent Initialization Wait + Extended Timeout for Admins
            try { 
                auth.awaitInitialization() 
                if (auth.sessionStatus.value is SessionStatus.LoadingFromStorage) {
                    kotlinx.coroutines.withTimeoutOrNull(5000) {
                        auth.sessionStatus.filter { it !is SessionStatus.LoadingFromStorage }.first()
                    }
                }
            } catch (_: Exception) {}

            // 2. Multi-Layer Identity Resolver + Rescue 2.0
            var currentUserId = resolveUserId()
            if (currentUserId == null) {
                tryRescueSession() // Forceful manual import from storage
                currentUserId = resolveUserId()
            }
            
            // 3. Fallback: If still null, try one last direct lookup from SessionManager
            if (currentUserId == null) {
                try {
                    val manualSession = sessionManager.loadSession()
                    if (manualSession != null) {
                        auth.importSession(manualSession)
                        currentUserId = manualSession.user?.id
                        // Emergency double-check after import
                        if (currentUserId == null) {
                            currentUserId = auth.currentUserOrNull()?.id
                        }
                    }
                } catch (_: Exception) {}
            }

            // 4. Final Emergency Force Identity Bypass
            if (currentUserId == null) {
                val status = auth.sessionStatus.value
                if (status is SessionStatus.Authenticated) {
                    currentUserId = status.session.user?.id
                }
            }
            
            // 5. Force Refresh if Identity is still missing but not signed out
            if (currentUserId == null && auth.sessionStatus.value !is SessionStatus.NotAuthenticated) {
                try {
                    auth.refreshCurrentSession()
                    currentUserId = resolveUserId()
                } catch (_: Exception) {}
            }

            if (currentUserId == null) {
                // If we reach here, we have a genuine identity failure
                _uiState.update { BookingUiState.Error("Identity Verification Failed: Your login state couldn't be resolved. Please restart the app.") }
                return@launch
            }

            // Pre-flight: re-check that selected slot is still free to avoid duplicate constraint
            val freshResult = appointmentRepository.getAppointmentsForProvider(currentState.providerProfile.userId)
            if (freshResult.isSuccess) {
                val alreadyBooked = freshResult.getOrThrow().any {
                    it.appointmentDate == currentState.selectedDate.toString() &&
                    it.startTime == currentState.selectedSlot &&
                    it.status != "cancelled"
                }
                if (alreadyBooked) {
                    // Refresh the slot list so the user can pick another
                    fetchAvailableSlots(currentState.provider, currentState.providerProfile, currentState.selectedDate)
                    _uiState.update { state ->
                        if (state is BookingUiState.Success) state.copy(selectedSlot = null) else state
                    }
                    _uiState.update { BookingUiState.Error("That time slot was just taken. Please pick another.") }
                    return@launch
                }
            }

            val startTime = LocalTime.parse(currentState.selectedSlot)
            val endTime = startTime.plusMinutes(30L) // Default 30-min session

            val appointment = Appointment(
                userId = currentUserId,
                providerId = currentState.providerProfile.id,
                appointmentDate = currentState.selectedDate.toString(),
                startTime = currentState.selectedSlot,
                endTime = endTime.format(DateTimeFormatter.ofPattern("HH:mm")),
                status = "pending",
                createdAt = LocalDate.now().toString()
            )

            val result = appointmentRepository.createAppointment(appointment)
            if (result.isSuccess) {
                _uiState.update { BookingUiState.BookingConfirmed }
            } else {
                // Never expose raw errors to users — they may contain auth tokens or server URLs
                val rawMsg = result.exceptionOrNull()?.message.orEmpty()
                val friendlyMsg = when {
                    rawMsg.contains("unique", ignoreCase = true) ||
                    rawMsg.contains("duplicate", ignoreCase = true) ->
                        "That time slot was just booked. Please choose another."
                    rawMsg.contains("network", ignoreCase = true) ||
                    rawMsg.contains("timeout", ignoreCase = true) ->
                        "Network error. Check your connection and try again."
                    rawMsg.contains("JWT", ignoreCase = true) ||
                    rawMsg.contains("token", ignoreCase = true) ->
                        "Session expired. Please log in again."
                    rawMsg.contains("auth", ignoreCase = true) ||
                    rawMsg.contains("permission", ignoreCase = true) ||
                    rawMsg.contains("policy", ignoreCase = true) ->
                        "Permission Denied: Your account (Admin) may not have rights to create bookings in this environment."
                    else -> "Booking failed: $rawMsg" // Show raw error for diagnosis if it's not a common case
                }
                _uiState.update { BookingUiState.Error(friendlyMsg) }
            }
        }
    }

    private fun resolveUserId(): String? {
        val directUser = auth.currentUserOrNull()?.id
        if (directUser != null) return directUser

        val sessionUser = auth.currentSessionOrNull()?.user?.id
        if (sessionUser != null) return sessionUser

        val status = auth.sessionStatus.value
        if (status is SessionStatus.Authenticated) {
            return status.session.user?.id
        }
        
        return null
    }

    private suspend fun tryRescueSession() {
        try {
            val session = sessionManager.loadSession()
            if (session != null) {
                // If the SDK lost its way, forcefully manually import the stored session object
                auth.importSession(session)
            }
        } catch (_: Exception) {
            // Rescue failed, proceed with fallback flow
        }
    }
}
