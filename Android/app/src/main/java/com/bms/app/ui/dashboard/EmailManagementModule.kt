package com.bms.app.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bms.app.ui.theme.*

data class EmailTemplate(
    val id: String,
    val name: String,
    val description: String,
    val subject: String,
    val variables: List<String>,
    val status: Boolean,
    val updated: String
)

val DummyTemplates = listOf(
    EmailTemplate(
        "1", "account_suspended", "Sent when account is suspended",
        "Account Suspended", listOf("{{user_name}}", "{{reason}}"), true, "Feb 28, 2026"
    ),
    EmailTemplate(
        "2", "appointment_cancelled", "Sent when appointment is cancelled",
        "Appointment Cancelled", listOf("{{user_name}}", "{{provider_name}}", "{{date}}", "+1"), true, "Feb 28, 2026"
    ),
    EmailTemplate(
        "3", "appointment_confirmation", "Sent when an appointment is confirmed",
        "Your appointment is confirmed", listOf("{{user_name}}", "{{provider_name}}", "{{date}}", "+1"), true, "Feb 28, 2026"
    ),
    EmailTemplate(
        "4", "appointment_reminder", "Sent as appointment reminder",
        "Reminder: Upcoming appointment", listOf("{{user_name}}", "{{provider_name}}", "{{date}}", "+1"), true, "Feb 28, 2026"
    ),
    EmailTemplate(
        "5", "provider_approved", "Sent when provider is approved",
        "Your provider account is approved!", listOf("{{provider_name}}"), true, "Feb 28, 2026"
    ),
    EmailTemplate(
        "6", "welcome", "Sent to new users",
        "Welcome to BookEase!", listOf("{{user_name}}"), true, "Feb 28, 2026"
    )
)

@Composable
fun EmailManagementModule() {
    Column(modifier = Modifier.fillMaxWidth()) {
        // ── Sub Navigation ──────────────────────────
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            EmailSubTab("Templates", Icons.Outlined.Email, isSelected = true)
            EmailSubTab("Preview", Icons.Outlined.AutoAwesome, isSelected = false)
        }

        Surface(
            color = SurfaceContainerLowest,
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Email Templates",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Manage your automated notifications",
                            style = MaterialTheme.typography.labelSmall,
                            color = OnSurfaceVariant
                        )
                    }

                    Button(
                        onClick = { },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1F2937)),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Icon(Icons.Outlined.Add, null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("New", fontSize = 13.sp)
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // List of Templates
                DummyTemplates.forEach { template ->
                    EmailTemplateCard(template)
                    if (template != DummyTemplates.last()) {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(80.dp))
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EmailTemplateCard(template: EmailTemplate) {
    var isEnabled by remember { mutableStateOf(template.status) }

    Surface(
        color = SurfaceContainerLow.copy(alpha = 0.5f),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Top Row: Name and Actions
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = template.name,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        color = OnSurface
                    )
                    Text(
                        text = template.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = OnSurfaceVariant
                    )
                }
                
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = { }, modifier = Modifier.size(32.dp)) {
                        Icon(Icons.Outlined.Visibility, null, tint = OnSurfaceVariant, modifier = Modifier.size(18.dp))
                    }
                    IconButton(onClick = { }, modifier = Modifier.size(32.dp)) {
                        Icon(Icons.Outlined.Edit, null, tint = OnSurfaceVariant, modifier = Modifier.size(18.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Subject Line
            Surface(
                color = Color.White.copy(alpha = 0.5f),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Subject: ${template.subject}",
                    style = MaterialTheme.typography.labelSmall,
                    color = OnSurfaceVariant,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Bottom Row: Tags and Switch
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // FlowRow for Tags
                FlowRow(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    template.variables.forEach { variable ->
                        Text(
                            text = variable,
                            fontSize = 10.sp,
                            color = Primary,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier
                                .background(Primary.copy(alpha = 0.08f), RoundedCornerShape(4.dp))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                Switch(
                    checked = isEnabled,
                    onCheckedChange = { isEnabled = it },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = Color(0xFF1F2937)
                    ),
                    modifier = Modifier.scale(0.8f)
                )
            }
        }
    }
}

@Composable
fun EmailSubTab(label: String, icon: androidx.compose.ui.graphics.vector.ImageVector, isSelected: Boolean) {
    Surface(
        color = if (isSelected) SurfaceContainerLowest else Color.Transparent,
        shape = RoundedCornerShape(8.dp),
        border = if (isSelected) androidx.compose.foundation.BorderStroke(1.dp, GhostBorder) else null
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (isSelected) Primary else OnSurfaceVariant,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = if (isSelected) OnSurface else OnSurfaceVariant,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
            )
        }
    }
}
