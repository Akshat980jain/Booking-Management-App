package com.bms.app

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.bms.app.ui.navigation.BmsNavigation
import com.bms.app.ui.theme.BMSTheme
import com.bms.app.domain.repository.NotificationRepository
import com.bms.app.util.NotificationHelper
import dagger.hilt.android.AndroidEntryPoint
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.gotrue.SessionStatus
import kotlinx.coroutines.launch
import kotlinx.coroutines.Job
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var auth: Auth

    @Inject
    lateinit var notificationRepository: NotificationRepository

    private var notificationListenerJob: Job? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Listen to session changes globally and start/stop the notifications listener
        lifecycleScope.launch {
            auth.sessionStatus.collect { status ->
                when (status) {
                    is SessionStatus.Authenticated -> {
                        val userId = status.session.user?.id
                        if (userId != null) {
                            startGlobalNotificationsListener(userId)
                            registerFcmToken(userId)
                        }
                    }
                    else -> {
                        stopGlobalNotificationsListener()
                    }
                }
            }
        }

        setContent {
            BMSTheme {
                // Launcher for notification permission
                val launcher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestPermission(),
                    onResult = { isGranted ->
                        // Permission result handled
                    }
                )

                // Request permission on launch for Android 13+
                LaunchedEffect(Unit) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        if (ContextCompat.checkSelfPermission(
                                this@MainActivity,
                                Manifest.permission.POST_NOTIFICATIONS
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {
                            launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
                        }
                    }
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BmsNavigation()
                }
            }
        }
    }

    private fun startGlobalNotificationsListener(userId: String) {
        if (notificationListenerJob?.isActive == true) return // Already running

        notificationListenerJob = lifecycleScope.launch {
            notificationRepository.getNotificationsFlow(userId).collect { list ->
                val notificationsToShow = list.filter { notification ->
                    notification.type == "contact_message"
                            && !notification.isRead
                            && notification.id.isNotBlank()
                            && !notificationRepository.hasBeenSeen(notification.id)
                            && notificationRepository.isRecentNotification(notification.createdAt)
                }
                for (notification in notificationsToShow) {
                    notificationRepository.markAsSeen(notification.id)
                    val senderGroupKey = notification.title.removePrefix("💬 ").trim().lowercase()
                    NotificationHelper.showChatNotification(
                        context = applicationContext,
                        senderName = notification.title,
                        messagePreview = notification.message,
                        senderId = senderGroupKey
                    )
                }
            }
        }
    }

    private fun stopGlobalNotificationsListener() {
        notificationListenerJob?.cancel()
        notificationListenerJob = null
    }

    private fun registerFcmToken(userId: String) {
        try {
            com.google.firebase.messaging.FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    android.util.Log.w("MainActivity", "Fetching FCM registration token failed", task.exception)
                    return@addOnCompleteListener
                }
                val token = task.result
                if (token != null) {
                    lifecycleScope.launch {
                        val deviceName = "${android.os.Build.MANUFACTURER} ${android.os.Build.MODEL}"
                        notificationRepository.registerFcmToken(userId, token, deviceName)
                    }
                }
            }
        } catch (e: Exception) {
            android.util.Log.e("MainActivity", "Error registering FCM token", e)
        }
    }
}
