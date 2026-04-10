package com.bms.app.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChatMessage(
    val id: String? = null,
    @SerialName("conversation_id")
    val conversationId: String = "",
    @SerialName("sender_id")
    val senderId: String,
    @SerialName("message")
    val content: String,
    @SerialName("created_at")
    val createdAt: String? = null
)
