package com.bms.app.ui.user;

import com.bms.app.domain.repository.AuthRepository;
import com.bms.app.domain.repository.ReviewRepository;
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
public final class ReviewViewModel_Factory implements Factory<ReviewViewModel> {
  private final Provider<ReviewRepository> reviewRepositoryProvider;

  private final Provider<AuthRepository> authRepositoryProvider;

  public ReviewViewModel_Factory(Provider<ReviewRepository> reviewRepositoryProvider,
      Provider<AuthRepository> authRepositoryProvider) {
    this.reviewRepositoryProvider = reviewRepositoryProvider;
    this.authRepositoryProvider = authRepositoryProvider;
  }

  @Override
  public ReviewViewModel get() {
    return newInstance(reviewRepositoryProvider.get(), authRepositoryProvider.get());
  }

  public static ReviewViewModel_Factory create(Provider<ReviewRepository> reviewRepositoryProvider,
      Provider<AuthRepository> authRepositoryProvider) {
    return new ReviewViewModel_Factory(reviewRepositoryProvider, authRepositoryProvider);
  }

  public static ReviewViewModel newInstance(ReviewRepository reviewRepository,
      AuthRepository authRepository) {
    return new ReviewViewModel(reviewRepository, authRepository);
  }
}
