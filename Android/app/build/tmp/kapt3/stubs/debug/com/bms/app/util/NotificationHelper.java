package com.bms.app.util;

/**
 * Utility class for Android system-level notifications (status bar, lock screen).
 * Uses MessagingStyle for a rich, WhatsApp-like chat notification experience.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0004H\u0002J\u000e\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eJ\u0016\u0010\u000f\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\u0004J0\u0010\u0011\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\u00042\u0006\u0010\u0013\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u00042\b\b\u0002\u0010\u0014\u001a\u00020\u0015R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0016"}, d2 = {"Lcom/bms/app/util/NotificationHelper;", "", "()V", "CHANNEL_DESC", "", "CHANNEL_ID_CHAT", "CHANNEL_NAME", "GROUP_KEY_CHAT", "createInitialsBitmap", "Landroid/graphics/Bitmap;", "name", "createNotificationChannels", "", "context", "Landroid/content/Context;", "dismissChatNotification", "senderId", "showChatNotification", "senderName", "messagePreview", "notificationId", "", "app_debug"})
public final class NotificationHelper {
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String CHANNEL_ID_CHAT = "bms_chat_messages";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String CHANNEL_NAME = "Chat Messages";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String CHANNEL_DESC = "Notifications for new chat messages";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String GROUP_KEY_CHAT = "bms_chat_group";
    @org.jetbrains.annotations.NotNull()
    public static final com.bms.app.util.NotificationHelper INSTANCE = null;
    
    private NotificationHelper() {
        super();
    }
    
    /**
     * Must be called once during Application.onCreate() to register the
     * notification channel with the Android system.
     */
    public final void createNotificationChannels(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
    }
    
    /**
     * Creates a circular bitmap with initials for use as a notification avatar.
     */
    private final android.graphics.Bitmap createInitialsBitmap(java.lang.String name) {
        return null;
    }
    
    /**
     * Shows a rich system notification for a new chat message.
     * Uses MessagingStyle for a modern, messaging-app look.
     */
    public final void showChatNotification(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String senderName, @org.jetbrains.annotations.NotNull()
    java.lang.String messagePreview, @org.jetbrains.annotations.NotNull()
    java.lang.String senderId, int notificationId) {
    }
    
    /**
     * Dismisses a chat notification for a specific sender.
     * Call this when the user opens the chat screen for that sender.
     */
    public final void dismissChatNotification(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String senderId) {
    }
}