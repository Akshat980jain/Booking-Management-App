package com.bms.app.ui.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bms.app.domain.model.ProviderProfile
import com.bms.app.domain.model.Review
import com.bms.app.domain.model.UserProfile
import com.bms.app.domain.repository.AppointmentRepository
import com.bms.app.domain.repository.ProfileRepository
import com.bms.app.domain.repository.ReviewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ProviderDetailUiState {
    object Loading : ProviderDetailUiState()
    data class Success(
        val provider: UserProfile,
        val profile: ProviderProfile,
        val reviews: List<Review>,
        val reviewerProfiles: Map<String, UserProfile>,
        val patientCount: Int
    ) : ProviderDetailUiState()
    data class Error(val message: String) : ProviderDetailUiState()
}

@HiltViewModel
class ProviderDetailViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val reviewRepository: ReviewRepository,
    private val appointmentRepository: AppointmentRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProviderDetailUiState>(ProviderDetailUiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun loadProviderDetail(providerUserId: String) {
        viewModelScope.launch {
            _uiState.value = ProviderDetailUiState.Loading
            try {
                // 1. Fetch Provider User Profile
                val userResult = profileRepository.getProfileById(providerUserId)
                val user = userResult.getOrThrow()

                // 2. Fetch Provider Details (bio, experience, etc.)
                val profileResult = profileRepository.getProviderProfile(providerUserId)
                val profile = profileResult.getOrThrow()

                // 3. Fetch Reviews
                val reviewsResult = reviewRepository.getReviewsForProvider(profile.id)
                val reviews = reviewsResult.getOrThrow()

                // 4. Fetch Profiles of reviewers
                val reviewerIds = reviews.map { it.userId }.distinct()
                val reviewerProfiles = if (reviewerIds.isNotEmpty()) {
                    profileRepository.getProfilesByIds(reviewerIds).getOrDefault(emptyList())
                } else emptyList()
                
                val reviewerMap = reviewerProfiles.associateBy { it.userId }

                // 5. Fetch Appointment Count (Patients)
                val appointmentsResult = appointmentRepository.getAppointmentsForProvider(profile.id)
                val patientCount = appointmentsResult.getOrDefault(emptyList()).size

                _uiState.value = ProviderDetailUiState.Success(
                    provider = user,
                    profile = profile,
                    reviews = reviews,
                    reviewerProfiles = reviewerMap,
                    patientCount = patientCount
                )
            } catch (e: Exception) {
                _uiState.value = ProviderDetailUiState.Error(e.message ?: "Failed to load provider details")
            }
        }
    }

    fun toggleFavorite(providerId: String) {
        // Implementation for toggling favorite would go here, 
        // likely reusing logic from ProfileRepository
    }
}
