package com.bms.app.domain.repository

import com.bms.app.domain.model.ChatMessage
import com.bms.app.domain.model.ChatConversation

interface ChatRepository {
    suspend fun getMessages(otherUserId: String): Result<List<ChatMessage>>
    suspend fun sendMessage(receiverId: String, content: String): Result<Unit>
    suspend fun getConversations(): Result<List<ChatConversation>>
    fun getCurrentUserId(): String?
}
