package com.bms.app.ui.admin;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0006\u0003\u0004\u0005\u0006\u0007\bB\u0007\b\u0004\u00a2\u0006\u0002\u0010\u0002\u0082\u0001\u0006\t\n\u000b\f\r\u000e\u00a8\u0006\u000f"}, d2 = {"Lcom/bms/app/ui/admin/BookingUiState;", "", "()V", "BookingConfirmed", "Error", "Idle", "Loading", "ProcessingPayment", "Success", "Lcom/bms/app/ui/admin/BookingUiState$BookingConfirmed;", "Lcom/bms/app/ui/admin/BookingUiState$Error;", "Lcom/bms/app/ui/admin/BookingUiState$Idle;", "Lcom/bms/app/ui/admin/BookingUiState$Loading;", "Lcom/bms/app/ui/admin/BookingUiState$ProcessingPayment;", "Lcom/bms/app/ui/admin/BookingUiState$Success;", "app_debug"})
public abstract class BookingUiState {
    
    private BookingUiState() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/bms/app/ui/admin/BookingUiState$BookingConfirmed;", "Lcom/bms/app/ui/admin/BookingUiState;", "()V", "app_debug"})
    public static final class BookingConfirmed extends com.bms.app.ui.admin.BookingUiState {
        @org.jetbrains.annotations.NotNull()
        public static final com.bms.app.ui.admin.BookingUiState.BookingConfirmed INSTANCE = null;
        
