package com.bms.app.ui.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bms.app.domain.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class SupportUiState {
    object Loading : SupportUiState()
    data class Ready(val adminUserId: String) : SupportUiState()
    data class Error(val message: String) : SupportUiState()
}

@HiltViewModel
class SupportViewModel @Inject constructor(
    private val profileRepository: ProfileRepository
) : ViewModel() {

    private val _state = MutableStateFlow<SupportUiState>(SupportUiState.Loading)
    val state: StateFlow<SupportUiState> = _state.asStateFlow()

    init {
        resolve()
    }

    fun resolve() {
        viewModelScope.launch {
            _state.value = SupportUiState.Loading
            val result = profileRepository.findAdminUserId()
            _state.value = if (result.isSuccess) {
                SupportUiState.Ready(result.getOrThrow())
            } else {
                SupportUiState.Error(
                    result.exceptionOrNull()?.message
                        ?: "Could not find platform support. Please try again."
                )
            }
        }
    }
}
