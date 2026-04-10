package com.bms.app.ui.admin

import androidx.compose.foundation.BorderStroke
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
import com.bms.app.domain.model.UserProfile
import com.bms.app.ui.components.*
import com.bms.app.ui.theme.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookServiceScreen(
    providerId: String,
    onBack: () -> Unit,
    onBookingSuccess: () -> Unit,
    viewModel: BookingViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    LaunchedEffect(providerId) {
        viewModel.loadProviderBookingData(providerId)
    }

    LaunchedEffect(uiState) {
        if (uiState is BookingUiState.BookingConfirmed) {
            onBookingSuccess()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Book Session", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Outlined.ArrowBack, null)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Background
                )
            )
        },
        bottomBar = {
            if (uiState is BookingUiState.Success) {
                val state = uiState as BookingUiState.Success
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    tonalElevation = 8.dp,
                    shadowElevation = 16.dp,
                    color = SurfaceContainerLowest
                ) {
                    Column(
                        modifier = Modifier
                            .padding(24.dp)
                            .navigationBarsPadding()
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text("Selected Slot", style = MaterialTheme.typography.labelMedium, color = OnSurfaceVariant)
                                Text(
                                    text = if (state.selectedSlot != null) "${state.selectedSlot} • ${state.selectedDate.format(DateTimeFormatter.ofPattern("MMM dd"))}" else "Not selected",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = OnSurface
                                )
                            }
                            Button(
                                onClick = { viewModel.confirmBooking() },
                                enabled = state.selectedSlot != null,
                                shape = RoundedCornerShape(12.dp),
                                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Primary)
                            ) {
                                Text("Confirm Booking")
                            }
                        }
                    }
                }
            }
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().background(Background).padding(padding)) {
            when (val state = uiState) {
                is BookingUiState.Loading -> {
                    SkeletonForm()
                }
                is BookingUiState.Error -> {
                    Column(
                        modifier = Modifier.fillMaxSize().padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(Icons.Outlined.ErrorOutline, null, modifier = Modifier.size(48.dp), tint = MaterialTheme.colorScheme.error)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(state.message, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
                        Spacer(modifier = Modifier.height(24.dp))
                        Button(onClick = { viewModel.loadProviderBookingData(providerId) }) {
                            Text("Retry")
                        }
                    }
                }
                is BookingUiState.Success -> {
                    BookingContent(
                        state = state,
                        onDateSelected = { viewModel.onDateSelected(it) },
                        onSlotSelected = { viewModel.selectSlot(it) }
                    )
                }
                else -> {}
            }
        }
    }
}

@Composable
private fun BookingContent(
    state: BookingUiState.Success,
    onDateSelected: (LocalDate) -> Unit,
    onSlotSelected: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp)
    ) {
        // Professional Header
        Spacer(modifier = Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(PrimaryContainer.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    state.provider.fullName.take(1).uppercase(),
                    style = MaterialTheme.typography.headlineSmall,
                    color = Primary,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    state.provider.fullName,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = OnSurface
                )
                Text(
                    state.providerProfile.profession.ifBlank { "Professional Consultant" },
                    style = MaterialTheme.typography.bodyMedium,
                    color = OnSurfaceVariant
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Outlined.Star, null, modifier = Modifier.size(14.dp), tint = Primary)
                    Text(" 4.9 (127 reviews)", style = MaterialTheme.typography.labelSmall, color = OnSurfaceVariant)
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Date Selection
        Text("Select Date", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        
        val today = LocalDate.now()
        val dates = remember { (0..14).map { today.plusDays(it.toLong()) } }
        
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 0.dp)
        ) {
            items(dates) { date ->
                DateChip(
                    date = date,
                    isSelected = date == state.selectedDate,
                    onClick = { onDateSelected(date) }
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Time Selection
        Text("Select Time", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        if (state.availableSlots.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 32.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("No available slots for this date.", color = OnSurfaceVariant)
            }
        } else {
            // Group slots by Morning/Afternoon/Evening
            val morning = state.availableSlots.filter { it.substringBefore(":").toInt() < 12 }
            val afternoon = state.availableSlots.filter { it.substringBefore(":").toInt() in 12..16 }
            val evening = state.availableSlots.filter { it.substringBefore(":").toInt() > 16 }

            if (morning.isNotEmpty()) {
                TimeSection("Morning", morning, state.selectedSlot, onSlotSelected)
                Spacer(modifier = Modifier.height(24.dp))
            }
            if (afternoon.isNotEmpty()) {
                TimeSection("Afternoon", afternoon, state.selectedSlot, onSlotSelected)
                Spacer(modifier = Modifier.height(24.dp))
            }
            if (evening.isNotEmpty()) {
                TimeSection("Evening", evening, state.selectedSlot, onSlotSelected)
                Spacer(modifier = Modifier.height(24.dp))
            }
        }

        Spacer(modifier = Modifier.height(100.dp))
    }
}

@Composable
private fun DateChip(date: LocalDate, isSelected: Boolean, onClick: () -> Unit) {
    val dayName = date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault())
    val dayOfMonth = date.dayOfMonth.toString()
    
    Surface(
        onClick = onClick,
        color = if (isSelected) Primary else SurfaceContainerLowest,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .width(64.dp)
            .height(84.dp)
            .border(
                width = 1.dp,
                color = if (isSelected) Primary else GhostBorder,
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
                color = if (isSelected) OnPrimary else OnSurfaceVariant
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

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun TimeSection(title: String, slots: List<String>, selectedSlot: String?, onSlotSelected: (String) -> Unit) {
    Column {
        Text(title, style = MaterialTheme.typography.labelLarge, color = OnSurfaceVariant, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(12.dp))
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            maxItemsInEachRow = 4
        ) {
            slots.forEach { slot ->
                val isSelected = slot == selectedSlot
                Surface(
                    onClick = { onSlotSelected(slot) },
                    color = if (isSelected) Primary.copy(alpha = 0.1f) else SurfaceContainerLowest,
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, if (isSelected) Primary else GhostBorder),
                    modifier = Modifier.height(40.dp).weight(1f, fill = false)
                ) {
                    Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp), contentAlignment = Alignment.Center) {
                        Text(
                            text = slot,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            color = if (isSelected) Primary else OnSurface
                        )
                    }
                }
            }
        }
    }
}
