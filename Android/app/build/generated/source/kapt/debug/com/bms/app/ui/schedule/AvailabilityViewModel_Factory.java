package com.bms.app.ui.schedule;

import com.bms.app.domain.repository.AppointmentRepository;
import com.bms.app.domain.repository.AvailabilityRepository;
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
public final class AvailabilityViewModel_Factory implements Factory<AvailabilityViewModel> {
  private final Provider<AvailabilityRepository> availabilityRepositoryProvider;

  private final Provider<ProfileRepository> profileRepositoryProvider;

  private final Provider<AppointmentRepository> appointmentRepositoryProvider;

  private final Provider<Auth> authProvider;

  public AvailabilityViewModel_Factory(
      Provider<AvailabilityRepository> availabilityRepositoryProvider,
      Provider<ProfileRepository> profileRepositoryProvider,
      Provider<AppointmentRepository> appointmentRepositoryProvider, Provider<Auth> authProvider) {
    this.availabilityRepositoryProvider = availabilityRepositoryProvider;
    this.profileRepositoryProvider = profileRepositoryProvider;
    this.appointmentRepositoryProvider = appointmentRepositoryProvider;
    this.authProvider = authProvider;
  }

  @Override
  public AvailabilityViewModel get() {
    return newInstance(availabilityRepositoryProvider.get(), profileRepositoryProvider.get(), appointmentRepositoryProvider.get(), authProvider.get());
  }

  public static AvailabilityViewModel_Factory create(
      Provider<AvailabilityRepository> availabilityRepositoryProvider,
      Provider<ProfileRepository> profileRepositoryProvider,
      Provider<AppointmentRepository> appointmentRepositoryProvider, Provider<Auth> authProvider) {
    return new AvailabilityViewModel_Factory(availabilityRepositoryProvider, profileRepositoryProvider, appointmentRepositoryProvider, authProvider);
  }

  public static AvailabilityViewModel newInstance(AvailabilityRepository availabilityRepository,
      ProfileRepository profileRepository, AppointmentRepository appointmentRepository, Auth auth) {
    return new AvailabilityViewModel(availabilityRepository, profileRepository, appointmentRepository, auth);
  }
}
