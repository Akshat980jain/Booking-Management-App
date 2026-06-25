package com.bms.app.data.repository

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
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
import java.time.Instant
import java.time.temporal.ChronoUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val postgrest: Postgrest,
    private val realtime: Realtime
) : NotificationRepository {

    private val prefs = context.getSharedPreferences("notification_prefs", Context.MODE_PRIVATE)

    override fun hasBeenSeen(notificationId: String): Boolean {
        val seenSet = prefs.getStringSet("seen_ids", emptySet()) ?: emptySet()
        return notificationId in seenSet
    }

    override fun markAsSeen(notificationId: String) {
        val seenSet = prefs.getStringSet("seen_ids", emptySet()) ?: emptySet()
        val newSet = seenSet.toMutableSet()
        newSet.add(notificationId)
        prefs.edit().putStringSet("seen_ids", newSet).apply()
    }

    /**
     * Returns true only if the notification was created within the last 24 hours.
     * This is the primary guard against months-old backlog notifications re-appearing
     * when the app is opened after a long period of inactivity.
     */
    override fun isRecentNotification(createdAt: String): Boolean {
        return try {
            val instant = Instant.parse(createdAt)
            val cutoff = Instant.now().minus(24, ChronoUnit.HOURS)
            instant.isAfter(cutoff)
        } catch (_: Exception) {
            // If we can't parse the date, treat it as stale — don't show it.
            false
        }
    }

    @OptIn(io.github.jan.supabase.annotations.SupabaseExperimental::class)
    override fun getNotificationsFlow(userId: String): Flow<List<Notification>> = flow {
        val channel = realtime.channel("notifications_user_$userId")

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
