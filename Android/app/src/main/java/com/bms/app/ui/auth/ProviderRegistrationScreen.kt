package com.bms.app.ui.auth

import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bms.app.ui.components.BmsPrimaryButton
import com.bms.app.ui.components.BmsTextField
import com.bms.app.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProviderRegistrationScreen(
    onBack: () -> Unit,
    onCreateAccount: () -> Unit
) {
    var phone by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var profession by remember { mutableStateOf("Select Profession") }
    var specialty by remember { mutableStateOf("") }
    var experience by remember { mutableStateOf("") }
    var dropdownExpanded by remember { mutableStateOf(false) }

    val professions = listOf(
        "General Physician", "Dentist", "Physical Therapist",
        "Psychologist", "Dermatologist", "Cardiologist"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Join as a Provider",
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, "Back")
                    }
                },
                actions = {
                    Icon(
                        Icons.Outlined.Person,
                        contentDescription = null,
                        tint = Primary,
                        modifier = Modifier.padding(end = 16.dp)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Background
                )
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
            // ── Header ────────────────────────────────
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "REGISTRATION",
                style = MaterialTheme.typography.labelSmall.copy(
                    letterSpacing = 2.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = OnSurfaceVariant
            )
            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = "Professional\nDetails",
                    style = MaterialTheme.typography.headlineLarge,
                    color = OnSurface
                )
                Text(
                    text = "Step 2 of\n2",
                    style = MaterialTheme.typography.bodySmall,
                    color = OnSurfaceVariant,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Progress bar
            LinearProgressIndicator(
                progress = { 1f },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .clip(PillShape),
                color = Primary,
                trackColor = SurfaceContainerLow
            )

            Spacer(modifier = Modifier.height(24.dp))

            // ── Form Card ─────────────────────────────
            Surface(
                color = SurfaceContainerLowest,
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    BmsTextField(
                        value = phone,
                        onValueChange = { phone = it },
                        label = "PHONE NUMBER",
                        placeholder = "+1 (555) 000-0000",
                        leadingIcon = Icons.Outlined.Phone
                    )

                    BmsTextField(
                        value = address,
                        onValueChange = { address = it },
                        label = "OFFICE ADDRESS",
                        placeholder = "123 Business Way",
                        leadingIcon = Icons.Outlined.LocationOn
                    )

                    BmsTextField(
                        value = city,
                        onValueChange = { city = it },
                        label = "CITY",
                        placeholder = "San Francisco",
                        leadingIcon = Icons.Outlined.LocationCity
                    )

                    // Profession Dropdown
                    Column {
                        Text(
                            text = "PROFESSION",
                            style = MaterialTheme.typography.labelMedium,
                            color = OnSurfaceVariant,
                            modifier = Modifier.padding(start = 4.dp, bottom = 8.dp)
                        )
                        ExposedDropdownMenuBox(
                            expanded = dropdownExpanded,
                            onExpandedChange = { dropdownExpanded = it }
                        ) {
                            OutlinedTextField(
                                value = profession,
                                onValueChange = {},
                                readOnly = true,
                                leadingIcon = {
                                    Icon(
                                        Icons.Outlined.Work,
                                        null,
                                        tint = Outline,
                                        modifier = Modifier.size(20.dp)
                                    )
                                },
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
                    }

                    BmsTextField(
                        value = specialty,
                        onValueChange = { specialty = it },
                        label = "SPECIALTY",
                        placeholder = "e.g. Sports Therapy",
                        leadingIcon = Icons.Outlined.MedicalServices
                    )

                    BmsTextField(
                        value = experience,
                        onValueChange = { experience = it },
                        label = "YEARS OF EXPERIENCE",
                        placeholder = "5",
                        leadingIcon = Icons.Outlined.Schedule
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ── Terms text ────────────────────────────
            Text(
                text = "By continuing, you agree to our Service Terms and confirm your information is accurate for professional verification.",
                style = MaterialTheme.typography.bodySmall,
                color = OnSurfaceVariant,
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // ── CTA ────────────────────────────────────
            BmsPrimaryButton(
                text = "Create Account",
                onClick = onCreateAccount,
                trailingIcon = Icons.Outlined.ArrowForward
            )

            Spacer(modifier = Modifier.height(24.dp))

            // ── Trust Badge ────────────────────────────
            Surface(
                color = SurfaceContainerHigh,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        Icons.Outlined.Shield,
                        contentDescription = null,
                        tint = OnSurfaceVariant,
                        modifier = Modifier.size(28.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            "Enterprise Security",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = OnSurface
                        )
                        Text(
                            "Your professional data is encrypted and stored according to global compliance standards.",
                            style = MaterialTheme.typography.bodySmall,
                            color = OnSurfaceVariant
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
