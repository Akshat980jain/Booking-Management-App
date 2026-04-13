package com.bms.app.data.repository

import com.bms.app.domain.model.Notification
import com.bms.app.domain.repository.NotificationRepository
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.realtime.Realtime
import io.github.jan.supabase.realtime.channel
import io.github.jan.supabase.realtime.postgresListDataFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.emitAll
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationRepositoryImpl @Inject constructor(
    private val postgrest: Postgrest,
    private val realtime: Realtime
) : NotificationRepository {

    @OptIn(io.github.jan.supabase.annotations.SupabaseExperimental::class)
    override fun getNotificationsFlow(userId: String): Flow<List<Notification>> = flow {
        val channel = realtime.channel("notifications_user_$userId")
        
        // Let the compiler infer generic types from the primaryKey object
        val dataFlow = channel.postgresListDataFlow(
            schema = "public",
            table = "notifications",
            primaryKey = Notification::id
        ).map { list -> 
            list.filter { it.userId == userId }
                .sortedByDescending { it.createdAt } 
        }
        
        emitAll(dataFlow)
    }

    override suspend fun markAsRead(notificationId: String): Result<Unit> {
        return try {
            postgrest["notifications"].update({
                set("is_read", true)
            }) {
                filter {
                    eq("id", notificationId)
                }
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getUnreadCount(userId: String): Result<Int> {
        return try {
            val notifications = postgrest["notifications"].select {
                filter {
                    eq("user_id", userId)
                    eq("is_read", false)
                }
            }.decodeList<Notification>()
            Result.success(notifications.size)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
