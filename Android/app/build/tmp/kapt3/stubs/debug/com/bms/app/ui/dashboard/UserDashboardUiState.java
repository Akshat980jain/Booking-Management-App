package com.bms.app.ui.dashboard;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0003\u0003\u0004\u0005B\u0007\b\u0004\u00a2\u0006\u0002\u0010\u0002\u0082\u0001\u0003\u0006\u0007\b\u00a8\u0006\t"}, d2 = {"Lcom/bms/app/ui/dashboard/UserDashboardUiState;", "", "()V", "Error", "Loading", "Success", "Lcom/bms/app/ui/dashboard/UserDashboardUiState$Error;", "Lcom/bms/app/ui/dashboard/UserDashboardUiState$Loading;", "Lcom/bms/app/ui/dashboard/UserDashboardUiState$Success;", "app_debug"})
public abstract class UserDashboardUiState {
    
    private UserDashboardUiState() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003H\u00c6\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u00d6\u0003J\t\u0010\r\u001a\u00020\u000eH\u00d6\u0001J\t\u0010\u000f\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0010"}, d2 = {"Lcom/bms/app/ui/dashboard/UserDashboardUiState$Error;", "Lcom/bms/app/ui/dashboard/UserDashboardUiState;", "message", "", "(Ljava/lang/String;)V", "getMessage", "()Ljava/lang/String;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "app_debug"})
    public static final class Error extends com.bms.app.ui.dashboard.UserDashboardUiState {
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
        public final com.bms.app.ui.dashboard.UserDashboardUiState.Error copy(@org.jetbrains.annotations.NotNull()
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/bms/app/ui/dashboard/UserDashboardUiState$Loading;", "Lcom/bms/app/ui/dashboard/UserDashboardUiState;", "()V", "app_debug"})
    public static final class Loading extends com.bms.app.ui.dashboard.UserDashboardUiState {
        @org.jetbrains.annotations.NotNull()
        public static final com.bms.app.ui.dashboard.UserDashboardUiState.Loading INSTANCE = null;
        
        private Loading() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\"\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0086\b\u0018\u00002\u00020\u0001B\u0083\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\t\u0012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00070\t\u0012\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\r0\f\u0012\u0012\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00030\f\u0012\u0006\u0010\u000f\u001a\u00020\u0010\u0012\u0006\u0010\u0011\u001a\u00020\u0010\u0012\u0006\u0010\u0012\u001a\u00020\u0010\u0012\u0006\u0010\u0013\u001a\u00020\u0010\u00a2\u0006\u0002\u0010\u0014J\t\u0010&\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\'\u001a\u00020\u0010H\u00c6\u0003J\t\u0010(\u001a\u00020\u0010H\u00c6\u0003J\t\u0010)\u001a\u00020\u0005H\u00c6\u0003J\u000b\u0010*\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003J\u000f\u0010+\u001a\b\u0012\u0004\u0012\u00020\u00070\tH\u00c6\u0003J\u000f\u0010,\u001a\b\u0012\u0004\u0012\u00020\u00070\tH\u00c6\u0003J\u0015\u0010-\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\r0\fH\u00c6\u0003J\u0015\u0010.\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00030\fH\u00c6\u0003J\t\u0010/\u001a\u00020\u0010H\u00c6\u0003J\t\u00100\u001a\u00020\u0010H\u00c6\u0003J\u009d\u0001\u00101\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00072\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\t2\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00070\t2\u0014\b\u0002\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\r0\f2\u0014\b\u0002\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00030\f2\b\b\u0002\u0010\u000f\u001a\u00020\u00102\b\b\u0002\u0010\u0011\u001a\u00020\u00102\b\b\u0002\u0010\u0012\u001a\u00020\u00102\b\b\u0002\u0010\u0013\u001a\u00020\u0010H\u00c6\u0001J\u0013\u00102\u001a\u0002032\b\u00104\u001a\u0004\u0018\u000105H\u00d6\u0003J\t\u00106\u001a\u00020\u0010H\u00d6\u0001J\t\u00107\u001a\u00020\u0005H\u00d6\u0001R\u0011\u0010\u0013\u001a\u00020\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\u0012\u001a\u00020\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0016R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00070\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u001d\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\r0\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0011\u0010\u000f\u001a\u00020\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0016R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u001bR\u0011\u0010\u0011\u001a\u00020\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u0016R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\"R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010$R\u001d\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00030\f\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010\u001d\u00a8\u00068"}, d2 = {"Lcom/bms/app/ui/dashboard/UserDashboardUiState$Success;", "Lcom/bms/app/ui/dashboard/UserDashboardUiState;", "userProfile", "Lcom/bms/app/domain/model/UserProfile;", "userInitials", "", "nextAppointment", "Lcom/bms/app/domain/model/Appointment;", "upcomingBookings", "", "pastBookings", "providerMap", "", "Lcom/bms/app/domain/model/ProviderProfile;", "userProfileMap", "totalBookings", "", "upcomingCount", "completedCount", "cancelledCount", "(Lcom/bms/app/domain/model/UserProfile;Ljava/lang/String;Lcom/bms/app/domain/model/Appointment;Ljava/util/List;Ljava/util/List;Ljava/util/Map;Ljava/util/Map;IIII)V", "getCancelledCount", "()I", "getCompletedCount", "getNextAppointment", "()Lcom/bms/app/domain/model/Appointment;", "getPastBookings", "()Ljava/util/List;", "getProviderMap", "()Ljava/util/Map;", "getTotalBookings", "getUpcomingBookings", "getUpcomingCount", "getUserInitials", "()Ljava/lang/String;", "getUserProfile", "()Lcom/bms/app/domain/model/UserProfile;", "getUserProfileMap", "component1", "component10", "component11", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "", "hashCode", "toString", "app_debug"})
    public static final class Success extends com.bms.app.ui.dashboard.UserDashboardUiState {
        @org.jetbrains.annotations.NotNull()
        private final com.bms.app.domain.model.UserProfile userProfile = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String userInitials = null;
        @org.jetbrains.annotations.Nullable()
        private final com.bms.app.domain.model.Appointment nextAppointment = null;
        @org.jetbrains.annotations.NotNull()
        private final java.util.List<com.bms.app.domain.model.Appointment> upcomingBookings = null;
        @org.jetbrains.annotations.NotNull()
        private final java.util.List<com.bms.app.domain.model.Appointment> pastBookings = null;
        
        /**
         * Maps providerId (provider_profiles.id) -> ProviderProfile
         */
        @org.jetbrains.annotations.NotNull()
        private final java.util.Map<java.lang.String, com.bms.app.domain.model.ProviderProfile> providerMap = null;
        
        /**
         * Maps userId (profiles.user_id) -> UserProfile — so we can show real names
         */
        @org.jetbrains.annotations.NotNull()
        private final java.util.Map<java.lang.String, com.bms.app.domain.model.UserProfile> userProfileMap = null;
        private final int totalBookings = 0;
        private final int upcomingCount = 0;
        private final int completedCount = 0;
        private final int cancelledCount = 0;
        
        public Success(@org.jetbrains.annotations.NotNull()
        com.bms.app.domain.model.UserProfile userProfile, @org.jetbrains.annotations.NotNull()
        java.lang.String userInitials, @org.jetbrains.annotations.Nullable()
        com.bms.app.domain.model.Appointment nextAppointment, @org.jetbrains.annotations.NotNull()
        java.util.List<com.bms.app.domain.model.Appointment> upcomingBookings, @org.jetbrains.annotations.NotNull()
        java.util.List<com.bms.app.domain.model.Appointment> pastBookings, @org.jetbrains.annotations.NotNull()
        java.util.Map<java.lang.String, com.bms.app.domain.model.ProviderProfile> providerMap, @org.jetbrains.annotations.NotNull()
        java.util.Map<java.lang.String, com.bms.app.domain.model.UserProfile> userProfileMap, int totalBookings, int upcomingCount, int completedCount, int cancelledCount) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.bms.app.domain.model.UserProfile getUserProfile() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getUserInitials() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final com.bms.app.domain.model.Appointment getNextAppointment() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.bms.app.domain.model.Appointment> getUpcomingBookings() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.bms.app.domain.model.Appointment> getPastBookings() {
            return null;
        }
        
        /**
         * Maps providerId (provider_profiles.id) -> ProviderProfile
         */
        @org.jetbrains.annotations.NotNull()
        public final java.util.Map<java.lang.String, com.bms.app.domain.model.ProviderProfile> getProviderMap() {
            return null;
        }
        
        /**
         * Maps userId (profiles.user_id) -> UserProfile — so we can show real names
         */
        @org.jetbrains.annotations.NotNull()
        public final java.util.Map<java.lang.String, com.bms.app.domain.model.UserProfile> getUserProfileMap() {
            return null;
        }
        
        public final int getTotalBookings() {
            return 0;
        }
        
        public final int getUpcomingCount() {
            return 0;
        }
        
        public final int getCompletedCount() {
            return 0;
        }
        
        public final int getCancelledCount() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.bms.app.domain.model.UserProfile component1() {
            return null;
        }
        
        public final int component10() {
            return 0;
        }
        
        public final int component11() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component2() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final com.bms.app.domain.model.Appointment component3() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.bms.app.domain.model.Appointment> component4() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.bms.app.domain.model.Appointment> component5() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.Map<java.lang.String, com.bms.app.domain.model.ProviderProfile> component6() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.Map<java.lang.String, com.bms.app.domain.model.UserProfile> component7() {
            return null;
        }
        
        public final int component8() {
            return 0;
        }
        
        public final int component9() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.bms.app.ui.dashboard.UserDashboardUiState.Success copy(@org.jetbrains.annotations.NotNull()
        com.bms.app.domain.model.UserProfile userProfile, @org.jetbrains.annotations.NotNull()
        java.lang.String userInitials, @org.jetbrains.annotations.Nullable()
        com.bms.app.domain.model.Appointment nextAppointment, @org.jetbrains.annotations.NotNull()
        java.util.List<com.bms.app.domain.model.Appointment> upcomingBookings, @org.jetbrains.annotations.NotNull()
        java.util.List<com.bms.app.domain.model.Appointment> pastBookings, @org.jetbrains.annotations.NotNull()
        java.util.Map<java.lang.String, com.bms.app.domain.model.ProviderProfile> providerMap, @org.jetbrains.annotations.NotNull()
        java.util.Map<java.lang.String, com.bms.app.domain.model.UserProfile> userProfileMap, int totalBookings, int upcomingCount, int completedCount, int cancelledCount) {
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