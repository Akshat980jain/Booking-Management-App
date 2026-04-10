package com.bms.app.data.repository;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
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
public final class AppointmentRepositoryImpl_Factory implements Factory<AppointmentRepositoryImpl> {
  private final Provider<Postgrest> postgrestProvider;

  public AppointmentRepositoryImpl_Factory(Provider<Postgrest> postgrestProvider) {
    this.postgrestProvider = postgrestProvider;
  }

  @Override
  public AppointmentRepositoryImpl get() {
    return newInstance(postgrestProvider.get());
  }

  public static AppointmentRepositoryImpl_Factory create(Provider<Postgrest> postgrestProvider) {
    return new AppointmentRepositoryImpl_Factory(postgrestProvider);
  }

  public static AppointmentRepositoryImpl newInstance(Postgrest postgrest) {
    return new AppointmentRepositoryImpl(postgrest);
  }
}
