package com.bms.app.ui.admin;

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
public final class UserDetailViewModel_Factory implements Factory<UserDetailViewModel> {
  private final Provider<ProfileRepository> profileRepositoryProvider;

  public UserDetailViewModel_Factory(Provider<ProfileRepository> profileRepositoryProvider) {
    this.profileRepositoryProvider = profileRepositoryProvider;
  }

  @Override
  public UserDetailViewModel get() {
    return newInstance(profileRepositoryProvider.get());
  }

  public static UserDetailViewModel_Factory create(
      Provider<ProfileRepository> profileRepositoryProvider) {
    return new UserDetailViewModel_Factory(profileRepositoryProvider);
  }

  public static UserDetailViewModel newInstance(ProfileRepository profileRepository) {
    return new UserDetailViewModel(profileRepository);
  }
}
