package com.bms.app.ui.schedule;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\t\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\'\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ\u0018\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\r2\b\u0010\u001d\u001a\u0004\u0018\u00010\rJ\u000e\u0010\u001e\u001a\u00020\u001b2\u0006\u0010\u001f\u001a\u00020\rJ\u0006\u0010 \u001a\u00020\u001bJ\u000e\u0010!\u001a\u00020\u001b2\u0006\u0010\u001f\u001a\u00020\rJ\b\u0010\"\u001a\u00020\u001bH\u0002J,\u0010#\u001a\u00020\u001b2\f\u0010$\u001a\b\u0012\u0004\u0012\u00020&0%2\u0006\u0010\'\u001a\u00020(2\u0006\u0010)\u001a\u00020(2\u0006\u0010*\u001a\u00020+R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\r0\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0017\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00100\u0017\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019\u00a8\u0006,"}, d2 = {"Lcom/bms/app/ui/schedule/AvailabilityViewModel;", "Landroidx/lifecycle/ViewModel;", "availabilityRepository", "Lcom/bms/app/domain/repository/AvailabilityRepository;", "profileRepository", "Lcom/bms/app/domain/repository/ProfileRepository;", "appointmentRepository", "Lcom/bms/app/domain/repository/AppointmentRepository;", "auth", "Lio/github/jan/supabase/gotrue/Auth;", "(Lcom/bms/app/domain/repository/AvailabilityRepository;Lcom/bms/app/domain/repository/ProfileRepository;Lcom/bms/app/domain/repository/AppointmentRepository;Lio/github/jan/supabase/gotrue/Auth;)V", "_uiEvents", "Lkotlinx/coroutines/channels/Channel;", "", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/bms/app/ui/schedule/AvailabilityUiState;", "providerId", "uiEvents", "Lkotlinx/coroutines/flow/Flow;", "getUiEvents", "()Lkotlinx/coroutines/flow/Flow;", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "addBlockedDate", "", "date", "reason", "completeAppointment", "id", "loadAvailability", "removeBlockedDate", "startPolling", "updateAvailabilitySettings", "slots", "", "Lcom/bms/app/domain/model/AvailabilitySlot;", "physicalFee", "", "videoFee", "videoEnabled", "", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class AvailabilityViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.bms.app.domain.repository.AvailabilityRepository availabilityRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.bms.app.domain.repository.ProfileRepository profileRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.bms.app.domain.repository.AppointmentRepository appointmentRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final io.github.jan.supabase.gotrue.Auth auth = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.bms.app.ui.schedule.AvailabilityUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.bms.app.ui.schedule.AvailabilityUiState> uiState = null;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String providerId;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.channels.Channel<java.lang.String> _uiEvents = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.lang.String> uiEvents = null;
    
    @javax.inject.Inject()
    public AvailabilityViewModel(@org.jetbrains.annotations.NotNull()
    com.bms.app.domain.repository.AvailabilityRepository availabilityRepository, @org.jetbrains.annotations.NotNull()
    com.bms.app.domain.repository.ProfileRepository profileRepository, @org.jetbrains.annotations.NotNull()
    com.bms.app.domain.repository.AppointmentRepository appointmentRepository, @org.jetbrains.annotations.NotNull()
    io.github.jan.supabase.gotrue.Auth auth) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.bms.app.ui.schedule.AvailabilityUiState> getUiState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.lang.String> getUiEvents() {
        return null;
    }
    
    private final void startPolling() {
    }
    
    public final void loadAvailability() {
    }
    
    public final void updateAvailabilitySettings(@org.jetbrains.annotations.NotNull()
    java.util.List<com.bms.app.domain.model.AvailabilitySlot> slots, double physicalFee, double videoFee, boolean videoEnabled) {
    }
    
    public final void addBlockedDate(@org.jetbrains.annotations.NotNull()
    java.lang.String date, @org.jetbrains.annotations.Nullable()
    java.lang.String reason) {
    }
    
    public final void removeBlockedDate(@org.jetbrains.annotations.NotNull()
    java.lang.String id) {
    }
    
    public final void completeAppointment(@org.jetbrains.annotations.NotNull()
    java.lang.String id) {
    }
}