package com.bms.app.ui.video

import android.app.*
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import co.daily.CallClient
import co.daily.CallClientListener
import com.bms.app.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class VideoCallService : Service() {

    private val binder = LocalBinder()
    
    // The CallClient is managed by the service to ensure it survives 
    // when the app is in the background or during screen sharing.
    private var _callClient: CallClient? = null
    val callClient: CallClient? get() = _callClient

    inner class LocalBinder : Binder() {
        fun getService(): VideoCallService = this@VideoCallService
    }

    override fun onBind(intent: Intent?): IBinder = binder

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        _callClient = CallClient(applicationContext)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val appointmentId = intent?.getStringExtra(EXTRA_APPOINTMENT_ID) ?: "Consultation"
        val notification = createNotification(appointmentId)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            startForeground(
                NOTIFICATION_ID,
                notification,
                android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_CAMERA or
                android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_MICROPHONE
            )
        } else {
            startForeground(NOTIFICATION_ID, notification)
        }
        return START_NOT_STICKY
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Video Consultations",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    private fun createNotification(appointmentId: String): Notification {
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Video Consultation Active")
            .setContentText("You are currently in a video call.")
            .setSmallIcon(R.mipmap.ic_launcher) // Use app icon
            .setOngoing(true)
            .setForegroundServiceBehavior(NotificationCompat.FOREGROUND_SERVICE_IMMEDIATE)
            .build()
    }

    override fun onDestroy() {
        super.onDestroy()
        _callClient?.release()
        _callClient = null
    }

    companion object {
        const val CHANNEL_ID = "video_call_channel"
        const val NOTIFICATION_ID = 1001
        const val EXTRA_APPOINTMENT_ID = "appointment_id"
    }
}
