package com.bms.app.ui.schedule

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bms.app.domain.model.Appointment
import com.bms.app.ui.components.*
import com.bms.app.ui.theme.*

@Composable
fun ProviderScheduleScreen(
    onNavigate: (String) -> Unit = {},
    viewModel: AvailabilityViewModel = hiltViewModel()
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Today's Agenda", "Availability")
    
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            BmsTopBar(
                title = "Schedule Hub",
                isLoading = uiState is AvailabilityUiState.Loading,
                avatarInitials = if (uiState is AvailabilityUiState.Success) {
                    (uiState as AvailabilityUiState.Success).providerInitials
                } else "P"
            )
        },
        bottomBar = {
            BmsBottomNavBar(
                items = MainNavItems,
                selectedRoute = "schedule",
                onItemSelected = { route ->
                    if (route != "schedule") onNavigate(route)
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Background)
                .padding(padding)
        ) {
            // Tab Row
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = SurfaceContainerLowest,
                contentColor = Primary,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                        color = Primary
                    )
                }
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { 
                            Text(
                                title, 
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal
                            ) 
                        }
                    )
                }
            }

            when (selectedTab) {
                0 -> AgendaTab(uiState, viewModel, onNavigate)
                1 -> AvailabilityTab(uiState, viewModel)
            }
        }
    }
}

@Composable
fun AgendaTab(uiState: AvailabilityUiState, viewModel: AvailabilityViewModel, onNavigate: (String) -> Unit) {
    when (val state = uiState) {
        is AvailabilityUiState.Loading -> {
            ProviderScheduleSkeleton()
        }
        is AvailabilityUiState.Error -> {
            Box(Modifier.fillMaxSize().padding(24.dp), contentAlignment = Alignment.Center) {
                Text(state.message, color = Error)
            }
        }
        is AvailabilityUiState.Success -> {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Innovative Daily Pulse Header
                item {
                    DailyPulseHeader(state)
                }

                item {
                    Text(
                        "Upcoming Bookings",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = OnSurface,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                val today = java.time.LocalDate.now().toString()
                val todayAppts = state.appointments.filter { it.appointmentDate == today }

                if (todayAppts.isEmpty()) {
                    item {
                        EmptyStatePlaceholder()
                    }
                } else {
                    items(todayAppts) { appointment ->
                        ProviderAppointmentCard(
                            appointment = appointment, 
                            users = state.users, 
                            currencySymbol = state.currencySymbol,
                            physicalFee = state.physicalFee,
                            videoFee = state.videoFee,
                            onComplete = { viewModel.completeAppointment(appointment.id) }
                        )
                    }
                }
                
                item { Spacer(Modifier.height(40.dp)) }
            }
        }
    }
}

@Composable
fun DailyPulseHeader(state: AvailabilityUiState.Success) {
    Surface(
        color = SurfaceContainerLowest,
        shape = RoundedCornerShape(24.dp),
        shadowElevation = 2.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(Primary.copy(alpha = 0.1f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Outlined.WavingHand, null, tint = Primary, modifier = Modifier.size(24.dp))
                }
                Spacer(Modifier.width(12.dp))
                Text(
                    "Good Morning, ${state.providerName.split(" ").firstOrNull()}!",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = OnSurface
                )
            }
            
            Spacer(Modifier.height(16.dp))
            
            val occupancy = if (state.totalBookingsCount > 0) 
                ((state.completedBookingsCount.toFloat() / state.totalBookingsCount.toFloat()) * 100).toInt() 
            else 0
            
            val statusText = if (state.totalBookingsCount == 0) {
                "No bookings scheduled for today."
            } else if (state.completedBookingsCount == state.totalBookingsCount) {
                "All ${state.totalBookingsCount} bookings completed! Great job! 🌟"
            } else {
                "You've completed ${state.completedBookingsCount} of ${state.totalBookingsCount} bookings today. You're $occupancy% there! 🔥"
            }

            Text(
                statusText,
                style = MaterialTheme.typography.bodyMedium,
                color = OnSurfaceVariant
            )
            
            Spacer(Modifier.height(20.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("Today's Projected Revenue", style = MaterialTheme.typography.labelSmall, color = OnSurfaceVariant)
                    Text(
                        "${state.currencySymbol}${String.format("%.2f", state.todayRevenue)}",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Black,
                        color = Primary
                    )
                }
                
                Surface(
                    color = Primary.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        "Trending Up",
                        color = Primary,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ProviderAppointmentCard(
    appointment: Appointment, 
    users: List<com.bms.app.domain.model.UserProfile>,
    currencySymbol: String,
    physicalFee: Double,
    videoFee: Double,
    onComplete: () -> Unit
) {
    val client = users.find { it.userId == appointment.userId }
    val clientName = client?.fullName ?: "Unknown Client"
    val initials = com.bms.app.domain.util.NameUtils.getInitials(clientName)

    val isCompleted = appointment.status.lowercase() == "completed"

    Surface(
        color = if (isCompleted) SurfaceContainerLow else SurfaceContainerLowest,
        shape = RoundedCornerShape(20.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, OutlineVariant.copy(alpha = if (isCompleted) 0.1f else 0.2f)),
        modifier = Modifier.fillMaxWidth().then(if (isCompleted) Modifier.alpha(0.7f) else Modifier)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    BmsAvatar(
                        name = clientName,
                        size = AvatarSize.MEDIUM
                    )
                    Spacer(Modifier.width(12.dp))
                    Column {
                        Text(clientName, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold, color = OnSurface)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Outlined.Schedule, null, tint = OnSurfaceVariant, modifier = Modifier.size(14.dp))
                            Spacer(Modifier.width(4.dp))
                            Text("${appointment.startTime.take(5)} - ${appointment.endTime.take(5)}", style = MaterialTheme.typography.bodySmall, color = OnSurfaceVariant)
                        }
                    }
                }
                
                Row(verticalAlignment = Alignment.CenterVertically) {
                    StatusBadge(appointment.status)
                    if (appointment.status.lowercase() != "completed" && appointment.status.lowercase() != "cancelled") {
                        Spacer(Modifier.width(8.dp))
                        IconButton(
                            onClick = onComplete,
                            modifier = Modifier.size(32.dp).background(Primary.copy(alpha = 0.1f), CircleShape)
                        ) {
                            Icon(Icons.Outlined.Check, null, tint = Primary, modifier = Modifier.size(18.dp))
                        }
                    }
                }
            }
            
            Spacer(Modifier.height(16.dp))
            HorizontalDivider(color = OutlineVariant.copy(alpha = 0.3f))
            Spacer(Modifier.height(16.dp))
            
            // Client Vibe Tags / Revenue Projection
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    VibeTag("Regular", Color(0xFF10B981))
                    if (appointment.isVideoConsultation == true) {
                        VibeTag("Video", Primary)
                    }
                    if (clientName.length % 2 == 0) { // Demo badge
                        VibeTag("VIP", Color(0xFF8B5CF6))
                    }
                }
                
                val sessionFee = if (appointment.isVideoConsultation == true && videoFee > 0) videoFee else physicalFee
                Text(
                    "+$currencySymbol %.2f".format(sessionFee),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF10B981)
                )
            }
        }
    }
}

