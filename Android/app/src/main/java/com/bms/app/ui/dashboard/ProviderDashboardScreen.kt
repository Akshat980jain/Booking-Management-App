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
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
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

    var reviewingAppointment by remember { mutableStateOf<Appointment?>(null) }
    var reviewingUser by remember { mutableStateOf<UserProfile?>(null) }
    var showRescheduleDialog by remember { mutableStateOf<Appointment?>(null) }

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
                            onClick = { appt, user ->
                                reviewingAppointment = appt
                                reviewingUser = user
                            }
                        )
                        Spacer(modifier = Modifier.height(32.dp))
                    }

                    // ── Manage Availability CTA ───────────────
                    BmsButton(
                        text = "Manage Availability",
                        onClick = onManageAvailability,
                        trailingIcon = Icons.Outlined.ArrowForward
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    // ── Upcoming Bookings ─────────────────────
                    ScheduleSection(
                        todayAppointments = state.todayAppointments,
                        allUpcomingAppointments = state.allUpcomingAppointments,
                        patientNames = state.patientNames,
                        patientRoles = state.patientRoles,
                        onMessagePatient = onMessagePatient
                    )

                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }

    // ── Reschedule Dialog logic ──
    showRescheduleDialog?.let { appt ->
        RescheduleDialog(
            onDismiss = { showRescheduleDialog = null },
            onConfirm = { message ->
                viewModel.suggestReschedule(appt.id, message)
                showRescheduleDialog = null
                reviewingAppointment = null
            }
        )
    }

    // ── Review Bottom Sheet ──
    if (reviewingAppointment != null) {
        val appt = reviewingAppointment!!
        val isActionLoading = (uiState as? DashboardUiState.Success)?.isActionLoading == appt.id
        
        // Auto-dismiss if removed from pending
        val isStillPending = (uiState as? DashboardUiState.Success)?.pendingAppointments?.any { it.id == appt.id } == true
        if (!isStillPending && !isActionLoading) {
            reviewingAppointment = null
        } else {
            AppointmentReviewSheet(
                appointment = appt,
                user = reviewingUser,
                isLoading = isActionLoading,
                currencySymbol = (uiState as? DashboardUiState.Success)?.currencySymbol ?: "₹",
                onDismiss = { reviewingAppointment = null },
                onConfirm = { viewModel.confirmAppointment(appt.id) },
                onSuggestReschedule = { showRescheduleDialog = appt },
                onDecline = {
                    viewModel.rejectAppointment(appt.id)
                    reviewingAppointment = null
                }
            )
        }
    }
}

// ── Pending Approvals Section ──────────────────────────────────────────────────

