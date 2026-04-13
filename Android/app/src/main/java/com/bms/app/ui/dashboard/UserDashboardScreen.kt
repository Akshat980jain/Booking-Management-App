package com.bms.app.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bms.app.domain.model.Appointment
import com.bms.app.domain.model.ProviderProfile
import com.bms.app.domain.util.NameUtils
import com.bms.app.ui.components.*
import com.bms.app.ui.theme.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.time.format.TextStyle as DateTextStyle
import kotlinx.coroutines.launch
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.border

// ── Helpers ───────────────────────────────────────────────────────────────────

private fun friendlyDate(dateStr: String): String {
    return try {
        val date = LocalDate.parse(dateStr)
        val today = LocalDate.now()
        when (date) {
            today              -> "Today"
            today.plusDays(1)  -> "Tomorrow"
            else               -> date.format(DateTimeFormatter.ofPattern("d MMM, yyyy"))
        }
    } catch (_: Exception) { dateStr }
}

private fun timeGreeting(): String {
    val hour = java.time.LocalTime.now().hour
    return when {
        hour < 12 -> "Good morning"
        hour < 17 -> "Good afternoon"
        else      -> "Good evening"
    }
}

// ── Screen ────────────────────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDashboardScreen(
    onNavigate: (String) -> Unit = {},
    onMessageProvider: (providerId: String) -> Unit = {},
    onRebookProvider: (providerId: String, oldAppointmentId: String) -> Unit = { _, _ -> },
    onBrowseProviders: () -> Unit = {},
    onViewAllBookings: () -> Unit = {},
    onInboxClick: () -> Unit = {},
    viewModel: UserDashboardViewModel = hiltViewModel()
) {
    var selectedNav by remember { mutableStateOf("home") }
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            val initials = (uiState as? UserDashboardUiState.Success)?.userInitials ?: "U"
            val unreadCount = (uiState as? UserDashboardUiState.Success)?.unreadNotificationCount ?: 0
            BmsTopBar(
                title = "BookEase24X7",
                avatarInitials = initials,
                isLoading = uiState is UserDashboardUiState.Loading,
                onMessagesClick = onInboxClick,
                onAvatarClick = { onNavigate("settings") },
                unreadCount = unreadCount
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            BmsBottomNavBar(
                items = UserNavItems,
                selectedRoute = selectedNav,
                onItemSelected = { route ->
                    selectedNav = route
                    onNavigate(route)
                }
            )
        },
        containerColor = Background
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(scrollState)
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            when (val state = uiState) {
                is UserDashboardUiState.Loading -> UserDashboardSkeleton()

                is UserDashboardUiState.Error -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(vertical = 60.dp),
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
                        BmsButton(
                            text = "Retry",
                            onClick = { viewModel.loadDashboard() },
                            leadingIcon = Icons.Outlined.Refresh
                        )
                    }
                }

                is UserDashboardUiState.Success -> {
                    // Pull to refresh simulation or real if available
                    // For now, simple Box since PullToRefreshBox might not be available in 1.2
                    UserDashboardContent(
                        state = state,
                        onNavigate = onNavigate,
                        onBrowseProviders = onBrowseProviders,
                        onViewAllBookings = onViewAllBookings,
                        onMessageProvider = onMessageProvider,
                        onRebookProvider = onRebookProvider,
                        onCancelBooking = { apptId, reason -> viewModel.cancelBooking(apptId, reason) },
                        onAcceptReschedule = { viewModel.acceptReschedule(it) },
                        onDeclineReschedule = { viewModel.declineReschedule(it) },
                        onRescheduleAppointment = { appt, date, time, reason -> 
                            viewModel.requestReschedule(appt.id, date, time, "", reason) 
                        },
                        onShowSnackbar = { msg ->
                            scope.launch { snackbarHostState.showSnackbar(msg) }
                        }
                    )
                }
            }
        }
    }
}

// ── Content (Success State) ───────────────────────────────────────────────────

