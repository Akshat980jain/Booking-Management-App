package com.bms.app.ui.user;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0003\u0003\u0004\u0005B\u0007\b\u0004\u00a2\u0006\u0002\u0010\u0002\u0082\u0001\u0003\u0006\u0007\b\u00a8\u0006\t"}, d2 = {"Lcom/bms/app/ui/user/ProviderDetailUiState;", "", "()V", "Error", "Loading", "Success", "Lcom/bms/app/ui/user/ProviderDetailUiState$Error;", "Lcom/bms/app/ui/user/ProviderDetailUiState$Loading;", "Lcom/bms/app/ui/user/ProviderDetailUiState$Success;", "app_debug"})
public abstract class ProviderDetailUiState {
    
    private ProviderDetailUiState() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003H\u00c6\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u00d6\u0003J\t\u0010\r\u001a\u00020\u000eH\u00d6\u0001J\t\u0010\u000f\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0010"}, d2 = {"Lcom/bms/app/ui/user/ProviderDetailUiState$Error;", "Lcom/bms/app/ui/user/ProviderDetailUiState;", "message", "", "(Ljava/lang/String;)V", "getMessage", "()Ljava/lang/String;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "app_debug"})
    public static final class Error extends com.bms.app.ui.user.ProviderDetailUiState {
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
        public final com.bms.app.ui.user.ProviderDetailUiState.Error copy(@org.jetbrains.annotations.NotNull()
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/bms/app/ui/user/ProviderDetailUiState$Loading;", "Lcom/bms/app/ui/user/ProviderDetailUiState;", "()V", "app_debug"})
    public static final class Loading extends com.bms.app.ui.user.ProviderDetailUiState {
        @org.jetbrains.annotations.NotNull()
        public static final com.bms.app.ui.user.ProviderDetailUiState.Loading INSTANCE = null;
        
        private Loading() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B7\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00030\n\u00a2\u0006\u0002\u0010\fJ\t\u0010\u0015\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0016\u001a\u00020\u0005H\u00c6\u0003J\u000f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\b0\u0007H\u00c6\u0003J\u0015\u0010\u0018\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00030\nH\u00c6\u0003JC\u0010\u0019\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\u0014\b\u0002\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00030\nH\u00c6\u0001J\u0013\u0010\u001a\u001a\u00020\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u00d6\u0003J\t\u0010\u001e\u001a\u00020\u001fH\u00d6\u0001J\t\u0010 \u001a\u00020\u000bH\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u001d\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00030\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014\u00a8\u0006!"}, d2 = {"Lcom/bms/app/ui/user/ProviderDetailUiState$Success;", "Lcom/bms/app/ui/user/ProviderDetailUiState;", "provider", "Lcom/bms/app/domain/model/UserProfile;", "profile", "Lcom/bms/app/domain/model/ProviderProfile;", "reviews", "", "Lcom/bms/app/domain/model/Review;", "reviewerProfiles", "", "", "(Lcom/bms/app/domain/model/UserProfile;Lcom/bms/app/domain/model/ProviderProfile;Ljava/util/List;Ljava/util/Map;)V", "getProfile", "()Lcom/bms/app/domain/model/ProviderProfile;", "getProvider", "()Lcom/bms/app/domain/model/UserProfile;", "getReviewerProfiles", "()Ljava/util/Map;", "getReviews", "()Ljava/util/List;", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "", "hashCode", "", "toString", "app_debug"})
    public static final class Success extends com.bms.app.ui.user.ProviderDetailUiState {
        @org.jetbrains.annotations.NotNull()
        private final com.bms.app.domain.model.UserProfile provider = null;
        @org.jetbrains.annotations.NotNull()
        private final com.bms.app.domain.model.ProviderProfile profile = null;
        @org.jetbrains.annotations.NotNull()
        private final java.util.List<com.bms.app.domain.model.Review> reviews = null;
        @org.jetbrains.annotations.NotNull()
        private final java.util.Map<java.lang.String, com.bms.app.domain.model.UserProfile> reviewerProfiles = null;
        
        public Success(@org.jetbrains.annotations.NotNull()
        com.bms.app.domain.model.UserProfile provider, @org.jetbrains.annotations.NotNull()
        com.bms.app.domain.model.ProviderProfile profile, @org.jetbrains.annotations.NotNull()
        java.util.List<com.bms.app.domain.model.Review> reviews, @org.jetbrains.annotations.NotNull()
        java.util.Map<java.lang.String, com.bms.app.domain.model.UserProfile> reviewerProfiles) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.bms.app.domain.model.UserProfile getProvider() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.bms.app.domain.model.ProviderProfile getProfile() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.bms.app.domain.model.Review> getReviews() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.Map<java.lang.String, com.bms.app.domain.model.UserProfile> getReviewerProfiles() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.bms.app.domain.model.UserProfile component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.bms.app.domain.model.ProviderProfile component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.bms.app.domain.model.Review> component3() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.Map<java.lang.String, com.bms.app.domain.model.UserProfile> component4() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.bms.app.ui.user.ProviderDetailUiState.Success copy(@org.jetbrains.annotations.NotNull()
        com.bms.app.domain.model.UserProfile provider, @org.jetbrains.annotations.NotNull()
        com.bms.app.domain.model.ProviderProfile profile, @org.jetbrains.annotations.NotNull()
        java.util.List<com.bms.app.domain.model.Review> reviews, @org.jetbrains.annotations.NotNull()
        java.util.Map<java.lang.String, com.bms.app.domain.model.UserProfile> reviewerProfiles) {
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