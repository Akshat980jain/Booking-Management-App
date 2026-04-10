package com.bms.app.ui.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bms.app.domain.model.ChatConversation
import com.bms.app.domain.repository.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class InboxUiState {
    object Loading : InboxUiState()
    data class Success(val conversations: List<ChatConversation>) : InboxUiState()
    data class Error(val message: String) : InboxUiState()
}

@HiltViewModel
class InboxViewModel @Inject constructor(
    private val chatRepository: ChatRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<InboxUiState>(InboxUiState.Loading)
    val uiState: StateFlow<InboxUiState> = _uiState.asStateFlow()

    init {
        loadConversations()
    }

    fun loadConversations() {
        _uiState.update { InboxUiState.Loading }
        viewModelScope.launch {
            chatRepository.getConversations()
                .onSuccess { list ->
                    _uiState.update { InboxUiState.Success(list) }
                }
                .onFailure { err ->
                    _uiState.update { InboxUiState.Error(err.message ?: "Failed to load messages") }
                }
        }
    }
}
