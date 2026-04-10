package com.bms.app.ui.admin;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000@\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0002\u001a6\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\b\b\u0002\u0010\u0007\u001a\u00020\bH\u0007\u001a8\u0010\t\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u000b2\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u00010\r2\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\rH\u0003\u001a&\u0010\u0010\u001a\u00020\u00012\u0006\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\u00132\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0003\u001a<\u0010\u0015\u001a\u00020\u00012\u0006\u0010\u0016\u001a\u00020\u00032\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00030\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u00032\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\rH\u0003\u00a8\u0006\u001a"}, d2 = {"BookServiceScreen", "", "providerId", "", "onBack", "Lkotlin/Function0;", "onBookingSuccess", "viewModel", "Lcom/bms/app/ui/admin/BookingViewModel;", "BookingContent", "state", "Lcom/bms/app/ui/admin/BookingUiState$Success;", "onDateSelected", "Lkotlin/Function1;", "Ljava/time/LocalDate;", "onSlotSelected", "DateChip", "date", "isSelected", "", "onClick", "TimeSection", "title", "slots", "", "selectedSlot", "app_debug"})
public final class BookServiceScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void BookServiceScreen(@org.jetbrains.annotations.NotNull()
    java.lang.String providerId, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBookingSuccess, @org.jetbrains.annotations.NotNull()
    com.bms.app.ui.admin.BookingViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void BookingContent(com.bms.app.ui.admin.BookingUiState.Success state, kotlin.jvm.functions.Function1<? super java.time.LocalDate, kotlin.Unit> onDateSelected, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onSlotSelected) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void DateChip(java.time.LocalDate date, boolean isSelected, kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.foundation.layout.ExperimentalLayoutApi.class})
    @androidx.compose.runtime.Composable()
    private static final void TimeSection(java.lang.String title, java.util.List<java.lang.String> slots, java.lang.String selectedSlot, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onSlotSelected) {
    }
}