package com.bms.app.ui.dashboard;

import com.bms.app.domain.repository.AppointmentRepository;
import com.bms.app.domain.repository.ProfileRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import io.github.jan.supabase.gotrue.Auth;
import io.github.jan.supabase.postgrest.Postgrest;
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
public final class AdminViewModel_Factory implements Factory<AdminViewModel> {
  private final Provider<ProfileRepository> profileRepositoryProvider;

  private final Provider<AppointmentRepository> appointmentRepositoryProvider;

  private final Provider<Auth> authProvider;

  private final Provider<Postgrest> postgrestProvider;

  public AdminViewModel_Factory(Provider<ProfileRepository> profileRepositoryProvider,
      Provider<AppointmentRepository> appointmentRepositoryProvider, Provider<Auth> authProvider,
      Provider<Postgrest> postgrestProvider) {
    this.profileRepositoryProvider = profileRepositoryProvider;
    this.appointmentRepositoryProvider = appointmentRepositoryProvider;
    this.authProvider = authProvider;
    this.postgrestProvider = postgrestProvider;
  }

  @Override
  public AdminViewModel get() {
    return newInstance(profileRepositoryProvider.get(), appointmentRepositoryProvider.get(), authProvider.get(), postgrestProvider.get());
  }

  public static AdminViewModel_Factory create(Provider<ProfileRepository> profileRepositoryProvider,
      Provider<AppointmentRepository> appointmentRepositoryProvider, Provider<Auth> authProvider,
      Provider<Postgrest> postgrestProvider) {
    return new AdminViewModel_Factory(profileRepositoryProvider, appointmentRepositoryProvider, authProvider, postgrestProvider);
  }

  public static AdminViewModel newInstance(ProfileRepository profileRepository,
      AppointmentRepository appointmentRepository, Auth auth, Postgrest postgrest) {
    return new AdminViewModel(profileRepository, appointmentRepository, auth, postgrest);
  }
}
