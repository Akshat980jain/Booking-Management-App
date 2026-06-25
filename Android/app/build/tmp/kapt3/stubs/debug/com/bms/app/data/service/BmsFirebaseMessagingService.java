package com.bms.app.data.service;

@dagger.hilt.android.AndroidEntryPoint()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u0010\u0010\u0015\u001a\u00020\u00122\u0006\u0010\u0016\u001a\u00020\u0017H\u0016R\u001e\u0010\u0003\u001a\u00020\u00048\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001e\u0010\t\u001a\u00020\n8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0018"}, d2 = {"Lcom/bms/app/data/service/BmsFirebaseMessagingService;", "Lcom/google/firebase/messaging/FirebaseMessagingService;", "()V", "auth", "Lio/github/jan/supabase/gotrue/Auth;", "getAuth", "()Lio/github/jan/supabase/gotrue/Auth;", "setAuth", "(Lio/github/jan/supabase/gotrue/Auth;)V", "notificationRepository", "Lcom/bms/app/domain/repository/NotificationRepository;", "getNotificationRepository", "()Lcom/bms/app/domain/repository/NotificationRepository;", "setNotificationRepository", "(Lcom/bms/app/domain/repository/NotificationRepository;)V", "serviceScope", "Lkotlinx/coroutines/CoroutineScope;", "onMessageReceived", "", "remoteMessage", "Lcom/google/firebase/messaging/RemoteMessage;", "onNewToken", "token", "", "app_debug"})
public final class BmsFirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    @javax.inject.Inject()
    public com.bms.app.domain.repository.NotificationRepository notificationRepository;
    @javax.inject.Inject()
    public io.github.jan.supabase.gotrue.Auth auth;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.CoroutineScope serviceScope = null;
    
    public BmsFirebaseMessagingService() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.bms.app.domain.repository.NotificationRepository getNotificationRepository() {
        return null;
    }
    
    public final void setNotificationRepository(@org.jetbrains.annotations.NotNull()
    com.bms.app.domain.repository.NotificationRepository p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final io.github.jan.supabase.gotrue.Auth getAuth() {
        return null;
    }
    
    public final void setAuth(@org.jetbrains.annotations.NotNull()
    io.github.jan.supabase.gotrue.Auth p0) {
    }
    
    @java.lang.Override()
    public void onMessageReceived(@org.jetbrains.annotations.NotNull()
    com.google.firebase.messaging.RemoteMessage remoteMessage) {
    }
    
    @java.lang.Override()
    public void onNewToken(@org.jetbrains.annotations.NotNull()
    java.lang.String token) {
    }
}