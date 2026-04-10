package com.bms.app.ui.settings.viewmodel;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0003\u0003\u0004\u0005B\u0007\b\u0004\u00a2\u0006\u0002\u0010\u0002\u0082\u0001\u0003\u0006\u0007\b\u00a8\u0006\t"}, d2 = {"Lcom/bms/app/ui/settings/viewmodel/ProfileUiState;", "", "()V", "Error", "Loading", "Success", "Lcom/bms/app/ui/settings/viewmodel/ProfileUiState$Error;", "Lcom/bms/app/ui/settings/viewmodel/ProfileUiState$Loading;", "Lcom/bms/app/ui/settings/viewmodel/ProfileUiState$Success;", "app_debug"})
public abstract class ProfileUiState {
    
    private ProfileUiState() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003H\u00c6\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u00d6\u0003J\t\u0010\r\u001a\u00020\u000eH\u00d6\u0001J\t\u0010\u000f\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0010"}, d2 = {"Lcom/bms/app/ui/settings/viewmodel/ProfileUiState$Error;", "Lcom/bms/app/ui/settings/viewmodel/ProfileUiState;", "message", "", "(Ljava/lang/String;)V", "getMessage", "()Ljava/lang/String;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "app_debug"})
    public static final class Error extends com.bms.app.ui.settings.viewmodel.ProfileUiState {
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String message = null;
        
        public Error(@org.jetbrains.annotations.NotNull()
        java.lang.String message) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getMessage() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.bms.app.ui.settings.viewmodel.ProfileUiState.Error copy(@org.jetbrains.annotations.NotNull()
        java.lang.String message) {
            return null;
        }
        
        @java.lang.Override()
        public boolean equals(@org.jetbrains.annotations.Nullable()
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override()
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.lang.String toString() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/bms/app/ui/settings/viewmodel/ProfileUiState$Loading;", "Lcom/bms/app/ui/settings/viewmodel/ProfileUiState;", "()V", "app_debug"})
    public static final class Loading extends com.bms.app.ui.settings.viewmodel.ProfileUiState {
        @org.jetbrains.annotations.NotNull()
        public static final com.bms.app.ui.settings.viewmodel.ProfileUiState.Loading INSTANCE = null;
        
        private Loading() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\f\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010\u0010\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\t\u0010\u0011\u001a\u00020\u0007H\u00c6\u0003J)\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007H\u00c6\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u00d6\u0003J\t\u0010\u0017\u001a\u00020\u0018H\u00d6\u0001J\t\u0010\u0019\u001a\u00020\u0007H\u00d6\u0001R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e\u00a8\u0006\u001a"}, d2 = {"Lcom/bms/app/ui/settings/viewmodel/ProfileUiState$Success;", "Lcom/bms/app/ui/settings/viewmodel/ProfileUiState;", "userProfile", "Lcom/bms/app/domain/model/UserProfile;", "providerProfile", "Lcom/bms/app/domain/model/ProviderProfile;", "userInitials", "", "(Lcom/bms/app/domain/model/UserProfile;Lcom/bms/app/domain/model/ProviderProfile;Ljava/lang/String;)V", "getProviderProfile", "()Lcom/bms/app/domain/model/ProviderProfile;", "getUserInitials", "()Ljava/lang/String;", "getUserProfile", "()Lcom/bms/app/domain/model/UserProfile;", "component1", "component2", "component3", "copy", "equals", "", "other", "", "hashCode", "", "toString", "app_debug"})
    public static final class Success extends com.bms.app.ui.settings.viewmodel.ProfileUiState {
        @org.jetbrains.annotations.NotNull()
        private final com.bms.app.domain.model.UserProfile userProfile = null;
        @org.jetbrains.annotations.Nullable()
        private final com.bms.app.domain.model.ProviderProfile providerProfile = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String userInitials = null;
        
        public Success(@org.jetbrains.annotations.NotNull()
        com.bms.app.domain.model.UserProfile userProfile, @org.jetbrains.annotations.Nullable()
        com.bms.app.domain.model.ProviderProfile providerProfile, @org.jetbrains.annotations.NotNull()
        java.lang.String userInitials) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.bms.app.domain.model.UserProfile getUserProfile() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final com.bms.app.domain.model.ProviderProfile getProviderProfile() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getUserInitials() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.bms.app.domain.model.UserProfile component1() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final com.bms.app.domain.model.ProviderProfile component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component3() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.bms.app.ui.settings.viewmodel.ProfileUiState.Success copy(@org.jetbrains.annotations.NotNull()
        com.bms.app.domain.model.UserProfile userProfile, @org.jetbrains.annotations.Nullable()
        com.bms.app.domain.model.ProviderProfile providerProfile, @org.jetbrains.annotations.NotNull()
        java.lang.String userInitials) {
            return null;
        }
        
        @java.lang.Override()
        public boolean equals(@org.jetbrains.annotations.Nullable()
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override()
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.lang.String toString() {
            return null;
        }
    }
}