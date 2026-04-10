package com.bms.app.ui.schedule;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0003\u0003\u0004\u0005B\u0007\b\u0004\u00a2\u0006\u0002\u0010\u0002\u0082\u0001\u0003\u0006\u0007\b\u00a8\u0006\t"}, d2 = {"Lcom/bms/app/ui/schedule/AvailabilityUiState;", "", "()V", "Error", "Loading", "Success", "Lcom/bms/app/ui/schedule/AvailabilityUiState$Error;", "Lcom/bms/app/ui/schedule/AvailabilityUiState$Loading;", "Lcom/bms/app/ui/schedule/AvailabilityUiState$Success;", "app_debug"})
public abstract class AvailabilityUiState {
    
    private AvailabilityUiState() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003H\u00c6\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u00d6\u0003J\t\u0010\r\u001a\u00020\u000eH\u00d6\u0001J\t\u0010\u000f\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0010"}, d2 = {"Lcom/bms/app/ui/schedule/AvailabilityUiState$Error;", "Lcom/bms/app/ui/schedule/AvailabilityUiState;", "message", "", "(Ljava/lang/String;)V", "getMessage", "()Ljava/lang/String;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "app_debug"})
    public static final class Error extends com.bms.app.ui.schedule.AvailabilityUiState {
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
        public final com.bms.app.ui.schedule.AvailabilityUiState.Error copy(@org.jetbrains.annotations.NotNull()
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/bms/app/ui/schedule/AvailabilityUiState$Loading;", "Lcom/bms/app/ui/schedule/AvailabilityUiState;", "()V", "app_debug"})
    public static final class Loading extends com.bms.app.ui.schedule.AvailabilityUiState {
        @org.jetbrains.annotations.NotNull()
        public static final com.bms.app.ui.schedule.AvailabilityUiState.Loading INSTANCE = null;
        
        private Loading() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b(\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0086\b\u0018\u00002\u00020\u0001B\u00b1\u0001\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003\u0012\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0003\u0012\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0003\u0012\b\b\u0002\u0010\u000b\u001a\u00020\f\u0012\b\b\u0002\u0010\r\u001a\u00020\f\u0012\b\b\u0002\u0010\u000e\u001a\u00020\f\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u0010\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0012\u0012\b\b\u0002\u0010\u0013\u001a\u00020\u0010\u0012\b\b\u0002\u0010\u0014\u001a\u00020\u0010\u0012\b\b\u0002\u0010\u0015\u001a\u00020\u0016\u0012\b\b\u0002\u0010\u0017\u001a\u00020\u0012\u0012\b\b\u0002\u0010\u0018\u001a\u00020\u0012\u0012\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\u001a\u00a2\u0006\u0002\u0010\u001bJ\u000f\u00101\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0003J\t\u00102\u001a\u00020\u0010H\u00c6\u0003J\t\u00103\u001a\u00020\u0010H\u00c6\u0003J\t\u00104\u001a\u00020\u0016H\u00c6\u0003J\t\u00105\u001a\u00020\u0012H\u00c6\u0003J\t\u00106\u001a\u00020\u0012H\u00c6\u0003J\u000b\u00107\u001a\u0004\u0018\u00010\u001aH\u00c6\u0003J\u000f\u00108\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003H\u00c6\u0003J\u000f\u00109\u001a\b\u0012\u0004\u0012\u00020\b0\u0003H\u00c6\u0003J\u000f\u0010:\u001a\b\u0012\u0004\u0012\u00020\n0\u0003H\u00c6\u0003J\t\u0010;\u001a\u00020\fH\u00c6\u0003J\t\u0010<\u001a\u00020\fH\u00c6\u0003J\t\u0010=\u001a\u00020\fH\u00c6\u0003J\t\u0010>\u001a\u00020\u0010H\u00c6\u0003J\t\u0010?\u001a\u00020\u0012H\u00c6\u0003J\u00b9\u0001\u0010@\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u00032\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u00032\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u00032\b\b\u0002\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\f2\b\b\u0002\u0010\u000e\u001a\u00020\f2\b\b\u0002\u0010\u000f\u001a\u00020\u00102\b\b\u0002\u0010\u0011\u001a\u00020\u00122\b\b\u0002\u0010\u0013\u001a\u00020\u00102\b\b\u0002\u0010\u0014\u001a\u00020\u00102\b\b\u0002\u0010\u0015\u001a\u00020\u00162\b\b\u0002\u0010\u0017\u001a\u00020\u00122\b\b\u0002\u0010\u0018\u001a\u00020\u00122\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u00c6\u0001J\u0013\u0010A\u001a\u00020\u00162\b\u0010B\u001a\u0004\u0018\u00010CH\u00d6\u0003J\t\u0010D\u001a\u00020\fH\u00d6\u0001J\t\u0010E\u001a\u00020\u0012H\u00d6\u0001R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001dR\u0011\u0010\r\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0011\u0010\u0017\u001a\u00020\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\"R\u0011\u0010\u0013\u001a\u00020\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010$R\u0011\u0010\u0018\u001a\u00020\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010\"R\u0011\u0010\u0011\u001a\u00020\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010\"R\u0013\u0010\u0019\u001a\u0004\u0018\u00010\u001a\u00a2\u0006\b\n\u0000\u001a\u0004\b\'\u0010(R\u0011\u0010\u000b\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010 R\u0011\u0010\u000f\u001a\u00020\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b*\u0010$R\u0011\u0010\u000e\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b+\u0010 R\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b,\u0010\u001dR\u0011\u0010\u0015\u001a\u00020\u0016\u00a2\u0006\b\n\u0000\u001a\u0004\b-\u0010.R\u0011\u0010\u0014\u001a\u00020\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b/\u0010$R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b0\u0010\u001d\u00a8\u0006F"}, d2 = {"Lcom/bms/app/ui/schedule/AvailabilityUiState$Success;", "Lcom/bms/app/ui/schedule/AvailabilityUiState;", "weeklySchedule", "", "Lcom/bms/app/domain/model/AvailabilitySlot;", "blockedDates", "Lcom/bms/app/domain/model/BlockedDate;", "appointments", "Lcom/bms/app/domain/model/Appointment;", "users", "Lcom/bms/app/domain/model/UserProfile;", "todayBookingsCount", "", "completedBookingsCount", "totalBookingsCount", "todayRevenue", "", "providerName", "", "physicalFee", "videoFee", "videoEnabled", "", "currencySymbol", "providerInitials", "providerProfile", "Lcom/bms/app/domain/model/ProviderProfile;", "(Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;IIIDLjava/lang/String;DDZLjava/lang/String;Ljava/lang/String;Lcom/bms/app/domain/model/ProviderProfile;)V", "getAppointments", "()Ljava/util/List;", "getBlockedDates", "getCompletedBookingsCount", "()I", "getCurrencySymbol", "()Ljava/lang/String;", "getPhysicalFee", "()D", "getProviderInitials", "getProviderName", "getProviderProfile", "()Lcom/bms/app/domain/model/ProviderProfile;", "getTodayBookingsCount", "getTodayRevenue", "getTotalBookingsCount", "getUsers", "getVideoEnabled", "()Z", "getVideoFee", "getWeeklySchedule", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "", "hashCode", "toString", "app_debug"})
    public static final class Success extends com.bms.app.ui.schedule.AvailabilityUiState {
        @org.jetbrains.annotations.NotNull()
        private final java.util.List<com.bms.app.domain.model.AvailabilitySlot> weeklySchedule = null;
        @org.jetbrains.annotations.NotNull()
        private final java.util.List<com.bms.app.domain.model.BlockedDate> blockedDates = null;
        @org.jetbrains.annotations.NotNull()
        private final java.util.List<com.bms.app.domain.model.Appointment> appointments = null;
        @org.jetbrains.annotations.NotNull()
        private final java.util.List<com.bms.app.domain.model.UserProfile> users = null;
        private final int todayBookingsCount = 0;
        private final int completedBookingsCount = 0;
        private final int totalBookingsCount = 0;
        private final double todayRevenue = 0.0;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String providerName = null;
        private final double physicalFee = 0.0;
        private final double videoFee = 0.0;
        private final boolean videoEnabled = false;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String currencySymbol = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String providerInitials = null;
        @org.jetbrains.annotations.Nullable()
        private final com.bms.app.domain.model.ProviderProfile providerProfile = null;
        
        public Success(@org.jetbrains.annotations.NotNull()
        java.util.List<com.bms.app.domain.model.AvailabilitySlot> weeklySchedule, @org.jetbrains.annotations.NotNull()
        java.util.List<com.bms.app.domain.model.BlockedDate> blockedDates, @org.jetbrains.annotations.NotNull()
        java.util.List<com.bms.app.domain.model.Appointment> appointments, @org.jetbrains.annotations.NotNull()
        java.util.List<com.bms.app.domain.model.UserProfile> users, int todayBookingsCount, int completedBookingsCount, int totalBookingsCount, double todayRevenue, @org.jetbrains.annotations.NotNull()
        java.lang.String providerName, double physicalFee, double videoFee, boolean videoEnabled, @org.jetbrains.annotations.NotNull()
        java.lang.String currencySymbol, @org.jetbrains.annotations.NotNull()
        java.lang.String providerInitials, @org.jetbrains.annotations.Nullable()
        com.bms.app.domain.model.ProviderProfile providerProfile) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.bms.app.domain.model.AvailabilitySlot> getWeeklySchedule() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.bms.app.domain.model.BlockedDate> getBlockedDates() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.bms.app.domain.model.Appointment> getAppointments() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.bms.app.domain.model.UserProfile> getUsers() {
            return null;
        }
        
        public final int getTodayBookingsCount() {
            return 0;
        }
        
        public final int getCompletedBookingsCount() {
            return 0;
        }
        
        public final int getTotalBookingsCount() {
            return 0;
        }
        
        public final double getTodayRevenue() {
            return 0.0;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getProviderName() {
            return null;
        }
        
        public final double getPhysicalFee() {
            return 0.0;
        }
        
        public final double getVideoFee() {
            return 0.0;
        }
        
        public final boolean getVideoEnabled() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getCurrencySymbol() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getProviderInitials() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final com.bms.app.domain.model.ProviderProfile getProviderProfile() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.bms.app.domain.model.AvailabilitySlot> component1() {
            return null;
        }
        
        public final double component10() {
            return 0.0;
        }
        
        public final double component11() {
            return 0.0;
        }
        
        public final boolean component12() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component13() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component14() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final com.bms.app.domain.model.ProviderProfile component15() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.bms.app.domain.model.BlockedDate> component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.bms.app.domain.model.Appointment> component3() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.bms.app.domain.model.UserProfile> component4() {
            return null;
        }
        
        public final int component5() {
            return 0;
        }
        
        public final int component6() {
            return 0;
        }
        
        public final int component7() {
            return 0;
        }
        
        public final double component8() {
            return 0.0;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component9() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.bms.app.ui.schedule.AvailabilityUiState.Success copy(@org.jetbrains.annotations.NotNull()
        java.util.List<com.bms.app.domain.model.AvailabilitySlot> weeklySchedule, @org.jetbrains.annotations.NotNull()
        java.util.List<com.bms.app.domain.model.BlockedDate> blockedDates, @org.jetbrains.annotations.NotNull()
        java.util.List<com.bms.app.domain.model.Appointment> appointments, @org.jetbrains.annotations.NotNull()
        java.util.List<com.bms.app.domain.model.UserProfile> users, int todayBookingsCount, int completedBookingsCount, int totalBookingsCount, double todayRevenue, @org.jetbrains.annotations.NotNull()
        java.lang.String providerName, double physicalFee, double videoFee, boolean videoEnabled, @org.jetbrains.annotations.NotNull()
        java.lang.String currencySymbol, @org.jetbrains.annotations.NotNull()
        java.lang.String providerInitials, @org.jetbrains.annotations.Nullable()
        com.bms.app.domain.model.ProviderProfile providerProfile) {
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