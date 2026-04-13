package com.bms.app.ui.dashboard;

import com.bms.app.data.local.SupabaseSessionManager;
import com.bms.app.domain.repository.AppointmentRepository;
import com.bms.app.domain.repository.NotificationRepository;
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
public final class UserDashboardViewModel_Factory implements Factory<UserDashboardViewModel> {
  private final Provider<ProfileRepository> profileRepositoryProvider;

  private final Provider<AppointmentRepository> appointmentRepositoryProvider;

  private final Provider<Auth> authProvider;

  private final Provider<SupabaseSessionManager> sessionManagerProvider;

  private final Provider<NotificationRepository> notificationRepositoryProvider;

  public UserDashboardViewModel_Factory(Provider<ProfileRepository> profileRepositoryProvider,
      Provider<AppointmentRepository> appointmentRepositoryProvider, Provider<Auth> authProvider,
      Provider<SupabaseSessionManager> sessionManagerProvider,
      Provider<NotificationRepository> notificationRepositoryProvider) {
    this.profileRepositoryProvider = profileRepositoryProvider;
    this.appointmentRepositoryProvider = appointmentRepositoryProvider;
    this.authProvider = authProvider;
    this.sessionManagerProvider = sessionManagerProvider;
    this.notificationRepositoryProvider = notificationRepositoryProvider;
  }

  @Override
  public UserDashboardViewModel get() {
    return newInstance(profileRepositoryProvider.get(), appointmentRepositoryProvider.get(), authProvider.get(), sessionManagerProvider.get(), notificationRepositoryProvider.get());
  }

  public static UserDashboardViewModel_Factory create(
      Provider<ProfileRepository> profileRepositoryProvider,
      Provider<AppointmentRepository> appointmentRepositoryProvider, Provider<Auth> authProvider,
      Provider<SupabaseSessionManager> sessionManagerProvider,
      Provider<NotificationRepository> notificationRepositoryProvider) {
    return new UserDashboardViewModel_Factory(profileRepositoryProvider, appointmentRepositoryProvider, authProvider, sessionManagerProvider, notificationRepositoryProvider);
  }

  public static UserDashboardViewModel newInstance(ProfileRepository profileRepository,
      AppointmentRepository appointmentRepository, Auth auth, SupabaseSessionManager sessionManager,
      NotificationRepository notificationRepository) {
    return new UserDashboardViewModel(profileRepository, appointmentRepository, auth, sessionManager, notificationRepository);
  }
}
