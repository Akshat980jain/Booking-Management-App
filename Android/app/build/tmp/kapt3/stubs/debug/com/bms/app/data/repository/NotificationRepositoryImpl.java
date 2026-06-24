package com.bms.app.data.repository;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010#\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u001c\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\f0\u000b2\u0006\u0010\u000e\u001a\u00020\tH\u0016J$\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u00102\u0006\u0010\u000e\u001a\u00020\tH\u0096@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0012\u0010\u0013J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\tH\u0016J\u000e\u0010\u0017\u001a\u00020\u00152\u0006\u0010\u0018\u001a\u00020\tJ$\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001a0\u00102\u0006\u0010\u0016\u001a\u00020\tH\u0096@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u001b\u0010\u0013J\u0010\u0010\u001c\u001a\u00020\u001a2\u0006\u0010\u0016\u001a\u00020\tH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006\u001d"}, d2 = {"Lcom/bms/app/data/repository/NotificationRepositoryImpl;", "Lcom/bms/app/domain/repository/NotificationRepository;", "postgrest", "Lio/github/jan/supabase/postgrest/Postgrest;", "realtime", "Lio/github/jan/supabase/realtime/Realtime;", "(Lio/github/jan/supabase/postgrest/Postgrest;Lio/github/jan/supabase/realtime/Realtime;)V", "seenNotificationIds", "", "", "getNotificationsFlow", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/bms/app/domain/model/Notification;", "userId", "getUnreadCount", "Lkotlin/Result;", "", "getUnreadCount-gIAlu-s", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "hasBeenSeen", "", "notificationId", "isRecentNotification", "createdAt", "markAsRead", "", "markAsRead-gIAlu-s", "markAsSeen", "app_debug"})
public final class NotificationRepositoryImpl implements com.bms.app.domain.repository.NotificationRepository {
    @org.jetbrains.annotations.NotNull()
    private final io.github.jan.supabase.postgrest.Postgrest postgrest = null;
    @org.jetbrains.annotations.NotNull()
    private final io.github.jan.supabase.realtime.Realtime realtime = null;
    
    /**
     * Session-scoped set — survives ViewModel recreation within the same process because
     * this class is @Singleton. Using a thread-safe ConcurrentHashMap-backed set to guard
     * against concurrent coroutine collector emissions.
     */
    @org.jetbrains.annotations.NotNull()
    private final java.util.Set<java.lang.String> seenNotificationIds = null;
    
    @javax.inject.Inject()
    public NotificationRepositoryImpl(@org.jetbrains.annotations.NotNull()
    io.github.jan.supabase.postgrest.Postgrest postgrest, @org.jetbrains.annotations.NotNull()
    io.github.jan.supabase.realtime.Realtime realtime) {
        super();
    }
    
    @java.lang.Override()
    public boolean hasBeenSeen(@org.jetbrains.annotations.NotNull()
    java.lang.String notificationId) {
        return false;
    }
    
    @java.lang.Override()
    public void markAsSeen(@org.jetbrains.annotations.NotNull()
    java.lang.String notificationId) {
    }
    
    /**
     * Returns true only if the notification was created within the last 24 hours.
     * This is the primary guard against months-old backlog notifications re-appearing
     * when the app is opened after a long period of inactivity.
     */
    public final boolean isRecentNotification(@org.jetbrains.annotations.NotNull()
    java.lang.String createdAt) {
        return false;
    }
    
    @java.lang.Override()
    @kotlin.OptIn(markerClass = {io.github.jan.supabase.annotations.SupabaseExperimental.class})
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.bms.app.domain.model.Notification>> getNotificationsFlow(@org.jetbrains.annotations.NotNull()
    java.lang.String userId) {
        return null;
    }
}