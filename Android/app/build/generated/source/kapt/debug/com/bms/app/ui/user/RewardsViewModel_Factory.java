package com.bms.app.ui.user;

import com.bms.app.domain.repository.LoyaltyRepository;
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
public final class RewardsViewModel_Factory implements Factory<RewardsViewModel> {
  private final Provider<LoyaltyRepository> loyaltyRepositoryProvider;

  private final Provider<Auth> authProvider;

  public RewardsViewModel_Factory(Provider<LoyaltyRepository> loyaltyRepositoryProvider,
      Provider<Auth> authProvider) {
    this.loyaltyRepositoryProvider = loyaltyRepositoryProvider;
    this.authProvider = authProvider;
  }

  @Override
  public RewardsViewModel get() {
    return newInstance(loyaltyRepositoryProvider.get(), authProvider.get());
  }

  public static RewardsViewModel_Factory create(
      Provider<LoyaltyRepository> loyaltyRepositoryProvider, Provider<Auth> authProvider) {
    return new RewardsViewModel_Factory(loyaltyRepositoryProvider, authProvider);
  }

  public static RewardsViewModel newInstance(LoyaltyRepository loyaltyRepository, Auth auth) {
    return new RewardsViewModel(loyaltyRepository, auth);
  }
}
