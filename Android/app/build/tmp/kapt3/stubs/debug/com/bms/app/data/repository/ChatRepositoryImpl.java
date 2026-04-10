package com.bms.app.data.repository;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\"\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\bH\u0096@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u000b\u0010\fJ\n\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0016J*\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\t0\b2\u0006\u0010\u0011\u001a\u00020\u000eH\u0096@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0012\u0010\u0013J\u001e\u0010\u0014\u001a\u00020\u000e2\u0006\u0010\u0015\u001a\u00020\u000e2\u0006\u0010\u0016\u001a\u00020\u000eH\u0082@\u00a2\u0006\u0002\u0010\u0017J,\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00190\b2\u0006\u0010\u001a\u001a\u00020\u000e2\u0006\u0010\u001b\u001a\u00020\u000eH\u0096@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u001c\u0010\u0017R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006\u001d"}, d2 = {"Lcom/bms/app/data/repository/ChatRepositoryImpl;", "Lcom/bms/app/domain/repository/ChatRepository;", "auth", "Lio/github/jan/supabase/gotrue/Auth;", "postgrest", "Lio/github/jan/supabase/postgrest/Postgrest;", "(Lio/github/jan/supabase/gotrue/Auth;Lio/github/jan/supabase/postgrest/Postgrest;)V", "getConversations", "Lkotlin/Result;", "", "Lcom/bms/app/domain/model/ChatConversation;", "getConversations-IoAF18A", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getCurrentUserId", "", "getMessages", "Lcom/bms/app/domain/model/ChatMessage;", "otherUserId", "getMessages-gIAlu-s", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getOrCreateConversationId", "userId1", "userId2", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "sendMessage", "", "receiverId", "content", "sendMessage-0E7RQCE", "app_debug"})
public final class ChatRepositoryImpl implements com.bms.app.domain.repository.ChatRepository {
    @org.jetbrains.annotations.NotNull()
    private final io.github.jan.supabase.gotrue.Auth auth = null;
    @org.jetbrains.annotations.NotNull()
    private final io.github.jan.supabase.postgrest.Postgrest postgrest = null;
    
    @javax.inject.Inject()
    public ChatRepositoryImpl(@org.jetbrains.annotations.NotNull()
    io.github.jan.supabase.gotrue.Auth auth, @org.jetbrains.annotations.NotNull()
    io.github.jan.supabase.postgrest.Postgrest postgrest) {
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
}