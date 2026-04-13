package com.bms.app.ui.admin;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000r\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B/\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u00a2\u0006\u0002\u0010\fJ\u0006\u0010\u001a\u001a\u00020\u001bJ&\u0010\u001c\u001a\u00020\u001b2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"H\u0082@\u00a2\u0006\u0002\u0010#J$\u0010$\u001a\u00020\u001b2\u0006\u0010%\u001a\u00020\u00112\n\b\u0002\u0010&\u001a\u0004\u0018\u00010\u00112\b\b\u0002\u0010!\u001a\u00020\"J\u000e\u0010\'\u001a\u00020\u001b2\u0006\u0010!\u001a\u00020\"J\n\u0010(\u001a\u0004\u0018\u00010\u0011H\u0002J\u000e\u0010)\u001a\u00020\u001b2\u0006\u0010*\u001a\u00020\u0011J\u000e\u0010+\u001a\u00020\u001b2\u0006\u0010,\u001a\u00020\u0011J\u000e\u0010-\u001a\u00020\u001b2\u0006\u0010.\u001a\u00020\u0011J\u0006\u0010/\u001a\u00020\u001bJ\u000e\u00100\u001a\u00020\u001b2\u0006\u00101\u001a\u000202J\u000e\u00103\u001a\u00020\u001bH\u0082@\u00a2\u0006\u0002\u00104R\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0015\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0010\u0010\u0018\u001a\u0004\u0018\u00010\u0019X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u00065"}, d2 = {"Lcom/bms/app/ui/admin/BookingViewModel;", "Landroidx/lifecycle/ViewModel;", "profileRepository", "Lcom/bms/app/domain/repository/ProfileRepository;", "availabilityRepository", "Lcom/bms/app/domain/repository/AvailabilityRepository;", "appointmentRepository", "Lcom/bms/app/domain/repository/AppointmentRepository;", "auth", "Lio/github/jan/supabase/gotrue/Auth;", "sessionManager", "Lcom/bms/app/data/local/SupabaseSessionManager;", "(Lcom/bms/app/domain/repository/ProfileRepository;Lcom/bms/app/domain/repository/AvailabilityRepository;Lcom/bms/app/domain/repository/AppointmentRepository;Lio/github/jan/supabase/gotrue/Auth;Lcom/bms/app/data/local/SupabaseSessionManager;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/bms/app/ui/admin/BookingUiState;", "currentProviderId", "", "lastSuccessState", "Lcom/bms/app/ui/admin/BookingUiState$Success;", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "waivedOldAppointment", "Lcom/bms/app/domain/model/Appointment;", "confirmBooking", "", "fetchAvailableSlots", "provider", "Lcom/bms/app/domain/model/UserProfile;", "providerProfile", "Lcom/bms/app/domain/model/ProviderProfile;", "date", "Ljava/time/LocalDate;", "(Lcom/bms/app/domain/model/UserProfile;Lcom/bms/app/domain/model/ProviderProfile;Ljava/time/LocalDate;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "loadProviderBookingData", "userId", "waivedBy", "onDateSelected", "resolveUserId", "selectSlot", "slot", "setBookingNote", "note", "setPaymentMethod", "method", "startBookingFlow", "toggleVideoConsultation", "enabled", "", "tryRescueSession", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class BookingViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.bms.app.domain.repository.ProfileRepository profileRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.bms.app.domain.repository.AvailabilityRepository availabilityRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.bms.app.domain.repository.AppointmentRepository appointmentRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final io.github.jan.supabase.gotrue.Auth auth = null;
    @org.jetbrains.annotations.NotNull()
    private final com.bms.app.data.local.SupabaseSessionManager sessionManager = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.bms.app.ui.admin.BookingUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.bms.app.ui.admin.BookingUiState> uiState = null;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String currentProviderId;
    @org.jetbrains.annotations.Nullable()
    private com.bms.app.domain.model.Appointment waivedOldAppointment;
    @org.jetbrains.annotations.Nullable()
    private com.bms.app.ui.admin.BookingUiState.Success lastSuccessState;
    
    @javax.inject.Inject()
    public BookingViewModel(@org.jetbrains.annotations.NotNull()
    com.bms.app.domain.repository.ProfileRepository profileRepository, @org.jetbrains.annotations.NotNull()
    com.bms.app.domain.repository.AvailabilityRepository availabilityRepository, @org.jetbrains.annotations.NotNull()
    com.bms.app.domain.repository.AppointmentRepository appointmentRepository, @org.jetbrains.annotations.NotNull()
    io.github.jan.supabase.gotrue.Auth auth, @org.jetbrains.annotations.NotNull()
    com.bms.app.data.local.SupabaseSessionManager sessionManager) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.bms.app.ui.admin.BookingUiState> getUiState() {
        return null;
    }
    
    public final void loadProviderBookingData(@org.jetbrains.annotations.NotNull()
    java.lang.String userId, @org.jetbrains.annotations.Nullable()
    java.lang.String waivedBy, @org.jetbrains.annotations.NotNull()
    java.time.LocalDate date) {
    }
    
    public final void onDateSelected(@org.jetbrains.annotations.NotNull()
    java.time.LocalDate date) {
    }
    
    private final java.lang.Object fetchAvailableSlots(com.bms.app.domain.model.UserProfile provider, com.bms.app.domain.model.ProviderProfile providerProfile, java.time.LocalDate date, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    public final void selectSlot(@org.jetbrains.annotations.NotNull()
    java.lang.String slot) {
    }
    
    public final void toggleVideoConsultation(boolean enabled) {
    }
    
    public final void setPaymentMethod(@org.jetbrains.annotations.NotNull()
    java.lang.String method) {
    }
    
    public final void setBookingNote(@org.jetbrains.annotations.NotNull()
    java.lang.String note) {
    }
    
    public final void startBookingFlow() {
    }
    
    public final void confirmBooking() {
    }
    
    private final java.lang.String resolveUserId() {
        return null;
    }
    
    private final java.lang.Object tryRescueSession(kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}