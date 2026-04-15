package com.bms.app.domain.repository

import com.bms.app.domain.model.ChatMessage
import com.bms.app.domain.model.ChatConversation
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    suspend fun getMessages(otherUserId: String): Result<List<ChatMessage>>
    fun getMessagesFlow(otherUserId: String): Flow<List<ChatMessage>>
    suspend fun sendMessage(receiverId: String, content: String): Result<Unit>
    suspend fun getConversations(): Result<List<ChatConversation>>
    fun getConversationsFlow(): Flow<List<ChatConversation>>
    fun getCurrentUserId(): String?
}
