package com.bms.app.data.service

import com.bms.app.util.NotificationHelper
import com.bms.app.domain.repository.NotificationRepository
import io.github.jan.supabase.gotrue.Auth
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class BmsFirebaseMessagingService : FirebaseMessagingService() {

    @Inject
    lateinit var notificationRepository: NotificationRepository

    @Inject
    lateinit var auth: Auth

    private val serviceScope = CoroutineScope(Dispatchers.IO)

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        val title = remoteMessage.notification?.title ?: remoteMessage.data["title"] ?: "New Message"
        val body = remoteMessage.notification?.body ?: remoteMessage.data["body"] ?: ""
        val senderId = remoteMessage.data["sender_id"] ?: ""

        NotificationHelper.showChatNotification(
            context = this,
            senderName = title,
            messagePreview = body,
            senderId = senderId
        )
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        val userId = auth.currentSessionOrNull()?.user?.id
        if (userId != null) {
            serviceScope.launch {
                val deviceName = "${android.os.Build.MANUFACTURER} ${android.os.Build.MODEL}"
                notificationRepository.registerFcmToken(userId, token, deviceName)
            }
        }
    }
}