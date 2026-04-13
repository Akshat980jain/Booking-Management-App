package com.bms.app.ui.schedule

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bms.app.domain.model.AvailabilitySlot
import com.bms.app.domain.model.BlockedDate
import com.bms.app.domain.util.NameUtils
import com.bms.app.ui.components.*
import com.bms.app.ui.theme.*

data class LocalDaySchedule(
    val dayOfWeek: Int,
    val name: String,
    var enabled: Boolean,
    var from: String = "09:00:00",
    var to: String = "17:00:00",
    var duration: Int = 30
)

val dayNames = listOf("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageAvailabilityScreen(
    onNavigate: (String) -> Unit = {},
    viewModel: AvailabilityViewModel = hiltViewModel()
) {
    var selectedNav by remember { mutableStateOf("schedule") }
    val uiState by viewModel.uiState.collectAsState()
    
    // Local copy of schedule to allow editing before saving
    val localSchedule = remember { mutableStateMapOf<Int, LocalDaySchedule>() }
    var selectedDuration by remember { mutableStateOf("30 min") }

    LaunchedEffect(uiState) {
        if (uiState is AvailabilityUiState.Success) {
            val successState = uiState as AvailabilityUiState.Success
            // Populate local schedule with existing slots or defaults
            for (i in 0..6) {
                val slot = successState.weeklySchedule.find { it.dayOfWeek == i }
                if (slot != null) {
                    localSchedule[i] = LocalDaySchedule(i, dayNames[i], slot.isActive, slot.startTime, slot.endTime, slot.slotDuration)
                    selectedDuration = "${slot.slotDuration} min"
                } else {
                    localSchedule[i] = LocalDaySchedule(i, dayNames[i], false)
                }
            }
        }
    }

    Scaffold(
        topBar = {
            BmsTopBar(
                title = "BookEase24X7",
                avatarInitials = if (uiState is AvailabilityUiState.Success) {
                    (uiState as AvailabilityUiState.Success).providerInitials
                } else "P",
                isLoading = uiState is AvailabilityUiState.Loading
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
        }
    ) { padding ->
        ManageAvailabilityContent(
            uiState = uiState,
            viewModel = viewModel,
            modifier = Modifier.padding(padding)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageAvailabilityContent(
    uiState: AvailabilityUiState,
    viewModel: AvailabilityViewModel,
    modifier: Modifier = Modifier
) {
    // Local copy of schedule to allow editing before saving
    val localSchedule = remember { mutableStateMapOf<Int, LocalDaySchedule>() }
    var selectedDuration by remember { mutableStateOf("30 min") }
    
    // Pricing state
    var physicalPrice by remember { mutableStateOf("0.0") }
    var videoPrice by remember { mutableStateOf("0.0") }
    var videoEnabled by remember { mutableStateOf(false) }
    
    // Blocked Date state
    var showDatePicker by remember { mutableStateOf(false) }
    var showReasonDialog by remember { mutableStateOf(false) }
    var selectedDateStr by remember { mutableStateOf("") }
    var blockReason by remember { mutableStateOf("Personal Leave") }
    
    val datePickerState = rememberDatePickerState(
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                val today = java.time.LocalDate.now().atStartOfDay(java.time.ZoneId.of("UTC")).toInstant().toEpochMilli()
                return utcTimeMillis >= today
            }
        }
    )

    LaunchedEffect(uiState) {
        if (uiState is AvailabilityUiState.Success) {
            val successState = uiState as AvailabilityUiState.Success
            physicalPrice = successState.physicalFee.toString()
            videoPrice = successState.videoFee.toString()
            videoEnabled = successState.videoEnabled
            
            // Populate local schedule with existing slots or defaults
            for (i in 0..6) {
                val slot = successState.weeklySchedule.find { it.dayOfWeek == i }
                if (slot != null) {
                    localSchedule[i] = LocalDaySchedule(i, dayNames[i], slot.isActive, slot.startTime, slot.endTime, slot.slotDuration)
                    selectedDuration = "${slot.slotDuration} min"
                } else {
                    localSchedule[i] = LocalDaySchedule(i, dayNames[i], false)
                }
            }
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        when (val state = uiState) {
            is AvailabilityUiState.Loading -> {
                SkeletonForm()
            }
            is AvailabilityUiState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = state.message, color = MaterialTheme.colorScheme.error)
                }
            }
            is AvailabilityUiState.Success -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Background)
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 24.dp)
                ) {
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        "Manage Availability",
                        style = MaterialTheme.typography.headlineLarge,
                        color = OnSurface
                    )
                    Text(
                        "Define your working hours and session rules.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = OnSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // ── Service Pricing ───────────────────────
                    Text(
                        "Service Pricing",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = OnSurface,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Physical Price Card
                        PriceSettingCard(
                            title = "Physical",
                            icon = Icons.Outlined.PersonPin,
                            value = physicalPrice,
                            onValueChange = { physicalPrice = it },
                            currencySymbol = state.currencySymbol,
                            activeColor = Primary,
                            modifier = Modifier.weight(1f)
                        )

                        // Video Price Card
                        PriceSettingCard(
                            title = "Video",
                            icon = Icons.Outlined.Videocam,
                            value = videoPrice,
                            onValueChange = { videoPrice = it },
                            currencySymbol = state.currencySymbol,
                            isEnabled = videoEnabled,
                            onEnabledChange = { videoEnabled = it },
                            activeColor = Color(0xFF10B981), // Emerald/Green for Video
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Dedicated Save Pricing Button
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Surface(
                            onClick = {
                                if (state is AvailabilityUiState.Success) {
                                    val currentSlots = localSchedule.values.map { day ->
                                        val existingSlot = state.weeklySchedule.find { it.dayOfWeek == day.dayOfWeek }
                                        AvailabilitySlot(
                                            id = existingSlot?.id ?: "",
                                            providerId = state.weeklySchedule.firstOrNull()?.providerId ?: "",
                                            dayOfWeek = day.dayOfWeek,
                                            startTime = day.from,
                                            endTime = day.to,
                                            slotDuration = day.duration,
                                            isActive = day.enabled
                                        )
                                    }
                                    viewModel.updateAvailabilitySettings(
                                        slots = currentSlots,
                                        physicalFee = physicalPrice.toDoubleOrNull() ?: 0.0,
                                        videoFee = videoPrice.toDoubleOrNull() ?: 0.0,
                                        videoEnabled = videoEnabled
                                    )
                                }
                            },
                            color = Primary.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(12.dp),
                            enabled = true
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Outlined.Save, null, tint = Primary, modifier = Modifier.size(16.dp))
                                Spacer(Modifier.width(8.dp))
                                Text(
                                    "Save Pricing",
                                    style = MaterialTheme.typography.labelLarge,
                                    color = Primary,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // ── Appointment Settings ──────────────────
                    Surface(
                        color = SurfaceContainerLowest,
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(20.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Outlined.Info, null, tint = Primary)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    "Appointment Settings",
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                    color = OnSurface
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                "SLOT DURATION",
                                style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 1.sp),
                                color = OnSurfaceVariant
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                listOf("30 min", "45 min", "60 min").forEach { duration ->
                                    val isSelected = selectedDuration == duration
                                    Surface(
                                        onClick = { 
                                            selectedDuration = duration
                                            val durInt = duration.take(2).toInt()
                                            localSchedule.values.forEach { it.duration = durInt }
                                        },
                                        color = if (isSelected) Primary else SurfaceContainerLowest,
                                        shape = InputShape,
                                        border = if (!isSelected) BorderStroke(1.dp, OutlineVariant.copy(alpha = 0.3f)) else null,
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text(
                                            text = duration,
                                            style = MaterialTheme.typography.labelLarge,
                                            color = if (isSelected) OnPrimary else OnSurfaceVariant,
                                            textAlign = TextAlign.Center,
                                            modifier = Modifier.padding(vertical = 12.dp)
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            // Buffer time (simplified, usually part of provider profile but shown here)
                            Surface(
                                color = SurfaceContainerLow,
                                shape = InputShape,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier.padding(16.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column {
                                        Text(
                                            "Buffer Time",
                                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                            color = OnSurface
                                        )
                                        Text(
                                            "Rest between sessions",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = OnSurfaceVariant
                                        )
                                    }
                                    Text(
                                        "15 min",
                                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                        color = OnSurface
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // ── Weekly Schedule ────────────────────────
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Weekly Schedule",
                            style = MaterialTheme.typography.headlineSmall,
                            color = OnSurface
                        )
                        Text(
                            "DEFAULT HOURS",
                            style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 1.sp),
                            color = OnSurfaceVariant
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Show Mon-Sun (1-6, 0)
                    val displayOrder = listOf(1, 2, 3, 4, 5, 6, 0)
                    displayOrder.forEach { dayIdx ->
                        localSchedule[dayIdx]?.let { day ->
                            DayScheduleCard(
                                day = day,
                                onToggle = { enabled ->
                                    val newDay = day.copy(enabled = enabled)
                                    localSchedule[dayIdx] = newDay
                                }
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Block Specific Dates",
                            style = MaterialTheme.typography.headlineSmall,
                            color = OnSurface
                        )
                        IconButton(onClick = { showDatePicker = true }) {
                            Icon(Icons.Filled.Add, "Add blocked date", tint = Primary)
                        }
                    }

                    // ── Dialogs ────────────────────────────
                    if (showDatePicker) {
                        DatePickerDialog(
                            onDismissRequest = { showDatePicker = false },
                            confirmButton = {
                                TextButton(onClick = {
                                    datePickerState.selectedDateMillis?.let { millis ->
                                        val date = java.time.Instant.ofEpochMilli(millis)
                                            .atZone(java.time.ZoneId.of("UTC"))
                                            .toLocalDate()
                                        selectedDateStr = date.toString()
                                        showDatePicker = false
                                        showReasonDialog = true
                                    }
                                }) { Text("Next") }
                            },
                            dismissButton = {
                                TextButton(onClick = { showDatePicker = false }) { Text("Cancel") }
                            }
                        ) {
                            DatePicker(state = datePickerState)
                        }
                    }

                    if (showReasonDialog) {
                        AlertDialog(
                            onDismissRequest = { showReasonDialog = false },
                            title = { Text("Reason for Unavailability") },
                            text = {
                                OutlinedTextField(
                                    value = blockReason,
                                    onValueChange = { blockReason = it },
                                    label = { Text("Reason") },
                                    modifier = Modifier.fillMaxWidth()
                                )
                            },
                            confirmButton = {
                                TextButton(onClick = {
                                    viewModel.addBlockedDate(selectedDateStr, blockReason)
                                    showReasonDialog = false
                                    blockReason = "Personal Leave" // Reset
                                }) { Text("Block Date") }
                            },
                            dismissButton = {
                                TextButton(onClick = { showReasonDialog = false }) { Text("Cancel") }
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Blocked date entry
                    state.blockedDates.forEach { blockedDate ->
                        Surface(
                            color = SurfaceContainerLow,
                            shape = InputShape,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.Outlined.Block,
                                    null,
                                    tint = Error,
                                    modifier = Modifier.size(28.dp)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        "${blockedDate.blockedDate} - ${blockedDate.reason ?: "Blocked"}",
                                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                        color = OnSurface
                                    )
                                    Text(
                                        "All day • Full unavailability",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = OnSurfaceVariant
                                    )
                                }
                                IconButton(onClick = { viewModel.removeBlockedDate(blockedDate.id) }) {
                                    Icon(
                                        Icons.Filled.Delete,
                                        "Remove",
                                        tint = OnSurfaceVariant
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // ── Update Button ─────────────────────────
                    BmsButton(
                        text = "Update Schedule",
                        onClick = {
                            val newSlots = localSchedule.values.map {
                                val currentSlotId = state.weeklySchedule.find { s -> s.dayOfWeek == it.dayOfWeek }?.id ?: ""
                                AvailabilitySlot(
                                    id = currentSlotId,
                                    providerId = state.weeklySchedule.firstOrNull()?.providerId ?: "",
                                    dayOfWeek = it.dayOfWeek,
                                    startTime = it.from,
                                    endTime = it.to,
                                    slotDuration = selectedDuration.take(2).toIntOrNull() ?: 30,
                                    isActive = it.enabled
                                )
                            }
                            viewModel.updateAvailabilitySettings(
                                slots = newSlots,
                                physicalFee = physicalPrice.toDoubleOrNull() ?: 0.0,
                                videoFee = videoPrice.toDoubleOrNull() ?: 0.0,
                                videoEnabled = videoEnabled
                            )
                        }
                    )

                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PriceSettingCard(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    value: String,
    onValueChange: (String) -> Unit,
    currencySymbol: String,
    modifier: Modifier = Modifier,
    activeColor: Color = Primary,
    isEnabled: Boolean = true,
    onEnabledChange: ((Boolean) -> Unit)? = null
) {
    val backgroundColor = if (isEnabled) SurfaceContainerLowest else SurfaceContainerLow.copy(alpha = 0.6f)
    val borderColor = if (isEnabled) activeColor.copy(alpha = 0.4f) else OutlineVariant.copy(alpha = 0.2f)
    
    Surface(
        color = backgroundColor,
        shape = RoundedCornerShape(24.dp),
        border = BorderStroke(if (isEnabled) 1.5.dp else 1.dp, borderColor),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .alpha(if (isEnabled) 1f else 0.6f)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = if (isEnabled) activeColor.copy(alpha = 0.1f) else SurfaceContainerLow,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = if (isEnabled) activeColor else OnSurfaceVariant,
                        modifier = Modifier
                            .padding(8.dp)
                            .size(20.dp)
                    )
                }

                if (onEnabledChange != null) {
                    Switch(
                        checked = isEnabled,
                        onCheckedChange = onEnabledChange,
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = activeColor,
                            uncheckedThumbColor = SurfaceContainerLowest,
                            uncheckedTrackColor = SurfaceContainerHigh
                        ),
                        modifier = Modifier.scale(0.8f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = if (isEnabled) OnSurface else OnSurfaceVariant
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = if (isEnabled) activeColor.copy(alpha = 0.05f) else Color.Transparent,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = if (isEnabled) activeColor.copy(alpha = 0.1f) else Color.Transparent,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Text(
                    text = currencySymbol,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = if (isEnabled) activeColor else OnSurfaceVariant
                )
                
                BasicTextField(
                    value = value,
                    onValueChange = { newVal ->
                        // Only allow numbers and decimal point
                        if (newVal.isEmpty() || newVal.all { it.isDigit() || it == '.' }) {
                            onValueChange(newVal)
                        }
                    },
                    enabled = isEnabled,
                    textStyle = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Black,
                        color = if (isEnabled) OnSurface else OnSurfaceVariant,
                        textAlign = TextAlign.End
                    ),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.weight(1f),
                    cursorBrush = androidx.compose.ui.graphics.SolidColor(activeColor)
                )
            }
        }
    }
}

@Composable
private fun DayScheduleCard(
    day: LocalDaySchedule,
    onToggle: (Boolean) -> Unit
) {
    // Format time from "HH:mm:ss" to "HH:mm"
    val fromDisplay = day.from.take(5)
    val toDisplay = day.to.take(5)

    Surface(
        color = SurfaceContainerLowest,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    day.name,
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                    color = OnSurface
                )
                Switch(
                    checked = day.enabled,
                    onCheckedChange = onToggle,
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = SurfaceContainerLowest,
                        checkedTrackColor = Primary,
                        uncheckedThumbColor = SurfaceContainerLowest,
                        uncheckedTrackColor = SurfaceContainerHigh,
                        uncheckedBorderColor = OutlineVariant
                    )
                )
            }

            if (day.enabled) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // FROM chip
                    Surface(
                        color = SurfaceContainerLow,
                        shape = InputShape
                    ) {
                        Column(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                "FROM",
                                style = MaterialTheme.typography.labelSmall,
                                color = OnSurfaceVariant
                            )
                            Text(
                                fromDisplay,
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                color = OnSurface,
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    Icon(
                        Icons.Outlined.ArrowForward,
                        null,
                        tint = OnSurfaceVariant,
                        modifier = Modifier.size(16.dp)
                    )

                    Text(
                        "TO  $toDisplay",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        color = OnSurface
                    )
                }
            } else {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    "CLOSED",
                    style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 1.sp),
                    color = OnSurfaceVariant
                )
            }
        }
    }
}
