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
    
    var fullName by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var insProvider by remember { mutableStateOf("") }
    var policyNum by remember { mutableStateOf("") }

    // Update local state when profile loads
    LaunchedEffect(uiState) {
        if (uiState is ProfileUiState.Success) {
            val userProfile = (uiState as ProfileUiState.Success).userProfile
            fullName = userProfile.fullName
            phone = userProfile.phone ?: ""
            insProvider = userProfile.insuranceProvider ?: ""
            policyNum = userProfile.policyNumber ?: ""
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
                "Manage your data, insurance details,\nand secure contact channels.",
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
                                "USER IDENTITY",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    letterSpacing = 2.sp,
                                    fontWeight = FontWeight.Bold
                                ),
                                color = OnSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                fullName.ifBlank { "Unset" },
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
                        onClick = { viewModel.updatePersonalInfo(fullName, phone) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ── Insurance Information ─────────────────
            Surface(
                color = SurfaceContainerLowest,
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        "INSURANCE INFORMATION",
                        style = MaterialTheme.typography.labelSmall.copy(
                            letterSpacing = 2.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        color = OnSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    BmsTextField(
                        value = insProvider,
                        onValueChange = { insProvider = it },
                        label = "INSURANCE PROVIDER",
                        placeholder = "e.g. Blue Cross Blue Shield",
                        leadingIcon = Icons.Outlined.Assignment
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    BmsTextField(
                        value = policyNum,
                        onValueChange = { policyNum = it },
                        label = "POLICY NUMBER",
                        placeholder = "e.g. ABC123456789",
                        leadingIcon = Icons.Outlined.Numbers
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Insurance Card Upload Placeholder
                    Surface(
                        color = Background,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth(),
                        border = androidx.compose.foundation.BorderStroke(1.dp, OutlineVariant)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(Icons.Outlined.CloudUpload, null, tint = OnSurfaceVariant)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Click to upload insurance card", style = MaterialTheme.typography.bodySmall, color = OnSurfaceVariant)
                            Text("JPEG, PNG, or PDF (Max 5MB)", style = MaterialTheme.typography.labelSmall, color = OnSurfaceVariant.copy(alpha = 0.6f))
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    BmsSecondaryButton(
                        text = "Save Insurance Info",
                        onClick = { viewModel.updateInsurance(insProvider, policyNum) }
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
