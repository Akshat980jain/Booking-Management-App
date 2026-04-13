package com.bms.app.ui.admin

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
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
    waivedBy: String? = null,
    onBack: () -> Unit,
    onBookingSuccess: () -> Unit,
    viewModel: BookingViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var showReviewSheet by remember { mutableStateOf(false) }

    LaunchedEffect(providerId) {
        viewModel.loadProviderBookingData(providerId, waivedBy)
    }

    LaunchedEffect(uiState) {
        if (uiState is BookingUiState.BookingConfirmed) {
            showReviewSheet = false
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
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Background)
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
                            .padding(horizontal = 20.dp, vertical = 16.dp)
                            .navigationBarsPadding()
                    ) {
                        val currentFee = if (state.isVideoSelected)
                            state.providerProfile.videoConsultationFee ?: state.providerProfile.consultationFee
                        else state.providerProfile.consultationFee

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text("Total Estimated Fee", style = MaterialTheme.typography.labelSmall, color = OnSurfaceVariant)
                                Text(
                                    text = "₹${currentFee.toInt()}",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = Primary
                                )
                                AnimatedVisibility(visible = state.selectedSlot != null) {
                                    Text(
                                        text = "${if (state.isVideoSelected) "📹 Video" else "📍 In-Person"} · ${state.selectedSlot}",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = OnSurfaceVariant
                                    )
                                }
                            }

                            Button(
                                onClick = {
                                    if (state.selectedSlot.isNullOrBlank()) {
                                        // Simple validate logic could go here
                                    } else {
                                        showReviewSheet = true
                                    }
                                },
                                enabled = state.selectedSlot != null,
                                shape = RoundedCornerShape(12.dp),
                                contentPadding = PaddingValues(horizontal = 32.dp, vertical = 14.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Primary)
                            ) {
                                Text("Review Booking", fontWeight = FontWeight.SemiBold)
                            }
                        }
                        
                        if (state.isFeeWaived) {
                            Spacer(modifier = Modifier.height(12.dp))
                            Surface(
                                color = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.3f),
                                shape = RoundedCornerShape(8.dp),
                                border = BorderStroke(1.dp, MaterialTheme.colorScheme.tertiary.copy(alpha = 0.5f)),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier.padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(Icons.Outlined.CheckCircle, null, tint = MaterialTheme.colorScheme.tertiary, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Your previous payment of ₹${state.waivedAmount} will automatically apply.",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onTertiaryContainer
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().background(Background).padding(padding)) {
            when (val state = uiState) {
                is BookingUiState.Loading -> SkeletonForm()
                is BookingUiState.Error -> {
                    Column(
                        modifier = Modifier.fillMaxSize().padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(Icons.Outlined.ErrorOutline, null, modifier = Modifier.size(48.dp), tint = MaterialTheme.colorScheme.error)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(state.message, textAlign = TextAlign.Center)
                        Spacer(modifier = Modifier.height(24.dp))
                        Button(onClick = { viewModel.loadProviderBookingData(providerId) }) { Text("Retry") }
                    }
                }
                is BookingUiState.Success -> {
                    BookingContent(
                        state = state,
                        onDateSelected = { viewModel.onDateSelected(it) },
                        onSlotSelected = { viewModel.selectSlot(it) },
                        onToggleVideo = { viewModel.toggleVideoConsultation(it == 1) },
                        onPaymentMethodChanged = { viewModel.setPaymentMethod(it) },
                        onNoteChanged = { viewModel.setBookingNote(it) }
                    )
                }
                is BookingUiState.ProcessingPayment -> {
                    PaymentGatewayDialog(
                        amount = state.amount,
                        onPaymentSuccess = { viewModel.confirmBooking() },
                        onCancel = { viewModel.loadProviderBookingData(providerId) } // Simple way to reset state
                    )
                }
                else -> {}
            }
        }
    }

    // ── Review & Confirm Bottom Sheet ─────────────────────────────────────────
    if (showReviewSheet && uiState is BookingUiState.Success) {
        val state = uiState as BookingUiState.Success
        BookingReviewSheet(
            state = state,
            onDismiss = { showReviewSheet = false },
            onConfirm = { viewModel.startBookingFlow() }
        )
    }
}

// ── Booking Review Bottom-Sheet ───────────────────────────────────────────────

