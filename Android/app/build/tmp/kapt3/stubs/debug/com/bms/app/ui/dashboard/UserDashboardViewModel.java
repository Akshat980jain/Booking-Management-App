package com.bms.app.ui.dashboard;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0014\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u000b\n\u0000\b\u0007\u0018\u00002\u00020\u0001B/\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u00a2\u0006\u0002\u0010\fJ\u000e\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017J\u0018\u0010\u0018\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\b\b\u0002\u0010\u0019\u001a\u00020\u0017J\u0006\u0010\u001a\u001a\u00020\u0015J\u000e\u0010\u001b\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017J\u0006\u0010\u001c\u001a\u00020\u0015J\u000e\u0010\u001d\u001a\u00020\u00152\u0006\u0010\u001e\u001a\u00020\u0017J.\u0010\u001f\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010 \u001a\u00020\u00172\u0006\u0010!\u001a\u00020\u00172\u0006\u0010\"\u001a\u00020\u00172\u0006\u0010\u0019\u001a\u00020\u0017J\u0010\u0010#\u001a\u00020\u00152\u0006\u0010$\u001a\u00020\u0017H\u0002J\u000e\u0010%\u001a\u00020\u00152\u0006\u0010&\u001a\u00020\u0017J\u000e\u0010\'\u001a\u00020\u00152\u0006\u0010(\u001a\u00020\u0017J \u0010)\u001a\u00020\u00152\b\u0010*\u001a\u0004\u0018\u00010\u00172\u0006\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020.R\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013\u00a8\u0006/"}, d2 = {"Lcom/bms/app/ui/dashboard/UserDashboardViewModel;", "Landroidx/lifecycle/ViewModel;", "profileRepository", "Lcom/bms/app/domain/repository/ProfileRepository;", "appointmentRepository", "Lcom/bms/app/domain/repository/AppointmentRepository;", "auth", "Lio/github/jan/supabase/gotrue/Auth;", "sessionManager", "Lcom/bms/app/data/local/SupabaseSessionManager;", "notificationRepository", "Lcom/bms/app/domain/repository/NotificationRepository;", "(Lcom/bms/app/domain/repository/ProfileRepository;Lcom/bms/app/domain/repository/AppointmentRepository;Lio/github/jan/supabase/gotrue/Auth;Lcom/bms/app/data/local/SupabaseSessionManager;Lcom/bms/app/domain/repository/NotificationRepository;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/bms/app/ui/dashboard/UserDashboardUiState;", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "acceptReschedule", "", "appointmentId", "", "cancelBooking", "reason", "clearComparison", "declineReschedule", "loadDashboard", "markNotificationAsRead", "notificationId", "requestReschedule", "newDate", "newStartTime", "newEndTime", "startNotificationsListener", "userId", "toggleComparisonSelection", "providerId", "toggleFavorite", "providerProfileId", "updateFilters", "profession", "minRating", "", "showVideoOnly", "", "app_debug"})
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
    private final com.bms.app.domain.repository.NotificationRepository notificationRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.bms.app.ui.dashboard.UserDashboardUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.bms.app.ui.dashboard.UserDashboardUiState> uiState = null;
    
    @javax.inject.Inject()
    public UserDashboardViewModel(@org.jetbrains.annotations.NotNull()
    com.bms.app.domain.repository.ProfileRepository profileRepository, @org.jetbrains.annotations.NotNull()
    com.bms.app.domain.repository.AppointmentRepository appointmentRepository, @org.jetbrains.annotations.NotNull()
    io.github.jan.supabase.gotrue.Auth auth, @org.jetbrains.annotations.NotNull()
    com.bms.app.data.local.SupabaseSessionManager sessionManager, @org.jetbrains.annotations.NotNull()
    com.bms.app.domain.repository.NotificationRepository notificationRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.bms.app.ui.dashboard.UserDashboardUiState> getUiState() {
        return null;
    }
    
    public final void loadDashboard() {
    }
    
    private final void startNotificationsListener(java.lang.String userId) {
    }
    
    public final void markNotificationAsRead(@org.jetbrains.annotations.NotNull()
    java.lang.String notificationId) {
    }
    
    /**
     * Toggles favorite status for a provider and updates local state.
     */
    public final void toggleFavorite(@org.jetbrains.annotations.NotNull()
    java.lang.String providerProfileId) {
    }
    
    /**
     * Cancel an appointment and reload the dashboard.
     */
    public final void cancelBooking(@org.jetbrains.annotations.NotNull()
    java.lang.String appointmentId, @org.jetbrains.annotations.NotNull()
    java.lang.String reason) {
    }
    
    /**
     * Toggles selection for comparison (max 3)
     */
    public final void toggleComparisonSelection(@org.jetbrains.annotations.NotNull()
    java.lang.String providerId) {
    }
    
    public final void clearComparison() {
    }
    
    public final void updateFilters(@org.jetbrains.annotations.Nullable()
    java.lang.String profession, float minRating, boolean showVideoOnly) {
    }
    
    public final void acceptReschedule(@org.jetbrains.annotations.NotNull()
    java.lang.String appointmentId) {
    }
    
    public final void declineReschedule(@org.jetbrains.annotations.NotNull()
    java.lang.String appointmentId) {
    }
    
    public final void requestReschedule(@org.jetbrains.annotations.NotNull()
    java.lang.String appointmentId, @org.jetbrains.annotations.NotNull()
    java.lang.String newDate, @org.jetbrains.annotations.NotNull()
    java.lang.String newStartTime, @org.jetbrains.annotations.NotNull()
    java.lang.String newEndTime, @org.jetbrains.annotations.NotNull()
    java.lang.String reason) {
    }
}