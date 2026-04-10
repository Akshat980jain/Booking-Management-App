package com.bms.app.domain.util;

/**
 * Utility class for name and initial related operations.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001c\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u00042\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007J\u0010\u0010\b\u001a\u00020\u00072\b\u0010\t\u001a\u0004\u0018\u00010\u0007\u00a8\u0006\n"}, d2 = {"Lcom/bms/app/domain/util/NameUtils;", "", "()V", "getAvatarColors", "Lkotlin/Pair;", "Landroidx/compose/ui/graphics/Color;", "name", "", "getInitials", "fullName", "app_debug"})
public final class NameUtils {
    @org.jetbrains.annotations.NotNull()
    public static final com.bms.app.domain.util.NameUtils INSTANCE = null;
    
    private NameUtils() {
        super();
    }
    
    /**
     * Extracts initials from a full name.
     * Returns up to 2 characters (e.g., "John Doe" -> "JD", "Aarjav" -> "AA" or "A").
     * For consistency, we take the first character of the first two names and uppercase them.
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getInitials(@org.jetbrains.annotations.Nullable()
    java.lang.String fullName) {
        return null;
    }
    
    /**
     * Generates a deterministic pair of colors for an avatar gradient based on the name.
     * This ensures that "Aarjav" always gets the same specific shades across the entire app.
     */
    @org.jetbrains.annotations.NotNull()
    public final kotlin.Pair<androidx.compose.ui.graphics.Color, androidx.compose.ui.graphics.Color> getAvatarColors(@org.jetbrains.annotations.Nullable()
    java.lang.String name) {
        return null;
    }
}