package com.bms.app;

import com.bms.app.domain.repository.NotificationRepository;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import io.github.jan.supabase.gotrue.Auth;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class MainActivity_MembersInjector implements MembersInjector<MainActivity> {
  private final Provider<Auth> authProvider;

  private final Provider<NotificationRepository> notificationRepositoryProvider;

  public MainActivity_MembersInjector(Provider<Auth> authProvider,
      Provider<NotificationRepository> notificationRepositoryProvider) {
    this.authProvider = authProvider;
    this.notificationRepositoryProvider = notificationRepositoryProvider;
  }

  public static MembersInjector<MainActivity> create(Provider<Auth> authProvider,
      Provider<NotificationRepository> notificationRepositoryProvider) {
    return new MainActivity_MembersInjector(authProvider, notificationRepositoryProvider);
  }

  @Override
  public void injectMembers(MainActivity instance) {
    injectAuth(instance, authProvider.get());
    injectNotificationRepository(instance, notificationRepositoryProvider.get());
  }

  @InjectedFieldSignature("com.bms.app.MainActivity.auth")
  public static void injectAuth(MainActivity instance, Auth auth) {
    instance.auth = auth;
  }

  @InjectedFieldSignature("com.bms.app.MainActivity.notificationRepository")
  public static void injectNotificationRepository(MainActivity instance,
      NotificationRepository notificationRepository) {
    instance.notificationRepository = notificationRepository;
  }
}
