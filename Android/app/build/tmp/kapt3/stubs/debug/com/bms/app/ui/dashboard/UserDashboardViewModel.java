package com.bms.app.ui.dashboard;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\'\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ\u0018\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\b\b\u0002\u0010\u0016\u001a\u00020\u0015J\u0006\u0010\u0017\u001a\u00020\u0013R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\r0\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011\u00a8\u0006\u0018"}, d2 = {"Lcom/bms/app/ui/dashboard/UserDashboardViewModel;", "Landroidx/lifecycle/ViewModel;", "profileRepository", "Lcom/bms/app/domain/repository/ProfileRepository;", "appointmentRepository", "Lcom/bms/app/domain/repository/AppointmentRepository;", "auth", "Lio/github/jan/supabase/gotrue/Auth;", "sessionManager", "Lcom/bms/app/data/local/SupabaseSessionManager;", "(Lcom/bms/app/domain/repository/ProfileRepository;Lcom/bms/app/domain/repository/AppointmentRepository;Lio/github/jan/supabase/gotrue/Auth;Lcom/bms/app/data/local/SupabaseSessionManager;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/bms/app/ui/dashboard/UserDashboardUiState;", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "cancelBooking", "", "appointmentId", "", "reason", "loadDashboard", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class UserDashboardViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.bms.app.domain.repository.ProfileRepository profileRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.bms.app.domain.repository.AppointmentRepository appointmentRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final io.github.jan.supabase.gotrue.Auth auth = null;
    @org.jetbrains.annotations.NotNull()
    private final com.bms.app.data.local.SupabaseSessionManager sessionManager = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.bms.app.ui.dashboard.UserDashboardUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.bms.app.ui.dashboard.UserDashboardUiState> uiState = null;
    
    @javax.inject.Inject()
    public UserDashboardViewModel(@org.jetbrains.annotations.NotNull()
    com.bms.app.domain.repository.ProfileRepository profileRepository, @org.jetbrains.annotations.NotNull()
    com.bms.app.domain.repository.AppointmentRepository appointmentRepository, @org.jetbrains.annotations.NotNull()
    io.github.jan.supabase.gotrue.Auth auth, @org.jetbrains.annotations.NotNull()
    com.bms.app.data.local.SupabaseSessionManager sessionManager) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.bms.app.ui.dashboard.UserDashboardUiState> getUiState() {
        return null;
    }
    
    public final void loadDashboard() {
    }
    
    /**
     * Cancel an appointment and reload the dashboard.
     */
    public final void cancelBooking(@org.jetbrains.annotations.NotNull()
    java.lang.String appointmentId, @org.jetbrains.annotations.NotNull()
    java.lang.String reason) {
    }
}