package com.bms.app.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bms.app.ui.theme.*

@Composable
fun RoleBadge(
    role: String,
    modifier: Modifier = Modifier
) {
    if (role.isBlank()) return

    // Split roles if multiple are present (e.g. "ADMIN, USER")
    val roles = role.split(",").map { it.trim() }.filter { it.isNotEmpty() }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        roles.forEach { roleName ->
            SingleRoleBadge(roleName = roleName)
        }
    }
}

@Composable
private fun SingleRoleBadge(roleName: String) {
    val roleUpper = roleName.uppercase()
    val (bgColor, textColor) = when {
        roleUpper.contains("ADMIN") -> Pair(PrimaryContainer, OnPrimaryContainer)
        roleUpper.contains("PROVIDER") -> Pair(SecondaryContainer, OnSecondaryContainer)
        roleUpper.contains("USER") -> Pair(SurfaceContainerHigh, OnSurfaceVariant)
        else -> Pair(SurfaceContainerHigh, OnSurfaceVariant)
    }

    Surface(
        color = bgColor,
        shape = ChipShape
    ) {
        Text(
            text = roleUpper,
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold
            ),
            color = textColor,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
        )
    }
}

@Composable
fun StatusBadge(
    text: String,
    backgroundColor: Color = SecondaryContainer,
    textColor: Color = OnSecondaryContainer,
    modifier: Modifier = Modifier
) {
    Surface(
        color = backgroundColor,
        shape = ChipShape,
        modifier = modifier
    ) {
        Text(
            text = text.uppercase(),
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold
            ),
            color = textColor,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
        )
    }
}
