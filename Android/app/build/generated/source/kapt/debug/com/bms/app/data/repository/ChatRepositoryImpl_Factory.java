package com.bms.app.data.repository;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import io.github.jan.supabase.gotrue.Auth;
import io.github.jan.supabase.postgrest.Postgrest;
import io.github.jan.supabase.realtime.Realtime;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class ChatRepositoryImpl_Factory implements Factory<ChatRepositoryImpl> {
  private final Provider<Auth> authProvider;

  private final Provider<Postgrest> postgrestProvider;

  private final Provider<Realtime> realtimeProvider;

  public ChatRepositoryImpl_Factory(Provider<Auth> authProvider,
      Provider<Postgrest> postgrestProvider, Provider<Realtime> realtimeProvider) {
    this.authProvider = authProvider;
    this.postgrestProvider = postgrestProvider;
    this.realtimeProvider = realtimeProvider;
  }

  @Override
  public ChatRepositoryImpl get() {
    return newInstance(authProvider.get(), postgrestProvider.get(), realtimeProvider.get());
  }

  public static ChatRepositoryImpl_Factory create(Provider<Auth> authProvider,
      Provider<Postgrest> postgrestProvider, Provider<Realtime> realtimeProvider) {
    return new ChatRepositoryImpl_Factory(authProvider, postgrestProvider, realtimeProvider);
  }

  public static ChatRepositoryImpl newInstance(Auth auth, Postgrest postgrest, Realtime realtime) {
    return new ChatRepositoryImpl(auth, postgrest, realtime);
  }
}
