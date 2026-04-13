package com.bms.app.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bms.app.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AuthUiState(
    val selectedTab: AuthTab = AuthTab.LOGIN,
    val selectedRole: AccessLevel = AccessLevel.ADMIN,
    val fullName: String = "",
    val email: String = "",
    val password: String = "",
    val passwordVisible: Boolean = false,
    val isLoading: Boolean = false,
    val errorMsg: String = ""
)

sealed class AuthEvent {
    data class Success(val role: AccessLevel) : AuthEvent()
    data class Error(val message: String) : AuthEvent()
}

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<AuthEvent>()
    val events: SharedFlow<AuthEvent> = _events.asSharedFlow()

    fun updateTab(tab: AuthTab) {
        _uiState.update { it.copy(selectedTab = tab, errorMsg = "") }
    }

    fun updateRole(role: AccessLevel) {
        _uiState.update { it.copy(selectedRole = role) }
    }

    fun updateFullName(name: String) {
        _uiState.update { it.copy(fullName = name) }
    }

    fun updateEmail(email: String) {
        _uiState.update { it.copy(email = email) }
    }

    fun updatePassword(password: String) {
        _uiState.update { it.copy(password = password) }
    }

    fun togglePasswordVisibility() {
        _uiState.update { it.copy(passwordVisible = !it.passwordVisible) }
    }

    fun authenticate() {
        val state = uiState.value
        
        if (state.email.isBlank()) {
            emitError("Email cannot be empty")
            return
        }
        if (state.password.isBlank()) {
            emitError("Password cannot be empty")
            return
        }

        if (state.selectedTab == AuthTab.SIGNUP && state.fullName.isBlank()) {
            emitError("Full name is required for sign up")
            return
        }

        _uiState.update { it.copy(isLoading = true, errorMsg = "") }

        viewModelScope.launch {
            val result = if (state.selectedTab == AuthTab.LOGIN) {
                authRepository.signIn(state.email, state.password)
            } else {
                authRepository.signUp(state.email, state.password, state.fullName, state.selectedRole)
            }

            result.onSuccess { role ->
                _uiState.update { it.copy(isLoading = false) }
                _events.emit(AuthEvent.Success(role))
            }.onFailure { err ->
                _uiState.update { it.copy(isLoading = false) }
                _events.emit(AuthEvent.Error(err.message ?: "Authentication failed"))
            }
        }
    }

    fun resetPassword(email: String) {
        if (email.isBlank()) {
            emitError("Please enter your email address")
            return
        }
        
        _uiState.update { it.copy(isLoading = true, errorMsg = "") }
        
        viewModelScope.launch {
            authRepository.resetPassword(email)
                .onSuccess {
                    _uiState.update { it.copy(isLoading = false) }
                    _events.emit(AuthEvent.Error("Password reset email sent. Check your inbox.")) // Using Error event for simple toast-like feedback
                }
                .onFailure { err ->
                    _uiState.update { it.copy(isLoading = false) }
                    _events.emit(AuthEvent.Error(err.message ?: "Failed to send reset email"))
                }
        }
    }

    private fun emitError(msg: String) {
        viewModelScope.launch {
            _events.emit(AuthEvent.Error(msg))
        }
    }

}
