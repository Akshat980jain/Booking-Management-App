package com.bms.app.ui.dashboard

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bms.app.domain.model.Appointment
import com.bms.app.domain.model.UserProfile
import com.bms.app.ui.components.*
import com.bms.app.ui.theme.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

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

    val snackbarHostState = remember { SnackbarHostState() }

    // Show error messages via Snackbar
    if (uiState is DashboardUiState.Success) {
        val successState = uiState as DashboardUiState.Success
        LaunchedEffect(successState.errorMessage) {
            successState.errorMessage?.let {
                snackbarHostState.showSnackbar(it)
                viewModel.clearError()
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
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
                    ProfileIncompleteDisplay(onNavigate)
                }
                is DashboardUiState.Error -> {
                    ErrorDisplay(state.message, viewModel::loadDashboard)
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
                    StatsGrid(state)

                    Spacer(modifier = Modifier.height(24.dp))

                    // ── Pending Approvals Section ─────────────
                    if (state.pendingAppointments.isNotEmpty()) {
                        PendingApprovalsSection(
                            appointments = state.pendingAppointments,
                            userProfiles = state.pendingUserProfiles,
                            isActionLoading = state.isActionLoading,
                            onApprove = { viewModel.confirmAppointment(it) },
                            onReject = { viewModel.rejectAppointment(it) }
                        )
                        Spacer(modifier = Modifier.height(32.dp))
                    }

                    // ── Manage Availability CTA ───────────────
                    BmsPrimaryButton(
                        text = "Manage Availability",
                        onClick = onManageAvailability,
                        trailingIcon = Icons.Outlined.ArrowForward
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    // ── Today's Schedule ──────────────────────
                    ScheduleSection(
                        todayAppointments = state.todayAppointments,
                        patientNames = state.patientNames,
                        patientRoles = state.patientRoles,
                        onMessagePatient = onMessagePatient
                    )

                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }
}

// ── Pending Approvals Section ──────────────────────────────────────────────────

@Composable
private fun PendingApprovalsSection(
    appointments: List<Appointment>,
    userProfiles: Map<String, UserProfile>,
    isActionLoading: String?,
    onApprove: (String) -> Unit,
    onReject: (String) -> Unit
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Pending Approvals",
                style = MaterialTheme.typography.headlineSmall,
                color = OnSurface
            )
            Surface(
                color = Primary.copy(alpha = 0.12f),
                shape = CircleShape
            ) {
                Text(
                    text = appointments.size.toString(),
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                    color = Primary,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 4.dp)
        ) {
            items(appointments) { appointment ->
                val isLoading = isActionLoading == appointment.id
                PendingRequestCard(
                    appointment = appointment,
                    user = userProfiles[appointment.userId],
                    isLoading = isLoading,
                    onApprove = { if (isActionLoading == null) onApprove(appointment.id) },
                    onReject = { if (isActionLoading == null) onReject(appointment.id) }
                )
            }
        }
    }
}

@Composable
private fun PendingRequestCard(
    appointment: Appointment,
    user: UserProfile?,
    isLoading: Boolean = false,
    onApprove: () -> Unit,
    onReject: () -> Unit
) {
    val date = try {
        LocalDate.parse(appointment.appointmentDate)
            .format(DateTimeFormatter.ofPattern("MMM d"))
    } catch (_: Exception) { appointment.appointmentDate }

    Surface(
        color = SurfaceContainerLowest,
        shape = RoundedCornerShape(20.dp),
        shadowElevation = 2.dp,
        modifier = Modifier.width(280.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Primary),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = user?.fullName?.take(1)?.uppercase() ?: "P",
                        style = MaterialTheme.typography.labelLarge,
                        color = OnPrimary,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = user?.fullName ?: "Patient",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = OnSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "$date • ${appointment.startTime.take(5)}",
                        style = MaterialTheme.typography.bodySmall,
                        color = OnSurfaceVariant
                    )
                }
                if (appointment.isVideoConsultation == true) {
                    Icon(Icons.Outlined.Videocam, null, tint = Primary, modifier = Modifier.size(18.dp))
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            if (!appointment.notes.isNullOrBlank()) {
                Surface(
                    color = SurfaceContainerLow,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = appointment.notes,
                        style = MaterialTheme.typography.bodySmall,
                        color = OnSurfaceVariant,
                        modifier = Modifier.padding(10.dp),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            } else {
                Spacer(modifier = Modifier.height(8.dp))
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedButton(
                    onClick = onReject,
                    enabled = !isLoading,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error),
                    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.error.copy(alpha = 0.3f))
                ) {
                    Text("Decline", style = MaterialTheme.typography.labelMedium)
                }
                Button(
                    onClick = onApprove,
                    enabled = !isLoading,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Primary)
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = OnPrimary,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text("Confirm", style = MaterialTheme.typography.labelMedium)
                    }
                }
            }
        }
    }
}

// ── Extracted Helper Composables ───────────────────────────────────────────────

@Composable
private fun StatsGrid(state: DashboardUiState.Success) {
    Column {
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
                modifier = Modifier.weight(1f),
                isHighlighted = state.pendingRequests > 0
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
    }
}

@Composable
private fun ScheduleSection(
    todayAppointments: List<Appointment>,
    patientNames: Map<String, String>,
    patientRoles: Map<String, String>,
    onMessagePatient: (String) -> Unit
) {
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

    if (todayAppointments.isEmpty()) {
        Text("No appointments today.", color = OnSurfaceVariant)
    } else {
        todayAppointments.forEach { appointment ->
            val isVideo = appointment.isVideoConsultation ?: false
            val statusColor = when(appointment.status) {
                "confirmed", "approved" -> StatusActive
                "pending" -> StatusPending
                "completed" -> StatusInfo
                "cancelled", "rejected" -> MaterialTheme.colorScheme.error
                else -> OnSurfaceVariant
            }
            
            val badgeText = appointment.status.uppercase()
            val patientName = patientNames[appointment.userId] ?: "Patient (${appointment.userId.take(4)})"
            val patientRole = patientRoles[appointment.userId]

            ScheduleItem(
                time = appointment.startTime.take(5),
                name = patientName,
                patientRole = patientRole,
                badge = badgeText,
                detail = appointment.notes ?: "No notes",
                badgeBg = statusColor.copy(alpha = 0.12f),
                badgeText = statusColor,
                showVideoIcon = isVideo,
                onMessagePatient = { onMessagePatient(appointment.userId) }
            )
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
private fun ProfileIncompleteDisplay(onNavigate: (String) -> Unit) {
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
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(32.dp))
        BmsPrimaryButton(
            text = "Finish Setup",
            onClick = { onNavigate("settings") }
        )
    }
}

@Composable
private fun ErrorDisplay(message: String, onRefresh: () -> Unit) {
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
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = OnSurfaceVariant,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(32.dp))
        BmsPrimaryButton(
            text = "Refresh Dashboard",
            onClick = onRefresh,
            leadingIcon = Icons.Outlined.Refresh
        )
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
