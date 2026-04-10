package com.bms.app.ui.chat;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\f\u001a\u00020\rR\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u000e"}, d2 = {"Lcom/bms/app/ui/chat/SupportViewModel;", "Landroidx/lifecycle/ViewModel;", "profileRepository", "Lcom/bms/app/domain/repository/ProfileRepository;", "(Lcom/bms/app/domain/repository/ProfileRepository;)V", "_state", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/bms/app/ui/chat/SupportUiState;", "state", "Lkotlinx/coroutines/flow/StateFlow;", "getState", "()Lkotlinx/coroutines/flow/StateFlow;", "resolve", "", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class SupportViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.bms.app.domain.repository.ProfileRepository profileRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.bms.app.ui.chat.SupportUiState> _state = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.bms.app.ui.chat.SupportUiState> state = null;
    
    @javax.inject.Inject()
    public SupportViewModel(@org.jetbrains.annotations.NotNull()
    com.bms.app.domain.repository.ProfileRepository profileRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.bms.app.ui.chat.SupportUiState> getState() {
        return null;
    }
    
    public final void resolve() {
    }
}