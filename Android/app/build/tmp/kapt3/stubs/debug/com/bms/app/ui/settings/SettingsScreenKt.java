package com.bms.app.ui.settings;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000<\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a(\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH\u0003\u001a2\u0010\t\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\b2\u0010\b\u0002\u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\u0001\u0018\u00010\fH\u0003\u001a4\u0010\r\u001a\u00020\u00012\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00010\f2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00010\fH\u0003\u001a\u001e\u0010\u0014\u001a\u00020\u00012\u0006\u0010\u0010\u001a\u00020\u00112\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00010\fH\u0003\u001a\u0098\u0001\u0010\u0016\u001a\u00020\u00012\b\b\u0002\u0010\u000e\u001a\u00020\u000f2\u000e\b\u0002\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00010\f2\u000e\b\u0002\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00010\f2\u000e\b\u0002\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00010\f2\u000e\b\u0002\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00010\f2\u000e\b\u0002\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00010\f2\u000e\b\u0002\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00010\f2\u000e\b\u0002\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00010\f2\u0014\b\u0002\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u001cH\u0007\u001a&\u0010\u001d\u001a\u00020\u00012\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u000e\u001a\u00020\u000f2\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00010\fH\u0003\u00a8\u0006\u001e"}, d2 = {"FeeItem", "", "label", "", "amount", "", "currency", "icon", "Landroidx/compose/ui/graphics/vector/ImageVector;", "LedgerRow", "value", "onClick", "Lkotlin/Function0;", "PersonalTab", "viewModel", "Lcom/bms/app/ui/settings/viewmodel/SettingsViewModel;", "uiState", "Lcom/bms/app/ui/settings/viewmodel/ProfileUiState;", "onNavigateToPersonal", "onLogout", "ProfessionalTab", "onNavigateToProfessional", "SettingsScreen", "onNavigateToNotifications", "onNavigateToVisibility", "onNavigateToAvailability", "onBack", "onBottomNav", "Lkotlin/Function1;", "VisibilityTab", "app_debug"})
public final class SettingsScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class, androidx.compose.foundation.ExperimentalFoundationApi.class})
    @androidx.compose.runtime.Composable()
    public static final void SettingsScreen(@org.jetbrains.annotations.NotNull()
    com.bms.app.ui.settings.viewmodel.SettingsViewModel viewModel, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToPersonal, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToNotifications, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToVisibility, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToAvailability, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToProfessional, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onLogout, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onBottomNav) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void PersonalTab(com.bms.app.ui.settings.viewmodel.SettingsViewModel viewModel, com.bms.app.ui.settings.viewmodel.ProfileUiState uiState, kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToPersonal, kotlin.jvm.functions.Function0<kotlin.Unit> onLogout) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void LedgerRow(java.lang.String label, java.lang.String value, androidx.compose.ui.graphics.vector.ImageVector icon, kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void ProfessionalTab(com.bms.app.ui.settings.viewmodel.ProfileUiState uiState, kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToProfessional) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void FeeItem(java.lang.String label, double amount, java.lang.String currency, androidx.compose.ui.graphics.vector.ImageVector icon) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void VisibilityTab(com.bms.app.ui.settings.viewmodel.ProfileUiState uiState, com.bms.app.ui.settings.viewmodel.SettingsViewModel viewModel, kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToVisibility) {
    }
}