package com.bms.app.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import com.bms.app.ui.theme.*

data class NavItem(
    val label: String,
    val iconSelected: ImageVector,
    val iconUnselected: ImageVector,
    val route: String
)

val MainNavItems = listOf(
    NavItem("Home", Icons.Filled.Home, Icons.Outlined.Home, "home"),
    NavItem("Schedule", Icons.Outlined.CalendarMonth, Icons.Outlined.CalendarMonth, "schedule"),
    NavItem("Providers", Icons.Outlined.Groups, Icons.Outlined.Groups, "providers"),
    NavItem("Settings", Icons.Filled.Settings, Icons.Outlined.Settings, "settings")
)

val AdminNavItems = listOf(
    NavItem("Home", Icons.Filled.Home, Icons.Outlined.Home, "home"),
    NavItem("Schedule", Icons.Outlined.CalendarMonth, Icons.Outlined.CalendarMonth, "schedule"),
    NavItem("Book", Icons.Outlined.AddCircle, Icons.Outlined.AddCircle, "admin_booking"), // High-emphasis center action
    NavItem("Providers", Icons.Outlined.Groups, Icons.Outlined.Groups, "providers"),
    NavItem("Settings", Icons.Filled.Settings, Icons.Outlined.Settings, "settings")
)

val SettingsNavItems = listOf(
    NavItem("Schedule", Icons.Outlined.CalendarMonth, Icons.Outlined.CalendarMonth, "schedule"),
    NavItem("Visibility", Icons.Outlined.Visibility, Icons.Outlined.Visibility, "visibility"),
    NavItem("Alerts", Icons.Outlined.Notifications, Icons.Outlined.Notifications, "alerts"),
    NavItem("Account", Icons.Outlined.Person, Icons.Outlined.Person, "account")
)

// ── User (Customer) Navigation ─────────────────────────────────────────
// 5 items → triggers NotchedBottomBarShape with floating + center button
val UserNavItems = listOf(
    NavItem("Home",     Icons.Filled.Home,                Icons.Outlined.Home,              "home"),
    NavItem("Bookings", Icons.Outlined.CalendarMonth,     Icons.Outlined.CalendarMonth,     "my_bookings"),
    NavItem("Book",     Icons.Outlined.Add,               Icons.Outlined.Add,               "browse_providers"), // center + action
    NavItem("Saved",    Icons.Outlined.Favorite,          Icons.Outlined.FavoriteBorder,    "favorites"),
    NavItem("Settings", Icons.Filled.Settings,            Icons.Outlined.Settings,          "settings")
)

class NotchedBottomBarShape(
    private val cutoutRadius: Float,
    private val cornerRadius: Float = 0f
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            val cutoutCenter = size.width / 2f
            val notchRadius = cutoutRadius
            
            moveTo(0f, cornerRadius)
            // Left top corner if needed
            if (cornerRadius > 0) {
                arcTo(Rect(0f, 0f, cornerRadius * 2, cornerRadius * 2), 180f, 90f, false)
            } else {
                moveTo(0f, 0f)
            }

            // Line to the start of the notch
            lineTo(cutoutCenter - notchRadius * 1.2f, 0f)
            
            // Smooth curve into the notch
            cubicTo(
                cutoutCenter - notchRadius * 0.8f, 0f,
                cutoutCenter - notchRadius, notchRadius * 0.2f,
                cutoutCenter - notchRadius, notchRadius
            )
            
            // Semi-circle cutout (notch)
            arcTo(
                rect = Rect(
                    left = cutoutCenter - notchRadius,
                    top = 0f,
                    right = cutoutCenter + notchRadius,
                    bottom = notchRadius * 2
                ),
                startAngleDegrees = 180f,
                sweepAngleDegrees = -180f,
                forceMoveTo = false
            )

            // Smooth curve out of the notch
            cubicTo(
                cutoutCenter + notchRadius, notchRadius * 0.2f,
                cutoutCenter + notchRadius * 0.8f, 0f,
                cutoutCenter + notchRadius * 1.2f, 0f
            )

            // Line to the end
            lineTo(size.width - cornerRadius, 0f)
            if (cornerRadius > 0) {
                arcTo(Rect(size.width - cornerRadius * 2, 0f, size.width, cornerRadius * 2), 270f, 90f, false)
            } else {
                lineTo(size.width, 0f)
            }

            // Draw to the bottom corners to complete the solid bar
            lineTo(size.width, size.height)
            lineTo(0f, size.height)
            close()
        }
        return androidx.compose.ui.graphics.Outline.Generic(path)
    }
}

@Composable
fun BmsBottomNavBar(
    items: List<NavItem>,
    selectedRoute: String,
    onItemSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val hasCenterAction = items.size == 5
    val notchRadius = 38.dp

    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        // The background surface with the notch
        Surface(
            color = SurfaceContainerLowest,
            tonalElevation = 8.dp,
            shadowElevation = 16.dp,
            shape = if (hasCenterAction) {
                // Notch radius should be larger than half the 64dp button size (32dp)
                // 42dp gives a nice 10dp margin around the button
                val radiusPx = with(androidx.compose.ui.platform.LocalDensity.current) { 42.dp.toPx() }
                NotchedBottomBarShape(cutoutRadius = radiusPx)
            } else {
                androidx.compose.foundation.shape.RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .align(Alignment.BottomCenter)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                items.forEachIndexed { index, item ->
                    val isAction = hasCenterAction && index == 2
                    
                    if (isAction) {
                        // Spacer for the center button which will be floated
                        Spacer(modifier = Modifier.weight(1f))
                    } else {
                        val isSelected = selectedRoute == item.route
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .clickable { onItemSelected(item.route) }
                                .padding(vertical = 8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = if (isSelected) item.iconSelected else item.iconUnselected,
                                contentDescription = item.label,
                                tint = if (isSelected) Primary else OnSurfaceVariant,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = item.label,
                                style = MaterialTheme.typography.labelSmall,
                                color = if (isSelected) Primary else OnSurfaceVariant,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }
                }
            }
        }

        // The Center Action Button (NESTLED IN NOTCH)
        if (hasCenterAction) {
            val centerItem = items[2]
            Surface(
                onClick = { onItemSelected(centerItem.route) },
                shape = CircleShape,
                color = Primary,
                shadowElevation = 12.dp,
                modifier = Modifier
                    .size(64.dp)
                    .align(Alignment.TopCenter)
                    .offset(y = (-12).dp) // Float it up into the notch area
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = centerItem.iconSelected,
                        contentDescription = centerItem.label,
                        tint = OnPrimary,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        }
    }
}
