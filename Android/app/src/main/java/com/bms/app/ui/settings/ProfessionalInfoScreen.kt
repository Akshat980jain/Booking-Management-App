package com.bms.app.ui.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bms.app.ui.components.*
import com.bms.app.ui.settings.viewmodel.ProfessionalInfoUiState
import com.bms.app.ui.settings.viewmodel.ProfessionalInfoViewModel
import com.bms.app.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfessionalInfoScreen(
    onBack: () -> Unit,
    viewModel: ProfessionalInfoViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var profession by remember { mutableStateOf("") }
    var specialty by remember { mutableStateOf("") }
    var bio by remember { mutableStateOf("") }
    var fee by remember { mutableStateOf("") }
    var experience by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var videoEnabled by remember { mutableStateOf(false) }
    var videoFee by remember { mutableStateOf("") }
    var requireVideoPayment by remember { mutableStateOf(false) }
    var requireInPersonPayment by remember { mutableStateOf(false) }
    var availableForBookings by remember { mutableStateOf(true) }
    var dropdownExpanded by remember { mutableStateOf(false) }

    val professions = listOf(
        "General Physician", "Dentist", "Physical Therapist",
        "Psychologist", "Dermatologist", "Cardiologist"
    )

    // Populate fields when data loads
    LaunchedEffect(uiState) {
        if (uiState is ProfessionalInfoUiState.Success) {
            val profile = (uiState as ProfessionalInfoUiState.Success).profile
            profession = profile.profession
            specialty = profile.specialty ?: ""
            bio = profile.bio ?: ""
            fee = if (profile.consultationFee > 0) profile.consultationFee.toInt().toString() else ""
            experience = if (profile.yearsOfExperience > 0) profile.yearsOfExperience.toString() else ""
            location = profile.location ?: ""
            videoEnabled = profile.videoEnabled
            videoFee = profile.videoConsultationFee?.toInt()?.toString() ?: ""
            requireVideoPayment = profile.requireVideoPayment
            requireInPersonPayment = profile.requireInPersonPayment
            availableForBookings = profile.isActive
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Professional Info",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Outlined.Brightness6, "Theme", tint = Primary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Background)
            )
        }
    ) { padding ->
        when (uiState) {
            is ProfessionalInfoUiState.Loading -> {
                SkeletonForm()
            }
            is ProfessionalInfoUiState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        (uiState as ProfessionalInfoUiState.Error).message,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
            is ProfessionalInfoUiState.Success -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 24.dp)
                ) {
                    Spacer(modifier = Modifier.height(8.dp))

                    // ── Main Card ─────────────────────────────
                    Surface(
                        color = SurfaceContainerLowest,
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(20.dp)) {
                            Text(
                                "Professional Information",
                                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                                color = OnSurface
                            )
                            Text(
                                "Provide details about your services and expertise",
                                style = MaterialTheme.typography.bodySmall,
                                color = Primary
                            )

                            Spacer(modifier = Modifier.height(20.dp))
                            HorizontalDivider(color = GhostBorder)
                            Spacer(modifier = Modifier.height(20.dp))

                            // Profession dropdown
                            Text("Profession", style = MaterialTheme.typography.titleSmall, color = OnSurface)
                            Spacer(modifier = Modifier.height(8.dp))
                            ExposedDropdownMenuBox(
                                expanded = dropdownExpanded,
                                onExpandedChange = { dropdownExpanded = it }
                            ) {
                                OutlinedTextField(
                                    value = profession,
                                    onValueChange = {},
                                    readOnly = true,
                                    trailingIcon = {
                                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = dropdownExpanded)
                                    },
                                    shape = InputShape,
                                    colors = OutlinedTextFieldDefaults.colors(
                                        unfocusedContainerColor = SurfaceContainerLowest,
                                        focusedContainerColor = SurfaceContainerLowest,
                                        unfocusedBorderColor = GhostBorder,
                                        focusedBorderColor = Primary
                                    ),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .menuAnchor()
                                )
                                ExposedDropdownMenu(
                                    expanded = dropdownExpanded,
                                    onDismissRequest = { dropdownExpanded = false }
                                ) {
                                    professions.forEach { prof ->
                                        DropdownMenuItem(
                                            text = { Text(prof) },
                                            onClick = {
                                                profession = prof
                                                dropdownExpanded = false
                                            }
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            BmsTextField(
                                value = specialty,
                                onValueChange = { specialty = it },
                                label = "Specialty",
                                placeholder = "e.g. Sports Therapy",
                                leadingIcon = Icons.Outlined.MedicalServices
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            BmsTextArea(
                                value = bio,
                                onValueChange = { bio = it },
                                label = "Bio",
                                placeholder = "Tell potential clients about yourself, your experience, and what makes you unique..."
                            )

                            Spacer(modifier = Modifier.height(16.dp))
                            HorizontalDivider(color = GhostBorder)
                            Spacer(modifier = Modifier.height(16.dp))

                            // Fee
                            BmsTextField(
                                value = fee,
                                onValueChange = { fee = it },
                                label = "Consultation Fee (₹)",
                                placeholder = "50",
                                leadingIcon = Icons.Outlined.AttachMoney
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            // Experience & Location side by side
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text("Years of Experience", style = MaterialTheme.typography.labelMedium, color = OnSurfaceVariant)
                                    Spacer(modifier = Modifier.height(8.dp))
                                    OutlinedTextField(
                                        value = experience,
                                        onValueChange = { experience = it },
                                        leadingIcon = {
                                            Icon(Icons.Outlined.Schedule, null, tint = Outline, modifier = Modifier.size(18.dp))
                                        },
                                        shape = InputShape,
                                        colors = OutlinedTextFieldDefaults.colors(
                                            unfocusedContainerColor = SurfaceContainerLowest,
                                            focusedContainerColor = SurfaceContainerLowest,
                                            unfocusedBorderColor = GhostBorder,
                                            focusedBorderColor = Primary
                                        ),
                                        singleLine = true,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                                Column(modifier = Modifier.weight(1f)) {
                                    Text("Location", style = MaterialTheme.typography.labelMedium, color = OnSurfaceVariant)
                                    Spacer(modifier = Modifier.height(8.dp))
                                    OutlinedTextField(
                                        value = location,
                                        onValueChange = { location = it },
                                        leadingIcon = {
                                            Icon(Icons.Outlined.LocationOn, null, tint = Outline, modifier = Modifier.size(18.dp))
                                        },
                                        shape = InputShape,
                                        colors = OutlinedTextFieldDefaults.colors(
                                            unfocusedContainerColor = SurfaceContainerLowest,
                                            focusedContainerColor = SurfaceContainerLowest,
                                            unfocusedBorderColor = GhostBorder,
                                            focusedBorderColor = Primary
                                        ),
                                        singleLine = true,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(24.dp))
                            HorizontalDivider(color = GhostBorder)
                            Spacer(modifier = Modifier.height(16.dp))

                            // ── Video Settings ────────────────
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Outlined.Videocam, null, tint = Primary, modifier = Modifier.size(22.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    "Video Consultation Settings",
                                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                    color = OnSurface
                                )
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            SettingsToggleRow(
                                title = "Enable Video Consultations",
                                subtitle = "Allow patients to book video appointments with you",
                                checked = videoEnabled,
                                onCheckedChange = { videoEnabled = it }
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            BmsTextField(
                                value = videoFee,
                                onValueChange = { videoFee = it },
                                label = "Video Consultation Fee (₹)",
                                placeholder = "Leave empty to use regular fee",
                                leadingIcon = Icons.Outlined.AttachMoney
                            )
                            Text(
                                "Set a different fee for video consultations, or leave empty to use your regular consultation fee",
                                style = MaterialTheme.typography.bodySmall,
                                color = OnSurfaceVariant,
                                modifier = Modifier.padding(top = 4.dp, start = 4.dp)
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            SettingsToggleRow(
                                title = "Require Upfront Payment for Video",
                                subtitle = "Patients must pay online before booking video sessions",
                                checked = requireVideoPayment,
                                onCheckedChange = { requireVideoPayment = it }
                            )

                            Spacer(modifier = Modifier.height(16.dp))
                            HorizontalDivider(color = GhostBorder)
                            Spacer(modifier = Modifier.height(16.dp))

                            // ── In-Person Settings ────────────────
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Outlined.PersonPin, null, tint = Primary, modifier = Modifier.size(22.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    "In-Person Visit Settings",
                                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                    color = OnSurface
                                )
                            }
                            Spacer(modifier = Modifier.height(12.dp))

                            SettingsToggleRow(
                                title = "Require Upfront Payment",
                                subtitle = "Force patients to pay online for in-person visits",
                                checked = requireInPersonPayment,
                                onCheckedChange = { requireInPersonPayment = it }
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            SettingsToggleRow(
                                title = "Available for Bookings",
                                subtitle = "Toggle off to temporarily pause receiving new appointment requests",
                                checked = availableForBookings,
                                onCheckedChange = { availableForBookings = it }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // ── Save Button ───────────────────────────
                    BmsButton(
                        text = "Save Professional Info",
                        onClick = {
                            val currentProfile = (uiState as ProfessionalInfoUiState.Success).profile
                            viewModel.updateProfile(
                                currentProfile.copy(
                                    profession = profession,
                                    specialty = specialty.ifBlank { null },
                                    bio = bio.ifBlank { null },
                                    consultationFee = fee.toDoubleOrNull() ?: 0.0,
                                    yearsOfExperience = experience.toIntOrNull() ?: 0,
                                    location = location.ifBlank { null },
                                    videoEnabled = videoEnabled,
                                    videoConsultationFee = videoFee.toDoubleOrNull(),
                                    requireVideoPayment = requireVideoPayment,
                                    requireInPersonPayment = requireInPersonPayment,
                                    isActive = availableForBookings
                                )
                            )
                        },
                        leadingIcon = Icons.Outlined.Check
                    )

                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }
}
