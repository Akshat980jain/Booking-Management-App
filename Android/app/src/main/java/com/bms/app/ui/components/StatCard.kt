package com.bms.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bms.app.ui.theme.*

@Composable
fun StatCard(
    title: String,
    value: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    badge: String? = null,
    badgeColor: Color = StatusActive,
    badgeTextColor: Color = OnStatusActive,
    isHighlighted: Boolean = false,
    containerColor: Color = SurfaceContainerLowest,
    contentColor: Color = OnSurface
) {
    Surface(
        modifier = modifier,
        color = if (isHighlighted) SurfaceContainerLow else containerColor,
        shape = CardShape,
        shadowElevation = 0.dp,
        tonalElevation = 0.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                // Badge goes top-left if present
                if (badge != null) {
                    Surface(
                        color = badgeColor,
                        shape = ChipShape
                    ) {
                        Text(
                            text = badge,
                            style = MaterialTheme.typography.labelSmall,
                            color = badgeTextColor,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                } else {
                    Spacer(modifier = Modifier.width(1.dp))
                }

                // Icon top-right (asymmetric per design)
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = if (isHighlighted) Primary else OnSurfaceVariant,
                    modifier = Modifier.size(28.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Label
            Text(
                text = title.uppercase(),
                style = MaterialTheme.typography.labelSmall.copy(
                    letterSpacing = 1.sp
                ),
                color = if (isHighlighted) OnSurfaceVariant else OnSurfaceVariant
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Value bottom-left (asymmetric per design)
            Text(
                text = value,
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = ManropeFamily
                ),
                color = contentColor
            )
        }
    }
}
