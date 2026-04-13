package com.bms.app.ui.user

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bms.app.domain.model.Appointment
import com.bms.app.domain.model.ProviderProfile
import com.bms.app.domain.util.NameUtils
import com.bms.app.ui.components.*
import com.bms.app.ui.dashboard.BookingListItem
import com.bms.app.ui.dashboard.UserDashboardUiState
import com.bms.app.ui.dashboard.UserDashboardViewModel
import com.bms.app.ui.theme.*

// ── Filter tabs ───────────────────────────────────────────────────────────────

private enum class BookingFilter(val label: String) {
    ALL("All"),
    UPCOMING("Upcoming"),
    COMPLETED("Completed"),
    CANCELLED("Cancelled")
}

// ── Screen ────────────────────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyBookingsScreen(
    onNavigate: (String) -> Unit = {},
    onBack: () -> Unit = {},
    onMessageProvider: (providerId: String) -> Unit = {},
    onRateProvider: (appointmentId: String, providerId: String, providerName: String) -> Unit = { _, _, _ -> },
    viewModel: UserDashboardViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var selectedFilter by remember { mutableStateOf(BookingFilter.ALL) }
    var selectedNav by remember { mutableStateOf("my_bookings") }

    Scaffold(
        topBar = {
            val initials = (uiState as? UserDashboardUiState.Success)?.userInitials ?: "U"
            BmsTopBar(
                title = "My Bookings",
                showBackButton = true,
                showAvatar = false,
                onNavigationClick = onBack,
                avatarInitials = initials,
                isLoading = uiState is UserDashboardUiState.Loading
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
        ) {
            // ── Filter tab row ──────────────────────────────────────────────
            ScrollableTabRow(
                selectedTabIndex = selectedFilter.ordinal,
                containerColor = Background,
                contentColor = Primary,
                edgePadding = 16.dp,
                divider = {},
                modifier = Modifier.fillMaxWidth()
            ) {
                BookingFilter.entries.forEach { filter ->
                    Tab(
                        selected = selectedFilter == filter,
                        onClick = { selectedFilter = filter },
                        text = {
                            Text(
                                text = filter.label,
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = if (selectedFilter == filter) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        selectedContentColor = Primary,
                        unselectedContentColor = OnSurfaceVariant
                    )
                }
            }

            HorizontalDivider(color = SurfaceContainerHigh, thickness = 1.dp)

            // ── Content ─────────────────────────────────────────────────────
            when (val state = uiState) {
                is UserDashboardUiState.Loading -> {
                    Column(modifier = Modifier.padding(16.dp)) {
                        repeat(4) {
                            SkeletonAppointmentCard()
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    }
                }

                is UserDashboardUiState.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                Icons.Outlined.ErrorOutline,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.error,
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(state.message, color = OnSurfaceVariant)
                        }
                    }
                }

                is UserDashboardUiState.Success -> {
                    val allAppointments = (state.upcomingBookings + state.pastBookings)
                        .sortedByDescending { it.appointmentDate }

                    val filtered = when (selectedFilter) {
                        BookingFilter.ALL       -> allAppointments
                        BookingFilter.UPCOMING  -> state.upcomingBookings
                        BookingFilter.COMPLETED -> state.pastBookings.filter { it.status == "completed" }
                        BookingFilter.CANCELLED -> state.pastBookings.filter { it.status == "cancelled" || it.status == "rejected" }
                    }

                    if (filtered.isEmpty()) {
                        BookingsEmptyState(filter = selectedFilter)
                    } else {
                        LazyColumn(
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(filtered, key = { it.id }) { appointment ->
                                val provider = state.providerMap[appointment.providerId]

                                // Real name from profiles table; fall back to profession
                                val realName = provider?.userId
                                    ?.let { state.userProfileMap[it]?.fullName }
                                    ?.takeIf { it.isNotBlank() }

                                val displayName = when {
                                    realName != null   -> "Dr. $realName"
                                    provider != null   -> "Dr. ${provider.profession}"
                                    else               -> "Provider"
                                }

                                // profession · specialty as subtitle
                                val subtitle = listOfNotNull(
                                    provider?.profession?.takeIf { it.isNotBlank() },
                                    provider?.specialty?.takeIf { it.isNotBlank() }
                                ).joinToString(" · ")

                                BookingListItem(
                                    appointment = appointment,
                                    providerName = displayName,
                                    providerSpecialty = subtitle,
                                    onMessage = { provider?.userId?.let { onMessageProvider(it) } },
                                    onRate = { 
                                        if (provider != null) {
                                            onRateProvider(appointment.id, provider.userId, displayName)
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

// ── Empty State ───────────────────────────────────────────────────────────────

@Composable
private fun BookingsEmptyState(filter: BookingFilter) {
    val (icon, title, subtitle) = when (filter) {
        BookingFilter.ALL       -> Triple(Icons.Outlined.CalendarMonth, "No bookings yet", "Tap + to book your first appointment")
        BookingFilter.UPCOMING  -> Triple(Icons.Outlined.EventAvailable, "No upcoming bookings", "Book a session with a provider")
        BookingFilter.COMPLETED -> Triple(Icons.Outlined.CheckCircle, "No completed bookings", "Completed sessions will appear here")
        BookingFilter.CANCELLED -> Triple(Icons.Outlined.Cancel, "No cancelled bookings", "You have no cancelled appointments")
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Surface(
                color = SurfaceContainerLow,
                shape = RoundedCornerShape(50),
                modifier = Modifier.size(72.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = OnSurfaceVariant,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                color = OnSurface
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = OnSurfaceVariant,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}