@Composable
private fun BookingReviewSheet(
    state: BookingUiState.Success,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    val currentFee = if (state.isVideoSelected)
        state.providerProfile.videoConsultationFee ?: state.providerProfile.consultationFee
    else state.providerProfile.consultationFee

    val formattedDate = runCatching {
        LocalDate.parse(state.selectedDate.toString())
            .format(DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy", Locale.getDefault()))
    }.getOrElse { state.selectedDate.toString() }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            // Scrim
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.45f))
                    .clickable(onClick = onDismiss)
            )

            // Sheet
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
                color = SurfaceContainerLowest,
                tonalElevation = 6.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                        .navigationBarsPadding()
                ) {
                    // Drag handle
                    Box(
                        modifier = Modifier
                            .width(48.dp)
                            .height(4.dp)
                            .clip(CircleShape)
                            .background(OutlineVariant)
                            .align(Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.height(24.dp))

                    // Header
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Outlined.Assignment, null, tint = Primary, modifier = Modifier.size(22.dp))
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            "Review Your Booking",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = OnSurface
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        "Please confirm the details below before we finalize your appointment.",
                        style = MaterialTheme.typography.bodySmall,
                        color = OnSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(24.dp))

                    // ── Provider Card ─────────────────────────────────────────
                    Surface(
                        color = SurfaceContainerLow,
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(52.dp)
                                    .clip(CircleShape)
                                    .background(Primary),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    state.provider.fullName.take(1).uppercase(),
                                    style = MaterialTheme.typography.titleMedium,
                                    color = OnPrimary,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Spacer(modifier = Modifier.width(14.dp))
                            Column {
                                Text(
                                    "Dr. ${state.provider.fullName}",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = OnSurface
                                )
                                Text(
                                    state.providerProfile.profession.ifBlank { "Consultant" },
                                    style = MaterialTheme.typography.bodySmall,
                                    color = OnSurfaceVariant
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // ── Appointment Details ───────────────────────────────────
                    Surface(
                        color = SurfaceContainerLow,
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            SlipRow(
                                icon = Icons.Outlined.CalendarToday,
                                label = "Date",
                                value = formattedDate
                            )
                            SlipDivider()
                            SlipRow(
                                icon = Icons.Outlined.AccessTime,
                                label = "Time",
                                value = "${state.selectedSlot} · ~30 min session"
                            )
                            SlipDivider()
                            SlipRow(
                                icon = if (state.isVideoSelected) Icons.Outlined.Videocam else Icons.Outlined.LocationOn,
                                label = "Type",
                                value = if (state.isVideoSelected) "Video Consultation" else "In-Person Visit"
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // ── Fee Breakdown ─────────────────────────────────────────
                    Surface(
                        color = Primary.copy(alpha = 0.06f),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text("Estimated Fee", style = MaterialTheme.typography.labelMedium, color = OnSurfaceVariant)
                                Text(
                                    if (state.isFeeWaived) "₹0 (Waived)" else "₹${currentFee.toInt()}",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = Primary
                                )
                            }
                            Surface(
                                color = Primary.copy(alpha = 0.12f),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    if (state.isFeeWaived) "Carried Over" else if (state.selectedPaymentMethod == "online") "Pay Online" else "Pay at clinic",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Primary,
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                )
                            }
                        }
                    }

                    // ── Reason for visit (if provided) ────────────────────────
                    if (state.bookingNote.isNotBlank()) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Surface(
                            color = SurfaceContainerLow,
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(modifier = Modifier.padding(16.dp)) {
                                Icon(Icons.Outlined.Edit, null, tint = OnSurfaceVariant, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text("Reason for Visit", style = MaterialTheme.typography.labelSmall, color = OnSurfaceVariant)
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(state.bookingNote, style = MaterialTheme.typography.bodySmall, color = OnSurface)
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // ── Cancellation Policy ───────────────────────────────────
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Top
                    ) {
                        Icon(
                            Icons.Outlined.Info,
                            null,
                            tint = OnSurfaceVariant,
                            modifier = Modifier.size(15.dp).padding(top = 1.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            "Free cancellation up to 24 hours before your appointment. After that, a cancellation fee may apply.",
                            style = MaterialTheme.typography.labelSmall,
                            color = OnSurfaceVariant,
                            lineHeight = 18.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(28.dp))

                    // ── Action Buttons ────────────────────────────────────────
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OutlinedButton(
                            onClick = onDismiss,
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = OnSurface),
                            border = BorderStroke(1.dp, OutlineVariant),
                            contentPadding = PaddingValues(vertical = 14.dp)
                        ) {
                            Text("Go Back", fontWeight = FontWeight.Medium)
                        }
                        Button(
                            onClick = onConfirm,
                            modifier = Modifier.weight(1.5f),
                            shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Primary),
                            contentPadding = PaddingValues(vertical = 14.dp)
                        ) {
                            val ctaIcon = if (state.selectedPaymentMethod == "online") Icons.Outlined.Lock else Icons.Outlined.CheckCircle
                            val ctaText = if (state.selectedPaymentMethod == "online") "Proceed to Secure Pay" else "Confirm Appointment"
                            Icon(ctaIcon, null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(ctaText, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}


// ── Slip Row Helper ───────────────────────────────────────────────────────────

@Composable
private fun SlipRow(icon: ImageVector, label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, null, tint = Primary, modifier = Modifier.size(18.dp))
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(label, style = MaterialTheme.typography.labelSmall, color = OnSurfaceVariant)
            Text(value, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold, color = OnSurface)
        }
    }
}

@Composable
private fun SlipDivider() {
    HorizontalDivider(
        modifier = Modifier.padding(vertical = 4.dp),
        color = SurfaceContainerHigh,
        thickness = 1.dp
    )
}

// ── Booking Content ───────────────────────────────────────────────────────────

@Composable
private fun BookingContent(
    state: BookingUiState.Success,
    onDateSelected: (LocalDate) -> Unit,
    onSlotSelected: (String) -> Unit,
    onToggleVideo: (Int) -> Unit,
    onPaymentMethodChanged: (String) -> Unit,
    onNoteChanged: (String) -> Unit
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
                    .background(Primary),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    state.provider.fullName.take(1).uppercase(),
                    style = MaterialTheme.typography.headlineSmall,
                    color = OnPrimary,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    "Dr. ${state.provider.fullName}",
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
                    Icon(Icons.Outlined.Star, null, modifier = Modifier.size(14.dp), tint = Gold)
                    val ratingText = if ((state.providerProfile.totalReviews ?: 0) > 0) {
                        " ${"%.1f".format(state.providerProfile.averageRating ?: 0.0)} (${state.providerProfile.totalReviews} reviews)"
                    } else {
                        " New Provider"
                    }
                    Text(ratingText, style = MaterialTheme.typography.labelSmall, color = OnSurfaceVariant)
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // ── Consultation Type ─────────────────────────────────────────────────
        if (state.providerProfile.videoEnabled) {
            Text("Consultation Type", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(12.dp))
            SegmentedControl(
                items = listOf("In-Person", "Video Call"),
                selectedIndex = if (state.isVideoSelected) 1 else 0,
                onItemSelected = onToggleVideo
            )
            Spacer(modifier = Modifier.height(32.dp))
        }

        // ── Select Date ───────────────────────────────────────────────────────
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

        // ── Select Time ───────────────────────────────────────────────────────
        Text("Select Time", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        if (state.availableSlots.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxWidth().padding(vertical = 32.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("No available slots for this date.", color = OnSurfaceVariant)
            }
        } else {
            val morning = state.availableSlots.filter { it.substringBefore(":").toInt() < 12 }
            val afternoon = state.availableSlots.filter { it.substringBefore(":").toInt() in 12..16 }
            val evening = state.availableSlots.filter { it.substringBefore(":").toInt() > 16 }

            if (morning.isNotEmpty()) { TimeSection("Morning", morning, state.selectedSlot, onSlotSelected); Spacer(modifier = Modifier.height(24.dp)) }
            if (afternoon.isNotEmpty()) { TimeSection("Afternoon", afternoon, state.selectedSlot, onSlotSelected); Spacer(modifier = Modifier.height(24.dp)) }
            if (evening.isNotEmpty()) { TimeSection("Evening", evening, state.selectedSlot, onSlotSelected); Spacer(modifier = Modifier.height(24.dp)) }
        }

        // ── Payment Method ────────────────────────────────────────────────────
        if (!state.isFeeWaived) {
            val requireOnline = if (state.isVideoSelected) state.providerProfile.requireVideoPayment 
                               else state.providerProfile.requireInPersonPayment

            Text("Payment Method", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(12.dp))
            
            if (requireOnline) {
                Surface(
                    color = Primary.copy(alpha = 0.08f),
                    shape = RoundedCornerShape(14.dp),
                    border = BorderStroke(1.dp, Primary.copy(alpha = 0.2f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Outlined.Lock, null, tint = Primary, modifier = Modifier.size(24.dp))
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                "Online Payment Required", 
                                style = MaterialTheme.typography.titleMedium, 
                                fontWeight = FontWeight.Bold, 
                                color = Primary
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                "Secure payment will be collected in the next step to confirm your booking.",
                                style = MaterialTheme.typography.bodySmall,
                                color = OnSurface
                            )
                        }
                    }
                }
            } else {
                SegmentedControl(
                    items = listOf("Pay at Clinic", "Pay Online"),
                    selectedIndex = if (state.selectedPaymentMethod == "online") 1 else 0,
                    onItemSelected = { index ->
                        val method = if (index == 0) "at_clinic" else "online"
                        onPaymentMethodChanged(method)
                    }
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
        }

        // ── Reason for Visit ──────────────────────────────────────────────────
        Spacer(modifier = Modifier.height(8.dp))
        Text("Reason for Visit", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            "Optional · Helps the provider prepare for your session",
            style = MaterialTheme.typography.labelSmall,
            color = OnSurfaceVariant
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = state.bookingNote,
            onValueChange = onNoteChanged,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("e.g. Follow-up for back pain, routine checkup…", style = MaterialTheme.typography.bodySmall) },
            minLines = 3,
            maxLines = 5,
            shape = RoundedCornerShape(14.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Primary,
                unfocusedBorderColor = OutlineVariant
            )
        )

        Spacer(modifier = Modifier.height(120.dp))
    }
}

// ── Date Chip ─────────────────────────────────────────────────────────────────

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
            Text(dayName, style = MaterialTheme.typography.labelSmall, color = if (isSelected) OnPrimary else OnSurfaceVariant)
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

// ── Time Section ──────────────────────────────────────────────────────────────

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
                    color = if (isSelected) Primary else SurfaceContainerLowest,
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, if (isSelected) Primary else GhostBorder),
                    modifier = Modifier.height(40.dp).weight(1f, fill = false)
                ) {
                    Box(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = slot,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            color = if (isSelected) OnPrimary else OnSurface
                        )
                    }
                }
            }
        }
    }
}

