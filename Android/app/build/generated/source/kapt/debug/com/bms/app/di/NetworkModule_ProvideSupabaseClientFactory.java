package com.bms.app.di;

import android.content.Context;
import com.bms.app.data.local.SupabaseSessionManager;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import io.github.jan.supabase.SupabaseClient;
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
public final class NetworkModule_ProvideSupabaseClientFactory implements Factory<SupabaseClient> {
  private final Provider<Context> contextProvider;

  private final Provider<SupabaseSessionManager> sessionManagerProvider;

  public NetworkModule_ProvideSupabaseClientFactory(Provider<Context> contextProvider,
      Provider<SupabaseSessionManager> sessionManagerProvider) {
    this.contextProvider = contextProvider;
    this.sessionManagerProvider = sessionManagerProvider;
  }

  @Override
  public SupabaseClient get() {
    return provideSupabaseClient(contextProvider.get(), sessionManagerProvider.get());
  }

  public static NetworkModule_ProvideSupabaseClientFactory create(Provider<Context> contextProvider,
      Provider<SupabaseSessionManager> sessionManagerProvider) {
    return new NetworkModule_ProvideSupabaseClientFactory(contextProvider, sessionManagerProvider);
  }

  public static SupabaseClient provideSupabaseClient(Context context,
      SupabaseSessionManager sessionManager) {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideSupabaseClient(context, sessionManager));
  }
}
