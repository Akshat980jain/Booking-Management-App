package com.bms.app.data.repository;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u001c\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\b2\u0006\u0010\u000b\u001a\u00020\fH\u0016J$\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\u0006\u0010\u000b\u001a\u00020\fH\u0096@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0010\u0010\u0011J$\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\u000e2\u0006\u0010\u0014\u001a\u00020\fH\u0096@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0015\u0010\u0011R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006\u0016"}, d2 = {"Lcom/bms/app/data/repository/NotificationRepositoryImpl;", "Lcom/bms/app/domain/repository/NotificationRepository;", "postgrest", "Lio/github/jan/supabase/postgrest/Postgrest;", "realtime", "Lio/github/jan/supabase/realtime/Realtime;", "(Lio/github/jan/supabase/postgrest/Postgrest;Lio/github/jan/supabase/realtime/Realtime;)V", "getNotificationsFlow", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/bms/app/domain/model/Notification;", "userId", "", "getUnreadCount", "Lkotlin/Result;", "", "getUnreadCount-gIAlu-s", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "markAsRead", "", "notificationId", "markAsRead-gIAlu-s", "app_debug"})
public final class NotificationRepositoryImpl implements com.bms.app.domain.repository.NotificationRepository {
    @org.jetbrains.annotations.NotNull()
    private final io.github.jan.supabase.postgrest.Postgrest postgrest = null;
    @org.jetbrains.annotations.NotNull()
    private final io.github.jan.supabase.realtime.Realtime realtime = null;
    
    @javax.inject.Inject()
    public NotificationRepositoryImpl(@org.jetbrains.annotations.NotNull()
    io.github.jan.supabase.postgrest.Postgrest postgrest, @org.jetbrains.annotations.NotNull()
    io.github.jan.supabase.realtime.Realtime realtime) {
        super();
    }
    
    @java.lang.Override()
    @kotlin.OptIn(markerClass = {io.github.jan.supabase.annotations.SupabaseExperimental.class})
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.bms.app.domain.model.Notification>> getNotificationsFlow(@org.jetbrains.annotations.NotNull()
    java.lang.String userId) {
        return null;
    }
}