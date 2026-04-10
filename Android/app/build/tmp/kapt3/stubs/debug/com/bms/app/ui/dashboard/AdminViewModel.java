package com.bms.app.ui.dashboard;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\f\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001B\'\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ\u001e\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u00192\u0006\u0010\u001b\u001a\u00020\u0019J\u000e\u0010\u001c\u001a\u00020\u00172\u0006\u0010\u001d\u001a\u00020\u0019J\u0016\u0010\u001e\u001a\u00020\u00172\u0006\u0010\u001d\u001a\u00020\u00192\u0006\u0010\u001f\u001a\u00020\u0019J\u000e\u0010 \u001a\u00020\u00172\u0006\u0010\u001d\u001a\u00020\u0019J\u0010\u0010!\u001a\u00020\u00172\b\b\u0002\u0010\"\u001a\u00020\u0019J\u000e\u0010#\u001a\u00020\u0017H\u0082@\u00a2\u0006\u0002\u0010$J\u0012\u0010%\u001a\u00020&2\b\u0010\'\u001a\u0004\u0018\u00010(H\u0002J\u0006\u0010)\u001a\u00020\u0017J\u0006\u0010*\u001a\u00020\u0017J\u000e\u0010+\u001a\u00020\u00172\u0006\u0010\u001d\u001a\u00020\u0019R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\r0\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0013\u00a8\u0006,"}, d2 = {"Lcom/bms/app/ui/dashboard/AdminViewModel;", "Landroidx/lifecycle/ViewModel;", "profileRepository", "Lcom/bms/app/domain/repository/ProfileRepository;", "appointmentRepository", "Lcom/bms/app/domain/repository/AppointmentRepository;", "auth", "Lio/github/jan/supabase/gotrue/Auth;", "postgrest", "Lio/github/jan/supabase/postgrest/Postgrest;", "(Lcom/bms/app/domain/repository/ProfileRepository;Lcom/bms/app/domain/repository/AppointmentRepository;Lio/github/jan/supabase/gotrue/Auth;Lio/github/jan/supabase/postgrest/Postgrest;)V", "_exportState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/bms/app/ui/dashboard/ExportState;", "_uiState", "Lcom/bms/app/ui/dashboard/AdminUiState;", "exportState", "Lkotlinx/coroutines/flow/StateFlow;", "getExportState", "()Lkotlinx/coroutines/flow/StateFlow;", "uiState", "getUiState", "addUser", "", "name", "", "email", "role", "banUser", "userId", "changeUserRole", "newRole", "deactivateUser", "exportToJSON", "tableName", "fetchDashboardData", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "isNetworkException", "", "e", "", "loadAdminDashboard", "resetExportState", "suspendUser", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class AdminViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.bms.app.domain.repository.ProfileRepository profileRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.bms.app.domain.repository.AppointmentRepository appointmentRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final io.github.jan.supabase.gotrue.Auth auth = null;
    @org.jetbrains.annotations.NotNull()
    private final io.github.jan.supabase.postgrest.Postgrest postgrest = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.bms.app.ui.dashboard.AdminUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.bms.app.ui.dashboard.AdminUiState> uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.bms.app.ui.dashboard.ExportState> _exportState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.bms.app.ui.dashboard.ExportState> exportState = null;
    
    @javax.inject.Inject()
    public AdminViewModel(@org.jetbrains.annotations.NotNull()
    com.bms.app.domain.repository.ProfileRepository profileRepository, @org.jetbrains.annotations.NotNull()
    com.bms.app.domain.repository.AppointmentRepository appointmentRepository, @org.jetbrains.annotations.NotNull()
    io.github.jan.supabase.gotrue.Auth auth, @org.jetbrains.annotations.NotNull()
    io.github.jan.supabase.postgrest.Postgrest postgrest) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.bms.app.ui.dashboard.AdminUiState> getUiState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.bms.app.ui.dashboard.ExportState> getExportState() {
        return null;
    }
    
    public final void loadAdminDashboard() {
    }
    
    private final boolean isNetworkException(java.lang.Throwable e) {
        return false;
    }
    
    private final java.lang.Object fetchDashboardData(kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    public final void deactivateUser(@org.jetbrains.annotations.NotNull()
    java.lang.String userId) {
    }
    
    public final void suspendUser(@org.jetbrains.annotations.NotNull()
    java.lang.String userId) {
    }
    
    public final void banUser(@org.jetbrains.annotations.NotNull()
    java.lang.String userId) {
    }
    
    public final void addUser(@org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    java.lang.String email, @org.jetbrains.annotations.NotNull()
    java.lang.String role) {
    }
    
    public final void changeUserRole(@org.jetbrains.annotations.NotNull()
    java.lang.String userId, @org.jetbrains.annotations.NotNull()
    java.lang.String newRole) {
    }
    
    public final void exportToJSON(@org.jetbrains.annotations.NotNull()
    java.lang.String tableName) {
    }
    
    public final void resetExportState() {
    }
}