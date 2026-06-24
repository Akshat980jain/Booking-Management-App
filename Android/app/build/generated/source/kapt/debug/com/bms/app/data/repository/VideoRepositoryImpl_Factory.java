package com.bms.app.data.repository;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import io.github.jan.supabase.SupabaseClient;
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
public final class VideoRepositoryImpl_Factory implements Factory<VideoRepositoryImpl> {
  private final Provider<Postgrest> postgrestProvider;

  private final Provider<SupabaseClient> supabaseClientProvider;

  public VideoRepositoryImpl_Factory(Provider<Postgrest> postgrestProvider,
      Provider<SupabaseClient> supabaseClientProvider) {
    this.postgrestProvider = postgrestProvider;
    this.supabaseClientProvider = supabaseClientProvider;
  }

  @Override
  public VideoRepositoryImpl get() {
    return newInstance(postgrestProvider.get(), supabaseClientProvider.get());
  }

  public static VideoRepositoryImpl_Factory create(Provider<Postgrest> postgrestProvider,
      Provider<SupabaseClient> supabaseClientProvider) {
    return new VideoRepositoryImpl_Factory(postgrestProvider, supabaseClientProvider);
  }

  public static VideoRepositoryImpl newInstance(Postgrest postgrest,
      SupabaseClient supabaseClient) {
    return new VideoRepositoryImpl(postgrest, supabaseClient);
  }
}
