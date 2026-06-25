package com.bms.app.data.service;

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
public final class BmsFirebaseMessagingService_MembersInjector implements MembersInjector<BmsFirebaseMessagingService> {
  private final Provider<NotificationRepository> notificationRepositoryProvider;

  private final Provider<Auth> authProvider;

  public BmsFirebaseMessagingService_MembersInjector(
      Provider<NotificationRepository> notificationRepositoryProvider,
      Provider<Auth> authProvider) {
    this.notificationRepositoryProvider = notificationRepositoryProvider;
    this.authProvider = authProvider;
  }

  public static MembersInjector<BmsFirebaseMessagingService> create(
      Provider<NotificationRepository> notificationRepositoryProvider,
      Provider<Auth> authProvider) {
    return new BmsFirebaseMessagingService_MembersInjector(notificationRepositoryProvider, authProvider);
  }

  @Override
  public void injectMembers(BmsFirebaseMessagingService instance) {
    injectNotificationRepository(instance, notificationRepositoryProvider.get());
    injectAuth(instance, authProvider.get());
  }

  @InjectedFieldSignature("com.bms.app.data.service.BmsFirebaseMessagingService.notificationRepository")
  public static void injectNotificationRepository(BmsFirebaseMessagingService instance,
      NotificationRepository notificationRepository) {
    instance.notificationRepository = notificationRepository;
  }

  @InjectedFieldSignature("com.bms.app.data.service.BmsFirebaseMessagingService.auth")
  public static void injectAuth(BmsFirebaseMessagingService instance, Auth auth) {
    instance.auth = auth;
  }
}
