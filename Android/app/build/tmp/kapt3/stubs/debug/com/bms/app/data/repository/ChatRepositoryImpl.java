package com.bms.app.data.repository;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0002\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001B\u001f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\"\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u000b0\nH\u0096@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\r\u0010\u000eJ\u0014\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u000b0\u0010H\u0016J\n\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0016J*\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00140\u000b0\n2\u0006\u0010\u0015\u001a\u00020\u0012H\u0096@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0016\u0010\u0017J\u001c\u0010\u0018\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00140\u000b0\u00102\u0006\u0010\u0015\u001a\u00020\u0012H\u0016J\u001e\u0010\u0019\u001a\u00020\u00122\u0006\u0010\u001a\u001a\u00020\u00122\u0006\u0010\u001b\u001a\u00020\u0012H\u0082@\u00a2\u0006\u0002\u0010\u001cJ,\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001e0\n2\u0006\u0010\u001f\u001a\u00020\u00122\u0006\u0010 \u001a\u00020\u0012H\u0096@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b!\u0010\u001cR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006\""}, d2 = {"Lcom/bms/app/data/repository/ChatRepositoryImpl;", "Lcom/bms/app/domain/repository/ChatRepository;", "auth", "Lio/github/jan/supabase/gotrue/Auth;", "postgrest", "Lio/github/jan/supabase/postgrest/Postgrest;", "realtime", "Lio/github/jan/supabase/realtime/Realtime;", "(Lio/github/jan/supabase/gotrue/Auth;Lio/github/jan/supabase/postgrest/Postgrest;Lio/github/jan/supabase/realtime/Realtime;)V", "getConversations", "Lkotlin/Result;", "", "Lcom/bms/app/domain/model/ChatConversation;", "getConversations-IoAF18A", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getConversationsFlow", "Lkotlinx/coroutines/flow/Flow;", "getCurrentUserId", "", "getMessages", "Lcom/bms/app/domain/model/ChatMessage;", "otherUserId", "getMessages-gIAlu-s", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getMessagesFlow", "getOrCreateConversationId", "userId1", "userId2", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "sendMessage", "", "receiverId", "content", "sendMessage-0E7RQCE", "app_debug"})
public final class ChatRepositoryImpl implements com.bms.app.domain.repository.ChatRepository {
    @org.jetbrains.annotations.NotNull()
    private final io.github.jan.supabase.gotrue.Auth auth = null;
    @org.jetbrains.annotations.NotNull()
    private final io.github.jan.supabase.postgrest.Postgrest postgrest = null;
    @org.jetbrains.annotations.NotNull()
    private final io.github.jan.supabase.realtime.Realtime realtime = null;
    
    @javax.inject.Inject()
    public ChatRepositoryImpl(@org.jetbrains.annotations.NotNull()
    io.github.jan.supabase.gotrue.Auth auth, @org.jetbrains.annotations.NotNull()
    io.github.jan.supabase.postgrest.Postgrest postgrest, @org.jetbrains.annotations.NotNull()
    io.github.jan.supabase.realtime.Realtime realtime) {
        super();
    }
    
    /**
     * Gets or creates a conversation between any two auth users.
     * Uses participant_1 / participant_2 (no provider constraint).
     * Normalises order so (A,B) and (B,A) always map to the same row.
     */
    private final java.lang.Object getOrCreateConversationId(java.lang.String userId1, java.lang.String userId2, kotlin.coroutines.Continuation<? super java.lang.String> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.String getCurrentUserId() {
        return null;
    }
    
    @java.lang.Override()
    @kotlin.OptIn(markerClass = {io.github.jan.supabase.annotations.SupabaseExperimental.class})
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.bms.app.domain.model.ChatMessage>> getMessagesFlow(@org.jetbrains.annotations.NotNull()
    java.lang.String otherUserId) {
        return null;
    }
    
    @java.lang.Override()
    @kotlin.OptIn(markerClass = {io.github.jan.supabase.annotations.SupabaseExperimental.class})
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.bms.app.domain.model.ChatConversation>> getConversationsFlow() {
        return null;
    }
}