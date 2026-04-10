package com.bms.app.data.local;

/**
 * Custom SessionManager for Supabase Auth that persists the UserSession
 * to Android SharedPreferences. This ensures the user stays logged in
 * across app restarts.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\n\u001a\u00020\u000bH\u0096@\u00a2\u0006\u0002\u0010\fJ\b\u0010\r\u001a\u0004\u0018\u00010\u000eJ\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0096@\u00a2\u0006\u0002\u0010\fJ\u0016\u0010\u0011\u001a\u00020\u000b2\u0006\u0010\u0012\u001a\u00020\u0010H\u0096@\u00a2\u0006\u0002\u0010\u0013R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0007\u001a\n \t*\u0004\u0018\u00010\b0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0014"}, d2 = {"Lcom/bms/app/data/local/SupabaseSessionManager;", "Lio/github/jan/supabase/gotrue/SessionManager;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "json", "Lkotlinx/serialization/json/Json;", "prefs", "Landroid/content/SharedPreferences;", "kotlin.jvm.PlatformType", "deleteSession", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getRawSession", "", "loadSession", "Lio/github/jan/supabase/gotrue/user/UserSession;", "saveSession", "session", "(Lio/github/jan/supabase/gotrue/user/UserSession;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class SupabaseSessionManager implements io.github.jan.supabase.gotrue.SessionManager {
    private final android.content.SharedPreferences prefs = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.serialization.json.Json json = null;
    
    public SupabaseSessionManager(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object saveSession(@org.jetbrains.annotations.NotNull()
    io.github.jan.supabase.gotrue.user.UserSession session, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object loadSession(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super io.github.jan.supabase.gotrue.user.UserSession> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object deleteSession(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    /**
     * Manual rescue method to get the raw string if the SDK loading fails.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getRawSession() {
        return null;
    }
}