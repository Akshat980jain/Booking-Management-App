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
public final class ProfileRepositoryImpl_Factory implements Factory<ProfileRepositoryImpl> {
  private final Provider<Postgrest> postgrestProvider;

  public ProfileRepositoryImpl_Factory(Provider<Postgrest> postgrestProvider) {
    this.postgrestProvider = postgrestProvider;
  }

  @Override
  public ProfileRepositoryImpl get() {
    return newInstance(postgrestProvider.get());
  }

  public static ProfileRepositoryImpl_Factory create(Provider<Postgrest> postgrestProvider) {
    return new ProfileRepositoryImpl_Factory(postgrestProvider);
  }

  public static ProfileRepositoryImpl newInstance(Postgrest postgrest) {
    return new ProfileRepositoryImpl(postgrest);
  }
}
