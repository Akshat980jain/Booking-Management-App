package com.bms.app.data.repository;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import io.github.jan.supabase.gotrue.Auth;
import io.github.jan.supabase.postgrest.Postgrest;
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

  public ChatRepositoryImpl_Factory(Provider<Auth> authProvider,
      Provider<Postgrest> postgrestProvider) {
    this.authProvider = authProvider;
    this.postgrestProvider = postgrestProvider;
  }

  @Override
  public ChatRepositoryImpl get() {
    return newInstance(authProvider.get(), postgrestProvider.get());
  }

  public static ChatRepositoryImpl_Factory create(Provider<Auth> authProvider,
      Provider<Postgrest> postgrestProvider) {
    return new ChatRepositoryImpl_Factory(authProvider, postgrestProvider);
  }

  public static ChatRepositoryImpl newInstance(Auth auth, Postgrest postgrest) {
    return new ChatRepositoryImpl(auth, postgrest);
  }
}
