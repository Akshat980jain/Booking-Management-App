package com.bms.app.ui.chat;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000$\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u001aC\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032!\u0010\u0004\u001a\u001d\u0012\u0013\u0012\u00110\u0006\u00a2\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\t\u0012\u0004\u0012\u00020\u00010\u00052\b\b\u0002\u0010\n\u001a\u00020\u000bH\u0007\u00a8\u0006\f"}, d2 = {"SupportScreen", "", "onBack", "Lkotlin/Function0;", "onOpenChat", "Lkotlin/Function1;", "", "Lkotlin/ParameterName;", "name", "adminUserId", "viewModel", "Lcom/bms/app/ui/chat/SupportViewModel;", "app_debug"})
public final class SupportScreenKt {
    
    /**
     * SupportScreen — resolves the admin user ID dynamically, then
     * transitions directly into the regular ChatScreen.
     *
     * If admin lookup fails (e.g. no admin in DB yet) it shows a clear
     * error state so the provider knows what happened.
     */
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void SupportScreen(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onOpenChat, @org.jetbrains.annotations.NotNull()
    com.bms.app.ui.chat.SupportViewModel viewModel) {
    }
}