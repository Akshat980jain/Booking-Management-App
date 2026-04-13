package com.bms.app.ui.dashboard;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000f\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010 \n\u0000\n\u0002\u0010$\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\u001ab\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00010\u000b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00010\u000b2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00010\u000b2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00010\u000bH\u0003\u001a\u001e\u0010\u000f\u001a\u00020\u00012\u0006\u0010\u0010\u001a\u00020\t2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00010\u000bH\u0003\u001aP\u0010\u0012\u001a\u00020\u00012\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00030\u00142\u0012\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00050\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\t2\u001a\u0010\u0018\u001a\u0016\u0012\u0004\u0012\u00020\u0003\u0012\u0006\u0012\u0004\u0018\u00010\u0005\u0012\u0004\u0012\u00020\u00010\u0019H\u0003\u001a2\u0010\u001a\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00010\u000bH\u0003\u001a\u001c\u0010\u001b\u001a\u00020\u00012\u0012\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00010\u001dH\u0003\u001a}\u0010\u001e\u001a\u00020\u00012\u000e\b\u0002\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00010\u000b2\u0014\b\u0002\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00010\u001d2#\b\u0002\u0010 \u001a\u001d\u0012\u0013\u0012\u00110\t\u00a2\u0006\f\b!\u0012\b\b\"\u0012\u0004\b\b(#\u0012\u0004\u0012\u00020\u00010\u001d2\u000e\b\u0002\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00010\u000b2\u000e\b\u0002\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00010\u000b2\b\b\u0002\u0010&\u001a\u00020\'H\u0007\u001a*\u0010(\u001a\u00020\u00012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00010\u000b2\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00010\u001dH\u0003\u001aj\u0010)\u001a\u00020\u00012\u0006\u0010*\u001a\u00020\t2\u0006\u0010\"\u001a\u00020\t2\n\b\u0002\u0010+\u001a\u0004\u0018\u00010\t2\u0006\u0010,\u001a\u00020\t2\u0006\u0010-\u001a\u00020\t2\u0006\u0010.\u001a\u00020/2\u0006\u00100\u001a\u00020/2\b\b\u0002\u00101\u001a\u00020\u00072\u0010\b\u0002\u0010 \u001a\n\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u000bH\u0003\u00f8\u0001\u0000\u00a2\u0006\u0004\b2\u00103\u001a`\u00104\u001a\u00020\u00012\f\u00105\u001a\b\u0012\u0004\u0012\u00020\u00030\u00142\f\u00106\u001a\b\u0012\u0004\u0012\u00020\u00030\u00142\u0012\u00107\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\t0\u00162\u0012\u00108\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\t0\u00162\u0012\u0010 \u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00010\u001dH\u0003\u001a\u0010\u00109\u001a\u00020\u00012\u0006\u0010:\u001a\u00020;H\u0003\u001a(\u0010<\u001a\u00020\u00012\u0006\u0010\"\u001a\u00020\t2\u0006\u0010=\u001a\u00020\t2\u0006\u0010>\u001a\u00020\t2\u0006\u0010?\u001a\u00020\tH\u0003\u0082\u0002\u0007\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006@"}, d2 = {"AppointmentReviewSheet", "", "appointment", "Lcom/bms/app/domain/model/Appointment;", "user", "Lcom/bms/app/domain/model/UserProfile;", "isLoading", "", "currencySymbol", "", "onDismiss", "Lkotlin/Function0;", "onConfirm", "onSuggestReschedule", "onDecline", "ErrorDisplay", "message", "onRefresh", "PendingApprovalsSection", "appointments", "", "userProfiles", "", "isActionLoading", "onClick", "Lkotlin/Function2;", "PendingRequestCard", "ProfileIncompleteDisplay", "onNavigate", "Lkotlin/Function1;", "ProviderDashboardScreen", "onManageAvailability", "onMessagePatient", "Lkotlin/ParameterName;", "name", "userId", "onContactSupport", "onInboxClick", "viewModel", "Lcom/bms/app/ui/dashboard/DashboardViewModel;", "RescheduleDialog", "ScheduleItem", "time", "patientRole", "badge", "detail", "badgeBg", "Landroidx/compose/ui/graphics/Color;", "badgeText", "showVideoIcon", "ScheduleItem-KSF8uv8", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;JJZLkotlin/jvm/functions/Function0;)V", "ScheduleSection", "todayAppointments", "allUpcomingAppointments", "patientNames", "patientRoles", "StatsGrid", "state", "Lcom/bms/app/ui/dashboard/DashboardUiState$Success;", "UpcomingCard", "date", "description", "initials", "app_debug"})
public final class ProviderDashboardScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void ProviderDashboardScreen(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onManageAvailability, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onNavigate, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onMessagePatient, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onContactSupport, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onInboxClick, @org.jetbrains.annotations.NotNull()
    com.bms.app.ui.dashboard.DashboardViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void PendingApprovalsSection(java.util.List<com.bms.app.domain.model.Appointment> appointments, java.util.Map<java.lang.String, com.bms.app.domain.model.UserProfile> userProfiles, java.lang.String isActionLoading, kotlin.jvm.functions.Function2<? super com.bms.app.domain.model.Appointment, ? super com.bms.app.domain.model.UserProfile, kotlin.Unit> onClick) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void PendingRequestCard(com.bms.app.domain.model.Appointment appointment, com.bms.app.domain.model.UserProfile user, boolean isLoading, kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void StatsGrid(com.bms.app.ui.dashboard.DashboardUiState.Success state) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void ScheduleSection(java.util.List<com.bms.app.domain.model.Appointment> todayAppointments, java.util.List<com.bms.app.domain.model.Appointment> allUpcomingAppointments, java.util.Map<java.lang.String, java.lang.String> patientNames, java.util.Map<java.lang.String, java.lang.String> patientRoles, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onMessagePatient) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void ProfileIncompleteDisplay(kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onNavigate) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void ErrorDisplay(java.lang.String message, kotlin.jvm.functions.Function0<kotlin.Unit> onRefresh) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void UpcomingCard(java.lang.String name, java.lang.String date, java.lang.String description, java.lang.String initials) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    private static final void AppointmentReviewSheet(com.bms.app.domain.model.Appointment appointment, com.bms.app.domain.model.UserProfile user, boolean isLoading, java.lang.String currencySymbol, kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss, kotlin.jvm.functions.Function0<kotlin.Unit> onConfirm, kotlin.jvm.functions.Function0<kotlin.Unit> onSuggestReschedule, kotlin.jvm.functions.Function0<kotlin.Unit> onDecline) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void RescheduleDialog(kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onConfirm) {
    }
}