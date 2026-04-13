package com.bms.app.ui.user;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\u001a9\u0010\u0000\u001a\u00020\u00012\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\u0011\u0010\b\u001a\r\u0012\u0004\u0012\u00020\u00010\t\u00a2\u0006\u0002\b\nH\u0003\u001aB\u0010\u000b\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u00132\u0012\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u000f0\u0016H\u0003\u001a<\u0010\u0018\u001a\u00020\u00012\u0006\u0010\u0019\u001a\u00020\u00172\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00010\t2\u0012\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u00010\u001c2\b\b\u0002\u0010\u001d\u001a\u00020\u001eH\u0007\u001a\u001a\u0010\u001f\u001a\u00020\u00012\u0006\u0010 \u001a\u00020\u00142\b\u0010!\u001a\u0004\u0018\u00010\u000fH\u0003\u001a \u0010\"\u001a\u00020\u00012\u0006\u0010#\u001a\u00020\u00172\u0006\u0010$\u001a\u00020\u00172\u0006\u0010%\u001a\u00020&H\u0003\u00a8\u0006\'"}, d2 = {"FlowRow", "", "modifier", "Landroidx/compose/ui/Modifier;", "horizontalArrangement", "Landroidx/compose/foundation/layout/Arrangement$Horizontal;", "verticalArrangement", "Landroidx/compose/foundation/layout/Arrangement$Vertical;", "content", "Lkotlin/Function0;", "Landroidx/compose/runtime/Composable;", "ProviderDetailContent", "padding", "Landroidx/compose/foundation/layout/PaddingValues;", "provider", "Lcom/bms/app/domain/model/UserProfile;", "profile", "Lcom/bms/app/domain/model/ProviderProfile;", "reviews", "", "Lcom/bms/app/domain/model/Review;", "reviewerProfiles", "", "", "ProviderDetailScreen", "providerUserId", "onBack", "onBook", "Lkotlin/Function1;", "viewModel", "Lcom/bms/app/ui/user/ProviderDetailViewModel;", "ReviewItem", "review", "userProfile", "StatItem", "label", "value", "icon", "Landroidx/compose/ui/graphics/vector/ImageVector;", "app_debug"})
public final class ProviderDetailScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void ProviderDetailScreen(@org.jetbrains.annotations.NotNull()
    java.lang.String providerUserId, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onBook, @org.jetbrains.annotations.NotNull()
    com.bms.app.ui.user.ProviderDetailViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void ProviderDetailContent(androidx.compose.foundation.layout.PaddingValues padding, com.bms.app.domain.model.UserProfile provider, com.bms.app.domain.model.ProviderProfile profile, java.util.List<com.bms.app.domain.model.Review> reviews, java.util.Map<java.lang.String, com.bms.app.domain.model.UserProfile> reviewerProfiles) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void StatItem(java.lang.String label, java.lang.String value, androidx.compose.ui.graphics.vector.ImageVector icon) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void ReviewItem(com.bms.app.domain.model.Review review, com.bms.app.domain.model.UserProfile userProfile) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.foundation.layout.ExperimentalLayoutApi.class})
    @androidx.compose.runtime.Composable()
    private static final void FlowRow(androidx.compose.ui.Modifier modifier, androidx.compose.foundation.layout.Arrangement.Horizontal horizontalArrangement, androidx.compose.foundation.layout.Arrangement.Vertical verticalArrangement, kotlin.jvm.functions.Function0<kotlin.Unit> content) {
    }
}