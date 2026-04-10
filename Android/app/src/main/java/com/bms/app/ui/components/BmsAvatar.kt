package com.bms.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bms.app.domain.util.NameUtils

enum class AvatarSize(val size: Dp, val fontSize: TextUnit, val fontWeight: FontWeight) {
    TINY(24.dp, 10.sp, FontWeight.Medium),
    SMALL(36.dp, 12.sp, FontWeight.SemiBold),
    MEDIUM(48.dp, 16.sp, FontWeight.Bold),
    LARGE(64.dp, 20.sp, FontWeight.ExtraBold),
    XL(80.dp, 24.sp, FontWeight.Black)
}

/**
 * A standardized, dynamic Avatar component for all users in the BMS app.
 * It uses deterministic color-coding and standardized initials logic.
 */
@Composable
fun BmsAvatar(
    name: String?,
    modifier: Modifier = Modifier,
    size: AvatarSize = AvatarSize.MEDIUM,
    borderColor: Color = Color.Transparent,
    borderWidth: Dp = 0.dp,
    showGlassEffect: Boolean = false
) {
    val initials = NameUtils.getInitials(name)
    val (primaryColor, darkColor) = NameUtils.getAvatarColors(name)
    
    val brush = if (showGlassEffect) {
        Brush.verticalGradient(
            colors = listOf(
                primaryColor.copy(alpha = 0.8f),
                darkColor.copy(alpha = 0.9f)
            )
        )
    } else {
        Brush.linearGradient(
            colors = listOf(primaryColor, darkColor)
        )
    }

    Box(
        modifier = modifier
            .size(size.size)
            .clip(CircleShape)
            .background(brush)
            .then(
                if (borderWidth > 0.dp) {
                    Modifier.border(borderWidth, borderColor, CircleShape)
                } else Modifier
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = initials,
            color = Color.White,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = size.fontSize,
                fontWeight = size.fontWeight,
                letterSpacing = if (size == AvatarSize.TINY) 0.sp else 1.sp
            )
        )
        
        // Subtle highlighting for premium feel
        if (showGlassEffect) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .border(1.dp, Color.White.copy(alpha = 0.2f), CircleShape)
            )
        }
    }
}
