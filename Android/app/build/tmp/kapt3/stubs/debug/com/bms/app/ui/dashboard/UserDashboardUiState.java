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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\"\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b:\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0086\b\u0018\u00002\u00020\u0001B\u0097\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\t\u0012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00070\t\u0012\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00070\t\u0012\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00070\t\u0012\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00070\t\u0012\u0012\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00100\u000f\u0012\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00030\u000f\u0012\u0006\u0010\u0012\u001a\u00020\u0013\u0012\u0006\u0010\u0014\u001a\u00020\u0013\u0012\u0006\u0010\u0015\u001a\u00020\u0013\u0012\u0006\u0010\u0016\u001a\u00020\u0013\u0012\u000e\b\u0002\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00070\t\u0012\u000e\b\u0002\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00050\u0019\u0012\u000e\b\u0002\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00050\u0019\u0012\n\b\u0002\u0010\u001b\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u001c\u001a\u00020\u001d\u0012\b\b\u0002\u0010\u001e\u001a\u00020\u001f\u0012\u000e\b\u0002\u0010 \u001a\b\u0012\u0004\u0012\u00020!0\t\u0012\b\b\u0002\u0010\"\u001a\u00020\u0013\u00a2\u0006\u0002\u0010#J\t\u0010C\u001a\u00020\u0003H\u00c6\u0003J\u0015\u0010D\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00030\u000fH\u00c6\u0003J\t\u0010E\u001a\u00020\u0013H\u00c6\u0003J\t\u0010F\u001a\u00020\u0013H\u00c6\u0003J\t\u0010G\u001a\u00020\u0013H\u00c6\u0003J\t\u0010H\u001a\u00020\u0013H\u00c6\u0003J\u000f\u0010I\u001a\b\u0012\u0004\u0012\u00020\u00070\tH\u00c6\u0003J\u000f\u0010J\u001a\b\u0012\u0004\u0012\u00020\u00050\u0019H\u00c6\u0003J\u000f\u0010K\u001a\b\u0012\u0004\u0012\u00020\u00050\u0019H\u00c6\u0003J\u000b\u0010L\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\t\u0010M\u001a\u00020\u001dH\u00c6\u0003J\t\u0010N\u001a\u00020\u0005H\u00c6\u0003J\t\u0010O\u001a\u00020\u001fH\u00c6\u0003J\u000f\u0010P\u001a\b\u0012\u0004\u0012\u00020!0\tH\u00c6\u0003J\t\u0010Q\u001a\u00020\u0013H\u00c6\u0003J\u000b\u0010R\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003J\u000f\u0010S\u001a\b\u0012\u0004\u0012\u00020\u00070\tH\u00c6\u0003J\u000f\u0010T\u001a\b\u0012\u0004\u0012\u00020\u00070\tH\u00c6\u0003J\u000f\u0010U\u001a\b\u0012\u0004\u0012\u00020\u00070\tH\u00c6\u0003J\u000f\u0010V\u001a\b\u0012\u0004\u0012\u00020\u00070\tH\u00c6\u0003J\u000f\u0010W\u001a\b\u0012\u0004\u0012\u00020\u00070\tH\u00c6\u0003J\u0015\u0010X\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00100\u000fH\u00c6\u0003J\u00b7\u0002\u0010Y\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00072\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\t2\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00070\t2\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00070\t2\u000e\b\u0002\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00070\t2\u000e\b\u0002\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00070\t2\u0014\b\u0002\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00100\u000f2\u0014\b\u0002\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00030\u000f2\b\b\u0002\u0010\u0012\u001a\u00020\u00132\b\b\u0002\u0010\u0014\u001a\u00020\u00132\b\b\u0002\u0010\u0015\u001a\u00020\u00132\b\b\u0002\u0010\u0016\u001a\u00020\u00132\u000e\b\u0002\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00070\t2\u000e\b\u0002\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00050\u00192\u000e\b\u0002\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00050\u00192\n\b\u0002\u0010\u001b\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u001c\u001a\u00020\u001d2\b\b\u0002\u0010\u001e\u001a\u00020\u001f2\u000e\b\u0002\u0010 \u001a\b\u0012\u0004\u0012\u00020!0\t2\b\b\u0002\u0010\"\u001a\u00020\u0013H\u00c6\u0001J\u0013\u0010Z\u001a\u00020\u001f2\b\u0010[\u001a\u0004\u0018\u00010\\H\u00d6\u0003J\t\u0010]\u001a\u00020\u0013H\u00d6\u0001J\t\u0010^\u001a\u00020\u0005H\u00d6\u0001R\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00070\t\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010%R\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00070\t\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010%R\u0011\u0010\u0016\u001a\u00020\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b\'\u0010(R\u0011\u0010\u0015\u001a\u00020\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010(R\u0017\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00050\u0019\u00a2\u0006\b\n\u0000\u001a\u0004\b*\u0010+R\u0011\u0010\u001c\u001a\u00020\u001d\u00a2\u0006\b\n\u0000\u001a\u0004\b,\u0010-R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b.\u0010/R\u0017\u0010 \u001a\b\u0012\u0004\u0012\u00020!0\t\u00a2\u0006\b\n\u0000\u001a\u0004\b0\u0010%R\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00070\t\u00a2\u0006\b\n\u0000\u001a\u0004\b1\u0010%R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00070\t\u00a2\u0006\b\n\u0000\u001a\u0004\b2\u0010%R\u001d\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00100\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b3\u00104R\u0017\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00070\t\u00a2\u0006\b\n\u0000\u001a\u0004\b5\u0010%R\u0017\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00050\u0019\u00a2\u0006\b\n\u0000\u001a\u0004\b6\u0010+R\u0013\u0010\u001b\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b7\u00108R\u0011\u0010\u001e\u001a\u00020\u001f\u00a2\u0006\b\n\u0000\u001a\u0004\b9\u0010:R\u0011\u0010\u0012\u001a\u00020\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b;\u0010(R\u0011\u0010\"\u001a\u00020\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b<\u0010(R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\t\u00a2\u0006\b\n\u0000\u001a\u0004\b=\u0010%R\u0011\u0010\u0014\u001a\u00020\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b>\u0010(R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b?\u00108R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b@\u0010AR\u001d\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00030\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\bB\u00104\u00a8\u0006_"}, d2 = {"Lcom/bms/app/ui/dashboard/UserDashboardUiState$Success;", "Lcom/bms/app/ui/dashboard/UserDashboardUiState;", "userProfile", "Lcom/bms/app/domain/model/UserProfile;", "userInitials", "", "nextAppointment", "Lcom/bms/app/domain/model/Appointment;", "upcomingBookings", "", "pastBookings", "cancelledBookings", "paidAppointments", "allAppointments", "providerMap", "", "Lcom/bms/app/domain/model/ProviderProfile;", "userProfileMap", "totalBookings", "", "upcomingCount", "completedCount", "cancelledCount", "rescheduleRequests", "favoriteProviderIds", "", "selectedComparisonIds", "selectedProfession", "minRating", "", "showVideoOnly", "", "notifications", "Lcom/bms/app/domain/model/Notification;", "unreadNotificationCount", "(Lcom/bms/app/domain/model/UserProfile;Ljava/lang/String;Lcom/bms/app/domain/model/Appointment;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/Map;Ljava/util/Map;IIIILjava/util/List;Ljava/util/Set;Ljava/util/Set;Ljava/lang/String;FZLjava/util/List;I)V", "getAllAppointments", "()Ljava/util/List;", "getCancelledBookings", "getCancelledCount", "()I", "getCompletedCount", "getFavoriteProviderIds", "()Ljava/util/Set;", "getMinRating", "()F", "getNextAppointment", "()Lcom/bms/app/domain/model/Appointment;", "getNotifications", "getPaidAppointments", "getPastBookings", "getProviderMap", "()Ljava/util/Map;", "getRescheduleRequests", "getSelectedComparisonIds", "getSelectedProfession", "()Ljava/lang/String;", "getShowVideoOnly", "()Z", "getTotalBookings", "getUnreadNotificationCount", "getUpcomingBookings", "getUpcomingCount", "getUserInitials", "getUserProfile", "()Lcom/bms/app/domain/model/UserProfile;", "getUserProfileMap", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component18", "component19", "component2", "component20", "component21", "component22", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "", "hashCode", "toString", "app_debug"})
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
        @org.jetbrains.annotations.NotNull()
        private final java.util.List<com.bms.app.domain.model.Appointment> cancelledBookings = null;
        @org.jetbrains.annotations.NotNull()
        private final java.util.List<com.bms.app.domain.model.Appointment> paidAppointments = null;
        @org.jetbrains.annotations.NotNull()
        private final java.util.List<com.bms.app.domain.model.Appointment> allAppointments = null;
        
        /**
         * Maps providerId (provider_profiles.id) -> ProviderProfile
         */
        @org.jetbrains.annotations.NotNull()
        private final java.util.Map<java.lang.String, com.bms.app.domain.model.ProviderProfile> providerMap = null;
        @org.jetbrains.annotations.NotNull()
        private final java.util.Map<java.lang.String, com.bms.app.domain.model.UserProfile> userProfileMap = null;
        private final int totalBookings = 0;
        private final int upcomingCount = 0;
        private final int completedCount = 0;
        private final int cancelledCount = 0;
        @org.jetbrains.annotations.NotNull()
        private final java.util.List<com.bms.app.domain.model.Appointment> rescheduleRequests = null;
        
        /**
         * List of provider ids (provider_profiles.id)
         */
        @org.jetbrains.annotations.NotNull()
        private final java.util.Set<java.lang.String> favoriteProviderIds = null;
        
        /**
         * List of provider ids currently selected for comparison
         */
        @org.jetbrains.annotations.NotNull()
        private final java.util.Set<java.lang.String> selectedComparisonIds = null;
        
        /**
         * Filtering
         */
        @org.jetbrains.annotations.Nullable()
        private final java.lang.String selectedProfession = null;
        private final float minRating = 0.0F;
        private final boolean showVideoOnly = false;
        
        /**
         * Real-time Notifications
         */
        @org.jetbrains.annotations.NotNull()
        private final java.util.List<com.bms.app.domain.model.Notification> notifications = null;
        private final int unreadNotificationCount = 0;
        
        public Success(@org.jetbrains.annotations.NotNull()
        com.bms.app.domain.model.UserProfile userProfile, @org.jetbrains.annotations.NotNull()
        java.lang.String userInitials, @org.jetbrains.annotations.Nullable()
        com.bms.app.domain.model.Appointment nextAppointment, @org.jetbrains.annotations.NotNull()
        java.util.List<com.bms.app.domain.model.Appointment> upcomingBookings, @org.jetbrains.annotations.NotNull()
        java.util.List<com.bms.app.domain.model.Appointment> pastBookings, @org.jetbrains.annotations.NotNull()
        java.util.List<com.bms.app.domain.model.Appointment> cancelledBookings, @org.jetbrains.annotations.NotNull()
        java.util.List<com.bms.app.domain.model.Appointment> paidAppointments, @org.jetbrains.annotations.NotNull()
        java.util.List<com.bms.app.domain.model.Appointment> allAppointments, @org.jetbrains.annotations.NotNull()
        java.util.Map<java.lang.String, com.bms.app.domain.model.ProviderProfile> providerMap, @org.jetbrains.annotations.NotNull()
        java.util.Map<java.lang.String, com.bms.app.domain.model.UserProfile> userProfileMap, int totalBookings, int upcomingCount, int completedCount, int cancelledCount, @org.jetbrains.annotations.NotNull()
        java.util.List<com.bms.app.domain.model.Appointment> rescheduleRequests, @org.jetbrains.annotations.NotNull()
        java.util.Set<java.lang.String> favoriteProviderIds, @org.jetbrains.annotations.NotNull()
        java.util.Set<java.lang.String> selectedComparisonIds, @org.jetbrains.annotations.Nullable()
        java.lang.String selectedProfession, float minRating, boolean showVideoOnly, @org.jetbrains.annotations.NotNull()
        java.util.List<com.bms.app.domain.model.Notification> notifications, int unreadNotificationCount) {
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
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.bms.app.domain.model.Appointment> getCancelledBookings() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.bms.app.domain.model.Appointment> getPaidAppointments() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.bms.app.domain.model.Appointment> getAllAppointments() {
            return null;
        }
        
        /**
         * Maps providerId (provider_profiles.id) -> ProviderProfile
         */
        @org.jetbrains.annotations.NotNull()
        public final java.util.Map<java.lang.String, com.bms.app.domain.model.ProviderProfile> getProviderMap() {
            return null;
        }
        
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
        public final java.util.List<com.bms.app.domain.model.Appointment> getRescheduleRequests() {
            return null;
        }
        
        /**
         * List of provider ids (provider_profiles.id)
         */
        @org.jetbrains.annotations.NotNull()
        public final java.util.Set<java.lang.String> getFavoriteProviderIds() {
            return null;
        }
        
        /**
         * List of provider ids currently selected for comparison
         */
        @org.jetbrains.annotations.NotNull()
        public final java.util.Set<java.lang.String> getSelectedComparisonIds() {
            return null;
        }
        
        /**
         * Filtering
         */
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String getSelectedProfession() {
            return null;
        }
        
        public final float getMinRating() {
            return 0.0F;
        }
        
        public final boolean getShowVideoOnly() {
            return false;
        }
        
        /**
         * Real-time Notifications
         */
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.bms.app.domain.model.Notification> getNotifications() {
            return null;
        }
        
        public final int getUnreadNotificationCount() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.bms.app.domain.model.UserProfile component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.Map<java.lang.String, com.bms.app.domain.model.UserProfile> component10() {
            return null;
        }
        
        public final int component11() {
            return 0;
        }
        
        public final int component12() {
            return 0;
        }
        
        public final int component13() {
            return 0;
        }
        
        public final int component14() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.bms.app.domain.model.Appointment> component15() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.Set<java.lang.String> component16() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.Set<java.lang.String> component17() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String component18() {
            return null;
        }
        
        public final float component19() {
            return 0.0F;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component2() {
            return null;
        }
        
        public final boolean component20() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.bms.app.domain.model.Notification> component21() {
            return null;
        }
        
        public final int component22() {
            return 0;
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
        public final java.util.List<com.bms.app.domain.model.Appointment> component6() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.bms.app.domain.model.Appointment> component7() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.bms.app.domain.model.Appointment> component8() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.Map<java.lang.String, com.bms.app.domain.model.ProviderProfile> component9() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.bms.app.ui.dashboard.UserDashboardUiState.Success copy(@org.jetbrains.annotations.NotNull()
        com.bms.app.domain.model.UserProfile userProfile, @org.jetbrains.annotations.NotNull()
        java.lang.String userInitials, @org.jetbrains.annotations.Nullable()
        com.bms.app.domain.model.Appointment nextAppointment, @org.jetbrains.annotations.NotNull()
        java.util.List<com.bms.app.domain.model.Appointment> upcomingBookings, @org.jetbrains.annotations.NotNull()
        java.util.List<com.bms.app.domain.model.Appointment> pastBookings, @org.jetbrains.annotations.NotNull()
        java.util.List<com.bms.app.domain.model.Appointment> cancelledBookings, @org.jetbrains.annotations.NotNull()
        java.util.List<com.bms.app.domain.model.Appointment> paidAppointments, @org.jetbrains.annotations.NotNull()
        java.util.List<com.bms.app.domain.model.Appointment> allAppointments, @org.jetbrains.annotations.NotNull()
        java.util.Map<java.lang.String, com.bms.app.domain.model.ProviderProfile> providerMap, @org.jetbrains.annotations.NotNull()
        java.util.Map<java.lang.String, com.bms.app.domain.model.UserProfile> userProfileMap, int totalBookings, int upcomingCount, int completedCount, int cancelledCount, @org.jetbrains.annotations.NotNull()
        java.util.List<com.bms.app.domain.model.Appointment> rescheduleRequests, @org.jetbrains.annotations.NotNull()
        java.util.Set<java.lang.String> favoriteProviderIds, @org.jetbrains.annotations.NotNull()
        java.util.Set<java.lang.String> selectedComparisonIds, @org.jetbrains.annotations.Nullable()
        java.lang.String selectedProfession, float minRating, boolean showVideoOnly, @org.jetbrains.annotations.NotNull()
        java.util.List<com.bms.app.domain.model.Notification> notifications, int unreadNotificationCount) {
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