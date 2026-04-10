package com.bms.app.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ChatConversation(
    val conversationId: String,
    val otherParticipantId: String,
    val otherParticipantName: String,
    val otherParticipantRole: String? = null,
    val otherParticipantAvatar: String? = null,
    val lastMessage: String = "",
    val lastMessageTime: String = "",
    val unreadCount: Int = 0
)
