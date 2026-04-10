package com.bms.app.ui.schedule

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bms.app.domain.model.Appointment
import com.bms.app.domain.model.UserProfile
import com.bms.app.ui.components.*
import com.bms.app.ui.dashboard.AdminUiState
import com.bms.app.ui.dashboard.AdminViewModel
import com.bms.app.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminScheduleScreen(
    onNavigate: (String) -> Unit = {},
    viewModel: AdminViewModel = hiltViewModel()
) {
    var selectedNav by remember { mutableStateOf("schedule") }
    var selectedFilter by remember { mutableStateOf("All") }
    var isNewestFirst by remember { mutableStateOf(true) }
    val filters = listOf("All", "Pending", "Confirmed", "Cancelled", "Completed")

    val uiState by viewModel.uiState.collectAsState()
    val isLoading = uiState is AdminUiState.Loading

    val adminInitials = if (uiState is AdminUiState.Success) {
        (uiState as AdminUiState.Success).adminInitials
    } else ""

    Scaffold(
        topBar = {
            BmsTopBar(
                title = "BookEase24X7",
                avatarInitials = adminInitials,
                isLoading = isLoading
            )
        },
        bottomBar = {
            BmsBottomNavBar(
                items = AdminNavItems,
                selectedRoute = selectedNav,
                onItemSelected = { route ->
                    selectedNav = route
                    onNavigate(route)
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Background)
                .padding(padding)
        ) {
            when (val state = uiState) {
                is AdminUiState.Loading -> {
                    ScheduleSkeleton()
                }
                is AdminUiState.Error -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            Icons.Outlined.WifiOff,
                            null,
                            tint = OnSurfaceVariant,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(Modifier.height(16.dp))
                        Text(
                            state.message,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(Modifier.height(12.dp))
                        BmsPrimaryButton(text = "Retry", onClick = { viewModel.loadAdminDashboard() })
                    }
                }
                is AdminUiState.Success -> {
                    val allAppointments = state.appointments
                    val filteredAppointments = if (selectedFilter == "All") allAppointments
                    else allAppointments.filter {
                        it.status.lowercase() == selectedFilter.lowercase()
                    }

                    val sortedAppointments = if (isNewestFirst) {
                        filteredAppointments.sortedWith(compareByDescending<Appointment> { it.appointmentDate }.thenByDescending { it.startTime })
                    } else {
                        filteredAppointments.sortedWith(compareBy<Appointment> { it.appointmentDate }.thenBy { it.startTime })
                    }

                    // Build lookup maps from loaded state
                    val userMap = state.users.associateBy { it.userId }

                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Header
                        item {
                            Column {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = "All Appointments",
                                            style = MaterialTheme.typography.headlineLarge,
                                            color = OnSurface
                                        )
                                        Text(
                                            text = "System-wide booking overview",
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = OnSurfaceVariant
                                        )
                                    }
                                    
                                    // Sort Toggle Button
                                    Surface(
                                        onClick = { isNewestFirst = !isNewestFirst },
                                        color = if (isNewestFirst) Primary.copy(alpha = 0.1f) else SurfaceContainerLow,
                                        shape = RoundedCornerShape(12.dp)
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                                        ) {
                                            Icon(
                                                if (isNewestFirst) Icons.Outlined.TrendingDown else Icons.Outlined.TrendingUp,
                                                null,
                                                tint = if (isNewestFirst) Primary else OnSurfaceVariant,
                                                modifier = Modifier.size(18.dp)
                                            )
                                            Text(
                                                if (isNewestFirst) "Newest" else "Oldest",
                                                style = MaterialTheme.typography.labelMedium,
                                                color = if (isNewestFirst) Primary else OnSurfaceVariant
                                            )
                                        }
                                    }
                                }
                                
                                Spacer(Modifier.height(16.dp))

                                // Filter chips in a horizontally scrollable row
                                LazyRow(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    items(filters) { filter ->
                                        val count = if (filter == "All") allAppointments.size
                                        else allAppointments.count {
                                            it.status.lowercase() == filter.lowercase()
                                        }
                                        FilterChip(
                                            selected = selectedFilter == filter,
                                            onClick = { selectedFilter = filter },
                                            label = {
                                                Text(
                                                    if (count > 0) "$filter ($count)" else filter,
                                                    fontSize = 12.sp
                                                )
                                            },
                                            colors = FilterChipDefaults.filterChipColors(
                                                selectedContainerColor = Primary,
                                                selectedLabelColor = OnPrimary,
                                                containerColor = SurfaceContainerLowest,
                                                labelColor = OnSurfaceVariant
                                            )
                                        )
                                    }
                                }

                                Spacer(Modifier.height(16.dp))

                                // Summary bar
                                AdminScheduleSummaryBar(
                                    total = allAppointments.size,
                                    pending = allAppointments.count { it.status.lowercase() == "pending" },
                                    confirmed = allAppointments.count { it.status.lowercase() == "confirmed" },
                                    completed = allAppointments.count { it.status.lowercase() == "completed" }
                                )
                                Spacer(Modifier.height(8.dp))
                            }
                        }

                        // List or empty state
                        if (sortedAppointments.isEmpty()) {
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(200.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Icon(
                                            Icons.Outlined.CalendarToday,
                                            null,
                                            tint = OnSurfaceVariant.copy(alpha = 0.5f),
                                            modifier = Modifier.size(48.dp)
                                        )
                                        Spacer(Modifier.height(12.dp))
                                        Text(
                                            "No $selectedFilter appointments",
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = OnSurfaceVariant
                                        )
                                    }
                                }
                            }
                        } else {
                            items(sortedAppointments, key = { it.id }) { appointment ->
                                AppointmentDetailCard(
                                    appointment = appointment,
                                    userMap = userMap
                                )
                            }
                        }

                        item { Spacer(Modifier.height(16.dp)) }
                    }
                }
            }
        }
    }
}