@Composable
private fun UserDashboardContent(
    state: UserDashboardUiState.Success,
    onNavigate: (String) -> Unit,
    onBrowseProviders: () -> Unit,
    onViewAllBookings: () -> Unit,
    onMessageProvider: (String) -> Unit,
    onRebookProvider: (String, String) -> Unit,
    onCancelBooking: (String, String) -> Unit,
    onAcceptReschedule: (String) -> Unit,
    onDeclineReschedule: (String) -> Unit,
    onRescheduleAppointment: (Appointment, String, String, String) -> Unit,
    onShowSnackbar: (String) -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    var isCalendarView by remember { mutableStateOf(false) }
    var selectedCalendarDate by remember { mutableStateOf<LocalDate?>(LocalDate.now()) }
    val tabTitles = listOf("Upcoming", "Past", "Cancelled", "Payments", "Reviews")

    var selectedDetailAppointment by remember { mutableStateOf<Appointment?>(null) }
    var appointmentToCancel by remember { mutableStateOf<Appointment?>(null) }
    var appointmentToReschedule by remember { mutableStateOf<Appointment?>(null) }

    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(24.dp))
        
        // ── Greeting Header ───────────────────────────────────────────────────────
        Text(
            text = timeGreeting().uppercase(),
            style = MaterialTheme.typography.labelSmall.copy(
                letterSpacing = 2.sp,
                fontWeight = FontWeight.Bold
            ),
            color = OnSurfaceVariant
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "${state.userProfile.fullName.split(" ").firstOrNull() ?: state.userProfile.fullName} \uD83D\uDC4B",
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
            color = OnSurface
        )

        Spacer(modifier = Modifier.height(24.dp))

        // ── Stats Grid 2x2 ────────────────────────────────────────────────────────
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            StatCard(
                title = "Total",
                value = state.totalBookings.toString(),
                icon = Icons.Outlined.CalendarMonth,
                modifier = Modifier.weight(1f)
            )
            StatCard(
                title = "Upcoming",
                value = state.upcomingCount.toString(),
                icon = Icons.Outlined.EventAvailable,
                modifier = Modifier.weight(1f),
                isHighlighted = state.upcomingCount > 0
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            StatCard(
                title = "Completed",
                value = state.completedCount.toString(),
                icon = Icons.Outlined.CheckCircle,
                modifier = Modifier.weight(1f)
            )
            StatCard(
                title = "Loyalty Pts",
                value = "View",
                icon = Icons.Outlined.MilitaryTech,
                modifier = Modifier.weight(1f),
                isHighlighted = true,
                onClick = { onNavigate("rewards") }
            )
        }

        Spacer(modifier = Modifier.height(28.dp))

        // ── Action Required (Reschedule Requests) ─────────────────────────────────
        if (state.rescheduleRequests.isNotEmpty()) {
            Text(
                text = "Action Required",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.error
            )
            Spacer(modifier = Modifier.height(12.dp))
            
            state.rescheduleRequests.forEach { req ->
                val provider = state.providerMap[req.providerId]
                val realName = provider?.userId?.let { state.userProfileMap[it]?.fullName }
                val displayName = when {
                    !realName.isNullOrBlank() -> "Dr. $realName"
                    provider != null -> "Dr. ${provider.profession}"
                    else -> "Provider"
                }
                
                RescheduleRequestBanner(
                    appointment = req,
                    providerName = displayName,
                    onAccept = { onAcceptReschedule(req.id) },
                    onDecline = { onDeclineReschedule(req.id) },
                    onRebook = { 
                        val targetUserId = provider?.userId ?: return@RescheduleRequestBanner
                        onRebookProvider(targetUserId, req.id) 
                    }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        // ── Next Appointment Banner ───────────────────────────────────────────────
        if (state.nextAppointment != null) {
            NextAppointmentBanner(
                appointment = state.nextAppointment,
                provider = state.providerMap[state.nextAppointment.providerId],
                realName = state.providerMap[state.nextAppointment.providerId]?.userId
                    ?.let { state.userProfileMap[it]?.fullName }
                    ?.takeIf { it.isNotBlank() },
                onMessage = { 
                    state.providerMap[state.nextAppointment.providerId]?.userId?.let { 
                        onMessageProvider(it) 
                    } 
                },
                onCancel = { appointmentToCancel = state.nextAppointment }
            )
            Spacer(modifier = Modifier.height(28.dp))
        }

        // ── Tabbed View & Calendar Toggle ─────────────────────────────────────────
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "My Bookings",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = OnSurface
            )
            Row {
                IconButton(onClick = { isCalendarView = false }) {
                    Icon(
                        Icons.Outlined.List,
                        contentDescription = "List View",
                        tint = if (!isCalendarView) Primary else OnSurfaceVariant
                    )
                }
                IconButton(onClick = { isCalendarView = true }) {
                    Icon(
                        Icons.Outlined.CalendarMonth,
                        contentDescription = "Calendar View",
                        tint = if (isCalendarView) Primary else OnSurfaceVariant
                    )
                }
            }
        }

        ScrollableTabRow(
            selectedTabIndex = selectedTab,
            containerColor = androidx.compose.ui.graphics.Color.Transparent,
            contentColor = Primary,
            edgePadding = 0.dp,
            divider = { }
        ) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = {
                        Text(
                            title,
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal
                            ),
                            color = if (selectedTab == index) Primary else OnSurfaceVariant
                        )
                    }
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))

        // ── Calendar View Component (Phase 5) ──
        if (isCalendarView) {
            Text(
                "Select Date",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            val today = LocalDate.now()
            val calendarDates = remember { (-7..21).map { today.plusDays(it.toLong()) } }
            
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(calendarDates) { date ->
                    CalendarDateChip(
                        date = date,
                        isSelected = date == selectedCalendarDate,
                        onClick = { selectedCalendarDate = date }
                    )
                }
            }
        }

        val displayList = when (selectedTab) {
            0 -> state.upcomingBookings
            1 -> state.pastBookings
            2 -> state.cancelledBookings
            3 -> state.paidAppointments
            else -> emptyList()
        }.let { list ->
            if (isCalendarView && selectedCalendarDate != null) {
                val dateStr = selectedCalendarDate.toString()
                list.filter { it.appointmentDate == dateStr }
            } else list
        }

        if (selectedTab == 4) {
            // Reviews Tab
            EmptyStateCard(
                icon = Icons.Outlined.Star,
                title = "Manage Reviews",
                subtitle = "View and edit your past feedback for providers."
            )
        } else if (displayList.isEmpty()) {
            EmptyStateCard(
                icon = if (isCalendarView) Icons.Outlined.EventBusy else Icons.Outlined.EventNote,
                title = if (isCalendarView) "No bookings on this day" else "No bookings found",
                subtitle = if (isCalendarView) "Try selecting another date on the calendar." else "There are no appointments in this category."
            )
        } else {
            displayList.forEach { appointment ->
                val provider = state.providerMap[appointment.providerId]
                val realName = provider?.userId?.let { state.userProfileMap[it]?.fullName }?.takeIf { it.isNotBlank() }
                val displayName = when {
                    realName != null -> "Dr. $realName"
                    provider != null -> "Dr. ${provider.profession}"
                    else             -> "Provider"
                }
                val subtitle = listOfNotNull(
                    provider?.profession?.takeIf { it.isNotBlank() },
                    provider?.specialty?.takeIf { it.isNotBlank() }
                ).joinToString(" · ")

                BookingListItem(
                    appointment = appointment,
                    providerName = if (selectedTab == 3) "Invoice: $displayName" else displayName,
                    providerSpecialty = if (selectedTab == 3) "Paid on ${friendlyDate(appointment.appointmentDate)}" else subtitle,
                    onClick = { selectedDetailAppointment = appointment },
                    onMessage = { provider?.userId?.let { onMessageProvider(it) } },
                    onRate = {
                        val name = realName ?: provider?.profession ?: "Provider"
                        onNavigate("review_submission/${appointment.id}/${appointment.providerId}/$name")
                    },
                    onPayNow = {
                        onRebookProvider(provider?.userId ?: "", appointment.id)
                    },
                    onDownloadInvoice = {
                        onShowSnackbar("Invoice downloaded for ${appointment.id}")
                    },
                    onTelehealthJoin = {
                        onShowSnackbar("Joining secure video consultation...")
                    }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }

        // ── Browse Providers CTA ──────────────────────────────────────────────────
        if (selectedTab == 0 && !isCalendarView) {
            Spacer(modifier = Modifier.height(16.dp))
            BmsButton(
                text = "Browse & Book a Provider",
                onClick = onBrowseProviders,
                leadingIcon = Icons.Outlined.Search,
                trailingIcon = Icons.Outlined.ArrowForward
            )
        }
        Spacer(modifier = Modifier.height(40.dp))
    }

    // ── Dialogs ───────────────────────────────────────────────────────────────
    selectedDetailAppointment?.let { appt ->
        val provider = state.providerMap[appt.providerId]
        val realName = provider?.userId?.let { state.userProfileMap[it]?.fullName }
        val displayName = if (!realName.isNullOrBlank()) "Dr. $realName" else "Dr. ${provider?.profession ?: "Provider"}"
        
        AppointmentDetailDialog(
            appointment = appt,
            provider = provider,
            providerName = displayName,
            onDismiss = { selectedDetailAppointment = null },
            onReschedule = { appointmentToReschedule = appt }
        )
    }

    appointmentToReschedule?.let { appt ->
        RescheduleDialog(
            appointment = appt,
            onDismiss = { appointmentToReschedule = null },
            onConfirm = { date, startTime, endTime, reason ->
                onRescheduleAppointment(appt, date, startTime, reason)
                appointmentToReschedule = null
            }
        )
    }

    appointmentToCancel?.let { appt ->
        CancelReasonDialog(
            onDismiss = { appointmentToCancel = null },
            onConfirm = { reason ->
                onCancelBooking(appt.id, reason)
                appointmentToCancel = null
            }
        )
    }
}

