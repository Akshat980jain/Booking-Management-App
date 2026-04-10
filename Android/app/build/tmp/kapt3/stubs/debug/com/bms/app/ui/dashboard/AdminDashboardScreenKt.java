package com.bms.app.ui.dashboard;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000J\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\u001a^\u0010\u0000\u001a\u00020\u00012\u0014\b\u0002\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00010\u00032\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00010\u00062\u0014\b\u0002\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00010\u00032\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00010\u00062\b\b\u0002\u0010\t\u001a\u00020\nH\u0007\u001a\u001e\u0010\u000b\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fH\u0003\u001a6\u0010\u0011\u001a\u00020\u00012\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00010\u00062\u001e\u0010\u0013\u001a\u001a\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00010\u0014H\u0003\u001a(\u0010\u0015\u001a\u00020\u00012\u0006\u0010\u0016\u001a\u00020\u00042\u0006\u0010\u0017\u001a\u00020\u00042\u0006\u0010\u0018\u001a\u00020\u00042\u0006\u0010\u0019\u001a\u00020\u001aH\u0003\u001an\u0010\u001b\u001a\u00020\u00012\u0006\u0010\u001c\u001a\u00020\u00102\u0014\b\u0002\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00010\u00032\u0014\b\u0002\u0010\u001d\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00010\u00032\u0014\b\u0002\u0010\u001e\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00010\u00032\u001a\b\u0002\u0010\u001f\u001a\u0014\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00010 H\u0003\u00a8\u0006!"}, d2 = {"AdminDashboardScreen", "", "onNavigate", "Lkotlin/Function1;", "", "onAvatarClick", "Lkotlin/Function0;", "onDeepNavigate", "onInboxClick", "viewModel", "Lcom/bms/app/ui/dashboard/AdminViewModel;", "AppointmentCard", "appointment", "Lcom/bms/app/domain/model/Appointment;", "users", "", "Lcom/bms/app/domain/model/UserProfile;", "QuickAddUserDialog", "onDismiss", "onConfirm", "Lkotlin/Function3;", "TransactionListItem", "title", "amount", "date", "isCredit", "", "UserListItem", "user", "onSuspend", "onBan", "onChangeRole", "Lkotlin/Function2;", "app_debug"})
public final class AdminDashboardScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void AdminDashboardScreen(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onNavigate, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onAvatarClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onDeepNavigate, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onInboxClick, @org.jetbrains.annotations.NotNull()
    com.bms.app.ui.dashboard.AdminViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void UserListItem(com.bms.app.domain.model.UserProfile user, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onDeepNavigate, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onSuspend, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onBan, kotlin.jvm.functions.Function2<? super java.lang.String, ? super java.lang.String, kotlin.Unit> onChangeRole) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void AppointmentCard(com.bms.app.domain.model.Appointment appointment, java.util.List<com.bms.app.domain.model.UserProfile> users) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void TransactionListItem(java.lang.String title, java.lang.String amount, java.lang.String date, boolean isCredit) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    private static final void QuickAddUserDialog(kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss, kotlin.jvm.functions.Function3<? super java.lang.String, ? super java.lang.String, ? super java.lang.String, kotlin.Unit> onConfirm) {
    }
}