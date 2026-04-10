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

sealed class ProfessionalInfoUiState {
    object Loading : ProfessionalInfoUiState()
    data class Success(val profile: ProviderProfile) : ProfessionalInfoUiState()
    data class Error(val message: String) : ProfessionalInfoUiState()
}

@HiltViewModel
class ProfessionalInfoViewModel @Inject constructor(
    private val repository: ProfileRepository,
    private val auth: Auth
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProfessionalInfoUiState>(ProfessionalInfoUiState.Loading)
    val uiState: StateFlow<ProfessionalInfoUiState> = _uiState.asStateFlow()

    init {
        loadProfile()
    }

    fun loadProfile() {
        viewModelScope.launch {
            _uiState.update { ProfessionalInfoUiState.Loading }
            val userId = auth.currentSessionOrNull()?.user?.id
            if (userId == null) {
                _uiState.update { ProfessionalInfoUiState.Error("Not authenticated") }
                return@launch
            }
            
            repository.getProviderProfile(userId).onSuccess { profile ->
                _uiState.update { ProfessionalInfoUiState.Success(profile) }
            }.onFailure { error ->
                _uiState.update { ProfessionalInfoUiState.Error(error.localizedMessage ?: "Unknown API Error") }
            }
        }
    }

    fun updateProfile(updatedProfile: ProviderProfile) {
        viewModelScope.launch {
            repository.updateProviderProfile(updatedProfile)
            loadProfile()
        }
    }
}
