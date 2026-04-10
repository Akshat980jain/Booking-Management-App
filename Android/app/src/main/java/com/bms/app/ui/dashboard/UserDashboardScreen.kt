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
    onBrowseProviders: () -> Unit = {},
    onViewAllBookings: () -> Unit = {},
    onInboxClick: () -> Unit = {},
    viewModel: UserDashboardViewModel = hiltViewModel()
) {
    var selectedNav by remember { mutableStateOf("home") }
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            val initials = (uiState as? UserDashboardUiState.Success)?.userInitials ?: "U"
            BmsTopBar(
                title = "BookEase24X7",
                avatarInitials = initials,
                isLoading = uiState is UserDashboardUiState.Loading,
                onMessagesClick = onInboxClick,
                onAvatarClick = { onNavigate("settings") }
            )
        },
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
                        BmsPrimaryButton(
                            text = "Retry",
                            onClick = { viewModel.loadDashboard() },
                            leadingIcon = Icons.Outlined.Refresh
                        )
                    }
                }

                is UserDashboardUiState.Success -> {
                    UserDashboardContent(
                        state = state,
                        onBrowseProviders = onBrowseProviders,
                        onViewAllBookings = onViewAllBookings,
                        onMessageProvider = onMessageProvider,
                        onCancelBooking = { apptId -> viewModel.cancelBooking(apptId) }
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
    onBrowseProviders: () -> Unit,
    onViewAllBookings: () -> Unit,
    onMessageProvider: (String) -> Unit,
    onCancelBooking: (String) -> Unit
) {
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
            title = "Cancelled",
            value = state.cancelledCount.toString(),
            icon = Icons.Outlined.Cancel,
            modifier = Modifier.weight(1f)
        )
    }

    Spacer(modifier = Modifier.height(28.dp))

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
            onCancel = { onCancelBooking(state.nextAppointment.id) }
        )
        Spacer(modifier = Modifier.height(28.dp))
    }

    // ── Upcoming Bookings Strip ───────────────────────────────────────────────
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Upcoming Bookings",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = OnSurface
        )
        TextButton(onClick = onViewAllBookings) {
            Text("See All", style = MaterialTheme.typography.labelLarge, color = Primary)
        }
    }
    Spacer(modifier = Modifier.height(12.dp))

    if (state.upcomingBookings.isEmpty()) {
        EmptyStateCard(
            icon = Icons.Outlined.CalendarMonth,
            title = "No upcoming bookings",
            subtitle = "Tap + to book your first appointment"
        )
    } else {
        state.upcomingBookings.take(3).forEach { appointment ->
            val provider = state.providerMap[appointment.providerId]

            val realName = provider?.userId
                ?.let { state.userProfileMap[it]?.fullName }
                ?.takeIf { it.isNotBlank() }

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
                providerName = displayName,
                providerSpecialty = subtitle,
                onMessage = { provider?.userId?.let { onMessageProvider(it) } }
            )
            Spacer(modifier = Modifier.height(12.dp))
        }
    }

    // ── Browse Providers CTA ──────────────────────────────────────────────────
    Spacer(modifier = Modifier.height(8.dp))
    BmsPrimaryButton(
        text = "Browse & Book a Provider",
        onClick = onBrowseProviders,
        leadingIcon = Icons.Outlined.Search,
        trailingIcon = Icons.Outlined.ArrowForward
    )

    Spacer(modifier = Modifier.height(32.dp))
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
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column(horizontalAlignment = Alignment.End) {
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
                if (appointment.status == "confirmed" || appointment.status == "approved" || appointment.status == "pending") {
                    Spacer(modifier = Modifier.height(6.dp))
                    IconButton(onClick = onMessage, modifier = Modifier.size(28.dp)) {
                        Icon(
                            Icons.Outlined.ChatBubbleOutline,
                            contentDescription = "Message Provider",
                            tint = Primary,
                            modifier = Modifier.size(18.dp)
                        )
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
