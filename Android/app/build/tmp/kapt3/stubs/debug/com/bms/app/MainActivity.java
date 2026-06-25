package com.bms.app;

@dagger.hilt.android.AndroidEntryPoint()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0014J\u0010\u0010\u0015\u001a\u00020\u00122\u0006\u0010\u0016\u001a\u00020\u0017H\u0002J\u0010\u0010\u0018\u001a\u00020\u00122\u0006\u0010\u0016\u001a\u00020\u0017H\u0002J\b\u0010\u0019\u001a\u00020\u0012H\u0002R\u001e\u0010\u0003\u001a\u00020\u00048\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001e\u0010\u000b\u001a\u00020\f8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010\u00a8\u0006\u001a"}, d2 = {"Lcom/bms/app/MainActivity;", "Landroidx/activity/ComponentActivity;", "()V", "auth", "Lio/github/jan/supabase/gotrue/Auth;", "getAuth", "()Lio/github/jan/supabase/gotrue/Auth;", "setAuth", "(Lio/github/jan/supabase/gotrue/Auth;)V", "notificationListenerJob", "Lkotlinx/coroutines/Job;", "notificationRepository", "Lcom/bms/app/domain/repository/NotificationRepository;", "getNotificationRepository", "()Lcom/bms/app/domain/repository/NotificationRepository;", "setNotificationRepository", "(Lcom/bms/app/domain/repository/NotificationRepository;)V", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "registerFcmToken", "userId", "", "startGlobalNotificationsListener", "stopGlobalNotificationsListener", "app_debug"})
public final class MainActivity extends androidx.activity.ComponentActivity {
    @javax.inject.Inject()
    public io.github.jan.supabase.gotrue.Auth auth;
    @javax.inject.Inject()
    public com.bms.app.domain.repository.NotificationRepository notificationRepository;
    @org.jetbrains.annotations.Nullable()
    private kotlinx.coroutines.Job notificationListenerJob;
    
    public MainActivity() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final io.github.jan.supabase.gotrue.Auth getAuth() {
        return null;
    }
    
    public final void setAuth(@org.jetbrains.annotations.NotNull()
    io.github.jan.supabase.gotrue.Auth p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.bms.app.domain.repository.NotificationRepository getNotificationRepository() {
        return null;
    }
    
    public final void setNotificationRepository(@org.jetbrains.annotations.NotNull()
    com.bms.app.domain.repository.NotificationRepository p0) {
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void startGlobalNotificationsListener(java.lang.String userId) {
    }
    
    private final void stopGlobalNotificationsListener() {
    }
    
    private final void registerFcmToken(java.lang.String userId) {
    }
}