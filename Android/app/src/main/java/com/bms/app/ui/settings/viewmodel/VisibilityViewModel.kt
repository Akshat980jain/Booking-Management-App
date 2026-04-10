package com.bms.app.ui.settings.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bms.app.domain.model.ProviderProfile
import com.bms.app.domain.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.gotrue.Auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class VisibilityUiState {
    object Loading : VisibilityUiState()
    data class Success(val profile: ProviderProfile) : VisibilityUiState()
    data class Error(val message: String) : VisibilityUiState()
}

@HiltViewModel
class VisibilityViewModel @Inject constructor(
    private val repository: ProfileRepository,
    private val auth: Auth
) : ViewModel() {

    private val _uiState = MutableStateFlow<VisibilityUiState>(VisibilityUiState.Loading)
    val uiState: StateFlow<VisibilityUiState> = _uiState.asStateFlow()

    init {
        loadVisibilitySettings()
    }

    fun loadVisibilitySettings() {
        viewModelScope.launch {
            _uiState.update { VisibilityUiState.Loading }
            val userId = auth.currentSessionOrNull()?.user?.id
            if (userId == null) {
                _uiState.update { VisibilityUiState.Error("Not authenticated") }
                return@launch
            }
            
            repository.getProviderProfile(userId).onSuccess { profile ->
                _uiState.update { VisibilityUiState.Success(profile) }
            }.onFailure { error ->
                _uiState.update { VisibilityUiState.Error(error.localizedMessage ?: "Unknown API Error") }
            }
        }
    }

    fun updateVisibilitySettings(updatedProfile: ProviderProfile) {
        viewModelScope.launch {
            repository.updateProviderProfile(updatedProfile)
            loadVisibilitySettings()
        }
    }
}
