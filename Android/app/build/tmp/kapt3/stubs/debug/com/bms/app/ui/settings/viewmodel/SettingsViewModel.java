package com.bms.app.ui.settings.viewmodel;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0010\b\n\u0002\b\u0005\b\u0007\u0018\u00002\u00020\u0001B\u001f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u001a\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0002J\u0006\u0010\u001a\u001a\u00020\u0015J\u0010\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001cH\u0002J\u000e\u0010\u001e\u001a\u00020\u00152\u0006\u0010\u001f\u001a\u00020 J\"\u0010!\u001a\u00020\u00152\u0006\u0010\u0018\u001a\u00020\u001c2\u0006\u0010\"\u001a\u00020\u001c2\n\b\u0002\u0010#\u001a\u0004\u0018\u00010\u001cJ\u0016\u0010$\u001a\u00020\u00152\u0006\u0010\u001d\u001a\u00020\u001c2\u0006\u0010%\u001a\u00020\u001cJ/\u0010&\u001a\u00020\u00152\n\b\u0002\u0010\'\u001a\u0004\u0018\u00010\u001c2\n\b\u0002\u0010(\u001a\u0004\u0018\u00010\u001c2\n\b\u0002\u0010)\u001a\u0004\u0018\u00010*\u00a2\u0006\u0002\u0010+J\u0016\u0010,\u001a\u00020\u00152\u0006\u0010-\u001a\u00020\u001c2\u0006\u0010.\u001a\u00020\u001cR\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000b0\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\r0\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0011\u00a8\u0006/"}, d2 = {"Lcom/bms/app/ui/settings/viewmodel/SettingsViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/bms/app/domain/repository/ProfileRepository;", "sessionManager", "Lcom/bms/app/data/local/SessionManager;", "auth", "Lio/github/jan/supabase/gotrue/Auth;", "(Lcom/bms/app/domain/repository/ProfileRepository;Lcom/bms/app/data/local/SessionManager;Lio/github/jan/supabase/gotrue/Auth;)V", "_profileStrength", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "_uiState", "Lcom/bms/app/ui/settings/viewmodel/ProfileUiState;", "profileStrength", "Lkotlinx/coroutines/flow/StateFlow;", "getProfileStrength", "()Lkotlinx/coroutines/flow/StateFlow;", "uiState", "getUiState", "calculateProfileStrength", "", "user", "Lcom/bms/app/domain/model/UserProfile;", "provider", "Lcom/bms/app/domain/model/ProviderProfile;", "fetchProfile", "getCurrencyForCountry", "", "country", "toggleTwoFa", "enabled", "", "updateInsurance", "policyNumber", "cardUrl", "updateLocation", "city", "updateOperationalLedger", "language", "currency", "timeout", "", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;)V", "updatePersonalInfo", "fullName", "phone", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class SettingsViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.bms.app.domain.repository.ProfileRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.bms.app.data.local.SessionManager sessionManager = null;
    @org.jetbrains.annotations.NotNull()
    private final io.github.jan.supabase.gotrue.Auth auth = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.bms.app.ui.settings.viewmodel.ProfileUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.bms.app.ui.settings.viewmodel.ProfileUiState> uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Float> _profileStrength = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Float> profileStrength = null;
    
    @javax.inject.Inject()
    public SettingsViewModel(@org.jetbrains.annotations.NotNull()
    com.bms.app.domain.repository.ProfileRepository repository, @org.jetbrains.annotations.NotNull()
    com.bms.app.data.local.SessionManager sessionManager, @org.jetbrains.annotations.NotNull()
    io.github.jan.supabase.gotrue.Auth auth) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.bms.app.ui.settings.viewmodel.ProfileUiState> getUiState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Float> getProfileStrength() {
        return null;
    }
    
    private final void calculateProfileStrength(com.bms.app.domain.model.UserProfile user, com.bms.app.domain.model.ProviderProfile provider) {
    }
    
    public final void fetchProfile() {
    }
    
    public final void toggleTwoFa(boolean enabled) {
    }
    
    public final void updateOperationalLedger(@org.jetbrains.annotations.Nullable()
    java.lang.String language, @org.jetbrains.annotations.Nullable()
    java.lang.String currency, @org.jetbrains.annotations.Nullable()
    java.lang.Integer timeout) {
    }
    
    public final void updateLocation(@org.jetbrains.annotations.NotNull()
    java.lang.String country, @org.jetbrains.annotations.NotNull()
    java.lang.String city) {
    }
    
    public final void updatePersonalInfo(@org.jetbrains.annotations.NotNull()
    java.lang.String fullName, @org.jetbrains.annotations.NotNull()
    java.lang.String phone) {
    }
    
    public final void updateInsurance(@org.jetbrains.annotations.NotNull()
    java.lang.String provider, @org.jetbrains.annotations.NotNull()
    java.lang.String policyNumber, @org.jetbrains.annotations.Nullable()
    java.lang.String cardUrl) {
    }
    
    private final java.lang.String getCurrencyForCountry(java.lang.String country) {
        return null;
    }
}