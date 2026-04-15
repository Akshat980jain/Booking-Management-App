package com.bms.app.ui.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bms.app.domain.model.ChatMessage
import com.bms.app.domain.model.UserProfile
import com.bms.app.domain.repository.ChatRepository
import com.bms.app.domain.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ChatUiState {
    object Loading : ChatUiState()
    data class Success(
        val recipient: UserProfile,
        val messages: List<ChatMessage>,
        val currentUserId: String // For bubble alignment
    ) : ChatUiState()
    data class Error(val message: String) : ChatUiState()
}

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatRepository: ChatRepository,
    private val profileRepository: ProfileRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<ChatUiState>(ChatUiState.Loading)
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    private var currentOtherUserId: String? = null
    private var realtimeJob: Job? = null

    /**
     * The userId of the user we're currently chatting with.
     * Exposed so other parts of the app can check if the user is actively
     * viewing this conversation (for notification suppression).
     */
    val activeChatPartnerId: StateFlow<String?> = MutableStateFlow<String?>(null)

    fun loadConversation(otherUserId: String) {
        currentOtherUserId = otherUserId
        (activeChatPartnerId as MutableStateFlow).value = otherUserId

        viewModelScope.launch {
            _uiState.update { ChatUiState.Loading }
            
            // 1. Load Recipient Profile
            val profileRes = profileRepository.getProfileById(otherUserId)
            if (profileRes.isFailure) {
                _uiState.update { ChatUiState.Error("Failed to load recipient profile. Ensure you are passing a valid Auth UUID (not an internal PK) and that the user exists in the profiles table.") }
                return@launch
            }
            val recipient = profileRes.getOrThrow()
            val myId = chatRepository.getCurrentUserId() ?: ""

            // 2. Load initial messages (one-shot)
            val messagesRes = chatRepository.getMessages(recipient.userId)
            if (messagesRes.isSuccess) {
                _uiState.update { 
                    ChatUiState.Success(
                        recipient = recipient,
                        messages = messagesRes.getOrThrow(),
                        currentUserId = myId
                    )
                }
            } else {
                val err = messagesRes.exceptionOrNull()?.message ?: "Unknown error"
                _uiState.update { ChatUiState.Error("Failed to load messages: $err") }
                return@launch
            }

            // 3. Start Realtime subscription for live updates
            realtimeJob?.cancel()
            realtimeJob = viewModelScope.launch {
                chatRepository.getMessagesFlow(otherUserId).collect { liveMessages ->
                    if (liveMessages.isNotEmpty()) {
                        _uiState.update { state ->
                            if (state is ChatUiState.Success) {
                                state.copy(messages = liveMessages)
                            } else state
                        }
                    }
                }
            }
        }
    }

    private val _sendError = MutableStateFlow<String?>(null)
    val sendError: StateFlow<String?> = _sendError.asStateFlow()

    fun sendMessage(content: String) {
        val otherId = currentOtherUserId ?: return
        if (content.isBlank()) return

        viewModelScope.launch {
            val result = chatRepository.sendMessage(otherId, content)
            if (result.isSuccess) {
                _sendError.update { null }
                // Realtime subscription will automatically pick up the new message.
                // But also do a one-shot refresh as a safety net in case Realtime
                // hasn't connected yet (e.g. first message in a new conversation).
                val currentState = _uiState.value
                if (currentState is ChatUiState.Success) {
                    val freshMessages = chatRepository.getMessages(otherId)
                    if (freshMessages.isSuccess) {
                        _uiState.update { state ->
                            if (state is ChatUiState.Success) {
                                state.copy(messages = freshMessages.getOrThrow())
                            } else state
                        }
                    }
                    // If this was the first message, start Realtime subscription
                    if (realtimeJob == null || realtimeJob?.isActive != true) {
                        realtimeJob = viewModelScope.launch {
                            chatRepository.getMessagesFlow(otherId).collect { liveMessages ->
                                if (liveMessages.isNotEmpty()) {
                                    _uiState.update { state ->
                                        if (state is ChatUiState.Success) {
                                            state.copy(messages = liveMessages)
                                        } else state
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                val raw = result.exceptionOrNull()?.message ?: "Unknown error"
                // Translate known DB schema errors into a friendly action message
                val friendly = when {
                    raw.contains("user_id") && raw.contains("not-null") ->
                        "⚠️ Chat setup required. Please run the database migration in Supabase (fix_chat_any_user.sql) to enable messaging between all users."
                    raw.contains("violates foreign key") || raw.contains("provider_id") ->
                        "⚠️ This user can't be messaged yet. Run the database migration to enable universal chat."
                    raw.contains("RLS") || raw.contains("row-level security") ->
                        "⚠️ Permission denied. Please run the database migration to update chat policies."
                    else -> "Failed to send: $raw"
                }
                // Show error as a snackbar-style overlay WITHOUT wiping the chat screen
                _sendError.update { friendly }
            }
        }
    }

    fun clearSendError() {
        _sendError.update { null }
    }

    override fun onCleared() {
        super.onCleared()
        (activeChatPartnerId as MutableStateFlow).value = null
        realtimeJob?.cancel()
    }
}
