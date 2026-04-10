package com.bms.app.ui.dashboard;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0003\u0003\u0004\u0005B\u0007\b\u0004\u00a2\u0006\u0002\u0010\u0002\u0082\u0001\u0003\u0006\u0007\b\u00a8\u0006\t"}, d2 = {"Lcom/bms/app/ui/dashboard/AdminUiState;", "", "()V", "Error", "Loading", "Success", "Lcom/bms/app/ui/dashboard/AdminUiState$Error;", "Lcom/bms/app/ui/dashboard/AdminUiState$Loading;", "Lcom/bms/app/ui/dashboard/AdminUiState$Success;", "app_debug"})
public abstract class AdminUiState {
    
    private AdminUiState() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\t\u0010\n\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u000b\u001a\u00020\u0005H\u00c6\u0003J\u001d\u0010\f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u00c6\u0001J\u0013\u0010\r\u001a\u00020\u00052\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u00d6\u0003J\t\u0010\u0010\u001a\u00020\u0011H\u00d6\u0001J\t\u0010\u0012\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t\u00a8\u0006\u0013"}, d2 = {"Lcom/bms/app/ui/dashboard/AdminUiState$Error;", "Lcom/bms/app/ui/dashboard/AdminUiState;", "message", "", "isNetwork", "", "(Ljava/lang/String;Z)V", "()Z", "getMessage", "()Ljava/lang/String;", "component1", "component2", "copy", "equals", "other", "", "hashCode", "", "toString", "app_debug"})
    public static final class Error extends com.bms.app.ui.dashboard.AdminUiState {
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String message = null;
        private final boolean isNetwork = false;
        
        public Error(@org.jetbrains.annotations.NotNull()
        java.lang.String message, boolean isNetwork) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getMessage() {
            return null;
        }
        
        public final boolean isNetwork() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component1() {
            return null;
        }
        
        public final boolean component2() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.bms.app.ui.dashboard.AdminUiState.Error copy(@org.jetbrains.annotations.NotNull()
        java.lang.String message, boolean isNetwork) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/bms/app/ui/dashboard/AdminUiState$Loading;", "Lcom/bms/app/ui/dashboard/AdminUiState;", "()V", "app_debug"})
    public static final class Loading extends com.bms.app.ui.dashboard.AdminUiState {
        @org.jetbrains.annotations.NotNull()
        public static final com.bms.app.ui.dashboard.AdminUiState.Loading INSTANCE = null;
        
