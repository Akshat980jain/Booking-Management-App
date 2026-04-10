package com.bms.app.ui.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bms.app.domain.model.UserProfile
import com.bms.app.domain.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class UserDetailUiState {
    object Loading : UserDetailUiState()
    data class Success(val profile: UserProfile) : UserDetailUiState()
    data class Error(val message: String) : UserDetailUiState()
}

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val profileRepository: ProfileRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UserDetailUiState>(UserDetailUiState.Loading)
    val uiState: StateFlow<UserDetailUiState> = _uiState.asStateFlow()

    fun loadUserDetail(userId: String) {
        viewModelScope.launch {
            _uiState.update { UserDetailUiState.Loading }
            val result = profileRepository.getProfileById(userId)
            if (result.isSuccess) {
                _uiState.update { UserDetailUiState.Success(result.getOrThrow()) }
            } else {
                _uiState.update { UserDetailUiState.Error("Failed to load user profile") }
            }
        }
    }

    fun deactivateUser(userId: String) {
        viewModelScope.launch {
            val result = profileRepository.deactivateUser(userId)
            if (result.isSuccess) {
                loadUserDetail(userId) // Reload to show new status
            }
        }
    }
}
