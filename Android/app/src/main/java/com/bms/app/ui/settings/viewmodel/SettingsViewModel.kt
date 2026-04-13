package com.bms.app.ui.settings.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bms.app.data.local.SessionManager
import com.bms.app.domain.model.ProviderProfile
import com.bms.app.domain.model.UserProfile
import com.bms.app.domain.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.gotrue.Auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ProfileUiState {
    object Loading : ProfileUiState()
    data class Success(
        val userProfile: UserProfile, 
        val providerProfile: ProviderProfile?,
        val userInitials: String = "U"
    ) : ProfileUiState()
    data class Error(val message: String) : ProfileUiState()
}

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repository: ProfileRepository,
    private val sessionManager: SessionManager,
    private val auth: Auth
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Loading)
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    private val _profileStrength = MutableStateFlow(0f)
    val profileStrength: StateFlow<Float> = _profileStrength.asStateFlow()

    init {
        fetchProfile()
    }

    private fun calculateProfileStrength(user: UserProfile, provider: ProviderProfile?) {
        var completed = 0
        var total = 10 // Expanded fields
        
        // User Profile Fields (7 fields)
        if (user.fullName.isNotBlank()) completed++
        if (user.email.isNotBlank()) completed++
        if (user.phone != null) completed++
        if (user.avatarUrl != null) completed++
        if (user.city != null && user.city.isNotBlank()) completed++
        if (user.country != null && user.country.isNotBlank()) completed++
        if (user.twoFaEnabled == true) completed++

        // Preferences (3 fields)
        if (user.preferredLanguage != null) completed++
        if (user.preferredCurrency != null) completed++
        if (user.timezone != null) completed++
        
        // Multiplier for providers
        if (provider != null) {
            total += 4 // Professional fields (Profession, Specialty, Bio, Location)
            if (provider.profession.isNotBlank()) completed++
            if (provider.specialty != null) completed++
            if (provider.bio != null) completed++
            if (provider.location != null) completed++
        }
        
        _profileStrength.value = completed.toFloat() / total.toFloat()
    }

    fun fetchProfile() {
        viewModelScope.launch {
            _uiState.update { ProfileUiState.Loading }
            
            val currentUserId = auth.currentSessionOrNull()?.user?.id ?: ""
            val userResult = repository.getCurrentUserProfile(currentUserId)
            userResult.onSuccess { userProfile ->
                val role = sessionManager.getUserRole()
                val enrichedProfile = userProfile.copy(role = role)
                if (role == "PROVIDER") {
                    val currentUserId = auth.currentSessionOrNull()?.user?.id
                    if (currentUserId != null) {
                        repository.getProviderProfile(currentUserId).onSuccess { providerProfile ->
                            _uiState.update { ProfileUiState.Success(enrichedProfile, providerProfile, com.bms.app.domain.util.NameUtils.getInitials(enrichedProfile.fullName)) }
                            calculateProfileStrength(enrichedProfile, providerProfile)
                        }.onFailure {
                            _uiState.update { ProfileUiState.Success(enrichedProfile, null, com.bms.app.domain.util.NameUtils.getInitials(enrichedProfile.fullName)) }
                            calculateProfileStrength(enrichedProfile, null)
                        }
                    } else {
                        _uiState.update { ProfileUiState.Success(enrichedProfile, null, com.bms.app.domain.util.NameUtils.getInitials(enrichedProfile.fullName)) }
                        calculateProfileStrength(enrichedProfile, null)
                    }
                } else {
                    _uiState.update { ProfileUiState.Success(enrichedProfile, null, com.bms.app.domain.util.NameUtils.getInitials(enrichedProfile.fullName)) }
                    calculateProfileStrength(enrichedProfile, null)
                }
            }.onFailure { error ->
                _uiState.update { ProfileUiState.Error(error.localizedMessage ?: "Unknown API Error") }
            }
        }
    }

    fun toggleTwoFa(enabled: Boolean) {
        val currentUserId = auth.currentSessionOrNull()?.user?.id ?: return
        viewModelScope.launch {
            repository.updateTwoFa(currentUserId, enabled).onSuccess {
                fetchProfile() // Force sub-state refresh
            }
        }
    }

    fun updateOperationalLedger(language: String? = null, currency: String? = null, timeout: Int? = null) {
        val currentUserId = auth.currentSessionOrNull()?.user?.id ?: return
        viewModelScope.launch {
            repository.updateUserSettings(currentUserId, language, currency, timeout).onSuccess {
                fetchProfile()
            }
        }
    }

    fun updateLocation(country: String, city: String) {
        val currentUserId = auth.currentSessionOrNull()?.user?.id ?: return
        val currency = getCurrencyForCountry(country)
        
        viewModelScope.launch {
            repository.updateUserSettings(
                userId = currentUserId,
                country = country,
                city = city,
                currency = currency
            ).onSuccess {
                fetchProfile()
            }
        }
    }

    fun updatePersonalInfo(fullName: String, phone: String) {
        viewModelScope.launch {
            repository.updateUserProfile(fullName, phone).onSuccess {
                fetchProfile()
            }
        }
    }

    fun updateInsurance(provider: String, policyNumber: String, cardUrl: String? = null) {
        viewModelScope.launch {
            val successState = _uiState.value as? ProfileUiState.Success ?: return@launch
            repository.updateUserProfile(
                fullName = successState.userProfile.fullName,
                phone = successState.userProfile.phone,
                insuranceProvider = provider,
                policyNumber = policyNumber,
                insuranceCardUrl = cardUrl
            ).onSuccess {
                fetchProfile()
            }
        }
    }

    private fun getCurrencyForCountry(country: String): String {
        return when (country.trim().lowercase()) {
            "india" -> "INR"
            "united states", "usa", "us" -> "USD"
            "united kingdom", "uk" -> "GBP"
            "germany", "france", "italy", "spain", "europe" -> "EUR"
            "japan" -> "JPY"
            "australia" -> "AUD"
            "canada" -> "CAD"
            "united arab emirates", "uae" -> "AED"
            else -> "USD" // Default to USD
        }
    }
}
