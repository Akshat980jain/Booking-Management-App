package com.bms.app.domain.util

/**
 * Utility class for name and initial related operations.
 */
object NameUtils {
    /**
     * Extracts initials from a full name.
     * Returns up to 2 characters (e.g., "John Doe" -> "JD", "Aarjav" -> "AA" or "A").
     * For consistency, we take the first character of the first two names and uppercase them.
     */
    fun getInitials(fullName: String?): String {
        if (fullName.isNullOrBlank()) return "U"
        
        val parts = fullName.trim().split("\\s+".toRegex())
        return if (parts.size >= 2) {
            val first = parts[0].firstOrNull()?.uppercaseChar() ?: ""
            val second = parts[1].firstOrNull()?.uppercaseChar() ?: ""
            "$first$second"
        } else {
            val name = parts[0]
            if (name.length >= 2) {
                // If only one name, take first two letters for consistency if possible, 
                // but standard is usually just the first or first two letters of the name.
                // User mentioned "some show A some show AA", so for single name 
                // we'll take the first two letters of the single name to ensure 2-char consistency if possible.
                name.take(2).uppercase()
            } else {
                name.uppercase()
            }
        }
    }

    /**
     * Generates a deterministic pair of colors for an avatar gradient based on the name.
     * This ensures that "Aarjav" always gets the same specific shades across the entire app.
     */
    fun getAvatarColors(name: String?): Pair<androidx.compose.ui.graphics.Color, androidx.compose.ui.graphics.Color> {
        if (name.isNullOrBlank()) {
            return androidx.compose.ui.graphics.Color(0xFF94A3B8) to androidx.compose.ui.graphics.Color(0xFF64748B) // Slate
        }

        // Curated professional color palettes for avatars (Primary shade, Darker shade)
        val palettes = listOf(
            androidx.compose.ui.graphics.Color(0xFF6366F1) to androidx.compose.ui.graphics.Color(0xFF4338CA), // Indigo
            androidx.compose.ui.graphics.Color(0xFF3B82F6) to androidx.compose.ui.graphics.Color(0xFF1D4ED8), // Blue
            androidx.compose.ui.graphics.Color(0xFFEC4899) to androidx.compose.ui.graphics.Color(0xFFBE185D), // Pink
            androidx.compose.ui.graphics.Color(0xFF8B5CF6) to androidx.compose.ui.graphics.Color(0xFF6D28D9), // Violet
            androidx.compose.ui.graphics.Color(0xFF10B981) to androidx.compose.ui.graphics.Color(0xFF047857), // Emerald
            androidx.compose.ui.graphics.Color(0xFFF59E0B) to androidx.compose.ui.graphics.Color(0xFFB45309), // Amber
            androidx.compose.ui.graphics.Color(0xFF06B6D4) to androidx.compose.ui.graphics.Color(0xFF0E7490), // Cyan
            androidx.compose.ui.graphics.Color(0xFFEF4444) to androidx.compose.ui.graphics.Color(0xFFB91C1C)  // Red
        )

        // Simple hash of the name to pick a stable index
        val hash = name.trim().uppercase().hashCode()
        val index = (hash % palettes.size).let { if (it < 0) it + palettes.size else it }
        
        return palettes[index]
    }
}
