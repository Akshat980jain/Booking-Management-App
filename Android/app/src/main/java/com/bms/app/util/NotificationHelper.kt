package com.bms.app.util

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Bitmap
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.Person
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.IconCompat
import com.bms.app.MainActivity
import com.bms.app.R

/**
 * Utility class for Android system-level notifications (status bar, lock screen).
 * Uses MessagingStyle for a rich, WhatsApp-like chat notification experience.
 */
object NotificationHelper {

    const val CHANNEL_ID_CHAT = "bms_chat_messages"
    private const val CHANNEL_NAME = "Chat Messages"
    private const val CHANNEL_DESC = "Notifications for new chat messages"
    private const val GROUP_KEY_CHAT = "bms_chat_group"

    /**
     * Must be called once during Application.onCreate() to register the
     * notification channel with the Android system.
     */
    fun createNotificationChannels(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val chatChannel = NotificationChannel(
                CHANNEL_ID_CHAT,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = CHANNEL_DESC
                enableVibration(true)
                vibrationPattern = longArrayOf(0, 200, 100, 200)
                setShowBadge(true)
                enableLights(true)
                lightColor = Color.parseColor("#4F46E5") // Indigo accent
            }

            val manager = context.getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(chatChannel)
        }
    }

    /**
     * Creates a circular bitmap with initials for use as a notification avatar.
     */
    private fun createInitialsBitmap(name: String): Bitmap {
        val size = 128
        val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        // Background circle — use a hash of the name for consistent color per person
        val colors = intArrayOf(
            Color.parseColor("#4F46E5"), // Indigo
            Color.parseColor("#7C3AED"), // Violet
            Color.parseColor("#2563EB"), // Blue
            Color.parseColor("#0891B2"), // Cyan
            Color.parseColor("#059669"), // Emerald
            Color.parseColor("#D97706"), // Amber
            Color.parseColor("#DC2626"), // Red
            Color.parseColor("#DB2777"), // Pink
        )
        val bgColor = colors[Math.abs(name.hashCode()) % colors.size]

        val bgPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = bgColor
            style = Paint.Style.FILL
        }
        canvas.drawCircle(size / 2f, size / 2f, size / 2f, bgPaint)

        // Initials text
        val initials = name.split(" ")
            .filter { it.isNotBlank() }
            .take(2)
            .joinToString("") { it.first().uppercase() }
            .ifEmpty { "?" }

        val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.WHITE
            textSize = size * 0.4f
            textAlign = Paint.Align.CENTER
            isFakeBoldText = true
        }
        val yOffset = (textPaint.descent() + textPaint.ascent()) / 2f
        canvas.drawText(initials, size / 2f, size / 2f - yOffset, textPaint)

        return bitmap
    }

    /**
     * Shows a rich system notification for a new chat message.
     * Uses MessagingStyle for a modern, messaging-app look.
     */
    fun showChatNotification(
        context: Context,
        senderName: String,
        messagePreview: String,
        senderId: String,
        notificationId: Int = senderId.hashCode()
    ) {
        // Check POST_NOTIFICATIONS permission on Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
        }

        // Deep-link intent → opens chat screen
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra("navigate_to", "chat/$senderId")
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            senderId.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Clean sender name (strip emoji prefix if present from DB trigger)
        val cleanName = senderName.removePrefix("💬 ").trim()

        // Create avatar bitmap with initials
        val avatarBitmap = createInitialsBitmap(cleanName)

        // Build the Person object for MessagingStyle
        val sender = Person.Builder()
            .setName(cleanName)
            .setIcon(IconCompat.createWithBitmap(avatarBitmap))
            .build()

        // Use MessagingStyle for a rich chat-like notification
        val messagingStyle = NotificationCompat.MessagingStyle(sender)
            .addMessage(messagePreview, System.currentTimeMillis(), sender)

        val notification = NotificationCompat.Builder(context, CHANNEL_ID_CHAT)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setStyle(messagingStyle)
            .setLargeIcon(avatarBitmap)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setVibrate(longArrayOf(0, 200, 100, 200))
            .setColor(Color.parseColor("#4F46E5"))
            .setGroup(GROUP_KEY_CHAT)
            .build()

        NotificationManagerCompat.from(context).notify(notificationId, notification)
    }

    /**
     * Dismisses a chat notification for a specific sender.
     * Call this when the user opens the chat screen for that sender.
     */
    fun dismissChatNotification(context: Context, senderId: String) {
        NotificationManagerCompat.from(context).cancel(senderId.hashCode())
    }
}
