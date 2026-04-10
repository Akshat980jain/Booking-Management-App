package com.bms.app.ui.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bms.app.ui.components.*
import com.bms.app.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationPreferencesScreen(
    onBack: () -> Unit
) {
    var emailNotifications by remember { mutableStateOf(true) }
    var smsNotifications by remember { mutableStateOf(false) }
    var appointmentReminders by remember { mutableStateOf(true) }
    var newBookingAlerts by remember { mutableStateOf(true) }
    var rescheduleRequests by remember { mutableStateOf(true) }
    var newReviews by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Settings",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Background)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
        ) {
            // ── Notification Preferences Card ─────────
            Surface(
                color = SurfaceContainerLowest,
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Outlined.Notifications, null, tint = OnSurfaceVariant)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "Notification Preferences",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                            color = OnSurface
                        )
                    }
                    Text(
                        "Choose how you want to receive updates about your appointments",
                        style = MaterialTheme.typography.bodySmall,
                        color = OnSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // ── Channels ───────────────────────
                    Text(
                        "Notification Channels",
                        style = MaterialTheme.typography.titleSmall,
                        color = Primary
                    )
                    HorizontalDivider(color = GhostBorder, modifier = Modifier.padding(vertical = 8.dp))

                    SettingsToggleRow(
                        title = "Email Notifications",
                        subtitle = "Receive notifications via email",
                        icon = Icons.Outlined.Email,
                        checked = emailNotifications,
                        onCheckedChange = { emailNotifications = it }
                    )

                    SettingsToggleRow(
                        title = "SMS Notifications",
                        subtitle = "Receive text messages for urgent updates",
                        icon = Icons.Outlined.Chat,
                        checked = smsNotifications,
                        onCheckedChange = { smsNotifications = it }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // ── Types ──────────────────────────
                    Text(
                        "Notification Types",
                        style = MaterialTheme.typography.titleSmall,
                        color = Primary
                    )
                    HorizontalDivider(color = GhostBorder, modifier = Modifier.padding(vertical = 8.dp))

                    SettingsToggleRow(
                        title = "Appointment Reminders",
                        subtitle = "Get reminded before upcoming appointments",
                        checked = appointmentReminders,
                        onCheckedChange = { appointmentReminders = it }
                    )

                    SettingsToggleRow(
                        title = "New Booking Alerts",
                        subtitle = "Notify when a new booking request is received",
                        checked = newBookingAlerts,
                        onCheckedChange = { newBookingAlerts = it }
                    )

                    SettingsToggleRow(
                        title = "Reschedule Requests",
                        subtitle = "Notify when a client requests to reschedule",
                        checked = rescheduleRequests,
                        onCheckedChange = { rescheduleRequests = it }
                    )

                    SettingsToggleRow(
                        title = "New Reviews",
                        subtitle = "Notify when a client leaves a review",
                        checked = newReviews,
                        onCheckedChange = { newReviews = it }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    BmsPrimaryButton(
                        text = "Save Preferences",
                        onClick = { },
                        leadingIcon = Icons.Outlined.Check
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // ── Calendar Sync Card ────────────────────
            Surface(
                color = SurfaceContainerLowest,
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Outlined.CalendarMonth, null, tint = Primary)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "Calendar Sync",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                            color = OnSurface
                        )
                    }
                    Text(
                        "Connect your calendar to automatically sync appointments",
                        style = MaterialTheme.typography.bodySmall,
                        color = OnSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Google Calendar item
                    Surface(
                        color = SurfaceContainerLow,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    Icons.Outlined.CalendarMonth,
                                    null,
                                    tint = Primary,
                                    modifier = Modifier.size(28.dp)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(
                                        "Google Calendar",
                                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                        color = OnSurface
                                    )
                                    Text(
                                        "Connect to sync your appointments",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = OnSurfaceVariant
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            BmsSecondaryButton(
                                text = "Connect",
                                onClick = { }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
