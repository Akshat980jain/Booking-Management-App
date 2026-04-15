package com.bms.app.data.repository;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import io.github.jan.supabase.functions.Functions;
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

  private final Provider<Functions> functionsProvider;

  public VideoRepositoryImpl_Factory(Provider<Postgrest> postgrestProvider,
      Provider<Functions> functionsProvider) {
    this.postgrestProvider = postgrestProvider;
    this.functionsProvider = functionsProvider;
  }

  @Override
  public VideoRepositoryImpl get() {
    return newInstance(postgrestProvider.get(), functionsProvider.get());
  }

  public static VideoRepositoryImpl_Factory create(Provider<Postgrest> postgrestProvider,
      Provider<Functions> functionsProvider) {
    return new VideoRepositoryImpl_Factory(postgrestProvider, functionsProvider);
  }

  public static VideoRepositoryImpl newInstance(Postgrest postgrest, Functions functions) {
    return new VideoRepositoryImpl(postgrest, functions);
  }
}
