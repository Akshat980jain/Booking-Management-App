package com.bms.app.ui.chat;

import com.bms.app.domain.repository.ChatRepository;
import com.bms.app.domain.repository.ProfileRepository;
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
public final class ChatViewModel_Factory implements Factory<ChatViewModel> {
  private final Provider<ChatRepository> chatRepositoryProvider;

  private final Provider<ProfileRepository> profileRepositoryProvider;

  public ChatViewModel_Factory(Provider<ChatRepository> chatRepositoryProvider,
      Provider<ProfileRepository> profileRepositoryProvider) {
    this.chatRepositoryProvider = chatRepositoryProvider;
    this.profileRepositoryProvider = profileRepositoryProvider;
  }

  @Override
  public ChatViewModel get() {
    return newInstance(chatRepositoryProvider.get(), profileRepositoryProvider.get());
  }

  public static ChatViewModel_Factory create(Provider<ChatRepository> chatRepositoryProvider,
      Provider<ProfileRepository> profileRepositoryProvider) {
    return new ChatViewModel_Factory(chatRepositoryProvider, profileRepositoryProvider);
  }

  public static ChatViewModel newInstance(ChatRepository chatRepository,
      ProfileRepository profileRepository) {
    return new ChatViewModel(chatRepository, profileRepository);
  }
}
