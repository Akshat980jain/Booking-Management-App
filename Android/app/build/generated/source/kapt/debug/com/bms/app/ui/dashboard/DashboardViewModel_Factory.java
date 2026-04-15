package com.bms.app.ui.dashboard;

import android.app.Application;
import com.bms.app.data.local.SupabaseSessionManager;
import com.bms.app.domain.repository.AppointmentRepository;
import com.bms.app.domain.repository.NotificationRepository;
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
public final class DashboardViewModel_Factory implements Factory<DashboardViewModel> {
  private final Provider<Application> applicationProvider;

  private final Provider<ProfileRepository> profileRepositoryProvider;

  private final Provider<AppointmentRepository> appointmentRepositoryProvider;

  private final Provider<Auth> authProvider;

  private final Provider<SupabaseSessionManager> sessionManagerProvider;

  private final Provider<Postgrest> postgrestProvider;

  private final Provider<NotificationRepository> notificationRepositoryProvider;

  public DashboardViewModel_Factory(Provider<Application> applicationProvider,
      Provider<ProfileRepository> profileRepositoryProvider,
      Provider<AppointmentRepository> appointmentRepositoryProvider, Provider<Auth> authProvider,
      Provider<SupabaseSessionManager> sessionManagerProvider,
      Provider<Postgrest> postgrestProvider,
      Provider<NotificationRepository> notificationRepositoryProvider) {
    this.applicationProvider = applicationProvider;
    this.profileRepositoryProvider = profileRepositoryProvider;
    this.appointmentRepositoryProvider = appointmentRepositoryProvider;
    this.authProvider = authProvider;
    this.sessionManagerProvider = sessionManagerProvider;
    this.postgrestProvider = postgrestProvider;
    this.notificationRepositoryProvider = notificationRepositoryProvider;
  }

  @Override
  public DashboardViewModel get() {
    return newInstance(applicationProvider.get(), profileRepositoryProvider.get(), appointmentRepositoryProvider.get(), authProvider.get(), sessionManagerProvider.get(), postgrestProvider.get(), notificationRepositoryProvider.get());
  }

  public static DashboardViewModel_Factory create(Provider<Application> applicationProvider,
      Provider<ProfileRepository> profileRepositoryProvider,
      Provider<AppointmentRepository> appointmentRepositoryProvider, Provider<Auth> authProvider,
      Provider<SupabaseSessionManager> sessionManagerProvider,
      Provider<Postgrest> postgrestProvider,
      Provider<NotificationRepository> notificationRepositoryProvider) {
    return new DashboardViewModel_Factory(applicationProvider, profileRepositoryProvider, appointmentRepositoryProvider, authProvider, sessionManagerProvider, postgrestProvider, notificationRepositoryProvider);
  }

  public static DashboardViewModel newInstance(Application application,
      ProfileRepository profileRepository, AppointmentRepository appointmentRepository, Auth auth,
      SupabaseSessionManager sessionManager, Postgrest postgrest,
      NotificationRepository notificationRepository) {
    return new DashboardViewModel(application, profileRepository, appointmentRepository, auth, sessionManager, postgrest, notificationRepository);
  }
}
