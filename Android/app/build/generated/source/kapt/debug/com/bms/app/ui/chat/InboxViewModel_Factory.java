package com.bms.app.ui.chat;

import com.bms.app.domain.repository.ChatRepository;
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
public final class InboxViewModel_Factory implements Factory<InboxViewModel> {
  private final Provider<ChatRepository> chatRepositoryProvider;

  public InboxViewModel_Factory(Provider<ChatRepository> chatRepositoryProvider) {
    this.chatRepositoryProvider = chatRepositoryProvider;
  }

  @Override
  public InboxViewModel get() {
    return newInstance(chatRepositoryProvider.get());
  }

  public static InboxViewModel_Factory create(Provider<ChatRepository> chatRepositoryProvider) {
    return new InboxViewModel_Factory(chatRepositoryProvider);
  }

  public static InboxViewModel newInstance(ChatRepository chatRepository) {
    return new InboxViewModel(chatRepository);
  }
}
