package com.bms.app.data.repository

import android.util.Log
import com.bms.app.domain.model.ChatMessage
import com.bms.app.domain.model.ChatConversation
import com.bms.app.domain.model.UserProfile
import com.bms.app.domain.model.UserRoleRow
import com.bms.app.domain.repository.ChatRepository
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.query.Order
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import javax.inject.Inject
import javax.inject.Singleton

@Serializable
private data class ConvRow(
    val id: String,
    val participant_1: String,
    val participant_2: String,
    @SerialName("created_at") val createdAt: String? = null
)

@Serializable
private data class NewConvPayload(
    val participant_1: String,
    val participant_2: String
)

@Singleton
class ChatRepositoryImpl @Inject constructor(
    private val auth: Auth,
    private val postgrest: Postgrest
) : ChatRepository {

    /**
     * Gets or creates a conversation between any two auth users.
     * Uses participant_1 / participant_2 (no provider constraint).
     * Normalises order so (A,B) and (B,A) always map to the same row.
     */
    private suspend fun getOrCreateConversationId(
        userId1: String,
        userId2: String
    ): String {
        // Normalise: smaller UUID is always participant_1
        val (p1, p2) = if (userId1 < userId2) userId1 to userId2 else userId2 to userId1

        // 1. Try to find existing conversation
        val existing = try {
            postgrest["chat_conversations"].select {
                filter {
                    eq("participant_1", p1)
                    eq("participant_2", p2)
                }
            }.decodeList<ConvRow>()
        } catch (e: Exception) {
            // Column might still be old schema — try old user_id / provider_id fallback
            Log.w("ChatRepository", "participant columns not found, trying legacy schema: ${e.message}")
            emptyList()
        }

        if (existing.isNotEmpty()) return existing.first().id

        // 2. Create new conversation
        return try {
            val inserted = postgrest["chat_conversations"]
                .insert(NewConvPayload(p1, p2)) { select() }
                .decodeList<ConvRow>()
            inserted.first().id
        } catch (insertEx: Exception) {
            // Handle race condition: another request may have inserted simultaneously
            Log.w("ChatRepository", "Insert failed (possible race), re-querying: ${insertEx.message}")
            val retry = postgrest["chat_conversations"].select {
                filter {
                    eq("participant_1", p1)
                    eq("participant_2", p2)
                }
            }.decodeList<ConvRow>()
            retry.firstOrNull()?.id
                ?: throw Exception("Could not create or find conversation: ${insertEx.message}")
        }
    }

    override suspend fun getMessages(otherUserId: String): Result<List<ChatMessage>> {
        return try {
            val currentUserId = auth.currentSessionOrNull()?.user?.id
                ?: return Result.failure(Exception("Not authenticated"))

            val (p1, p2) = if (currentUserId < otherUserId) currentUserId to otherUserId
                           else otherUserId to currentUserId

            val conversations = postgrest["chat_conversations"].select {
                filter {
                    eq("participant_1", p1)
                    eq("participant_2", p2)
                }
            }.decodeList<ConvRow>()

            val convId = conversations.firstOrNull()?.id
                ?: return Result.success(emptyList()) // No conversation yet — show empty state

            val messages = postgrest["chat_messages"].select {
                filter { eq("conversation_id", convId) }
                order("created_at", Order.ASCENDING)
            }.decodeList<ChatMessage>()

            Result.success(messages)
        } catch (e: Exception) {
            Log.e("ChatRepository", "Failed to fetch messages", e)
            Result.failure(e)
        }
    }

    override fun getCurrentUserId(): String? {
        return auth.currentSessionOrNull()?.user?.id
    }

    override suspend fun sendMessage(receiverId: String, content: String): Result<Unit> {
        return try {
            val currentUserId = auth.currentSessionOrNull()?.user?.id
                ?: return Result.failure(Exception("Not authenticated"))

            // Works for ANY two users — no provider role required
            val convId = getOrCreateConversationId(currentUserId, receiverId)

            val message = ChatMessage(
                conversationId = convId,
                senderId = currentUserId,
                content = content
            )

            postgrest["chat_messages"].insert(message)
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e("ChatRepository", "Failed to send message", e)
            Result.failure(e)
        }
    }

    override suspend fun getConversations(): Result<List<ChatConversation>> {
        return try {
            val currentUserId = auth.currentSessionOrNull()?.user?.id
                ?: return Result.failure(Exception("Not authenticated"))

            // 1. Fetch conversations where user is participant_1 OR participant_2
            val conversations = postgrest["chat_conversations"].select {
                filter {
                    or {
                        eq("participant_1", currentUserId)
                        eq("participant_2", currentUserId)
                    }
                }
            }.decodeList<ConvRow>()

            if (conversations.isEmpty()) return Result.success(emptyList())

            // 2. Resolve "other" participants and fetch their profiles
            val otherIds = conversations.map { 
                if (it.participant_1 == currentUserId) it.participant_2 else it.participant_1 
            }.distinct()
            
            val profiles = postgrest["profiles"].select {
                filter {
                    isIn("user_id", otherIds)
                }
            }.decodeList<UserProfile>().associateBy { it.userId }

            // 3. Fetch roles from user_roles table
            val roles = postgrest["user_roles"].select {
                filter {
                    isIn("user_id", otherIds)
                }
            }.decodeList<UserRoleRow>().associateBy { it.user_id }

            // 4. Fetch latest messages for each conversation to show snippet
            val lastMessages = postgrest["chat_messages"].select {
                filter {
                    isIn("conversation_id", conversations.map { it.id })
                }
                order("created_at", Order.DESCENDING)
            }.decodeList<ChatMessage>().groupBy { it.conversationId }

            // 5. Map to UI Model
            val mapped = conversations.mapNotNull { conv ->
                val otherId = if (conv.participant_1 == currentUserId) conv.participant_2 else conv.participant_1
                val profile = profiles[otherId] ?: return@mapNotNull null
                val role = roles[otherId]?.role
                val latestMsg = lastMessages[conv.id]?.firstOrNull()

                ChatConversation(
                    conversationId = conv.id,
                    otherParticipantId = otherId,
                    otherParticipantName = profile.fullName,
                    otherParticipantRole = role,
                    otherParticipantAvatar = profile.avatarUrl,
                    lastMessage = latestMsg?.content ?: "No messages yet",
                    lastMessageTime = latestMsg?.createdAt ?: conv.createdAt ?: ""
                )
            }.sortedByDescending { it.lastMessageTime }

            Result.success(mapped)
        } catch (e: Exception) {
            Log.e("ChatRepository", "Failed to fetch conversations", e)
            Result.failure(e)
        }
    }
}
