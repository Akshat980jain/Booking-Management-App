package com.bms.app.ui.user;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000L\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a]\u0010\u0000\u001a\u00020\u00012\u0014\b\u0002\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00010\u00032\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00010\u00062#\b\u0002\u0010\u0007\u001a\u001d\u0012\u0013\u0012\u00110\u0004\u00a2\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0004\u0012\u00020\u00010\u00032\b\b\u0002\u0010\u000b\u001a\u00020\fH\u0007\u001aR\u0010\r\u001a\u00020\u00012\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00130\u00122\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00010\u00032\u0012\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00010\u0003H\u0003\u001a?\u0010\u0016\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u00042\u0006\u0010\u0018\u001a\u00020\u00192\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\u0017\u0010\u001a\u001a\u0013\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00010\u0003\u00a2\u0006\u0002\b\u001bH\u0003\u001a\u0016\u0010\u001c\u001a\u00020\u00012\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00010\u0006H\u0003\u00a8\u0006\u001e"}, d2 = {"CompareProvidersScreen", "", "onNavigate", "Lkotlin/Function1;", "", "onBack", "Lkotlin/Function0;", "onBookProvider", "Lkotlin/ParameterName;", "name", "providerId", "viewModel", "Lcom/bms/app/ui/dashboard/UserDashboardViewModel;", "ComparisonGrid", "providers", "", "Lcom/bms/app/domain/model/ProviderProfile;", "userProfileMap", "", "Lcom/bms/app/domain/model/UserProfile;", "onRemove", "onBook", "ComparisonRow", "label", "icon", "Landroidx/compose/ui/graphics/vector/ImageVector;", "content", "Landroidx/compose/runtime/Composable;", "EmptyComparison", "onAdd", "app_debug"})
public final class CompareProvidersScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void CompareProvidersScreen(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onNavigate, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onBookProvider, @org.jetbrains.annotations.NotNull()
    com.bms.app.ui.dashboard.UserDashboardViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void ComparisonGrid(java.util.List<com.bms.app.domain.model.ProviderProfile> providers, java.util.Map<java.lang.String, com.bms.app.domain.model.UserProfile> userProfileMap, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onRemove, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onBook) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void ComparisonRow(java.lang.String label, androidx.compose.ui.graphics.vector.ImageVector icon, java.util.List<com.bms.app.domain.model.ProviderProfile> providers, kotlin.jvm.functions.Function1<? super com.bms.app.domain.model.ProviderProfile, kotlin.Unit> content) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void EmptyComparison(kotlin.jvm.functions.Function0<kotlin.Unit> onAdd) {
    }
}