package com.bms.app.ui.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bms.app.domain.model.ChatConversation
import com.bms.app.domain.repository.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
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

    private var realtimeJob: Job? = null

    init {
        loadConversations()
    }

    fun loadConversations() {
        _uiState.update { InboxUiState.Loading }

        // 1. One-shot fetch for immediate display
        viewModelScope.launch {
            chatRepository.getConversations()
                .onSuccess { list ->
                    _uiState.update { InboxUiState.Success(list) }
                }
                .onFailure { err ->
                    _uiState.update { InboxUiState.Error(err.message ?: "Failed to load messages") }
                }
        }

        // 2. Start Realtime subscription for live updates
        realtimeJob?.cancel()
        realtimeJob = viewModelScope.launch {
            chatRepository.getConversationsFlow().collect { liveList ->
                _uiState.update { InboxUiState.Success(liveList) }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        realtimeJob?.cancel()
    }
}
