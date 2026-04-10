package com.bms.app.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val BmsShapes = Shapes(
    extraSmall = RoundedCornerShape(4.dp),   // Small chips
    small = RoundedCornerShape(8.dp),        // Chips, badges
    medium = RoundedCornerShape(12.dp),      // Input fields, small cards
    large = RoundedCornerShape(16.dp),       // Cards, containers
    extraLarge = RoundedCornerShape(24.dp)   // Bottom sheets, large cards
)

// Specific shape tokens
val PillShape = RoundedCornerShape(percent = 50)
val CardShape = RoundedCornerShape(16.dp)
val InputShape = RoundedCornerShape(12.dp)
val ChipShape = RoundedCornerShape(8.dp)