// ── Next Appointment Banner ───────────────────────────────────────────────────

@Composable
private fun NextAppointmentBanner(
    appointment: Appointment,
    provider: ProviderProfile?,
    realName: String?,
    onMessage: () -> Unit,
    onCancel: () -> Unit
) {
    val isVideo = appointment.isVideoConsultation == true
    Surface(
        color = Primary.copy(alpha = 0.08f),
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(18.dp)) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Next Appointment",
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                    color = Primary
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (appointment.status == "pending") {
                        Surface(
                            color = StatusPending.copy(alpha = 0.12f),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.padding(end = 8.dp)
                        ) {
                            Text(
                                "Waiting for approval",
                                style = MaterialTheme.typography.labelSmall,
                                color = StatusPending,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                            )
                        }
                    }
                    StatusBadge(
                        text = if (isVideo) "VIDEO" else "IN-PERSON",
                        backgroundColor = if (isVideo) PrimaryContainer else SecondaryContainer,
                        textColor = if (isVideo) OnPrimaryContainer else OnSecondaryContainer
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Provider info
            Row(verticalAlignment = Alignment.CenterVertically) {
                val displayName = when {
                    !realName.isNullOrBlank() -> realName
                    provider != null          -> provider.profession
                    else                      -> "DR"
                }
                val providerInitials = NameUtils.getInitials(displayName)
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(PrimaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = providerInitials,
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                        color = OnPrimaryContainer
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = if (!realName.isNullOrBlank()) "Dr. $realName" else "Dr. ${provider?.profession ?: "Provider"}",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        color = OnSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    // profession · specialty as subtitle
                    val subtitle = listOfNotNull(
                        provider?.profession?.takeIf { it.isNotBlank() },
                        provider?.specialty?.takeIf { it.isNotBlank() }
                    ).joinToString(" · ")
                    if (subtitle.isNotBlank()) {
                        Text(
                            text = subtitle,
                            style = MaterialTheme.typography.bodySmall,
                            color = OnSurfaceVariant
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Date + time
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Outlined.CalendarToday, contentDescription = null, tint = Primary, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = friendlyDate(appointment.appointmentDate),
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = OnSurface
                )
                Spacer(modifier = Modifier.width(16.dp))
                Icon(Icons.Outlined.AccessTime, contentDescription = null, tint = Primary, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = appointment.startTime.take(5),
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = OnSurface
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Action buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = onMessage,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Primary)
                ) {
                    Icon(Icons.Outlined.ChatBubbleOutline, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Message", style = MaterialTheme.typography.labelMedium)
                }
                OutlinedButton(
                    onClick = onCancel,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.error
                    ),
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp, MaterialTheme.colorScheme.error.copy(alpha = 0.5f)
                    )
                ) {
                    Icon(Icons.Outlined.Cancel, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Cancel", style = MaterialTheme.typography.labelMedium)
                }
            }
        }
    }
}

