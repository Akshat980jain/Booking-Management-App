package com.bms.app.data.repository;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import io.github.jan.supabase.postgrest.Postgrest;
import io.github.jan.supabase.realtime.Realtime;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class NotificationRepositoryImpl_Factory implements Factory<NotificationRepositoryImpl> {
  private final Provider<Context> contextProvider;

  private final Provider<Postgrest> postgrestProvider;

  private final Provider<Realtime> realtimeProvider;

  public NotificationRepositoryImpl_Factory(Provider<Context> contextProvider,
      Provider<Postgrest> postgrestProvider, Provider<Realtime> realtimeProvider) {
    this.contextProvider = contextProvider;
    this.postgrestProvider = postgrestProvider;
    this.realtimeProvider = realtimeProvider;
  }

  @Override
  public NotificationRepositoryImpl get() {
    return newInstance(contextProvider.get(), postgrestProvider.get(), realtimeProvider.get());
  }

  public static NotificationRepositoryImpl_Factory create(Provider<Context> contextProvider,
      Provider<Postgrest> postgrestProvider, Provider<Realtime> realtimeProvider) {
    return new NotificationRepositoryImpl_Factory(contextProvider, postgrestProvider, realtimeProvider);
  }

  public static NotificationRepositoryImpl newInstance(Context context, Postgrest postgrest,
      Realtime realtime) {
    return new NotificationRepositoryImpl(context, postgrest, realtime);
  }
}
