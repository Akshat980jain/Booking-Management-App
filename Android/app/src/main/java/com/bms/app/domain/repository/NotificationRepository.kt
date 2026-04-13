package com.bms.app.domain.repository

import com.bms.app.domain.model.Notification
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {
    fun getNotificationsFlow(userId: String): Flow<List<Notification>>
    suspend fun markAsRead(notificationId: String): Result<Unit>
    suspend fun getUnreadCount(userId: String): Result<Int>
}