@Composable
private fun PendingApprovalsSection(
    appointments: List<Appointment>,
    userProfiles: Map<String, UserProfile>,
    isActionLoading: String?,
    onClick: (Appointment, UserProfile?) -> Unit
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
                val user = userProfiles[appointment.userId]
                PendingRequestCard(
                    appointment = appointment,
                    user = user,
                    isLoading = isLoading,
                    onClick = { if (isActionLoading == null) onClick(appointment, user) }
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
    onClick: () -> Unit
) {
    val date = try {
        LocalDate.parse(appointment.appointmentDate)
            .format(DateTimeFormatter.ofPattern("MMM d"))
    } catch (_: Exception) { appointment.appointmentDate }

    Surface(
        color = SurfaceContainerLowest,
        shape = RoundedCornerShape(20.dp),
        shadowElevation = 2.dp,
        modifier = Modifier.width(280.dp),
        onClick = onClick
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

            // Replacing immediate buttons with a subtle action prompt since the card is now clickable
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(20.dp), color = Primary, strokeWidth = 2.dp)
                } else {
                    Text("Tap to review", style = MaterialTheme.typography.labelSmall, color = Primary, fontWeight = FontWeight.Bold)
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
    allUpcomingAppointments: List<Appointment>,
    patientNames: Map<String, String>,
    patientRoles: Map<String, String>,
    onMessagePatient: (String) -> Unit
) {
    // Only display active or pending upcoming appointments
    val displayList = allUpcomingAppointments.filter { it.status.lowercase() in listOf("confirmed", "approved", "pending") }
    val today = java.time.LocalDate.now()

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Upcoming Bookings",
            style = MaterialTheme.typography.headlineSmall,
            color = OnSurface
        )
        if (displayList.isNotEmpty()) {
            Surface(
                color = Primary.copy(alpha = 0.12f),
                shape = RoundedCornerShape(20.dp)
            ) {
                Text(
                    "${displayList.size} total",
                    style = MaterialTheme.typography.labelSmall,
                    color = Primary,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                )
            }
        }
    }

    Spacer(modifier = Modifier.height(16.dp))

    if (displayList.isEmpty()) {
        Surface(
            color = SurfaceContainerLow,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(Icons.Outlined.EventBusy, null, tint = OnSurfaceVariant, modifier = Modifier.size(36.dp))
                Spacer(modifier = Modifier.height(12.dp))
                Text("No upcoming bookings", style = MaterialTheme.typography.titleSmall, color = OnSurface, fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(4.dp))
                Text("Approved appointments will appear here.", style = MaterialTheme.typography.bodySmall, color = OnSurfaceVariant, textAlign = TextAlign.Center)
            }
        }
    } else {
        // Group by date for clarity
        val grouped = displayList.groupBy { it.appointmentDate }
        grouped.forEach { (dateStr, appointments) ->
            val label = runCatching {
                val d = java.time.LocalDate.parse(dateStr)
                when (d) {
                    today -> "Today"
                    today.plusDays(1) -> "Tomorrow"
                    else -> d.format(java.time.format.DateTimeFormatter.ofPattern("EEE, d MMM", java.util.Locale.getDefault()))
                }
            }.getOrElse { dateStr }

            // Date group header
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                color = Primary,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            appointments.forEach { appointment ->
                val isVideo = appointment.isVideoConsultation ?: false
                val statusColor = when (appointment.status) {
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
        BmsButton(
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
        BmsButton(
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

// ── Appointment Review Sheet ──────────────────────────────────────────────────
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppointmentReviewSheet(
    appointment: Appointment,
    user: UserProfile?,
    isLoading: Boolean,
    currencySymbol: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    onSuggestReschedule: () -> Unit,
    onDecline: () -> Unit
) {
    val date = try {
        java.time.LocalDate.parse(appointment.appointmentDate)
            .format(java.time.format.DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy"))
    } catch (_: Exception) { appointment.appointmentDate }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = SurfaceContainerLowest,
        dragHandle = { BottomSheetDefaults.DragHandle() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 32.dp)
        ) {
            Text(
                "Review Request",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = OnSurface
            )
            Spacer(modifier = Modifier.height(24.dp))

            // User Info
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(Primary),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = user?.fullName?.take(1)?.uppercase() ?: "P",
                        style = MaterialTheme.typography.titleLarge,
                        color = OnPrimary,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = user?.fullName ?: "Patient",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = OnSurface
                    )
                    Text(
                        text = "New Patient",
                        style = MaterialTheme.typography.labelSmall,
                        color = OnSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Details
            Surface(
                color = SurfaceContainerLow,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row {
                        Icon(Icons.Outlined.CalendarToday, null, tint = Primary, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(date, style = MaterialTheme.typography.bodyMedium, color = OnSurface)
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Row {
                        Icon(Icons.Outlined.AccessTime, null, tint = Primary, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(appointment.startTime, style = MaterialTheme.typography.bodyMedium, color = OnSurface)
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Row {
                        Icon(if (appointment.isVideoConsultation == true) Icons.Outlined.Videocam else Icons.Outlined.LocationOn, null, tint = Primary, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(if (appointment.isVideoConsultation == true) "Video Consultation" else "In-Person Visit", style = MaterialTheme.typography.bodyMedium, color = OnSurface)
                    }
                }
            }

            if (!appointment.notes.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(16.dp))
                Text("Patient Notes", style = MaterialTheme.typography.labelMedium, color = OnSurfaceVariant)
                Spacer(modifier = Modifier.height(8.dp))
                Surface(
                    color = SurfaceContainerLow,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = appointment.notes,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(16.dp),
                        color = OnSurface
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Actions
            Button(
                onClick = onConfirm,
                enabled = !isLoading,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                contentPadding = PaddingValues(vertical = 14.dp)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(20.dp), color = OnPrimary, strokeWidth = 2.dp)
                } else {
                    Text("Approve Appointment", fontWeight = FontWeight.Bold)
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedButton(
                onClick = onSuggestReschedule,
                enabled = !isLoading,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                contentPadding = PaddingValues(vertical = 14.dp)
            ) {
                Text("Suggest Change of Date")
            }
            Spacer(modifier = Modifier.height(12.dp))
            TextButton(
                onClick = onDecline,
                enabled = !isLoading,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Decline Request", color = MaterialTheme.colorScheme.error)
            }
        }
    }
}

// ── Reschedule Dialog ────────────────────────────────────────────────────────
@Composable
private fun RescheduleDialog(
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var message by remember { mutableStateOf("") }
    
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .wrapContentHeight(),
            shape = RoundedCornerShape(20.dp),
            color = SurfaceContainerLowest,
            tonalElevation = 6.dp
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text(
                    "Suggest Reschedule",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "This will decline the current request and prompt the patient to choose another date. Please include a message with your suggested availability.",
                    style = MaterialTheme.typography.bodySmall,
                    color = OnSurfaceVariant
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = message,
                    onValueChange = { message = it },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    placeholder = { Text("e.g. Can you please book for tomorrow afternoon? I am unavailable today.") },
                    minLines = 3
                )
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Cancel", color = OnSurfaceVariant)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = { onConfirm(message.ifBlank { "Please select another date." }) },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Primary)
                    ) {
                        Text("Send Request", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
