package com.bms.app.ui.config

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bms.app.domain.model.AppConfig
import com.bms.app.domain.repository.ConfigRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ConfigUiState {
    object Loading : ConfigUiState()
    data class Success(val config: AppConfig, val isUpdateRequired: Boolean) : ConfigUiState()
    data class Error(val message: String) : ConfigUiState()
}

@HiltViewModel
class SystemConfigViewModel @Inject constructor(
    private val configRepository: ConfigRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<ConfigUiState>(ConfigUiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun checkVersion(currentVersionName: String) {
        viewModelScope.launch {
            _uiState.value = ConfigUiState.Loading
            configRepository.getAppConfig()
                .onSuccess { config ->
                    val isRequired = isUpdateRequired(currentVersionName, config.minRequiredVersionName)
                    _uiState.value = ConfigUiState.Success(config, isRequired)
                }
                .onFailure {
                    // On failure, we'll assume no update is required to avoid blocking the user 
                    // unnecessarily if Supabase is down or unreachable.
                    _uiState.value = ConfigUiState.Success(AppConfig(), false)
                }
        }
    }

    private fun isUpdateRequired(current: String, minimum: String): Boolean {
        try {
            val currentParts = current.split(".").map { it.toInt() }
            val minParts = minimum.split(".").map { it.toInt() }
            
            val maxLength = maxOf(currentParts.size, minParts.size)
            
            for (i in 0 until maxLength) {
                val currentPart = currentParts.getOrElse(i) { 0 }
                val minPart = minParts.getOrElse(i) { 0 }
                
                if (currentPart < minPart) return true
                if (currentPart > minPart) return false
            }
            return false
        } catch (e: Exception) {
            // Fallback: If version strings are not in expected format, return false to be safe
            return false
        }
    }
}