        private Loading() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u001d\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0086\b\u0018\u00002\u00020\u0001Bo\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\u0006\u0010\t\u001a\u00020\u0003\u0012\u0006\u0010\n\u001a\u00020\u0003\u0012\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f\u0012\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\f\u0012\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\f\u0012\u0006\u0010\u0012\u001a\u00020\u0013\u00a2\u0006\u0002\u0010\u0014J\t\u0010$\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00110\fH\u00c6\u0003J\t\u0010&\u001a\u00020\u0013H\u00c6\u0003J\t\u0010\'\u001a\u00020\u0003H\u00c6\u0003J\t\u0010(\u001a\u00020\u0003H\u00c6\u0003J\t\u0010)\u001a\u00020\u0007H\u00c6\u0003J\t\u0010*\u001a\u00020\u0003H\u00c6\u0003J\t\u0010+\u001a\u00020\u0003H\u00c6\u0003J\t\u0010,\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010-\u001a\b\u0012\u0004\u0012\u00020\r0\fH\u00c6\u0003J\u000f\u0010.\u001a\b\u0012\u0004\u0012\u00020\u000f0\fH\u00c6\u0003J\u0089\u0001\u0010/\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\u00032\b\b\u0002\u0010\n\u001a\u00020\u00032\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f2\u000e\b\u0002\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\f2\u000e\b\u0002\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\f2\b\b\u0002\u0010\u0012\u001a\u00020\u0013H\u00c6\u0001J\u0013\u00100\u001a\u0002012\b\u00102\u001a\u0004\u0018\u000103H\u00d6\u0003J\t\u00104\u001a\u00020\u0003H\u00d6\u0001J\t\u00105\u001a\u00020\u0013H\u00d6\u0001R\u0011\u0010\u0012\u001a\u00020\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\t\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001aR\u0011\u0010\n\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001aR\u0011\u0010\b\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001aR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001aR\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u001aR\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u0018R\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010\u0018\u00a8\u00066"}, d2 = {"Lcom/bms/app/ui/dashboard/AdminUiState$Success;", "Lcom/bms/app/ui/dashboard/AdminUiState;", "totalUsers", "", "totalAppointments", "newUsersThisWeek", "totalRevenue", "", "pendingProviders", "completedSessions", "pendingBookings", "users", "", "Lcom/bms/app/domain/model/UserProfile;", "appointments", "Lcom/bms/app/domain/model/Appointment;", "transactions", "Lcom/bms/app/domain/model/PaymentTransaction;", "adminInitials", "", "(IIIDIIILjava/util/List;Ljava/util/List;Ljava/util/List;Ljava/lang/String;)V", "getAdminInitials", "()Ljava/lang/String;", "getAppointments", "()Ljava/util/List;", "getCompletedSessions", "()I", "getNewUsersThisWeek", "getPendingBookings", "getPendingProviders", "getTotalAppointments", "getTotalRevenue", "()D", "getTotalUsers", "getTransactions", "getUsers", "component1", "component10", "component11", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "", "hashCode", "toString", "app_debug"})
    public static final class Success extends com.bms.app.ui.dashboard.AdminUiState {
        private final int totalUsers = 0;
        private final int totalAppointments = 0;
        private final int newUsersThisWeek = 0;
        private final double totalRevenue = 0.0;
        private final int pendingProviders = 0;
        private final int completedSessions = 0;
        private final int pendingBookings = 0;
        @org.jetbrains.annotations.NotNull()
        private final java.util.List<com.bms.app.domain.model.UserProfile> users = null;
        @org.jetbrains.annotations.NotNull()
        private final java.util.List<com.bms.app.domain.model.Appointment> appointments = null;
        @org.jetbrains.annotations.NotNull()
        private final java.util.List<com.bms.app.domain.model.PaymentTransaction> transactions = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String adminInitials = null;
        
        public Success(int totalUsers, int totalAppointments, int newUsersThisWeek, double totalRevenue, int pendingProviders, int completedSessions, int pendingBookings, @org.jetbrains.annotations.NotNull()
        java.util.List<com.bms.app.domain.model.UserProfile> users, @org.jetbrains.annotations.NotNull()
        java.util.List<com.bms.app.domain.model.Appointment> appointments, @org.jetbrains.annotations.NotNull()
        java.util.List<com.bms.app.domain.model.PaymentTransaction> transactions, @org.jetbrains.annotations.NotNull()
        java.lang.String adminInitials) {
        }
        
        public final int getTotalUsers() {
            return 0;
        }
        
        public final int getTotalAppointments() {
            return 0;
        }
        
        public final int getNewUsersThisWeek() {
            return 0;
        }
        
        public final double getTotalRevenue() {
            return 0.0;
        }
        
        public final int getPendingProviders() {
            return 0;
        }
        
        public final int getCompletedSessions() {
            return 0;
        }
        
        public final int getPendingBookings() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.bms.app.domain.model.UserProfile> getUsers() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.bms.app.domain.model.Appointment> getAppointments() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.bms.app.domain.model.PaymentTransaction> getTransactions() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getAdminInitials() {
            return null;
        }
        
        public final int component1() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.bms.app.domain.model.PaymentTransaction> component10() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component11() {
            return null;
        }
        
        public final int component2() {
            return 0;
        }
        
        public final int component3() {
            return 0;
        }
        
        public final double component4() {
            return 0.0;
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
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.bms.app.domain.model.UserProfile> component8() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.bms.app.domain.model.Appointment> component9() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.bms.app.ui.dashboard.AdminUiState.Success copy(int totalUsers, int totalAppointments, int newUsersThisWeek, double totalRevenue, int pendingProviders, int completedSessions, int pendingBookings, @org.jetbrains.annotations.NotNull()
        java.util.List<com.bms.app.domain.model.UserProfile> users, @org.jetbrains.annotations.NotNull()
        java.util.List<com.bms.app.domain.model.Appointment> appointments, @org.jetbrains.annotations.NotNull()
        java.util.List<com.bms.app.domain.model.PaymentTransaction> transactions, @org.jetbrains.annotations.NotNull()
        java.lang.String adminInitials) {
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