package com.bms.app.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bms.app.domain.model.UserProfile
import com.bms.app.ui.settings.viewmodel.ProfileUiState
import com.bms.app.ui.settings.viewmodel.SettingsViewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bms.app.ui.components.*
import com.bms.app.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalInfoScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    
    var fullName by remember { mutableStateOf("Loading...") }
    var phone by remember { mutableStateOf("Loading...") }

    // Update local state when profile loads
    LaunchedEffect(uiState) {
        if (uiState is ProfileUiState.Success) {
            val userProfile = (uiState as ProfileUiState.Success).userProfile
            fullName = userProfile.fullName
            phone = userProfile.phone ?: ""
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Personal Information") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Background)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Background)
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                "Personal\nInformation",
                style = MaterialTheme.typography.headlineLarge,
                color = OnSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                "Manage your public-facing identity and\nsecure contact channels.",
                style = MaterialTheme.typography.bodyMedium,
                color = OnSurfaceVariant
            )

            Spacer(modifier = Modifier.height(24.dp))

            // ── Identity Card ─────────────────────────
            Surface(
                color = SurfaceContainerLowest,
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    // Avatar row
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape)
                                .background(SurfaceContainerLow),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Outlined.Person,
                                null,
                                tint = OnSurfaceVariant,
                                modifier = Modifier.size(40.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Column {
                            Text(
                                "PROVIDER IDENTITY",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    letterSpacing = 2.sp,
                                    fontWeight = FontWeight.Bold
                                ),
                                color = OnSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                fullName.replace(" ", "\n"),
                                style = MaterialTheme.typography.headlineSmall,
                                color = OnSurface
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Form fields
                    BmsTextField(
                        value = fullName,
                        onValueChange = { fullName = it },
                        label = "FULL NAME",
                        placeholder = "Enter your full name",
                        leadingIcon = Icons.Outlined.Person
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    BmsTextField(
                        value = phone,
                        onValueChange = { phone = it },
                        label = "PHONE NUMBER",
                        placeholder = "+1 (555) 000-0000",
                        leadingIcon = Icons.Outlined.Phone
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    BmsSecondaryButton(
                        text = "Save Personal Info",
                        onClick = { }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ── Verified Credentials ──────────────────
            Surface(
                color = SurfaceContainerLowest,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(modifier = Modifier.padding(16.dp)) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(SecondaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Outlined.Verified,
                            null,
                            tint = OnSecondaryContainer,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            "Verified Credentials",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = OnSurface
                        )
                        Text(
                            "Your professional credentials were last verified on Oct 24, 2023. Changes to your name may require re-verification.",
                            style = MaterialTheme.typography.bodySmall,
                            color = OnSurfaceVariant
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ── 2FA ───────────────────────────────────
            Surface(
                color = SurfaceContainerLowest,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Outlined.Security,
                        null,
                        tint = Primary,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        "Two-Factor Authentication",
                        style = MaterialTheme.typography.titleSmall,
                        color = OnSurface,
                        modifier = Modifier.weight(1f)
                    )
                    StatusBadge(
                        "ENABLED",
                        SecondaryContainer,
                        OnSecondaryContainer
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
