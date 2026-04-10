package com.bms.app;

import android.app.Activity;
import android.app.Service;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import com.bms.app.data.local.SessionManager;
import com.bms.app.data.local.SupabaseSessionManager;
import com.bms.app.data.repository.AppointmentRepositoryImpl;
import com.bms.app.data.repository.AuthRepositoryImpl;
import com.bms.app.data.repository.AvailabilityRepositoryImpl;
import com.bms.app.data.repository.ChatRepositoryImpl;
import com.bms.app.data.repository.ProfileRepositoryImpl;
import com.bms.app.di.AppModule_ProvideSessionManagerFactory;
import com.bms.app.di.NetworkModule_ProvideSessionManagerFactory;
import com.bms.app.di.NetworkModule_ProvideSupabaseAuthFactory;
import com.bms.app.di.NetworkModule_ProvideSupabaseClientFactory;
import com.bms.app.di.NetworkModule_ProvideSupabasePostgrestFactory;
import com.bms.app.domain.repository.ProfileRepository;
import com.bms.app.ui.admin.BookingViewModel;
import com.bms.app.ui.admin.BookingViewModel_HiltModules_KeyModule_ProvideFactory;
import com.bms.app.ui.admin.UserDetailViewModel;
import com.bms.app.ui.admin.UserDetailViewModel_HiltModules_KeyModule_ProvideFactory;
import com.bms.app.ui.auth.AuthViewModel;
import com.bms.app.ui.auth.AuthViewModel_HiltModules_KeyModule_ProvideFactory;
import com.bms.app.ui.chat.ChatViewModel;
import com.bms.app.ui.chat.ChatViewModel_HiltModules_KeyModule_ProvideFactory;
import com.bms.app.ui.chat.InboxViewModel;
import com.bms.app.ui.chat.InboxViewModel_HiltModules_KeyModule_ProvideFactory;
import com.bms.app.ui.chat.SupportViewModel;
import com.bms.app.ui.chat.SupportViewModel_HiltModules_KeyModule_ProvideFactory;
import com.bms.app.ui.dashboard.AdminViewModel;
import com.bms.app.ui.dashboard.AdminViewModel_HiltModules_KeyModule_ProvideFactory;
import com.bms.app.ui.dashboard.DashboardViewModel;
import com.bms.app.ui.dashboard.DashboardViewModel_HiltModules_KeyModule_ProvideFactory;
import com.bms.app.ui.schedule.AvailabilityViewModel;
import com.bms.app.ui.schedule.AvailabilityViewModel_HiltModules_KeyModule_ProvideFactory;
import com.bms.app.ui.settings.viewmodel.ProfessionalInfoViewModel;
import com.bms.app.ui.settings.viewmodel.ProfessionalInfoViewModel_HiltModules_KeyModule_ProvideFactory;
import com.bms.app.ui.settings.viewmodel.SettingsViewModel;
import com.bms.app.ui.settings.viewmodel.SettingsViewModel_HiltModules_KeyModule_ProvideFactory;
import com.bms.app.ui.settings.viewmodel.VisibilityViewModel;
import com.bms.app.ui.settings.viewmodel.VisibilityViewModel_HiltModules_KeyModule_ProvideFactory;
import dagger.hilt.android.ActivityRetainedLifecycle;
import dagger.hilt.android.ViewModelLifecycle;
import dagger.hilt.android.internal.builders.ActivityComponentBuilder;
import dagger.hilt.android.internal.builders.ActivityRetainedComponentBuilder;
import dagger.hilt.android.internal.builders.FragmentComponentBuilder;
import dagger.hilt.android.internal.builders.ServiceComponentBuilder;
import dagger.hilt.android.internal.builders.ViewComponentBuilder;
import dagger.hilt.android.internal.builders.ViewModelComponentBuilder;
import dagger.hilt.android.internal.builders.ViewWithFragmentComponentBuilder;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories_InternalFactoryFactory_Factory;
import dagger.hilt.android.internal.managers.ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory;
import dagger.hilt.android.internal.managers.SavedStateHandleHolder;
import dagger.hilt.android.internal.modules.ApplicationContextModule;
import dagger.hilt.android.internal.modules.ApplicationContextModule_ProvideContextFactory;
import dagger.internal.DaggerGenerated;
import dagger.internal.DoubleCheck;
import dagger.internal.MapBuilder;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import dagger.internal.SetBuilder;
import io.github.jan.supabase.SupabaseClient;
import io.github.jan.supabase.gotrue.Auth;
import io.github.jan.supabase.postgrest.Postgrest;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

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
public final class DaggerBmsApplication_HiltComponents_SingletonC {
  private DaggerBmsApplication_HiltComponents_SingletonC() {
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private ApplicationContextModule applicationContextModule;

    private Builder() {
    }

    public Builder applicationContextModule(ApplicationContextModule applicationContextModule) {
      this.applicationContextModule = Preconditions.checkNotNull(applicationContextModule);
      return this;
    }

    public BmsApplication_HiltComponents.SingletonC build() {
      Preconditions.checkBuilderRequirement(applicationContextModule, ApplicationContextModule.class);
      return new SingletonCImpl(applicationContextModule);
    }
  }

