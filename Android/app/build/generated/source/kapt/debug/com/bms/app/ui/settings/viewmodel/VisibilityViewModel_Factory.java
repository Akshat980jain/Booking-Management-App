package com.bms.app.ui.settings.viewmodel;

import com.bms.app.domain.repository.ProfileRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import io.github.jan.supabase.gotrue.Auth;
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
public final class VisibilityViewModel_Factory implements Factory<VisibilityViewModel> {
  private final Provider<ProfileRepository> repositoryProvider;

  private final Provider<Auth> authProvider;

  public VisibilityViewModel_Factory(Provider<ProfileRepository> repositoryProvider,
      Provider<Auth> authProvider) {
    this.repositoryProvider = repositoryProvider;
    this.authProvider = authProvider;
  }

  @Override
  public VisibilityViewModel get() {
    return newInstance(repositoryProvider.get(), authProvider.get());
  }

  public static VisibilityViewModel_Factory create(Provider<ProfileRepository> repositoryProvider,
      Provider<Auth> authProvider) {
    return new VisibilityViewModel_Factory(repositoryProvider, authProvider);
  }

  public static VisibilityViewModel newInstance(ProfileRepository repository, Auth auth) {
    return new VisibilityViewModel(repository, auth);
  }
}
