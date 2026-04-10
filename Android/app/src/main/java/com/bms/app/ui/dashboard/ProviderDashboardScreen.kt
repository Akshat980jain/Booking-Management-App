package com.bms.app.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bms.app.ui.components.*
import com.bms.app.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProviderDashboardScreen(
    onManageAvailability: () -> Unit = {},
    onNavigate: (String) -> Unit = {},
    onMessagePatient: (userId: String) -> Unit = {},
    onContactSupport: () -> Unit = {},
    onInboxClick: () -> Unit = {},
    viewModel: DashboardViewModel = hiltViewModel()
) {
    var selectedNav by remember { mutableStateOf("home") }
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    // Premium FAB Logic: Show label when at top, shrink to icon when scrolling
    val isExpanded by remember {
        derivedStateOf { scrollState.value == 0 }
    }

    Scaffold(
        topBar = {
            val initials = if (uiState is DashboardUiState.Success) {
                (uiState as DashboardUiState.Success).userInitials
            } else "DM"
            BmsTopBar(
                title = "BookEase24X7",
                avatarInitials = initials,
                isLoading = uiState is DashboardUiState.Loading,
                onMessagesClick = onInboxClick,
                onAvatarClick = { onNavigate("settings") }
            )
        },
        bottomBar = {
            BmsBottomNavBar(
                items = MainNavItems,
                selectedRoute = selectedNav,
                onItemSelected = { route ->
                    selectedNav = route
                    onNavigate(route)
                }
            )
        },
        floatingActionButton = {
            if (uiState is DashboardUiState.Success) {
                ExtendedFloatingActionButton(
                    text = { Text("Support", style = MaterialTheme.typography.labelLarge) },
                    icon = { Icon(Icons.Outlined.SupportAgent, contentDescription = null) },
                    onClick = onContactSupport,
                    expanded = isExpanded,
                    containerColor = Primary,
                    contentColor = OnPrimary,
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Background)
                .padding(padding)
                .verticalScroll(scrollState)
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            when (val state = uiState) {
                is DashboardUiState.Loading -> {
                    ProviderDashboardSkeleton()
                }
                is DashboardUiState.ProfileIncomplete -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(vertical = 40.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Surface(
                            color = PrimaryContainer.copy(alpha = 0.4f),
                            shape = CircleShape,
                            modifier = Modifier.size(80.dp)
                        ) {
                            Icon(
                                Icons.Outlined.PersonPin,
                                contentDescription = null,
                                tint = Primary,
                                modifier = Modifier.padding(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(24.dp))
                        Text(
                            text = "Complete Your Profile",
                            style = MaterialTheme.typography.headlineSmall,
                            color = OnSurface,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Please set up your professional profile to start managing bookings and availability.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = OnSurfaceVariant,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                        Spacer(modifier = Modifier.height(32.dp))
                        BmsPrimaryButton(
                            text = "Finish Setup",
                            onClick = { onNavigate("settings") } // Navigate to settings/profile
                        )
                    }
                }
                is DashboardUiState.Error -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Surface(
                            color = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.4f),
                            shape = CircleShape,
                            modifier = Modifier.size(80.dp)
                        ) {
                            Icon(
                                Icons.Outlined.ErrorOutline,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.error,
                                modifier = Modifier.padding(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(24.dp))
                        Text(
                            text = "Something went wrong",
                            style = MaterialTheme.typography.headlineSmall,
                            color = OnSurface,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = state.message,
                            style = MaterialTheme.typography.bodyMedium,
                            color = OnSurfaceVariant,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(32.dp))
                        BmsPrimaryButton(
                            text = "Refresh Dashboard",
                            onClick = { viewModel.loadDashboard() },
                            leadingIcon = Icons.Outlined.Refresh
                        )
                    }
                }
                is DashboardUiState.Success -> {
                    // ── Welcome Header ────────────────────────
                    Text(
                        text = "OVERVIEW",
                        style = MaterialTheme.typography.labelSmall.copy(
                            letterSpacing = 2.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        color = OnSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Welcome back,\n${state.userProfile.fullName}",
                        style = MaterialTheme.typography.headlineLarge,
                        color = OnSurface
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // ── Stats Grid 2×2 ────────────────────────
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        StatCard(
                            title = "Today's Appts",
                            value = state.todayAppointments.size.toString(),
                            icon = Icons.Outlined.EventAvailable,
                            modifier = Modifier.weight(1f)
                        )
                        StatCard(
                            title = "Pending",
                            value = state.pendingRequests.toString(),
                            icon = Icons.Outlined.Description,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        StatCard(
                            title = "Patients",
                            value = state.newPatients.toString(),
                            icon = Icons.Outlined.Groups,
                            modifier = Modifier.weight(1f)
                        )
                        StatCard(
                            title = "Total Revenue",
                            value = "${state.currencySymbol}${state.totalRevenue.toInt()}",
                            icon = Icons.Outlined.AccountBalanceWallet,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // ── Manage Availability CTA ───────────────
                    BmsPrimaryButton(
                        text = "Manage Availability",
                        onClick = onManageAvailability,
                        trailingIcon = Icons.Outlined.ArrowForward
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    // ── Today's Schedule ──────────────────────
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Today's Schedule",
                            style = MaterialTheme.typography.headlineSmall,
                            color = OnSurface
                        )
                        TextButton(onClick = { }) {
                            Text(
                                "View Calendar",
                                style = MaterialTheme.typography.labelLarge,
                                color = Primary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    if (state.todayAppointments.isEmpty()) {
                        Text("No appointments today.", color = OnSurfaceVariant)
                    } else {
                        state.todayAppointments.forEach { appointment ->
                            val isVideo = appointment.isVideoConsultation ?: false
                            val badgeText = if (isVideo) "VIDEO CALL" else "IN-PERSON"
                            val badgeBg = if (isVideo) PrimaryContainer else SecondaryContainer
                            val badgeTextColor = if (isVideo) OnPrimaryContainer else OnSecondaryContainer

                            val patientName = state.patientNames[appointment.userId] ?: "Patient (${appointment.userId.take(4)})"
                            val patientRole = state.patientRoles[appointment.userId]

                            ScheduleItem(
                                time = appointment.startTime.take(5),
                                name = patientName,
                                patientRole = patientRole,
                                badge = badgeText,
                                detail = appointment.notes ?: "No notes",
                                badgeBg = badgeBg,
                                badgeText = badgeTextColor,
                                showVideoIcon = isVideo,
                                onMessagePatient = { onMessagePatient(appointment.userId) }
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }
}

@Composable
private fun ScheduleItem(
    time: String,
    name: String,
    patientRole: String? = null,
    badge: String,
    detail: String,
    badgeBg: androidx.compose.ui.graphics.Color,
    badgeText: androidx.compose.ui.graphics.Color,
    showVideoIcon: Boolean = false,
    onMessagePatient: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        // Time column
        Text(
            text = time,
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
            color = OnSurfaceVariant,
            modifier = Modifier.width(48.dp)
        )

        // Timeline line
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(Primary)
            )
            Box(
                modifier = Modifier
                    .width(2.dp)
                    .height(60.dp)
                    .background(SurfaceContainerHigh)
            )
        }

        // Content card
        Surface(
            color = SurfaceContainerLowest,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = name,
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = OnSurface,
                        )
                        if (!patientRole.isNullOrBlank()) {
                            Spacer(modifier = Modifier.height(4.dp))
                            RoleBadge(role = patientRole)
                        }
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    StatusBadge(
                        text = badge,
                        backgroundColor = badgeBg,
                        textColor = badgeText
                    )
                    // ── Message Patient icon ──────────────────
                    if (onMessagePatient != null) {
                        Spacer(modifier = Modifier.width(4.dp))
                        IconButton(
                            onClick = onMessagePatient,
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                Icons.Outlined.ChatBubbleOutline,
                                contentDescription = "Message Patient",
                                tint = Primary,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(6.dp))
                if (showVideoIcon) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Outlined.Videocam,
                            null,
                            tint = Primary,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = detail,
                            style = MaterialTheme.typography.bodySmall,
                            color = Primary
                        )
                    }
                } else {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        if (detail.contains("Room")) {
                            Icon(
                                Icons.Outlined.LocationOn,
                                null,
                                tint = OnSurfaceVariant,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                        }
                        Text(
                            text = detail,
                            style = MaterialTheme.typography.bodySmall,
                            color = OnSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun UpcomingCard(
    name: String,
    date: String,
    description: String,
    initials: String
) {
    Surface(
        color = SurfaceContainerLow,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.width(200.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(SecondaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = initials,
                        style = MaterialTheme.typography.labelMedium,
                        color = OnSecondaryContainer
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = name,
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        color = OnSurface
                    )
                    Text(
                        text = date,
                        style = MaterialTheme.typography.bodySmall,
                        color = OnSurfaceVariant
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = OnSurfaceVariant
            )
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = { 0.6f },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(3.dp)
                    .clip(PillShape),
                color = Primary,
                trackColor = SurfaceContainerHigh
            )
        }
    }
}
