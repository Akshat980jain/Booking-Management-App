package com.bms.app.ui.admin;

import com.bms.app.data.local.SupabaseSessionManager;
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
public final class BookingViewModel_Factory implements Factory<BookingViewModel> {
  private final Provider<ProfileRepository> profileRepositoryProvider;

  private final Provider<AvailabilityRepository> availabilityRepositoryProvider;

  private final Provider<AppointmentRepository> appointmentRepositoryProvider;

  private final Provider<Auth> authProvider;

  private final Provider<SupabaseSessionManager> sessionManagerProvider;

  public BookingViewModel_Factory(Provider<ProfileRepository> profileRepositoryProvider,
      Provider<AvailabilityRepository> availabilityRepositoryProvider,
      Provider<AppointmentRepository> appointmentRepositoryProvider, Provider<Auth> authProvider,
      Provider<SupabaseSessionManager> sessionManagerProvider) {
    this.profileRepositoryProvider = profileRepositoryProvider;
    this.availabilityRepositoryProvider = availabilityRepositoryProvider;
    this.appointmentRepositoryProvider = appointmentRepositoryProvider;
    this.authProvider = authProvider;
    this.sessionManagerProvider = sessionManagerProvider;
  }

  @Override
  public BookingViewModel get() {
    return newInstance(profileRepositoryProvider.get(), availabilityRepositoryProvider.get(), appointmentRepositoryProvider.get(), authProvider.get(), sessionManagerProvider.get());
  }

  public static BookingViewModel_Factory create(
      Provider<ProfileRepository> profileRepositoryProvider,
      Provider<AvailabilityRepository> availabilityRepositoryProvider,
      Provider<AppointmentRepository> appointmentRepositoryProvider, Provider<Auth> authProvider,
      Provider<SupabaseSessionManager> sessionManagerProvider) {
    return new BookingViewModel_Factory(profileRepositoryProvider, availabilityRepositoryProvider, appointmentRepositoryProvider, authProvider, sessionManagerProvider);
  }

  public static BookingViewModel newInstance(ProfileRepository profileRepository,
      AvailabilityRepository availabilityRepository, AppointmentRepository appointmentRepository,
      Auth auth, SupabaseSessionManager sessionManager) {
    return new BookingViewModel(profileRepository, availabilityRepository, appointmentRepository, auth, sessionManager);
  }
}
