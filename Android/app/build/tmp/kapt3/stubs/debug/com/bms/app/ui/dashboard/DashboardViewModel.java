package com.bms.app.ui.dashboard;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\b\b\u0007\u0018\u00002\u00020\u0001B?\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u00a2\u0006\u0002\u0010\u0010J\u000e\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bJ\u0006\u0010\u001c\u001a\u00020\u0019J\u000e\u0010\u001d\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bJ\u000e\u0010\u001e\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bJ\u0010\u0010\u001f\u001a\u00020\u00192\b\b\u0002\u0010 \u001a\u00020!J\u0018\u0010\"\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\b\b\u0002\u0010#\u001a\u00020\u001bJ\b\u0010$\u001a\u00020\u0019H\u0002J\u0016\u0010%\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010&\u001a\u00020\u001bJ\u000e\u0010\'\u001a\u00020\u00192\u0006\u0010(\u001a\u00020!R\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00130\u0015\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017\u00a8\u0006)"}, d2 = {"Lcom/bms/app/ui/dashboard/DashboardViewModel;", "Landroidx/lifecycle/ViewModel;", "application", "Landroid/app/Application;", "profileRepository", "Lcom/bms/app/domain/repository/ProfileRepository;", "appointmentRepository", "Lcom/bms/app/domain/repository/AppointmentRepository;", "auth", "Lio/github/jan/supabase/gotrue/Auth;", "sessionManager", "Lcom/bms/app/data/local/SupabaseSessionManager;", "postgrest", "Lio/github/jan/supabase/postgrest/Postgrest;", "notificationRepository", "Lcom/bms/app/domain/repository/NotificationRepository;", "(Landroid/app/Application;Lcom/bms/app/domain/repository/ProfileRepository;Lcom/bms/app/domain/repository/AppointmentRepository;Lio/github/jan/supabase/gotrue/Auth;Lcom/bms/app/data/local/SupabaseSessionManager;Lio/github/jan/supabase/postgrest/Postgrest;Lcom/bms/app/domain/repository/NotificationRepository;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/bms/app/ui/dashboard/DashboardUiState;", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "acceptReschedule", "", "appointmentId", "", "clearError", "confirmAppointment", "declineReschedule", "loadDashboard", "isRefresh", "", "rejectAppointment", "reason", "startPolling", "suggestReschedule", "customMessage", "toggleStatus", "active", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class DashboardViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final android.app.Application application = null;
    @org.jetbrains.annotations.NotNull()
    private final com.bms.app.domain.repository.ProfileRepository profileRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.bms.app.domain.repository.AppointmentRepository appointmentRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final io.github.jan.supabase.gotrue.Auth auth = null;
    @org.jetbrains.annotations.NotNull()
    private final com.bms.app.data.local.SupabaseSessionManager sessionManager = null;
    @org.jetbrains.annotations.NotNull()
    private final io.github.jan.supabase.postgrest.Postgrest postgrest = null;
    @org.jetbrains.annotations.NotNull()
    private final com.bms.app.domain.repository.NotificationRepository notificationRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.bms.app.ui.dashboard.DashboardUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.bms.app.ui.dashboard.DashboardUiState> uiState = null;
    
    @javax.inject.Inject()
    public DashboardViewModel(@org.jetbrains.annotations.NotNull()
    android.app.Application application, @org.jetbrains.annotations.NotNull()
    com.bms.app.domain.repository.ProfileRepository profileRepository, @org.jetbrains.annotations.NotNull()
    com.bms.app.domain.repository.AppointmentRepository appointmentRepository, @org.jetbrains.annotations.NotNull()
    io.github.jan.supabase.gotrue.Auth auth, @org.jetbrains.annotations.NotNull()
    com.bms.app.data.local.SupabaseSessionManager sessionManager, @org.jetbrains.annotations.NotNull()
    io.github.jan.supabase.postgrest.Postgrest postgrest, @org.jetbrains.annotations.NotNull()
    com.bms.app.domain.repository.NotificationRepository notificationRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.bms.app.ui.dashboard.DashboardUiState> getUiState() {
        return null;
    }
    
    private final void startPolling() {
    }
    
    public final void loadDashboard(boolean isRefresh) {
    }
    
    public final void confirmAppointment(@org.jetbrains.annotations.NotNull()
    java.lang.String appointmentId) {
    }
    
    public final void rejectAppointment(@org.jetbrains.annotations.NotNull()
    java.lang.String appointmentId, @org.jetbrains.annotations.NotNull()
    java.lang.String reason) {
    }
    
    public final void suggestReschedule(@org.jetbrains.annotations.NotNull()
    java.lang.String appointmentId, @org.jetbrains.annotations.NotNull()
    java.lang.String customMessage) {
    }
    
    public final void acceptReschedule(@org.jetbrains.annotations.NotNull()
    java.lang.String appointmentId) {
    }
    
    public final void declineReschedule(@org.jetbrains.annotations.NotNull()
    java.lang.String appointmentId) {
    }
    
    public final void clearError() {
    }
    
    /**
     * Toggles the provider's Online/Offline status.
     * Uses optimistic update: the UI reflects the change immediately, and the database
     * is updated asynchronously. On failure, state is reverted.
     */
    public final void toggleStatus(boolean active) {
    }
}