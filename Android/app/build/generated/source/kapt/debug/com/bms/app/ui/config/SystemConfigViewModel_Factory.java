package com.bms.app.ui.config;

import com.bms.app.domain.repository.ConfigRepository;
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
public final class SystemConfigViewModel_Factory implements Factory<SystemConfigViewModel> {
  private final Provider<ConfigRepository> configRepositoryProvider;

  public SystemConfigViewModel_Factory(Provider<ConfigRepository> configRepositoryProvider) {
    this.configRepositoryProvider = configRepositoryProvider;
  }

  @Override
  public SystemConfigViewModel get() {
    return newInstance(configRepositoryProvider.get());
  }

  public static SystemConfigViewModel_Factory create(
      Provider<ConfigRepository> configRepositoryProvider) {
    return new SystemConfigViewModel_Factory(configRepositoryProvider);
  }

  public static SystemConfigViewModel newInstance(ConfigRepository configRepository) {
    return new SystemConfigViewModel(configRepository);
  }
}
