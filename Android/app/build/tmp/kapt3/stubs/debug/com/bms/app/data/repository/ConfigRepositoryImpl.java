package com.bms.app.data.repository;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u001c\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0096@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\b\u0010\tR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006\n"}, d2 = {"Lcom/bms/app/data/repository/ConfigRepositoryImpl;", "Lcom/bms/app/domain/repository/ConfigRepository;", "postgrest", "Lio/github/jan/supabase/postgrest/Postgrest;", "(Lio/github/jan/supabase/postgrest/Postgrest;)V", "getAppConfig", "Lkotlin/Result;", "Lcom/bms/app/domain/model/AppConfig;", "getAppConfig-IoAF18A", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class ConfigRepositoryImpl implements com.bms.app.domain.repository.ConfigRepository {
    @org.jetbrains.annotations.NotNull()
    private final io.github.jan.supabase.postgrest.Postgrest postgrest = null;
    
    @javax.inject.Inject()
    public ConfigRepositoryImpl(@org.jetbrains.annotations.NotNull()
    io.github.jan.supabase.postgrest.Postgrest postgrest) {
        super();
    }
}