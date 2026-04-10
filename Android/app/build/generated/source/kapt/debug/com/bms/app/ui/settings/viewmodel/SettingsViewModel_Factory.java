package com.bms.app.ui.settings.viewmodel;

import com.bms.app.data.local.SessionManager;
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
public final class SettingsViewModel_Factory implements Factory<SettingsViewModel> {
  private final Provider<ProfileRepository> repositoryProvider;

  private final Provider<SessionManager> sessionManagerProvider;

  private final Provider<Auth> authProvider;

  public SettingsViewModel_Factory(Provider<ProfileRepository> repositoryProvider,
      Provider<SessionManager> sessionManagerProvider, Provider<Auth> authProvider) {
    this.repositoryProvider = repositoryProvider;
    this.sessionManagerProvider = sessionManagerProvider;
    this.authProvider = authProvider;
  }

  @Override
  public SettingsViewModel get() {
    return newInstance(repositoryProvider.get(), sessionManagerProvider.get(), authProvider.get());
  }

  public static SettingsViewModel_Factory create(Provider<ProfileRepository> repositoryProvider,
      Provider<SessionManager> sessionManagerProvider, Provider<Auth> authProvider) {
    return new SettingsViewModel_Factory(repositoryProvider, sessionManagerProvider, authProvider);
  }

  public static SettingsViewModel newInstance(ProfileRepository repository,
      SessionManager sessionManager, Auth auth) {
    return new SettingsViewModel(repository, sessionManager, auth);
  }
}
