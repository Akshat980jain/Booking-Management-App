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
public final class AuthRepositoryImpl_Factory implements Factory<AuthRepositoryImpl> {
  private final Provider<Auth> authProvider;

  private final Provider<Postgrest> postgrestProvider;

  public AuthRepositoryImpl_Factory(Provider<Auth> authProvider,
      Provider<Postgrest> postgrestProvider) {
    this.authProvider = authProvider;
    this.postgrestProvider = postgrestProvider;
  }

  @Override
  public AuthRepositoryImpl get() {
    return newInstance(authProvider.get(), postgrestProvider.get());
  }

  public static AuthRepositoryImpl_Factory create(Provider<Auth> authProvider,
      Provider<Postgrest> postgrestProvider) {
    return new AuthRepositoryImpl_Factory(authProvider, postgrestProvider);
  }

  public static AuthRepositoryImpl newInstance(Auth auth, Postgrest postgrest) {
    return new AuthRepositoryImpl(auth, postgrest);
  }
}
