package com.bms.app.ui.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bms.app.domain.model.Review
import com.bms.app.domain.repository.AuthRepository
import com.bms.app.domain.repository.ReviewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ReviewUiState {
    object Idle : ReviewUiState()
    object Loading : ReviewUiState()
    object Success : ReviewUiState()
    data class Error(val message: String) : ReviewUiState()
}

@HiltViewModel
class ReviewViewModel @Inject constructor(
    private val reviewRepository: ReviewRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<ReviewUiState>(ReviewUiState.Idle)
    val uiState = _uiState.asStateFlow()

    fun submitReview(
        providerId: String,
        appointmentId: String,
        rating: Int,
        text: String
    ) {
        viewModelScope.launch {
            _uiState.value = ReviewUiState.Loading
            try {
                val userId = authRepository.getCurrentUserId() ?: throw Exception("User not authenticated")
                
                val review = Review(
                    userId = userId,
                    providerId = providerId,
                    appointmentId = appointmentId,
                    rating = rating,
                    reviewText = text.ifBlank { null }
                )
                
                val result = reviewRepository.submitReview(review)
                if (result.isSuccess) {
                    _uiState.value = ReviewUiState.Success
                } else {
                    _uiState.value = ReviewUiState.Error(result.exceptionOrNull()?.message ?: "Failed to submit review")
                }
            } catch (e: Exception) {
                _uiState.value = ReviewUiState.Error(e.message ?: "An unexpected error occurred")
            }
        }
    }
}
