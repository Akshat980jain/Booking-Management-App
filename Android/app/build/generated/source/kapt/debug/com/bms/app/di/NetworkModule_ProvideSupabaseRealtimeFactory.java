package com.bms.app.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import io.github.jan.supabase.SupabaseClient;
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
public final class NetworkModule_ProvideSupabaseRealtimeFactory implements Factory<Realtime> {
  private final Provider<SupabaseClient> clientProvider;

  public NetworkModule_ProvideSupabaseRealtimeFactory(Provider<SupabaseClient> clientProvider) {
    this.clientProvider = clientProvider;
  }

  @Override
  public Realtime get() {
    return provideSupabaseRealtime(clientProvider.get());
  }

  public static NetworkModule_ProvideSupabaseRealtimeFactory create(
      Provider<SupabaseClient> clientProvider) {
    return new NetworkModule_ProvideSupabaseRealtimeFactory(clientProvider);
  }

  public static Realtime provideSupabaseRealtime(SupabaseClient client) {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideSupabaseRealtime(client));
  }
}