@Composable
fun StatusBadge(status: String) {
    val (color, bgColor) = when (status.lowercase()) {
        "confirmed" -> Color(0xFF10B981) to Color(0xFF10B981).copy(alpha = 0.1f)
        "pending" -> Color(0xFFF59E0B) to Color(0xFFF59E0B).copy(alpha = 0.1f)
        "cancelled" -> Color(0xFFEF4444) to Color(0xFFEF4444).copy(alpha = 0.1f)
        else -> OnSurfaceVariant to SurfaceContainerLow
    }
    
    Surface(
        color = bgColor,
        shape = RoundedCornerShape(20.dp)
    ) {
        Text(
            status.uppercase(),
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = color
        )
    }
}

@Composable
fun VibeTag(text: String, color: Color) {
    Surface(
        color = color.copy(alpha = 0.08f),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelSmall,
            color = color,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun EmptyStatePlaceholder() {
    Column(
        modifier = Modifier.fillMaxWidth().padding(vertical = 60.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(Icons.Outlined.CalendarMonth, null, tint = OnSurfaceVariant.copy(alpha = 0.2f), modifier = Modifier.size(80.dp))
        Spacer(Modifier.height(16.dp))
        Text("No appointments for today", style = MaterialTheme.typography.bodyLarge, color = OnSurfaceVariant)
        Text("Your calendar is wide open!", style = MaterialTheme.typography.bodySmall, color = OnSurfaceVariant.copy(alpha = 0.7f))
    }
}

@Composable
fun AvailabilityTab(uiState: AvailabilityUiState, viewModel: AvailabilityViewModel) {
    ManageAvailabilityContent(
        uiState = uiState,
        viewModel = viewModel
    )
}
