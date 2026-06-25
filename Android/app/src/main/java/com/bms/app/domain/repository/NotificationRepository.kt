package com.bms.app.domain.repository

import com.bms.app.domain.model.Notification
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {
    fun getNotificationsFlow(userId: String): Flow<List<Notification>>
    suspend fun markAsRead(notificationId: String): Result<Unit>
    suspend fun getUnreadCount(userId: String): Result<Int>

    /** Returns true if this notification has already been shown as a system notification
     *  during the current app session. Thread-safe. */
    fun hasBeenSeen(notificationId: String): Boolean

    /** Marks a notification as shown for this session. Thread-safe. */
    fun markAsSeen(notificationId: String)

    /** Returns true if the notification was created within the last 24 hours. */
    fun isRecentNotification(createdAt: String): Boolean
}
