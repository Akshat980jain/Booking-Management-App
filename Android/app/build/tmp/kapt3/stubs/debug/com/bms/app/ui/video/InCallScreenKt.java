package com.bms.app.ui.video;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u00008\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\u001a9\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\tH\u0007\u00a2\u0006\u0002\u0010\n\u001a\u0090\u0001\u0010\u000b\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\r2\u0012\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00110\u000f2\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u00112\b\b\u0002\u0010\u0013\u001a\u00020\u00052\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\u000e\b\u0002\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00010\u0007H\u0007\u001a\u0010\u0010\u001a\u001a\u00020\u00012\u0006\u0010\u001b\u001a\u00020\u0011H\u0003\u00a8\u0006\u001c"}, d2 = {"CallControlButton", "", "icon", "Landroidx/compose/ui/graphics/vector/ImageVector;", "active", "", "onClick", "Lkotlin/Function0;", "badge", "", "(Landroidx/compose/ui/graphics/vector/ImageVector;ZLkotlin/jvm/functions/Function0;Ljava/lang/Integer;)V", "InCallScreen", "callClient", "Lco/daily/CallClient;", "participants", "", "Lco/daily/model/ParticipantId;", "Lco/daily/model/Participant;", "localParticipant", "isProvider", "onMuteToggle", "onVideoToggle", "onFlipCamera", "onShareScreen", "onAdmitPatient", "onEndCall", "ParticipantTile", "participant", "app_debug"})
public final class InCallScreenKt {
    
    @androidx.compose.runtime.Composable()
    public static final void InCallScreen(@org.jetbrains.annotations.NotNull()
    co.daily.CallClient callClient, @org.jetbrains.annotations.NotNull()
    java.util.Map<co.daily.model.ParticipantId, co.daily.model.Participant> participants, @org.jetbrains.annotations.Nullable()
    co.daily.model.Participant localParticipant, boolean isProvider, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onMuteToggle, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onVideoToggle, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onFlipCamera, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onShareScreen, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onAdmitPatient, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onEndCall) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void ParticipantTile(co.daily.model.Participant participant) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void CallControlButton(@org.jetbrains.annotations.NotNull()
    androidx.compose.ui.graphics.vector.ImageVector icon, boolean active, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick, @org.jetbrains.annotations.Nullable()
    java.lang.Integer badge) {
    }
}