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
        val selectedSlot: String? = null,
        val isVideoSelected: Boolean = false,
        val selectedPaymentMethod: String = "at_clinic", // "online" or "at_clinic"
        val bookingNote: String = "",
        val isFeeWaived: Boolean = false,
        val waivedAmount: Int = 0
    ) : BookingUiState()
    data class ProcessingPayment(
        val amount: Int,
        val method: String // "upi", "card", etc.
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
    private var waivedOldAppointment: Appointment? = null

    fun loadProviderBookingData(userId: String, waivedBy: String? = null, date: LocalDate = LocalDate.now()) {
        currentProviderId = userId
        viewModelScope.launch {
            _uiState.update { BookingUiState.Loading }

            // 1. Permanent Initialization Wait + Rescue Try
            try { 
                auth.awaitInitialization() 
                if (resolveUserId() == null) tryRescueSession() // Try rescue early
            } catch (_: Exception) {}

            var isWaived = false
            var wAmount = 0
            if (waivedBy != null) {
                val uid = resolveUserId()
                if (uid != null) {
                    try {
                        val appts = appointmentRepository.getAppointmentsForUser(uid).getOrNull() ?: emptyList()
                        val oldAppt = appts.find { it.id == waivedBy && it.paymentStatus == "paid" }
                        if (oldAppt != null) {
                            waivedOldAppointment = oldAppt
                            isWaived = true
                            wAmount = oldAppt.paymentAmount ?: 0
                        }
                    } catch (_: Exception) {}
                }
            }

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

            val existingState = _uiState.value as? BookingUiState.Success
            val isVideo = existingState?.isVideoSelected ?: false
            val requireOnline = if (isVideo) providerProfile.requireVideoPayment else providerProfile.requireInPersonPayment
            val paymentMethod = if (requireOnline) "online" else (existingState?.selectedPaymentMethod ?: "at_clinic")

            _uiState.update {
                BookingUiState.Success(
                    provider = provider,
                    providerProfile = providerProfile,
                    availableSlots = availableTimes,
                    selectedDate = date,
                    isVideoSelected = isVideo,
                    selectedPaymentMethod = paymentMethod,
                    bookingNote = existingState?.bookingNote ?: "",
                    isFeeWaived = waivedOldAppointment != null,
                    waivedAmount = waivedOldAppointment?.paymentAmount ?: 0
                    // We purposefully drop selectedSlot because changing the date resets the time selection
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

    fun toggleVideoConsultation(enabled: Boolean) {
        val currentState = _uiState.value
        if (currentState is BookingUiState.Success) {
            val requireOnline = if (enabled) currentState.providerProfile.requireVideoPayment 
                               else currentState.providerProfile.requireInPersonPayment
            
            _uiState.update { 
                currentState.copy(
                    isVideoSelected = enabled,
                    selectedPaymentMethod = if (requireOnline) "online" else currentState.selectedPaymentMethod
                ) 
            }
        }
    }

    fun setPaymentMethod(method: String) {
        val currentState = _uiState.value
        if (currentState is BookingUiState.Success) {
            _uiState.update { currentState.copy(selectedPaymentMethod = method) }
        }
    }

    fun setBookingNote(note: String) {
        val currentState = _uiState.value
        if (currentState is BookingUiState.Success) {
            _uiState.update { currentState.copy(bookingNote = note) }
        }
    }

    private var lastSuccessState: BookingUiState.Success? = null

    fun startBookingFlow() {
        val currentState = _uiState.value as? BookingUiState.Success ?: return
        lastSuccessState = currentState // Save for post-payment
        
        if (currentState.isFeeWaived) {
            confirmBooking()
            return
        }
        
        if (currentState.selectedPaymentMethod == "online") {
            val amount = if (currentState.isVideoSelected)
                currentState.providerProfile.videoConsultationFee ?: currentState.providerProfile.consultationFee
            else currentState.providerProfile.consultationFee
            
            _uiState.update { 
                BookingUiState.ProcessingPayment(
                    amount = amount.toInt(),
                    method = "UPI / Card"
                )
            }
        } else {
            confirmBooking()
        }
    }

    fun confirmBooking() {
        // Retrieve state either from current or saved
        val state = (_uiState.value as? BookingUiState.Success) ?: lastSuccessState ?: return
        if (state.selectedSlot == null) return

        viewModelScope.launch {
            _uiState.update { BookingUiState.Loading }

            // 1. Session Readiness
            try { 
                auth.awaitInitialization() 
                if (auth.sessionStatus.filter { it !is SessionStatus.LoadingFromStorage }.first() is SessionStatus.Authenticated) {
                    // Good to go
                }
            } catch (_: Exception) {}

            // 2. Identity Resolution
            var currentUserId = resolveUserId()
            if (currentUserId == null) {
                tryRescueSession()
                currentUserId = resolveUserId()
            }
            
            val userId = currentUserId ?: resolveUserId()
            if (userId == null) {
                _uiState.update { BookingUiState.Error("Identity Failure: Please log in again.") }
                return@launch
            }

            // 3. Slot Availability Final Check
            val freshResult = appointmentRepository.getAppointmentsForProvider(state.providerProfile.userId)
            if (freshResult.isSuccess) {
                val alreadyBooked = freshResult.getOrThrow().any {
                    it.appointmentDate == state.selectedDate.toString() &&
                    it.startTime == state.selectedSlot &&
                    it.status != "cancelled" && it.status != "rejected"
                }
                if (alreadyBooked) {
                    fetchAvailableSlots(state.provider, state.providerProfile, state.selectedDate)
                    _uiState.update { BookingUiState.Error("That time slot is no longer available.") }
                    return@launch
                }
            }

            val startTime = LocalTime.parse(state.selectedSlot)
            val endTime = startTime.plusMinutes(30L) 

            val currentFee = if (state.isVideoSelected)
                state.providerProfile.videoConsultationFee ?: state.providerProfile.consultationFee
            else state.providerProfile.consultationFee

            val finalPaymentStatus = if (state.isFeeWaived) "paid" 
                else if (state.selectedPaymentMethod == "online") "paid" 
                else "pending"
                
            val finalAmount = if (state.isFeeWaived) state.waivedAmount else currentFee.toInt()

            // 4. Create Appointment
            val appointment = Appointment(
                userId = userId,
                providerId = state.providerProfile.id,
                appointmentDate = state.selectedDate.toString(),
                startTime = state.selectedSlot!!,
                endTime = endTime.format(DateTimeFormatter.ofPattern("HH:mm")),
                status = "pending",
                notes = state.bookingNote.ifBlank { null },
                isVideoConsultation = state.isVideoSelected,
                paymentMethod = if (state.isFeeWaived) "carry_over" else state.selectedPaymentMethod,
                paymentStatus = finalPaymentStatus,
                paymentAmount = finalAmount
            )

            val result = appointmentRepository.createAppointment(appointment)
            if (result.isSuccess) {
                if (state.isFeeWaived && waivedOldAppointment != null) {
                    try {
                        appointmentRepository.cancelAppointment(waivedOldAppointment!!.id, "Resolved: Rescheduled")
                    } catch (e: Exception) {}
                }
                _uiState.update { BookingUiState.BookingConfirmed }
            } else {
                val rawMsg = result.exceptionOrNull()?.message.orEmpty()
                val roleStr = try {
                    if (auth.sessionStatus.value is SessionStatus.Authenticated) {
                        (auth.sessionStatus.value as SessionStatus.Authenticated).session.user?.userMetadata?.get("role")?.toString() ?: "User"
                    } else "User"
                } catch (_: Exception) { "User" }

                val friendlyMsg = when {
                    rawMsg.contains("unique", ignoreCase = true) -> "Slot already taken."
                    rawMsg.contains("permission", ignoreCase = true) -> "Database Permission Denied ($roleStr)."
                    else -> "Booking failed: $rawMsg"
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
