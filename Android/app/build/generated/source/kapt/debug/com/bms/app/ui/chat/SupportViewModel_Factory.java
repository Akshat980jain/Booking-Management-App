package com.bms.app.ui.chat;

import com.bms.app.domain.repository.ProfileRepository;
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
public final class SupportViewModel_Factory implements Factory<SupportViewModel> {
  private final Provider<ProfileRepository> profileRepositoryProvider;

  public SupportViewModel_Factory(Provider<ProfileRepository> profileRepositoryProvider) {
    this.profileRepositoryProvider = profileRepositoryProvider;
  }

  @Override
  public SupportViewModel get() {
    return newInstance(profileRepositoryProvider.get());
  }

  public static SupportViewModel_Factory create(
      Provider<ProfileRepository> profileRepositoryProvider) {
    return new SupportViewModel_Factory(profileRepositoryProvider);
  }

  public static SupportViewModel newInstance(ProfileRepository profileRepository) {
    return new SupportViewModel(profileRepository);
  }
}
