package com.bms.app.di;

import android.content.Context;
import com.bms.app.data.local.SupabaseSessionManager;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
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
public final class NetworkModule_ProvideSessionManagerFactory implements Factory<SupabaseSessionManager> {
  private final Provider<Context> contextProvider;

  public NetworkModule_ProvideSessionManagerFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public SupabaseSessionManager get() {
    return provideSessionManager(contextProvider.get());
  }

  public static NetworkModule_ProvideSessionManagerFactory create(
      Provider<Context> contextProvider) {
    return new NetworkModule_ProvideSessionManagerFactory(contextProvider);
  }

  public static SupabaseSessionManager provideSessionManager(Context context) {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideSessionManager(context));
  }
}
