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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bms.app.ui.components.*
import com.bms.app.ui.settings.viewmodel.VisibilityUiState
import com.bms.app.ui.settings.viewmodel.VisibilityViewModel
import com.bms.app.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VisibilitySettingsScreen(
    onBack: () -> Unit,
    viewModel: VisibilityViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var profileActive by remember { mutableStateOf(true) }
    var showPhone by remember { mutableStateOf(false) }
    var videoConsultations by remember { mutableStateOf(true) }
    var requirePayment by remember { mutableStateOf(true) }
    var requirePaymentVideo by remember { mutableStateOf(true) }

    // Populate fields when data loads
    LaunchedEffect(uiState) {
        if (uiState is VisibilityUiState.Success) {
            val profile = (uiState as VisibilityUiState.Success).profile
            profileActive = profile.isActive
            videoConsultations = profile.videoEnabled
            // showPhone, requirePayment, requirePaymentVideo not directly in provider_profiles
            // so we keep them as local defaults for now
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Outlined.WbSunny, null, tint = OnSurfaceVariant)
                    }
                    IconButton(onClick = { }) {
                        Icon(Icons.Outlined.Notifications, null, tint = OnSurfaceVariant)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Background)
            )
        }
    ) { padding ->
        when (uiState) {
            is VisibilityUiState.Loading -> {
                SkeletonSettings()
            }
            is VisibilityUiState.Error -> {
                Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                    Text((uiState as VisibilityUiState.Error).message, color = MaterialTheme.colorScheme.error)
                }
            }
            is VisibilityUiState.Success -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 24.dp)
                ) {
                    // Logo row
                    Row {
                        Icon(Icons.Outlined.CalendarMonth, null, tint = Primary)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "BookEase24X7",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = OnSurface
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Header
                    Text(
                        "Provider Settings",
                        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                        color = OnSurface
                    )
                    Text(
                        "Manage your profile visibility, notifications, and integrations",
                        style = MaterialTheme.typography.bodySmall,
                        color = OnSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(4.dp))
                    HorizontalDivider(color = GhostBorder)
                    Spacer(modifier = Modifier.height(16.dp))

                    // ── Main Card ─────────────────────────────
                    Surface(
                        color = SurfaceContainerLowest,
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(20.dp)) {
                            Row {
                                Icon(Icons.Outlined.Language, null, tint = Primary)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    "Profile Visibility",
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                    color = OnSurface
                                )
                            }
                            Text(
                                "Control how your profile appears to potential clients",
                                style = MaterialTheme.typography.bodySmall,
                                color = OnSurfaceVariant
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            SettingsToggleRow(
                                title = "Profile Active",
                                subtitle = "When enabled, your profile is visible in search results and can receive bookings",
                                icon = Icons.Outlined.Visibility,
                                checked = profileActive,
                                onCheckedChange = { profileActive = it }
                            )

                            HorizontalDivider(color = GhostBorder)

                            SettingsToggleRow(
                                title = "Show Phone Number",
                                subtitle = "Display your phone number on your public profile",
                                icon = Icons.Outlined.Phone,
                                checked = showPhone,
                                onCheckedChange = { showPhone = it }
                            )

                            HorizontalDivider(color = GhostBorder)

                            SettingsToggleRow(
                                title = "Video Consultations",
                                subtitle = "Allow clients to book video consultations with you",
                                icon = Icons.Outlined.Videocam,
                                checked = videoConsultations,
                                onCheckedChange = { videoConsultations = it }
                            )

                            HorizontalDivider(color = GhostBorder)

                            SettingsToggleRow(
                                title = "Require Payment for Appointments",
                                subtitle = "When disabled, clients can book without paying — \"Free\" badge will show on your profile",
                                icon = Icons.Outlined.CreditCard,
                                checked = requirePayment,
                                onCheckedChange = { requirePayment = it }
                            )

                            HorizontalDivider(color = GhostBorder)

                            SettingsToggleRow(
                                title = "Require Payment for Video Calls",
                                subtitle = "When disabled, clients can join video consultations without paying",
                                icon = Icons.Outlined.CreditCard,
                                checked = requirePaymentVideo,
                                onCheckedChange = { requirePaymentVideo = it }
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            BmsButton(
                                text = "Save Changes",
                                onClick = {
                                    val currentProfile = (uiState as VisibilityUiState.Success).profile
                                    viewModel.updateVisibilitySettings(
                                        currentProfile.copy(
                                            isActive = profileActive,
                                            videoEnabled = videoConsultations
                                        )
                                    )
                                },
                                leadingIcon = Icons.Outlined.Check
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }
}
