package com.bms.app.ui.schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bms.app.domain.model.Appointment
import com.bms.app.domain.model.AvailabilitySlot
import com.bms.app.domain.model.BlockedDate
import com.bms.app.domain.model.UserProfile
import com.bms.app.domain.util.NameUtils
import com.bms.app.domain.repository.AppointmentRepository
import com.bms.app.domain.repository.AvailabilityRepository
import com.bms.app.domain.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.gotrue.Auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class AvailabilityUiState {
    object Loading : AvailabilityUiState()
    data class Success(
        val weeklySchedule: List<AvailabilitySlot>,
        val blockedDates: List<BlockedDate>,
        val appointments: List<com.bms.app.domain.model.Appointment> = emptyList(),
        val users: List<com.bms.app.domain.model.UserProfile> = emptyList(),
        val todayBookingsCount: Int = 0,
        val completedBookingsCount: Int = 0,
        val totalBookingsCount: Int = 0,
        val todayRevenue: Double = 0.0,
        val providerName: String = "",
        val physicalFee: Double = 0.0,
        val videoFee: Double = 0.0,
        val videoEnabled: Boolean = false,
        val currencySymbol: String = "$",
        val providerInitials: String = "P",
        val providerProfile: com.bms.app.domain.model.ProviderProfile? = null
    ) : AvailabilityUiState()
    data class Error(val message: String) : AvailabilityUiState()
}

@HiltViewModel
class AvailabilityViewModel @Inject constructor(
    private val availabilityRepository: AvailabilityRepository,
    private val profileRepository: ProfileRepository,
    private val appointmentRepository: AppointmentRepository,
    private val auth: Auth
) : ViewModel() {

    private val _uiState = MutableStateFlow<AvailabilityUiState>(AvailabilityUiState.Loading)
    val uiState: StateFlow<AvailabilityUiState> = _uiState.asStateFlow()

    private var providerId: String? = null

    init {
        loadAvailability()
    }

    fun loadAvailability() {
        viewModelScope.launch {
            _uiState.update { AvailabilityUiState.Loading }
            
            val userId = auth.currentSessionOrNull()?.user?.id
            if (userId == null) {
                _uiState.update { AvailabilityUiState.Error("Not authenticated") }
                return@launch
            }

            val providerResult = profileRepository.getProviderProfile(userId)
            val userResult = profileRepository.getProfileById(userId)
            
            if (providerResult.isFailure || userResult.isFailure) {
                val msg = if (providerResult.exceptionOrNull()?.message == "PROVIDER_NOT_FOUND") {
                    "Please complete your profile to manage availability"
                } else {
                    "Profile not found"
                }
                _uiState.update { AvailabilityUiState.Error(msg) }
                return@launch
            }
            val provider = providerResult.getOrThrow()
            val user = userResult.getOrThrow()
            
            providerId = provider.id
            val providerName = user.fullName

            val scheduleResult = availabilityRepository.getWeeklySchedule(providerId!!)
            val blockedDatesResult = availabilityRepository.getBlockedDates(providerId!!)
            val appointmentsResult = appointmentRepository.getAppointmentsForProvider(providerId!!)
            val allUsersResult = profileRepository.getAllProfiles()

            if (scheduleResult.isSuccess && blockedDatesResult.isSuccess) {
                val appointments: List<com.bms.app.domain.model.Appointment> = appointmentsResult.getOrElse { emptyList() }
                val users = allUsersResult.getOrElse { emptyList() }
                
                // Calculate today's stats
                val today = java.time.LocalDate.now().toString()
                val todayAppts = appointments.filter { appt -> appt.appointmentDate == today && appt.status.lowercase() != "cancelled" }
                val completedCount = todayAppts.count { it.status.lowercase() == "completed" }
                val totalCount = todayAppts.size
                
                val revenue = todayAppts.filter { appt -> appt.status.lowercase() == "completed" || appt.status.lowercase() == "confirmed" }
                    .sumOf { appt -> 
                        if (appt.isVideoConsultation == true) (provider.videoConsultationFee ?: provider.consultationFee)
                        else provider.consultationFee
                    }

                val currencyCode = user.preferredCurrency ?: "USD"
                val currencySymbol = try {
                    java.util.Currency.getInstance(currencyCode).symbol
                } catch (e: Exception) {
                    "$"
                }

                _uiState.update {
                    AvailabilityUiState.Success(
                        weeklySchedule = scheduleResult.getOrThrow(),
                        blockedDates = blockedDatesResult.getOrThrow(),
                        appointments = appointments.sortedBy { appt -> appt.startTime },
                        users = users,
                        todayBookingsCount = todayAppts.size,
                        completedBookingsCount = completedCount,
                        totalBookingsCount = totalCount,
                        todayRevenue = revenue,
                        providerName = providerName,
                        physicalFee = provider.consultationFee,
                        videoFee = provider.videoConsultationFee ?: 0.0,
                        videoEnabled = provider.videoEnabled,
                        currencySymbol = currencySymbol,
                        providerInitials = NameUtils.getInitials(providerName),
                        providerProfile = provider
                    )
                }
            } else {
                _uiState.update { AvailabilityUiState.Error("Failed to load availability info") }
            }
        }
    }

    fun updateAvailabilitySettings(
        slots: List<AvailabilitySlot>,
        physicalFee: Double,
        videoFee: Double,
        videoEnabled: Boolean
    ) {
        viewModelScope.launch {
            if (providerId == null) return@launch
            
            // 1. Update weekly schedule items
            availabilityRepository.updateWeeklySchedule(slots)
            
            // 2. Update provider profile (fees and video toggle)
            val currentState = _uiState.value
            if (currentState is AvailabilityUiState.Success && currentState.providerProfile != null) {
                val updatedProfile = currentState.providerProfile.copy(
                    consultationFee = physicalFee,
                    videoConsultationFee = videoFee,
                    videoEnabled = videoEnabled
                )
                profileRepository.updateProviderProfile(updatedProfile)
            }
            
            loadAvailability() // reload
        }
    }

    fun addBlockedDate(date: String, reason: String?) {
        viewModelScope.launch {
            if (providerId == null) return@launch
            val blockedDate = BlockedDate(providerId = providerId!!, blockedDate = date, reason = reason)
            availabilityRepository.addBlockedDate(blockedDate)
            loadAvailability()
        }
    }

    fun removeBlockedDate(id: String) {
        viewModelScope.launch {
            availabilityRepository.removeBlockedDate(id)
            loadAvailability()
        }
    }

    fun completeAppointment(id: String) {
        viewModelScope.launch {
            appointmentRepository.completeAppointment(id)
            loadAvailability()
        }
    }
}
