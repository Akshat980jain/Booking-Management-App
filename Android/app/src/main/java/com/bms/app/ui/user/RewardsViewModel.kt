package com.bms.app.ui.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bms.app.domain.model.LoyaltyTransaction
import com.bms.app.domain.model.UserLoyalty
import com.bms.app.domain.repository.LoyaltyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.gotrue.Auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class RewardsUiState {
    object Loading : RewardsUiState()
    data class Success(
        val loyalty: UserLoyalty,
        val transactions: List<LoyaltyTransaction>
    ) : RewardsUiState()
    data class Error(val message: String) : RewardsUiState()
}

@HiltViewModel
class RewardsViewModel @Inject constructor(
    private val loyaltyRepository: LoyaltyRepository,
    private val auth: Auth
) : ViewModel() {

    private val _uiState = MutableStateFlow<RewardsUiState>(RewardsUiState.Loading)
    val uiState: StateFlow<RewardsUiState> = _uiState.asStateFlow()

    init {
        loadRewards()
    }

    fun loadRewards() {
        val userId = auth.currentSessionOrNull()?.user?.id ?: return
        viewModelScope.launch {
            _uiState.update { RewardsUiState.Loading }
            
            val loyaltyResult = loyaltyRepository.getUserLoyalty(userId)
            val transactionsResult = loyaltyRepository.getLoyaltyTransactions(userId)

            if (loyaltyResult.isSuccess && transactionsResult.isSuccess) {
                _uiState.update {
                    RewardsUiState.Success(
                        loyalty = loyaltyResult.getOrThrow(),
                        transactions = transactionsResult.getOrThrow()
                    )
                }
            } else {
                _uiState.update { RewardsUiState.Error("Failed to load rewards data") }
            }
        }
    }
}
