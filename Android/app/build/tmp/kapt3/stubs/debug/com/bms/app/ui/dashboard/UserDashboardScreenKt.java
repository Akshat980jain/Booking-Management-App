package com.bms.app.ui.dashboard;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000r\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a\u0088\u0001\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\u000e\b\u0002\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\u000e\b\u0002\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\b\b\u0002\u0010\u000e\u001a\u00020\u000fH\u0007\u001a&\u0010\u0010\u001a\u00020\u00012\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00010\bH\u0003\u001a \u0010\u0015\u001a\u00020\u00012\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00052\u0006\u0010\u0019\u001a\u00020\u0005H\u0003\u001a@\u0010\u001a\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\b\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u00052\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00010\bH\u0003\u001aB\u0010\u001f\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00010\b2\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00010\bH\u0003\u001a\u00ea\u0001\u0010#\u001a\u00020\u00012\u0006\u0010$\u001a\u00020%2\u0012\u0010&\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\'2\f\u0010(\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\f\u0010)\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\u0012\u0010*\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\'2\u0018\u0010+\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010,2\u0018\u0010-\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010,2\u0012\u0010.\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\'2\u0012\u0010/\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\'2$\u00100\u001a \u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0001012\u0012\u00102\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\'H\u0003\u001a\u00b7\u0001\u00103\u001a\u00020\u00012\u0014\b\u0002\u0010&\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\'2#\b\u0002\u0010*\u001a\u001d\u0012\u0013\u0012\u00110\u0005\u00a2\u0006\f\b4\u0012\b\b5\u0012\u0004\b\b(6\u0012\u0004\u0012\u00020\u00010\'28\b\u0002\u0010+\u001a2\u0012\u0013\u0012\u00110\u0005\u00a2\u0006\f\b4\u0012\b\b5\u0012\u0004\b\b(6\u0012\u0013\u0012\u00110\u0005\u00a2\u0006\f\b4\u0012\b\b5\u0012\u0004\b\b(7\u0012\u0004\u0012\u00020\u00010,2\u000e\b\u0002\u0010(\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\u000e\b\u0002\u0010)\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\u000e\b\u0002\u00108\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\b\b\u0002\u00109\u001a\u00020:H\u0007\u001a\u0010\u0010;\u001a\u00020\u00052\u0006\u0010<\u001a\u00020\u0005H\u0002\u001a\b\u0010=\u001a\u00020\u0005H\u0002\u00a8\u0006>"}, d2 = {"BookingListItem", "", "appointment", "Lcom/bms/app/domain/model/Appointment;", "providerName", "", "providerSpecialty", "onMessage", "Lkotlin/Function0;", "onClick", "onRate", "onPayNow", "onDownloadInvoice", "onTelehealthJoin", "modifier", "Landroidx/compose/ui/Modifier;", "CalendarDateChip", "date", "Ljava/time/LocalDate;", "isSelected", "", "EmptyStateCard", "icon", "Landroidx/compose/ui/graphics/vector/ImageVector;", "title", "subtitle", "NextAppointmentBanner", "provider", "Lcom/bms/app/domain/model/ProviderProfile;", "realName", "onCancel", "RescheduleRequestBanner", "onAccept", "onDecline", "onRebook", "UserDashboardContent", "state", "Lcom/bms/app/ui/dashboard/UserDashboardUiState$Success;", "onNavigate", "Lkotlin/Function1;", "onBrowseProviders", "onViewAllBookings", "onMessageProvider", "onRebookProvider", "Lkotlin/Function2;", "onCancelBooking", "onAcceptReschedule", "onDeclineReschedule", "onRescheduleAppointment", "Lkotlin/Function4;", "onShowSnackbar", "UserDashboardScreen", "Lkotlin/ParameterName;", "name", "providerId", "oldAppointmentId", "onInboxClick", "viewModel", "Lcom/bms/app/ui/dashboard/UserDashboardViewModel;", "friendlyDate", "dateStr", "timeGreeting", "app_debug"})
public final class UserDashboardScreenKt {
    
    private static final java.lang.String friendlyDate(java.lang.String dateStr) {
        return null;
    }
    
    private static final java.lang.String timeGreeting() {
        return null;
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void UserDashboardScreen(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onNavigate, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onMessageProvider, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super java.lang.String, ? super java.lang.String, kotlin.Unit> onRebookProvider, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBrowseProviders, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onViewAllBookings, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onInboxClick, @org.jetbrains.annotations.NotNull()
    com.bms.app.ui.dashboard.UserDashboardViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void UserDashboardContent(com.bms.app.ui.dashboard.UserDashboardUiState.Success state, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onNavigate, kotlin.jvm.functions.Function0<kotlin.Unit> onBrowseProviders, kotlin.jvm.functions.Function0<kotlin.Unit> onViewAllBookings, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onMessageProvider, kotlin.jvm.functions.Function2<? super java.lang.String, ? super java.lang.String, kotlin.Unit> onRebookProvider, kotlin.jvm.functions.Function2<? super java.lang.String, ? super java.lang.String, kotlin.Unit> onCancelBooking, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onAcceptReschedule, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onDeclineReschedule, kotlin.jvm.functions.Function4<? super com.bms.app.domain.model.Appointment, ? super java.lang.String, ? super java.lang.String, ? super java.lang.String, kotlin.Unit> onRescheduleAppointment, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onShowSnackbar) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void NextAppointmentBanner(com.bms.app.domain.model.Appointment appointment, com.bms.app.domain.model.ProviderProfile provider, java.lang.String realName, kotlin.jvm.functions.Function0<kotlin.Unit> onMessage, kotlin.jvm.functions.Function0<kotlin.Unit> onCancel) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void BookingListItem(@org.jetbrains.annotations.NotNull()
    com.bms.app.domain.model.Appointment appointment, @org.jetbrains.annotations.NotNull()
    java.lang.String providerName, @org.jetbrains.annotations.NotNull()
    java.lang.String providerSpecialty, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onMessage, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onRate, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onPayNow, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onDownloadInvoice, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onTelehealthJoin, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void EmptyStateCard(androidx.compose.ui.graphics.vector.ImageVector icon, java.lang.String title, java.lang.String subtitle) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void RescheduleRequestBanner(com.bms.app.domain.model.Appointment appointment, java.lang.String providerName, kotlin.jvm.functions.Function0<kotlin.Unit> onAccept, kotlin.jvm.functions.Function0<kotlin.Unit> onDecline, kotlin.jvm.functions.Function0<kotlin.Unit> onRebook) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void CalendarDateChip(java.time.LocalDate date, boolean isSelected, kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
}