package com.bms.app.ui.video;

import android.content.Context;
import com.bms.app.domain.repository.VideoRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import io.github.jan.supabase.realtime.Realtime;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class VideoCallViewModel_Factory implements Factory<VideoCallViewModel> {
  private final Provider<Context> contextProvider;

  private final Provider<VideoRepository> videoRepositoryProvider;

  private final Provider<Realtime> realtimeProvider;

  public VideoCallViewModel_Factory(Provider<Context> contextProvider,
      Provider<VideoRepository> videoRepositoryProvider, Provider<Realtime> realtimeProvider) {
    this.contextProvider = contextProvider;
    this.videoRepositoryProvider = videoRepositoryProvider;
    this.realtimeProvider = realtimeProvider;
  }

  @Override
  public VideoCallViewModel get() {
    return newInstance(contextProvider.get(), videoRepositoryProvider.get(), realtimeProvider.get());
  }

  public static VideoCallViewModel_Factory create(Provider<Context> contextProvider,
      Provider<VideoRepository> videoRepositoryProvider, Provider<Realtime> realtimeProvider) {
    return new VideoCallViewModel_Factory(contextProvider, videoRepositoryProvider, realtimeProvider);
  }

  public static VideoCallViewModel newInstance(Context context, VideoRepository videoRepository,
      Realtime realtime) {
    return new VideoCallViewModel(context, videoRepository, realtime);
  }
}
