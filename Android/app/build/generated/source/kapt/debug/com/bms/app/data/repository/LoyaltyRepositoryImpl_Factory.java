package com.bms.app.data.repository;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import io.github.jan.supabase.postgrest.Postgrest;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class LoyaltyRepositoryImpl_Factory implements Factory<LoyaltyRepositoryImpl> {
  private final Provider<Postgrest> postgrestProvider;

  public LoyaltyRepositoryImpl_Factory(Provider<Postgrest> postgrestProvider) {
    this.postgrestProvider = postgrestProvider;
  }

  @Override
  public LoyaltyRepositoryImpl get() {
    return newInstance(postgrestProvider.get());
  }

  public static LoyaltyRepositoryImpl_Factory create(Provider<Postgrest> postgrestProvider) {
    return new LoyaltyRepositoryImpl_Factory(postgrestProvider);
  }

  public static LoyaltyRepositoryImpl newInstance(Postgrest postgrest) {
    return new LoyaltyRepositoryImpl(postgrest);
  }
}
