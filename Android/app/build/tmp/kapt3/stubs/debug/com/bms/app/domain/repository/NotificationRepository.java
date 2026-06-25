package com.bms.app.domain.repository;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\b\bf\u0018\u00002\u00020\u0001J\u001c\u0010\u0002\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u00032\u0006\u0010\u0006\u001a\u00020\u0007H&J$\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t2\u0006\u0010\u0006\u001a\u00020\u0007H\u00a6@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u000b\u0010\fJ\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0007H&J\u0010\u0010\u0010\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u0007H&J$\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\t2\u0006\u0010\u000f\u001a\u00020\u0007H\u00a6@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0014\u0010\fJ\u0010\u0010\u0015\u001a\u00020\u00132\u0006\u0010\u000f\u001a\u00020\u0007H&J8\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00130\t2\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u0017\u001a\u00020\u00072\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\u0007H\u00a6@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0019\u0010\u001a\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006\u001b"}, d2 = {"Lcom/bms/app/domain/repository/NotificationRepository;", "", "getNotificationsFlow", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/bms/app/domain/model/Notification;", "userId", "", "getUnreadCount", "Lkotlin/Result;", "", "getUnreadCount-gIAlu-s", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "hasBeenSeen", "", "notificationId", "isRecentNotification", "createdAt", "markAsRead", "", "markAsRead-gIAlu-s", "markAsSeen", "registerFcmToken", "token", "deviceName", "registerFcmToken-BWLJW6A", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public abstract interface NotificationRepository {
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.bms.app.domain.model.Notification>> getNotificationsFlow(@org.jetbrains.annotations.NotNull()
    java.lang.String userId);
    
    /**
     * Returns true if this notification has already been shown as a system notification
     * during the current app session. Thread-safe.
     */
    public abstract boolean hasBeenSeen(@org.jetbrains.annotations.NotNull()
    java.lang.String notificationId);
    
    /**
     * Marks a notification as shown for this session. Thread-safe.
     */
    public abstract void markAsSeen(@org.jetbrains.annotations.NotNull()
    java.lang.String notificationId);
    
    /**
     * Returns true if the notification was created within the last 24 hours.
     */
    public abstract boolean isRecentNotification(@org.jetbrains.annotations.NotNull()
    java.lang.String createdAt);
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}