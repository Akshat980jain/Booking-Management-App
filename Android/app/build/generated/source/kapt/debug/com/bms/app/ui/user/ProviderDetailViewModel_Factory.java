package com.bms.app.ui.user;

import com.bms.app.domain.repository.ProfileRepository;
import com.bms.app.domain.repository.ReviewRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
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
public final class ProviderDetailViewModel_Factory implements Factory<ProviderDetailViewModel> {
  private final Provider<ProfileRepository> profileRepositoryProvider;

  private final Provider<ReviewRepository> reviewRepositoryProvider;

  public ProviderDetailViewModel_Factory(Provider<ProfileRepository> profileRepositoryProvider,
      Provider<ReviewRepository> reviewRepositoryProvider) {
    this.profileRepositoryProvider = profileRepositoryProvider;
    this.reviewRepositoryProvider = reviewRepositoryProvider;
  }

  @Override
  public ProviderDetailViewModel get() {
    return newInstance(profileRepositoryProvider.get(), reviewRepositoryProvider.get());
  }

  public static ProviderDetailViewModel_Factory create(
      Provider<ProfileRepository> profileRepositoryProvider,
      Provider<ReviewRepository> reviewRepositoryProvider) {
    return new ProviderDetailViewModel_Factory(profileRepositoryProvider, reviewRepositoryProvider);
  }

  public static ProviderDetailViewModel newInstance(ProfileRepository profileRepository,
      ReviewRepository reviewRepository) {
    return new ProviderDetailViewModel(profileRepository, reviewRepository);
  }
}