// ── Booking List Item (also used by MyBookingsScreen) ─────────────────────────

@Composable
fun BookingListItem(
    appointment: Appointment,
    providerName: String,
    providerSpecialty: String,
    onMessage: () -> Unit,
    onClick: () -> Unit = {},
    onRate: () -> Unit = {},
    onPayNow: () -> Unit = {},
    onDownloadInvoice: () -> Unit = {},
    onTelehealthJoin: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val isVideo = appointment.isVideoConsultation == true
    val statusColor = when (appointment.status) {
        "confirmed", "approved" -> StatusActive
        "pending"   -> StatusPending
        "completed" -> StatusInfo
        "cancelled", "rejected" -> MaterialTheme.colorScheme.error
        else        -> OnSurfaceVariant
    }
    val statusText = appointment.status.replaceFirstChar { it.uppercase() }

    Surface(
        color = SurfaceContainerLowest,
        shape = RoundedCornerShape(14.dp),
        onClick = onClick,
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val initials = NameUtils.getInitials(providerName)
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(SecondaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = initials,
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                    color = OnSecondaryContainer
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = providerName,
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                    color = OnSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                if (providerSpecialty.isNotBlank()) {
                    Text(
                        text = providerSpecialty,
                        style = MaterialTheme.typography.bodySmall,
                        color = OnSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        if (isVideo) Icons.Outlined.Videocam else Icons.Outlined.LocationOn,
                        contentDescription = null,
                        tint = Primary,
                        modifier = Modifier.size(12.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${friendlyDate(appointment.appointmentDate)} · ${appointment.startTime.take(5)}",
                        style = MaterialTheme.typography.bodySmall,
                        color = OnSurfaceVariant
                    )
                    if (appointment.paymentStatus == "paid") {
                        Spacer(modifier = Modifier.width(6.dp))
                        Surface(
                            color = OnStatusActive.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                "Paid",
                                style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp, fontWeight = FontWeight.Bold),
                                color = OnStatusActive,
                                modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                            )
                        }
                    }
                } // Close Row
                
                // Cancellation Reason
                if (!appointment.cancellationReason.isNullOrBlank()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Surface(
                        color = MaterialTheme.colorScheme.errorContainer,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = appointment.cancellationReason,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onErrorContainer,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column(horizontalAlignment = Alignment.End) {
                // Status Badge
                Surface(
                    color = statusColor.copy(alpha = 0.12f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = statusText,
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = statusColor,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Action Buttons Row/Column
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Telehealth Trigger (Phase 4)
                    if (isVideo && appointment.status == "confirmed") {
                        IconButton(
                            onClick = onTelehealthJoin,
                            modifier = Modifier.size(28.dp)
                        ) {
                            Icon(
                                Icons.Outlined.Videocam,
                                contentDescription = "Join Video",
                                tint = Primary,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(4.dp))
                    }

                    if (appointment.status == "confirmed" || appointment.status == "approved" || appointment.status == "pending") {
                        IconButton(onClick = onMessage, modifier = Modifier.size(28.dp)) {
                            Icon(
                                Icons.Outlined.ChatBubbleOutline,
                                contentDescription = "Message Provider",
                                tint = OnSurfaceVariant,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }

                    if (appointment.status == "completed") {
                        IconButton(onClick = onRate, modifier = Modifier.size(28.dp)) {
                            Icon(
                                imageVector = Icons.Outlined.StarRate,
                                contentDescription = "Rate Provider",
                                tint = Gold,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                    
                    if (appointment.paymentStatus == "paid" || appointment.paymentStatus == "waived") {
                        IconButton(onClick = onDownloadInvoice, modifier = Modifier.size(28.dp)) {
                            Icon(
                                Icons.Outlined.FileDownload,
                                contentDescription = "Download Invoice",
                                tint = Primary,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }

                if (appointment.paymentStatus != "paid" && appointment.paymentStatus != "waived" && 
                    appointment.status != "cancelled" && appointment.status != "rejected") {
                    Spacer(modifier = Modifier.height(4.dp))
                    Button(
                        onClick = onPayNow,
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                        modifier = Modifier.height(30.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Primary)
                    ) {
                        Text("Pay Now", style = MaterialTheme.typography.labelSmall)
                    }
                }
            }
        }
    }
}

// ── Empty State Card ──────────────────────────────────────────────────────────

@Composable
private fun EmptyStateCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String
) {
    Surface(
        color = SurfaceContainerLow,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(icon, contentDescription = null, tint = OnSurfaceVariant, modifier = Modifier.size(36.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
                color = OnSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = OnSurfaceVariant,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}

// ── Reschedule Request Banner ────────────────────────────────────────────────
@Composable
private fun RescheduleRequestBanner(
    appointment: Appointment,
    providerName: String,
    onAccept: () -> Unit,
    onDecline: () -> Unit,
    onRebook: () -> Unit
) {
    val warningColor = androidx.compose.ui.graphics.Color(0xFFFFA000)
    val declineColor = MaterialTheme.colorScheme.error
    val warningContainerColor = androidx.compose.ui.graphics.Color(0xFFFFF8E1)

    Surface(
        color = warningContainerColor,
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, warningColor.copy(alpha = 0.5f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Outlined.ErrorOutline, null, tint = warningColor, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Reschedule Requested",
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                    color = warningColor
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            val message = appointment.cancellationReason?.removePrefix("reschedule: ") ?: "Provider requested a new time."
            Text(
                text = "$providerName requested to reschedule this appointment to ${appointment.appointmentDate} at ${appointment.startTime.take(5)}. \n\nMessage: \"$message\"",
                style = MaterialTheme.typography.bodySmall,
                color = OnSurfaceVariant,
                modifier = Modifier.padding(start = 28.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.End)
            ) {
                OutlinedButton(
                    onClick = onDecline,
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = declineColor),
                    border = androidx.compose.foundation.BorderStroke(1.dp, declineColor.copy(alpha = 0.5f)),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text("Decline", style = MaterialTheme.typography.labelSmall)
                }
                
                Button(
                    onClick = onAccept,
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = warningColor),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp)
                ) {
                    Text("Accept New Time", style = MaterialTheme.typography.labelSmall)
                }
            }
        }
    }
}
@Composable
private fun CalendarDateChip(date: LocalDate, isSelected: Boolean, onClick: () -> Unit) {
    val dayName = date.dayOfWeek.getDisplayName(DateTextStyle.SHORT, Locale.getDefault())
    val dayOfMonth = date.dayOfMonth.toString()
    val isToday = date == LocalDate.now()

    Surface(
        onClick = onClick,
        color = if (isSelected) Primary else if (isToday) Primary.copy(alpha = 0.05f) else SurfaceContainerLowest,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .width(64.dp)
            .height(84.dp)
            .border(
                width = if (isToday && !isSelected) 2.dp else 1.dp,
                color = if (isSelected) Primary else if (isToday) Primary.copy(alpha = 0.3f) else GhostBorder,
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                dayName,
                style = MaterialTheme.typography.labelSmall,
                color = if (isSelected) OnPrimary else if (isToday) Primary else OnSurfaceVariant
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                dayOfMonth,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = if (isSelected) OnPrimary else OnSurface
            )
        }
    }
}