  private static final class ActivityRetainedCBuilder implements BmsApplication_HiltComponents.ActivityRetainedC.Builder {
    private final SingletonCImpl singletonCImpl;

    private SavedStateHandleHolder savedStateHandleHolder;

    private ActivityRetainedCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ActivityRetainedCBuilder savedStateHandleHolder(
        SavedStateHandleHolder savedStateHandleHolder) {
      this.savedStateHandleHolder = Preconditions.checkNotNull(savedStateHandleHolder);
      return this;
    }

    @Override
    public BmsApplication_HiltComponents.ActivityRetainedC build() {
      Preconditions.checkBuilderRequirement(savedStateHandleHolder, SavedStateHandleHolder.class);
      return new ActivityRetainedCImpl(singletonCImpl, savedStateHandleHolder);
    }
  }

  private static final class ActivityCBuilder implements BmsApplication_HiltComponents.ActivityC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private Activity activity;

    private ActivityCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ActivityCBuilder activity(Activity activity) {
      this.activity = Preconditions.checkNotNull(activity);
      return this;
    }

    @Override
    public BmsApplication_HiltComponents.ActivityC build() {
      Preconditions.checkBuilderRequirement(activity, Activity.class);
      return new ActivityCImpl(singletonCImpl, activityRetainedCImpl, activity);
    }
  }

  private static final class FragmentCBuilder implements BmsApplication_HiltComponents.FragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private Fragment fragment;

    private FragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public FragmentCBuilder fragment(Fragment fragment) {
      this.fragment = Preconditions.checkNotNull(fragment);
      return this;
    }

    @Override
    public BmsApplication_HiltComponents.FragmentC build() {
      Preconditions.checkBuilderRequirement(fragment, Fragment.class);
      return new FragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragment);
    }
  }

  private static final class ViewWithFragmentCBuilder implements BmsApplication_HiltComponents.ViewWithFragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private View view;

    private ViewWithFragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;
    }

    @Override
    public ViewWithFragmentCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public BmsApplication_HiltComponents.ViewWithFragmentC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewWithFragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl, view);
    }
  }

  private static final class ViewCBuilder implements BmsApplication_HiltComponents.ViewC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private View view;

    private ViewCBuilder(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public ViewCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public BmsApplication_HiltComponents.ViewC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, view);
    }
  }

  private static final class ViewModelCBuilder implements BmsApplication_HiltComponents.ViewModelC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private SavedStateHandle savedStateHandle;

    private ViewModelLifecycle viewModelLifecycle;

    private ViewModelCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ViewModelCBuilder savedStateHandle(SavedStateHandle handle) {
      this.savedStateHandle = Preconditions.checkNotNull(handle);
      return this;
    }

    @Override
    public ViewModelCBuilder viewModelLifecycle(ViewModelLifecycle viewModelLifecycle) {
      this.viewModelLifecycle = Preconditions.checkNotNull(viewModelLifecycle);
      return this;
    }

    @Override
    public BmsApplication_HiltComponents.ViewModelC build() {
      Preconditions.checkBuilderRequirement(savedStateHandle, SavedStateHandle.class);
      Preconditions.checkBuilderRequirement(viewModelLifecycle, ViewModelLifecycle.class);
      return new ViewModelCImpl(singletonCImpl, activityRetainedCImpl, savedStateHandle, viewModelLifecycle);
    }
  }

  private static final class ServiceCBuilder implements BmsApplication_HiltComponents.ServiceC.Builder {
    private final SingletonCImpl singletonCImpl;

    private Service service;

    private ServiceCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ServiceCBuilder service(Service service) {
      this.service = Preconditions.checkNotNull(service);
      return this;
    }

    @Override
    public BmsApplication_HiltComponents.ServiceC build() {
      Preconditions.checkBuilderRequirement(service, Service.class);
      return new ServiceCImpl(singletonCImpl, service);
    }
  }

  private static final class ViewWithFragmentCImpl extends BmsApplication_HiltComponents.ViewWithFragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private final ViewWithFragmentCImpl viewWithFragmentCImpl = this;

    private ViewWithFragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;


    }
  }

  private static final class FragmentCImpl extends BmsApplication_HiltComponents.FragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl = this;

    private FragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        Fragment fragmentParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return activityCImpl.getHiltInternalFactoryFactory();
    }

    @Override
    public ViewWithFragmentComponentBuilder viewWithFragmentComponentBuilder() {
      return new ViewWithFragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl);
    }
  }

  private static final class ViewCImpl extends BmsApplication_HiltComponents.ViewC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final ViewCImpl viewCImpl = this;

    private ViewCImpl(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }
  }

  private static final class ActivityCImpl extends BmsApplication_HiltComponents.ActivityC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl = this;

    private ActivityCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, Activity activityParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;


    }

    @Override
    public void injectMainActivity(MainActivity arg0) {
    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return DefaultViewModelFactories_InternalFactoryFactory_Factory.newInstance(getViewModelKeys(), new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl));
    }

    @Override
    public Set<String> getViewModelKeys() {
      return SetBuilder.<String>newSetBuilder(12).add(AdminViewModel_HiltModules_KeyModule_ProvideFactory.provide()).add(AuthViewModel_HiltModules_KeyModule_ProvideFactory.provide()).add(AvailabilityViewModel_HiltModules_KeyModule_ProvideFactory.provide()).add(BookingViewModel_HiltModules_KeyModule_ProvideFactory.provide()).add(ChatViewModel_HiltModules_KeyModule_ProvideFactory.provide()).add(DashboardViewModel_HiltModules_KeyModule_ProvideFactory.provide()).add(InboxViewModel_HiltModules_KeyModule_ProvideFactory.provide()).add(ProfessionalInfoViewModel_HiltModules_KeyModule_ProvideFactory.provide()).add(SettingsViewModel_HiltModules_KeyModule_ProvideFactory.provide()).add(SupportViewModel_HiltModules_KeyModule_ProvideFactory.provide()).add(UserDetailViewModel_HiltModules_KeyModule_ProvideFactory.provide()).add(VisibilityViewModel_HiltModules_KeyModule_ProvideFactory.provide()).build();
    }

    @Override
    public ViewModelComponentBuilder getViewModelComponentBuilder() {
      return new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public FragmentComponentBuilder fragmentComponentBuilder() {
      return new FragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @Override
    public ViewComponentBuilder viewComponentBuilder() {
      return new ViewCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }
  }

  private static final class ViewModelCImpl extends BmsApplication_HiltComponents.ViewModelC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ViewModelCImpl viewModelCImpl = this;

    private Provider<AdminViewModel> adminViewModelProvider;

    private Provider<AuthViewModel> authViewModelProvider;

    private Provider<AvailabilityViewModel> availabilityViewModelProvider;

    private Provider<BookingViewModel> bookingViewModelProvider;

    private Provider<ChatViewModel> chatViewModelProvider;

    private Provider<DashboardViewModel> dashboardViewModelProvider;

    private Provider<InboxViewModel> inboxViewModelProvider;

    private Provider<ProfessionalInfoViewModel> professionalInfoViewModelProvider;

    private Provider<SettingsViewModel> settingsViewModelProvider;

    private Provider<SupportViewModel> supportViewModelProvider;

    private Provider<UserDetailViewModel> userDetailViewModelProvider;

    private Provider<VisibilityViewModel> visibilityViewModelProvider;

    private ViewModelCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, SavedStateHandle savedStateHandleParam,
        ViewModelLifecycle viewModelLifecycleParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;

      initialize(savedStateHandleParam, viewModelLifecycleParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandle savedStateHandleParam,
        final ViewModelLifecycle viewModelLifecycleParam) {
      this.adminViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 0);
      this.authViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 1);
      this.availabilityViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 2);
      this.bookingViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 3);
      this.chatViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 4);
      this.dashboardViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 5);
      this.inboxViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 6);
      this.professionalInfoViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 7);
      this.settingsViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 8);
      this.supportViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 9);
      this.userDetailViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 10);
      this.visibilityViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 11);
    }

    @Override
    public Map<String, javax.inject.Provider<ViewModel>> getHiltViewModelMap() {
      return MapBuilder.<String, javax.inject.Provider<ViewModel>>newMapBuilder(12).put("com.bms.app.ui.dashboard.AdminViewModel", ((Provider) adminViewModelProvider)).put("com.bms.app.ui.auth.AuthViewModel", ((Provider) authViewModelProvider)).put("com.bms.app.ui.schedule.AvailabilityViewModel", ((Provider) availabilityViewModelProvider)).put("com.bms.app.ui.admin.BookingViewModel", ((Provider) bookingViewModelProvider)).put("com.bms.app.ui.chat.ChatViewModel", ((Provider) chatViewModelProvider)).put("com.bms.app.ui.dashboard.DashboardViewModel", ((Provider) dashboardViewModelProvider)).put("com.bms.app.ui.chat.InboxViewModel", ((Provider) inboxViewModelProvider)).put("com.bms.app.ui.settings.viewmodel.ProfessionalInfoViewModel", ((Provider) professionalInfoViewModelProvider)).put("com.bms.app.ui.settings.viewmodel.SettingsViewModel", ((Provider) settingsViewModelProvider)).put("com.bms.app.ui.chat.SupportViewModel", ((Provider) supportViewModelProvider)).put("com.bms.app.ui.admin.UserDetailViewModel", ((Provider) userDetailViewModelProvider)).put("com.bms.app.ui.settings.viewmodel.VisibilityViewModel", ((Provider) visibilityViewModelProvider)).build();
    }

    @Override
    public Map<String, Object> getHiltViewModelAssistedMap() {
      return Collections.<String, Object>emptyMap();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final ViewModelCImpl viewModelCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          ViewModelCImpl viewModelCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.viewModelCImpl = viewModelCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.bms.app.ui.dashboard.AdminViewModel 
          return (T) new AdminViewModel(singletonCImpl.bindProfileRepositoryProvider.get(), singletonCImpl.appointmentRepositoryImplProvider.get(), singletonCImpl.provideSupabaseAuthProvider.get(), singletonCImpl.provideSupabasePostgrestProvider.get());

          case 1: // com.bms.app.ui.auth.AuthViewModel 
          return (T) new AuthViewModel(singletonCImpl.authRepositoryImplProvider.get());

          case 2: // com.bms.app.ui.schedule.AvailabilityViewModel 
          return (T) new AvailabilityViewModel(singletonCImpl.availabilityRepositoryImplProvider.get(), singletonCImpl.bindProfileRepositoryProvider.get(), singletonCImpl.appointmentRepositoryImplProvider.get(), singletonCImpl.provideSupabaseAuthProvider.get());

          case 3: // com.bms.app.ui.admin.BookingViewModel 
          return (T) new BookingViewModel(singletonCImpl.bindProfileRepositoryProvider.get(), singletonCImpl.availabilityRepositoryImplProvider.get(), singletonCImpl.appointmentRepositoryImplProvider.get(), singletonCImpl.provideSupabaseAuthProvider.get(), singletonCImpl.provideSessionManagerProvider.get());

          case 4: // com.bms.app.ui.chat.ChatViewModel 
          return (T) new ChatViewModel(singletonCImpl.chatRepositoryImplProvider.get(), singletonCImpl.bindProfileRepositoryProvider.get());

          case 5: // com.bms.app.ui.dashboard.DashboardViewModel 
          return (T) new DashboardViewModel(singletonCImpl.bindProfileRepositoryProvider.get(), singletonCImpl.appointmentRepositoryImplProvider.get(), singletonCImpl.provideSupabaseAuthProvider.get(), singletonCImpl.provideSessionManagerProvider.get(), singletonCImpl.provideSupabasePostgrestProvider.get());

          case 6: // com.bms.app.ui.chat.InboxViewModel 
          return (T) new InboxViewModel(singletonCImpl.chatRepositoryImplProvider.get());

          case 7: // com.bms.app.ui.settings.viewmodel.ProfessionalInfoViewModel 
          return (T) new ProfessionalInfoViewModel(singletonCImpl.bindProfileRepositoryProvider.get(), singletonCImpl.provideSupabaseAuthProvider.get());

          case 8: // com.bms.app.ui.settings.viewmodel.SettingsViewModel 
          return (T) new SettingsViewModel(singletonCImpl.bindProfileRepositoryProvider.get(), singletonCImpl.provideSessionManagerProvider2.get(), singletonCImpl.provideSupabaseAuthProvider.get());

          case 9: // com.bms.app.ui.chat.SupportViewModel 
          return (T) new SupportViewModel(singletonCImpl.bindProfileRepositoryProvider.get());

          case 10: // com.bms.app.ui.admin.UserDetailViewModel 
          return (T) new UserDetailViewModel(singletonCImpl.bindProfileRepositoryProvider.get());

          case 11: // com.bms.app.ui.settings.viewmodel.VisibilityViewModel 
          return (T) new VisibilityViewModel(singletonCImpl.bindProfileRepositoryProvider.get(), singletonCImpl.provideSupabaseAuthProvider.get());

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ActivityRetainedCImpl extends BmsApplication_HiltComponents.ActivityRetainedC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl = this;

    private Provider<ActivityRetainedLifecycle> provideActivityRetainedLifecycleProvider;

    private ActivityRetainedCImpl(SingletonCImpl singletonCImpl,
        SavedStateHandleHolder savedStateHandleHolderParam) {
      this.singletonCImpl = singletonCImpl;

      initialize(savedStateHandleHolderParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandleHolder savedStateHandleHolderParam) {
      this.provideActivityRetainedLifecycleProvider = DoubleCheck.provider(new SwitchingProvider<ActivityRetainedLifecycle>(singletonCImpl, activityRetainedCImpl, 0));
    }

    @Override
    public ActivityComponentBuilder activityComponentBuilder() {
      return new ActivityCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public ActivityRetainedLifecycle getActivityRetainedLifecycle() {
      return provideActivityRetainedLifecycleProvider.get();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // dagger.hilt.android.ActivityRetainedLifecycle 
          return (T) ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory.provideActivityRetainedLifecycle();

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ServiceCImpl extends BmsApplication_HiltComponents.ServiceC {
    private final SingletonCImpl singletonCImpl;

    private final ServiceCImpl serviceCImpl = this;

    private ServiceCImpl(SingletonCImpl singletonCImpl, Service serviceParam) {
      this.singletonCImpl = singletonCImpl;


    }
  }

  private static final class SingletonCImpl extends BmsApplication_HiltComponents.SingletonC {
    private final ApplicationContextModule applicationContextModule;

    private final SingletonCImpl singletonCImpl = this;

    private Provider<SupabaseSessionManager> provideSessionManagerProvider;

    private Provider<SupabaseClient> provideSupabaseClientProvider;

    private Provider<Postgrest> provideSupabasePostgrestProvider;

    private Provider<ProfileRepositoryImpl> profileRepositoryImplProvider;

    private Provider<ProfileRepository> bindProfileRepositoryProvider;

    private Provider<AppointmentRepositoryImpl> appointmentRepositoryImplProvider;

    private Provider<Auth> provideSupabaseAuthProvider;

    private Provider<AuthRepositoryImpl> authRepositoryImplProvider;

    private Provider<AvailabilityRepositoryImpl> availabilityRepositoryImplProvider;

    private Provider<ChatRepositoryImpl> chatRepositoryImplProvider;

    private Provider<SessionManager> provideSessionManagerProvider2;

    private SingletonCImpl(ApplicationContextModule applicationContextModuleParam) {
      this.applicationContextModule = applicationContextModuleParam;
      initialize(applicationContextModuleParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final ApplicationContextModule applicationContextModuleParam) {
      this.provideSessionManagerProvider = DoubleCheck.provider(new SwitchingProvider<SupabaseSessionManager>(singletonCImpl, 3));
      this.provideSupabaseClientProvider = DoubleCheck.provider(new SwitchingProvider<SupabaseClient>(singletonCImpl, 2));
      this.provideSupabasePostgrestProvider = DoubleCheck.provider(new SwitchingProvider<Postgrest>(singletonCImpl, 1));
      this.profileRepositoryImplProvider = new SwitchingProvider<>(singletonCImpl, 0);
      this.bindProfileRepositoryProvider = DoubleCheck.provider((Provider) profileRepositoryImplProvider);
      this.appointmentRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<AppointmentRepositoryImpl>(singletonCImpl, 4));
      this.provideSupabaseAuthProvider = DoubleCheck.provider(new SwitchingProvider<Auth>(singletonCImpl, 5));
      this.authRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<AuthRepositoryImpl>(singletonCImpl, 6));
      this.availabilityRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<AvailabilityRepositoryImpl>(singletonCImpl, 7));
      this.chatRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<ChatRepositoryImpl>(singletonCImpl, 8));
      this.provideSessionManagerProvider2 = DoubleCheck.provider(new SwitchingProvider<SessionManager>(singletonCImpl, 9));
    }

    @Override
    public void injectBmsApplication(BmsApplication arg0) {
    }

    @Override
    public Set<Boolean> getDisableFragmentGetContextFix() {
      return Collections.<Boolean>emptySet();
    }

    @Override
    public ActivityRetainedComponentBuilder retainedComponentBuilder() {
      return new ActivityRetainedCBuilder(singletonCImpl);
    }

    @Override
    public ServiceComponentBuilder serviceComponentBuilder() {
      return new ServiceCBuilder(singletonCImpl);
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.bms.app.data.repository.ProfileRepositoryImpl 
          return (T) new ProfileRepositoryImpl(singletonCImpl.provideSupabasePostgrestProvider.get());

          case 1: // io.github.jan.supabase.postgrest.Postgrest 
          return (T) NetworkModule_ProvideSupabasePostgrestFactory.provideSupabasePostgrest(singletonCImpl.provideSupabaseClientProvider.get());

          case 2: // io.github.jan.supabase.SupabaseClient 
          return (T) NetworkModule_ProvideSupabaseClientFactory.provideSupabaseClient(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule), singletonCImpl.provideSessionManagerProvider.get());

          case 3: // com.bms.app.data.local.SupabaseSessionManager 
          return (T) NetworkModule_ProvideSessionManagerFactory.provideSessionManager(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 4: // com.bms.app.data.repository.AppointmentRepositoryImpl 
          return (T) new AppointmentRepositoryImpl(singletonCImpl.provideSupabasePostgrestProvider.get());

          case 5: // io.github.jan.supabase.gotrue.Auth 
          return (T) NetworkModule_ProvideSupabaseAuthFactory.provideSupabaseAuth(singletonCImpl.provideSupabaseClientProvider.get());

          case 6: // com.bms.app.data.repository.AuthRepositoryImpl 
          return (T) new AuthRepositoryImpl(singletonCImpl.provideSupabaseAuthProvider.get(), singletonCImpl.provideSupabasePostgrestProvider.get());

          case 7: // com.bms.app.data.repository.AvailabilityRepositoryImpl 
          return (T) new AvailabilityRepositoryImpl(singletonCImpl.provideSupabasePostgrestProvider.get());

          case 8: // com.bms.app.data.repository.ChatRepositoryImpl 
          return (T) new ChatRepositoryImpl(singletonCImpl.provideSupabaseAuthProvider.get(), singletonCImpl.provideSupabasePostgrestProvider.get());

          case 9: // com.bms.app.data.local.SessionManager 
          return (T) AppModule_ProvideSessionManagerFactory.provideSessionManager(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          default: throw new AssertionError(id);
        }
      }
    }
  }
}