@Composable
private fun AdminScheduleSummaryBar(
    total: Int,
    pending: Int,
    confirmed: Int,
    completed: Int
) {
    Surface(
        color = SurfaceContainerLowest,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            SummaryStatItem("Total", total.toString(), Primary)
            Box(Modifier.width(1.dp).height(32.dp).background(OnSurface.copy(alpha = 0.1f)))
            SummaryStatItem("Pending", pending.toString(), Color(0xFFF59E0B))
            Box(Modifier.width(1.dp).height(32.dp).background(OnSurface.copy(alpha = 0.1f)))
            SummaryStatItem("Confirmed", confirmed.toString(), Color(0xFF10B981))
            Box(Modifier.width(1.dp).height(32.dp).background(OnSurface.copy(alpha = 0.1f)))
            SummaryStatItem("Completed", completed.toString(), OnSurfaceVariant)
        }
    }
}

@Composable
private fun SummaryStatItem(label: String, value: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold), color = color)
        Text(label, style = MaterialTheme.typography.labelSmall, color = OnSurfaceVariant)
    }
}

@Composable
private fun AppointmentDetailCard(
    appointment: Appointment,
    userMap: Map<String, UserProfile>
) {
    val clientProfile = userMap[appointment.userId]
    val providerProfile = userMap[appointment.providerId]
    val clientName = clientProfile?.fullName?.ifBlank { "Unknown User" } ?: "User ${appointment.userId.take(6)}"
    val providerName = providerProfile?.fullName?.ifBlank { "Unknown Provider" } ?: "Provider ${appointment.providerId.take(6)}"
    val clientInitials = clientName.split(" ").mapNotNull { it.firstOrNull()?.uppercase() }.take(2).joinToString("")
    val providerInitials = providerName.split(" ").mapNotNull { it.firstOrNull()?.uppercase() }.take(2).joinToString("")

    val (statusColor, statusBg) = when (appointment.status.lowercase()) {
        "confirmed" -> Pair(Color(0xFF10B981), Color(0xFF10B981).copy(alpha = 0.12f))
        "pending" -> Pair(Color(0xFFF59E0B), Color(0xFFF59E0B).copy(alpha = 0.12f))
        "cancelled" -> Pair(Color(0xFFEF4444), Color(0xFFEF4444).copy(alpha = 0.12f))
        "completed" -> Pair(OnSurfaceVariant, OnSurfaceVariant.copy(alpha = 0.12f))
        else -> Pair(OnSurfaceVariant, OnSurfaceVariant.copy(alpha = 0.12f))
    }

    // Parse date nicely
    val displayDate = try {
        val parts = appointment.appointmentDate.split("-")
        if (parts.size == 3) {
            val months = listOf("", "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")
            "${parts[2]} ${months[parts[1].toInt()]} ${parts[0]}"
        } else appointment.appointmentDate
    } catch (e: Exception) { appointment.appointmentDate }

    val startDisplay = appointment.startTime.take(5)
    val endDisplay = appointment.endTime.take(5)

    Surface(
        color = SurfaceContainerLowest,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Top: Status & Date
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        Icons.Outlined.CalendarMonth,
                        null,
                        tint = Primary,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        displayDate,
                        style = MaterialTheme.typography.labelMedium,
                        color = OnSurface,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        "·  $startDisplay – $endDisplay",
                        style = MaterialTheme.typography.labelSmall,
                        color = OnSurfaceVariant
                    )
                }

                Surface(
                    color = statusBg,
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text(
                        appointment.status.replaceFirstChar { it.uppercase() },
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = statusColor,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(Modifier.height(12.dp))
            Divider(color = OnSurface.copy(alpha = 0.06f))
            Spacer(Modifier.height(12.dp))

            // People involved
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Client
                Column(modifier = Modifier.weight(1f)) {
                    Text("CLIENT", style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 1.sp), color = OnSurfaceVariant)
                    Spacer(Modifier.height(6.dp))
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Box(
                            Modifier.size(32.dp).clip(CircleShape).background(PrimaryContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(clientInitials, style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold), color = Primary)
                        }
                        Text(clientName, style = MaterialTheme.typography.bodySmall, color = OnSurface)
                    }
                }

                // Provider
                Column(modifier = Modifier.weight(1f)) {
                    Text("PROVIDER", style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 1.sp), color = OnSurfaceVariant)
                    Spacer(Modifier.height(6.dp))
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Box(
                            Modifier.size(32.dp).clip(CircleShape).background(SecondaryContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(providerInitials, style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold), color = Secondary)
                        }
                        Text(providerName, style = MaterialTheme.typography.bodySmall, color = OnSurface)
                    }
                }
            }

            // Notes / video indicator
            if (!appointment.notes.isNullOrBlank() || appointment.isVideoConsultation == true) {
                Spacer(Modifier.height(10.dp))
                Divider(color = OnSurface.copy(alpha = 0.06f))
                Spacer(Modifier.height(10.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (appointment.isVideoConsultation == true) {
                        Surface(color = Primary.copy(alpha = 0.1f), shape = RoundedCornerShape(8.dp)) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(Icons.Outlined.VideoCall, null, tint = Primary, modifier = Modifier.size(14.dp))
                                Text("Video", style = MaterialTheme.typography.labelSmall, color = Primary)
                            }
                        }
                    }
                    if (!appointment.notes.isNullOrBlank()) {
                        Text(
                            appointment.notes,
                            style = MaterialTheme.typography.bodySmall,
                            color = OnSurfaceVariant,
                            maxLines = 1
                        )
                    }
                }
            }
        }
    }
}
