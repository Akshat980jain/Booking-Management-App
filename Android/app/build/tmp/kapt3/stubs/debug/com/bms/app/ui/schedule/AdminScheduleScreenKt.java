package com.bms.app.ui.schedule;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000<\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a(\u0010\u0000\u001a\u00020\u00012\u0014\b\u0002\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00010\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0006H\u0007\u001a(\u0010\u0007\u001a\u00020\u00012\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\tH\u0003\u001a$\u0010\r\u001a\u00020\u00012\u0006\u0010\u000e\u001a\u00020\u000f2\u0012\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00120\u0011H\u0003\u001a*\u0010\u0013\u001a\u00020\u00012\u0006\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00020\u00042\u0006\u0010\u0016\u001a\u00020\u0017H\u0003\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0018\u0010\u0019\u0082\u0002\u0007\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006\u001a"}, d2 = {"AdminScheduleScreen", "", "onNavigate", "Lkotlin/Function1;", "", "viewModel", "Lcom/bms/app/ui/dashboard/AdminViewModel;", "AdminScheduleSummaryBar", "total", "", "pending", "confirmed", "completed", "AppointmentDetailCard", "appointment", "Lcom/bms/app/domain/model/Appointment;", "userMap", "", "Lcom/bms/app/domain/model/UserProfile;", "SummaryStatItem", "label", "value", "color", "Landroidx/compose/ui/graphics/Color;", "SummaryStatItem-mxwnekA", "(Ljava/lang/String;Ljava/lang/String;J)V", "app_debug"})
public final class AdminScheduleScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void AdminScheduleScreen(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onNavigate, @org.jetbrains.annotations.NotNull()
    com.bms.app.ui.dashboard.AdminViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void AdminScheduleSummaryBar(int total, int pending, int confirmed, int completed) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void AppointmentDetailCard(com.bms.app.domain.model.Appointment appointment, java.util.Map<java.lang.String, com.bms.app.domain.model.UserProfile> userMap) {
    }
}