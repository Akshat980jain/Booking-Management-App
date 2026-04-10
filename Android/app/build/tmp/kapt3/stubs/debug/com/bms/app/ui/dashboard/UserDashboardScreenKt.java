package com.bms.app.ui.dashboard;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000T\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a8\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\b\b\u0002\u0010\t\u001a\u00020\nH\u0007\u001a \u0010\u000b\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u00052\u0006\u0010\u000f\u001a\u00020\u0005H\u0003\u001a@\u0010\u0010\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\b\u0010\u0011\u001a\u0004\u0018\u00010\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u00052\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00010\bH\u0003\u001aT\u0010\u0015\u001a\u00020\u00012\u0006\u0010\u0016\u001a\u00020\u00172\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\u0012\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u001b2\u0012\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u001bH\u0003\u001a}\u0010\u001d\u001a\u00020\u00012\u0014\b\u0002\u0010\u001e\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u001b2#\b\u0002\u0010\u001a\u001a\u001d\u0012\u0013\u0012\u00110\u0005\u00a2\u0006\f\b\u001f\u0012\b\b \u0012\u0004\b\b(!\u0012\u0004\u0012\u00020\u00010\u001b2\u000e\b\u0002\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\u000e\b\u0002\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\u000e\b\u0002\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\b\b\u0002\u0010#\u001a\u00020$H\u0007\u001a\u0010\u0010%\u001a\u00020\u00052\u0006\u0010&\u001a\u00020\u0005H\u0002\u001a\b\u0010\'\u001a\u00020\u0005H\u0002\u00a8\u0006("}, d2 = {"BookingListItem", "", "appointment", "Lcom/bms/app/domain/model/Appointment;", "providerName", "", "providerSpecialty", "onMessage", "Lkotlin/Function0;", "modifier", "Landroidx/compose/ui/Modifier;", "EmptyStateCard", "icon", "Landroidx/compose/ui/graphics/vector/ImageVector;", "title", "subtitle", "NextAppointmentBanner", "provider", "Lcom/bms/app/domain/model/ProviderProfile;", "realName", "onCancel", "UserDashboardContent", "state", "Lcom/bms/app/ui/dashboard/UserDashboardUiState$Success;", "onBrowseProviders", "onViewAllBookings", "onMessageProvider", "Lkotlin/Function1;", "onCancelBooking", "UserDashboardScreen", "onNavigate", "Lkotlin/ParameterName;", "name", "providerId", "onInboxClick", "viewModel", "Lcom/bms/app/ui/dashboard/UserDashboardViewModel;", "friendlyDate", "dateStr", "timeGreeting", "app_debug"})
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
    kotlin.jvm.functions.Function0<kotlin.Unit> onBrowseProviders, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onViewAllBookings, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onInboxClick, @org.jetbrains.annotations.NotNull()
    com.bms.app.ui.dashboard.UserDashboardViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void UserDashboardContent(com.bms.app.ui.dashboard.UserDashboardUiState.Success state, kotlin.jvm.functions.Function0<kotlin.Unit> onBrowseProviders, kotlin.jvm.functions.Function0<kotlin.Unit> onViewAllBookings, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onMessageProvider, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onCancelBooking) {
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
    androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void EmptyStateCard(androidx.compose.ui.graphics.vector.ImageVector icon, java.lang.String title, java.lang.String subtitle) {
    }
}