// ── Payment Gateway Dialog ──────────────────────────────────────────────────

@Composable
private fun PaymentGatewayDialog(
    amount: Int,
    onPaymentSuccess: () -> Unit,
    onCancel: () -> Unit
) {
    var isProcessing by remember { mutableStateOf(false) }
    var paymentMethod by remember { mutableStateOf("upi") }

    Dialog(
        onDismissRequest = { if (!isProcessing) onCancel() },
        properties = DialogProperties(dismissOnBackPress = !isProcessing, dismissOnClickOutside = !isProcessing)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp),
            shape = RoundedCornerShape(24.dp),
            color = SurfaceContainerLowest,
            tonalElevation = 6.dp
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (!isProcessing) {
                    // Header
                    Icon(Icons.Outlined.Lock, null, tint = Primary, modifier = Modifier.size(32.dp))
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Secure Payment", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    Text("Pay ₹$amount to finalize booking", style = MaterialTheme.typography.bodySmall, color = OnSurfaceVariant)
                    
                    Spacer(modifier = Modifier.height(32.dp))

                    // Payment Method options
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        PaymentMethodItem(
                            title = "UPI (GPay, PhonePe, Paytm)",
                            icon = Icons.Outlined.AccountBalanceWallet,
                            isSelected = paymentMethod == "upi",
                            onClick = { paymentMethod = "upi" }
                        )
                        PaymentMethodItem(
                            title = "Credit / Debit Card",
                            icon = Icons.Outlined.CreditCard,
                            isSelected = paymentMethod == "card",
                            onClick = { paymentMethod = "card" }
                        )
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    Button(
                        onClick = { isProcessing = true },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        contentPadding = PaddingValues(vertical = 16.dp)
                    ) {
                        Text("Pay Now", fontWeight = FontWeight.Bold)
                    }
                    
                    TextButton(onClick = onCancel) {
                        Text("Cancel", color = OnSurfaceVariant)
                    }
                } else {
                    // Processing UI
                    LaunchedEffect(Unit) {
                        kotlinx.coroutines.delay(2000) // Simulate processing time
                        onPaymentSuccess()
                    }

                    CircularProgressIndicator(modifier = Modifier.size(48.dp), color = Primary)
                    Spacer(modifier = Modifier.height(24.dp))
                    Text("Processing Payment...", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text("Do not close the app or refresh", style = MaterialTheme.typography.bodySmall, color = OnSurfaceVariant)
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}

@Composable
private fun PaymentMethodItem(
    title: String,
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        color = if (isSelected) Primary.copy(alpha = 0.08f) else SurfaceContainerLow,
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, if (isSelected) Primary else Color.Transparent),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, null, tint = if (isSelected) Primary else OnSurfaceVariant, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Text(title, style = MaterialTheme.typography.bodyMedium, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal, color = if (isSelected) OnSurface else OnSurfaceVariant)
            Spacer(modifier = Modifier.weight(1f))
            if (isSelected) {
                Icon(Icons.Outlined.CheckCircle, null, tint = Primary, modifier = Modifier.size(20.dp))
            }
        }
    }
}
