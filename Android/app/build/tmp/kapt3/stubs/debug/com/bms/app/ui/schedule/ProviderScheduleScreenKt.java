package com.bms.app.ui.schedule;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000R\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a,\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00010\u0007H\u0007\u001a\u0018\u0010\t\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0007\u001a\u0010\u0010\n\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\fH\u0007\u001a\b\u0010\r\u001a\u00020\u0001H\u0007\u001aD\u0010\u000e\u001a\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u00102\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u00122\u0006\u0010\u0014\u001a\u00020\b2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00162\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00010\u0019H\u0007\u001a(\u0010\u001a\u001a\u00020\u00012\u0014\b\u0002\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00010\u00072\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u0007\u001a\u0010\u0010\u001b\u001a\u00020\u00012\u0006\u0010\u001c\u001a\u00020\bH\u0007\u001a\"\u0010\u001d\u001a\u00020\u00012\u0006\u0010\u001e\u001a\u00020\b2\u0006\u0010\u001f\u001a\u00020 H\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b!\u0010\"\u0082\u0002\u0007\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006#"}, d2 = {"AgendaTab", "", "uiState", "Lcom/bms/app/ui/schedule/AvailabilityUiState;", "viewModel", "Lcom/bms/app/ui/schedule/AvailabilityViewModel;", "onNavigate", "Lkotlin/Function1;", "", "AvailabilityTab", "DailyPulseHeader", "state", "Lcom/bms/app/ui/schedule/AvailabilityUiState$Success;", "EmptyStatePlaceholder", "ProviderAppointmentCard", "appointment", "Lcom/bms/app/domain/model/Appointment;", "users", "", "Lcom/bms/app/domain/model/UserProfile;", "currencySymbol", "physicalFee", "", "videoFee", "onComplete", "Lkotlin/Function0;", "ProviderScheduleScreen", "StatusBadge", "status", "VibeTag", "text", "color", "Landroidx/compose/ui/graphics/Color;", "VibeTag-4WTKRHQ", "(Ljava/lang/String;J)V", "app_debug"})
public final class ProviderScheduleScreenKt {
    
    @androidx.compose.runtime.Composable()
    public static final void ProviderScheduleScreen(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onNavigate, @org.jetbrains.annotations.NotNull()
    com.bms.app.ui.schedule.AvailabilityViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void AgendaTab(@org.jetbrains.annotations.NotNull()
    com.bms.app.ui.schedule.AvailabilityUiState uiState, @org.jetbrains.annotations.NotNull()
    com.bms.app.ui.schedule.AvailabilityViewModel viewModel, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onNavigate) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void DailyPulseHeader(@org.jetbrains.annotations.NotNull()
    com.bms.app.ui.schedule.AvailabilityUiState.Success state) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void ProviderAppointmentCard(@org.jetbrains.annotations.NotNull()
    com.bms.app.domain.model.Appointment appointment, @org.jetbrains.annotations.NotNull()
    java.util.List<com.bms.app.domain.model.UserProfile> users, @org.jetbrains.annotations.NotNull()
    java.lang.String currencySymbol, double physicalFee, double videoFee, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onComplete) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void StatusBadge(@org.jetbrains.annotations.NotNull()
    java.lang.String status) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void EmptyStatePlaceholder() {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void AvailabilityTab(@org.jetbrains.annotations.NotNull()
    com.bms.app.ui.schedule.AvailabilityUiState uiState, @org.jetbrains.annotations.NotNull()
    com.bms.app.ui.schedule.AvailabilityViewModel viewModel) {
    }
}