        private BookingConfirmed() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003H\u00c6\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u00d6\u0003J\t\u0010\r\u001a\u00020\u000eH\u00d6\u0001J\t\u0010\u000f\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0010"}, d2 = {"Lcom/bms/app/ui/admin/BookingUiState$Error;", "Lcom/bms/app/ui/admin/BookingUiState;", "message", "", "(Ljava/lang/String;)V", "getMessage", "()Ljava/lang/String;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "app_debug"})
    public static final class Error extends com.bms.app.ui.admin.BookingUiState {
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
        public final com.bms.app.ui.admin.BookingUiState.Error copy(@org.jetbrains.annotations.NotNull()
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/bms/app/ui/admin/BookingUiState$Idle;", "Lcom/bms/app/ui/admin/BookingUiState;", "()V", "app_debug"})
    public static final class Idle extends com.bms.app.ui.admin.BookingUiState {
        @org.jetbrains.annotations.NotNull()
        public static final com.bms.app.ui.admin.BookingUiState.Idle INSTANCE = null;
        
        private Idle() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/bms/app/ui/admin/BookingUiState$Loading;", "Lcom/bms/app/ui/admin/BookingUiState;", "()V", "app_debug"})
    public static final class Loading extends com.bms.app.ui.admin.BookingUiState {
        @org.jetbrains.annotations.NotNull()
        public static final com.bms.app.ui.admin.BookingUiState.Loading INSTANCE = null;
        
        private Loading() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\f\u001a\u00020\u0005H\u00c6\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u00c6\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u00d6\u0003J\t\u0010\u0012\u001a\u00020\u0003H\u00d6\u0001J\t\u0010\u0013\u001a\u00020\u0005H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u00a8\u0006\u0014"}, d2 = {"Lcom/bms/app/ui/admin/BookingUiState$ProcessingPayment;", "Lcom/bms/app/ui/admin/BookingUiState;", "amount", "", "method", "", "(ILjava/lang/String;)V", "getAmount", "()I", "getMethod", "()Ljava/lang/String;", "component1", "component2", "copy", "equals", "", "other", "", "hashCode", "toString", "app_debug"})
    public static final class ProcessingPayment extends com.bms.app.ui.admin.BookingUiState {
        private final int amount = 0;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String method = null;
        
        public ProcessingPayment(int amount, @org.jetbrains.annotations.NotNull()
        java.lang.String method) {
        }
        
        public final int getAmount() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getMethod() {
            return null;
        }
        
        public final int component1() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.bms.app.ui.admin.BookingUiState.ProcessingPayment copy(int amount, @org.jetbrains.annotations.NotNull()
        java.lang.String method) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u001d\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0086\b\u0018\u00002\u00020\u0001Bi\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\u0006\u0010\t\u001a\u00020\n\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\b\u0012\b\b\u0002\u0010\f\u001a\u00020\r\u0012\b\b\u0002\u0010\u000e\u001a\u00020\b\u0012\b\b\u0002\u0010\u000f\u001a\u00020\b\u0012\b\b\u0002\u0010\u0010\u001a\u00020\r\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0012\u00a2\u0006\u0002\u0010\u0013J\t\u0010#\u001a\u00020\u0003H\u00c6\u0003J\t\u0010$\u001a\u00020\u0012H\u00c6\u0003J\t\u0010%\u001a\u00020\u0005H\u00c6\u0003J\u000f\u0010&\u001a\b\u0012\u0004\u0012\u00020\b0\u0007H\u00c6\u0003J\t\u0010\'\u001a\u00020\nH\u00c6\u0003J\u000b\u0010(\u001a\u0004\u0018\u00010\bH\u00c6\u0003J\t\u0010)\u001a\u00020\rH\u00c6\u0003J\t\u0010*\u001a\u00020\bH\u00c6\u0003J\t\u0010+\u001a\u00020\bH\u00c6\u0003J\t\u0010,\u001a\u00020\rH\u00c6\u0003Ju\u0010-\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\b\b\u0002\u0010\t\u001a\u00020\n2\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\b2\b\b\u0002\u0010\f\u001a\u00020\r2\b\b\u0002\u0010\u000e\u001a\u00020\b2\b\b\u0002\u0010\u000f\u001a\u00020\b2\b\b\u0002\u0010\u0010\u001a\u00020\r2\b\b\u0002\u0010\u0011\u001a\u00020\u0012H\u00c6\u0001J\u0013\u0010.\u001a\u00020\r2\b\u0010/\u001a\u0004\u0018\u000100H\u00d6\u0003J\t\u00101\u001a\u00020\u0012H\u00d6\u0001J\t\u00102\u001a\u00020\bH\u00d6\u0001R\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\u000f\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\u0010\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0018R\u0011\u0010\f\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0018R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0011\u0010\u000e\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0017R\u0013\u0010\u000b\u001a\u0004\u0018\u00010\b\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u0017R\u0011\u0010\u0011\u001a\u00020\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\"\u00a8\u00063"}, d2 = {"Lcom/bms/app/ui/admin/BookingUiState$Success;", "Lcom/bms/app/ui/admin/BookingUiState;", "provider", "Lcom/bms/app/domain/model/UserProfile;", "providerProfile", "Lcom/bms/app/domain/model/ProviderProfile;", "availableSlots", "", "", "selectedDate", "Ljava/time/LocalDate;", "selectedSlot", "isVideoSelected", "", "selectedPaymentMethod", "bookingNote", "isFeeWaived", "waivedAmount", "", "(Lcom/bms/app/domain/model/UserProfile;Lcom/bms/app/domain/model/ProviderProfile;Ljava/util/List;Ljava/time/LocalDate;Ljava/lang/String;ZLjava/lang/String;Ljava/lang/String;ZI)V", "getAvailableSlots", "()Ljava/util/List;", "getBookingNote", "()Ljava/lang/String;", "()Z", "getProvider", "()Lcom/bms/app/domain/model/UserProfile;", "getProviderProfile", "()Lcom/bms/app/domain/model/ProviderProfile;", "getSelectedDate", "()Ljava/time/LocalDate;", "getSelectedPaymentMethod", "getSelectedSlot", "getWaivedAmount", "()I", "component1", "component10", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "", "hashCode", "toString", "app_debug"})
    public static final class Success extends com.bms.app.ui.admin.BookingUiState {
        @org.jetbrains.annotations.NotNull()
        private final com.bms.app.domain.model.UserProfile provider = null;
        @org.jetbrains.annotations.NotNull()
        private final com.bms.app.domain.model.ProviderProfile providerProfile = null;
        @org.jetbrains.annotations.NotNull()
        private final java.util.List<java.lang.String> availableSlots = null;
        @org.jetbrains.annotations.NotNull()
        private final java.time.LocalDate selectedDate = null;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.String selectedSlot = null;
        private final boolean isVideoSelected = false;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String selectedPaymentMethod = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String bookingNote = null;
        private final boolean isFeeWaived = false;
        private final int waivedAmount = 0;
        
        public Success(@org.jetbrains.annotations.NotNull()
        com.bms.app.domain.model.UserProfile provider, @org.jetbrains.annotations.NotNull()
        com.bms.app.domain.model.ProviderProfile providerProfile, @org.jetbrains.annotations.NotNull()
        java.util.List<java.lang.String> availableSlots, @org.jetbrains.annotations.NotNull()
        java.time.LocalDate selectedDate, @org.jetbrains.annotations.Nullable()
        java.lang.String selectedSlot, boolean isVideoSelected, @org.jetbrains.annotations.NotNull()
        java.lang.String selectedPaymentMethod, @org.jetbrains.annotations.NotNull()
        java.lang.String bookingNote, boolean isFeeWaived, int waivedAmount) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.bms.app.domain.model.UserProfile getProvider() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.bms.app.domain.model.ProviderProfile getProviderProfile() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<java.lang.String> getAvailableSlots() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.time.LocalDate getSelectedDate() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String getSelectedSlot() {
            return null;
        }
        
        public final boolean isVideoSelected() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getSelectedPaymentMethod() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getBookingNote() {
            return null;
        }
        
        public final boolean isFeeWaived() {
            return false;
        }
        
        public final int getWaivedAmount() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.bms.app.domain.model.UserProfile component1() {
            return null;
        }
        
        public final int component10() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.bms.app.domain.model.ProviderProfile component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<java.lang.String> component3() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.time.LocalDate component4() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String component5() {
            return null;
        }
        
        public final boolean component6() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component7() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component8() {
            return null;
        }
        
        public final boolean component9() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.bms.app.ui.admin.BookingUiState.Success copy(@org.jetbrains.annotations.NotNull()
        com.bms.app.domain.model.UserProfile provider, @org.jetbrains.annotations.NotNull()
        com.bms.app.domain.model.ProviderProfile providerProfile, @org.jetbrains.annotations.NotNull()
        java.util.List<java.lang.String> availableSlots, @org.jetbrains.annotations.NotNull()
        java.time.LocalDate selectedDate, @org.jetbrains.annotations.Nullable()
        java.lang.String selectedSlot, boolean isVideoSelected, @org.jetbrains.annotations.NotNull()
        java.lang.String selectedPaymentMethod, @org.jetbrains.annotations.NotNull()
        java.lang.String bookingNote, boolean isFeeWaived, int waivedAmount) {
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