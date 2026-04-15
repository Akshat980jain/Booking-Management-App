package com.bms.app.ui.settings

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.scale
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bms.app.domain.model.UserProfile
import com.bms.app.ui.settings.viewmodel.ProfileUiState
import com.bms.app.ui.settings.viewmodel.SettingsViewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bms.app.ui.components.*
import com.bms.app.ui.theme.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
    onNavigateToPersonal: () -> Unit = {},
    onNavigateToNotifications: () -> Unit = {},
    onNavigateToVisibility: () -> Unit = {},
    onNavigateToAvailability: () -> Unit = {},
    onNavigateToProfessional: () -> Unit = {},
    onLogout: () -> Unit = {},
    onBack: () -> Unit = {},
    onBottomNav: (String) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    
    val tabs = listOf("Personal", "Professional", "Visibility")
    val pagerState = rememberPagerState(pageCount = { tabs.size })
    val coroutineScope = rememberCoroutineScope()
    // Keep 'settings' selected so users can see which tab they are on
    var selectedBottomNav by remember { mutableStateOf("settings") }

    Scaffold(
        topBar = {
            val initials = if (uiState is ProfileUiState.Success) {
                (uiState as ProfileUiState.Success).userInitials
            } else "..."
            val (title, header, subtitle) = when {
                uiState is ProfileUiState.Success -> {
                    val role = (uiState as ProfileUiState.Success).userProfile.role?.uppercase() ?: "USER"
                    when (role) {
                        "ADMIN" -> Triple("Admin Console", "Admin Settings", "Manage system configurations and user access.")
                        "PROVIDER" -> Triple("Settings", "Provider Settings", "Manage your professional identity and\noperational preferences.")
                        else -> Triple("Settings", "Account Settings", "Manage your personal profile and\nsecurity preferences.")
                    }
                }
                else -> Triple("Settings", "Settings", "Manage your preferences.")
            }

            BmsTopBar(
                title = title,
                userName = (uiState as? ProfileUiState.Success)?.userProfile?.fullName,
                avatarInitials = initials,
                isLoading = uiState is ProfileUiState.Loading,
                showBackButton = true,
                onNavigationClick = { onBack() }
            )
        },
        bottomBar = {
            val role = (uiState as? ProfileUiState.Success)?.userProfile?.role?.uppercase() ?: "USER"
            val navItems = when (role) {
                "ADMIN" -> AdminNavItems
                "PROVIDER" -> ProviderNavItems
                else -> UserNavItems
            }
            
            BmsBottomNavBar(
                items = navItems,
                selectedRoute = selectedBottomNav,
                onItemSelected = { route ->
                    selectedBottomNav = route
                    onBottomNav(route)
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
            val (_, header, subtitle) = when {
                uiState is ProfileUiState.Success -> {
                    val role = (uiState as ProfileUiState.Success).userProfile.role?.uppercase() ?: "USER"
                    when (role) {
                        "ADMIN" -> Triple("Admin Console", "Admin Settings", "Manage system configurations and user access.")
                        "PROVIDER" -> Triple("Settings", "Provider Settings", "Manage your professional identity and\noperational preferences.")
                        else -> Triple("Settings", "Account Settings", "Manage your personal profile and\nsecurity preferences.")
                    }
                }
                else -> Triple("Settings", "Settings", "Manage your preferences.")
            }

            // ── Header ────────────────────────────────
            Column(
                modifier = Modifier.padding(horizontal = 24.dp)
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = header,
                    style = MaterialTheme.typography.headlineLarge,
                    color = OnSurface
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = OnSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ── Tab Row ───────────────────────────────
            TabRow(
                selectedTabIndex = pagerState.currentPage,
                containerColor = Background,
                contentColor = Primary,
                modifier = Modifier.padding(horizontal = 24.dp)
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = {
                            coroutineScope.launch { pagerState.animateScrollToPage(index) }
                        },
                        text = {
                            Text(
                                text = title,
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = if (pagerState.currentPage == index) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        selectedContentColor = Primary,
                        unselectedContentColor = OnSurfaceVariant
                    )
                }
            }

            // ── Pager Content ─────────────────────────
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                when (page) {
                    0 -> PersonalTab(viewModel, uiState, onNavigateToPersonal, onLogout)
                    1 -> ProfessionalTab(uiState, onNavigateToProfessional)
                    2 -> VisibilityTab(uiState, viewModel, onNavigateToVisibility)
                }
            }
        }
    }
}

@Composable
private fun PersonalTab(
    viewModel: SettingsViewModel,
    uiState: ProfileUiState,
    onNavigateToPersonal: () -> Unit,
    onLogout: () -> Unit
) {
    val strength by viewModel.profileStrength.collectAsStateWithLifecycle()

    // Derive the current saved country/city from the uiState (not hardcoded)
    val currentCountry = if (uiState is ProfileUiState.Success) {
        (uiState as ProfileUiState.Success).userProfile.country ?: ""
    } else ""
    val currentCity = if (uiState is ProfileUiState.Success) {
        (uiState as ProfileUiState.Success).userProfile.city ?: ""
    } else ""

    var showLocationDialog by remember { mutableStateOf(false) }
    // These are keyed to currentCountry/City so they reset whenever the DB data changes
    var selectedCountry by remember(currentCountry) { mutableStateOf(currentCountry.ifBlank { "USA" }) }
    var selectedCity by remember(currentCity) { mutableStateOf(currentCity) }

    val countries = listOf("USA", "India", "United Kingdom", "Germany", "Japan", "UAE", "Australia", "Canada", "France")

    if (showLocationDialog) {
        AlertDialog(
            onDismissRequest = { showLocationDialog = false },
            title = { Text("Select Region", style = MaterialTheme.typography.titleLarge) },
            text = {
                Column {
                    Text(
                        "Currency is automatically assigned based on country.",
                        style = MaterialTheme.typography.bodySmall,
                        color = OnSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(
                        value = selectedCity,
                        onValueChange = { selectedCity = it },
                        label = { Text("City") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("Country", style = MaterialTheme.typography.labelMedium, color = OnSurfaceVariant)
                    Spacer(modifier = Modifier.height(4.dp))
                    countries.forEach { country ->
                        Row(
                            Modifier.fillMaxWidth().clickable { selectedCountry = country }.padding(vertical = 2.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(selected = selectedCountry == country, onClick = { selectedCountry = country })
                            Text(country, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(start = 4.dp))
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.updateLocation(selectedCountry, selectedCity)
                    showLocationDialog = false
                }) { Text("Apply") }
            },
            dismissButton = {
                TextButton(onClick = { showLocationDialog = false }) { Text("Cancel") }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {
        when (uiState) {
            is ProfileUiState.Loading -> {
                SkeletonSettings()
            }
            is ProfileUiState.Error -> {
                Text("Error loading profile: ${uiState.message}", color = MaterialTheme.colorScheme.error)
            }
            is ProfileUiState.Success -> {
                val userProfile = uiState.userProfile
                val providerProfile = uiState.providerProfile
                
                // ── Profile Card ──────────────────────────
                Surface(
                    color = SurfaceContainerLowest,
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Top
                        ) {
                            BmsAvatar(
                                name = userProfile.fullName,
                                size = AvatarSize.LARGE,
                                showGlassEffect = true
                            )

                            // Secondary avatar placeholder (AJ circle in background)
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(CircleShape)
                                    .background(SurfaceContainerLow),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = uiState.userInitials,
                                    style = MaterialTheme.typography.labelLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = OnSurfaceVariant.copy(alpha = 0.4f)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            TextButton(
                                onClick = { },
                                colors = ButtonDefaults.textButtonColors(contentColor = Primary),
                                contentPadding = PaddingValues(0.dp)
                            ) {
                                Icon(Icons.Outlined.Edit, null, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("EDIT PHOTO", style = MaterialTheme.typography.labelSmall)
                            }

                            if (providerProfile != null) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        if (providerProfile.isActive) "ONLINE" else "OFFLINE",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = if (providerProfile.isActive) Color(0xFF10B981) else OnSurfaceVariant
                                    )
                                    Spacer(Modifier.width(8.dp))
                                    Switch(
                                        checked = providerProfile.isActive,
                                        onCheckedChange = { viewModel.toggleActiveStatus(it) },
                                        colors = SwitchDefaults.colors(
                                            checkedThumbColor = Color.White,
                                            checkedTrackColor = Color(0xFF10B981),
                                            uncheckedThumbColor = Color.White,
                                            uncheckedTrackColor = SurfaceContainerHigh
                                        ),
                                        modifier = Modifier
                                            .scale(0.7f)
                                            .height(24.dp)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        // Info Section
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                userProfile.fullName,
                                style = MaterialTheme.typography.headlineSmall,
                                color = OnSurface,
                                fontWeight = FontWeight.ExtraBold
                            )
                            val roleLabel = when {
                                providerProfile != null -> providerProfile.profession
                                userProfile.role != null -> userProfile.role.lowercase()
                                    .replaceFirstChar { it.uppercase() }
                                else -> "User"
                            }
                            Text(
                                roleLabel.uppercase(),
                                style = MaterialTheme.typography.labelMedium.copy(letterSpacing = 0.5.sp),
                                color = Primary,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(20.dp))

                            Text(
                                "EMAIL ADDRESS",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    letterSpacing = 1.sp,
                                    fontWeight = FontWeight.Black
                                ),
                                color = OnSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                userProfile.email,
                                style = MaterialTheme.typography.bodyMedium,
                                color = OnSurface,
                                fontWeight = FontWeight.SemiBold
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                "CONTACT NUMBER",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    letterSpacing = 1.sp,
                                    fontWeight = FontWeight.Black
                                ),
                                color = OnSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                userProfile.phone ?: "Not set",
                                style = MaterialTheme.typography.bodyMedium,
                                color = OnSurface,
                                fontWeight = FontWeight.SemiBold
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                if (providerProfile?.isVerified == true) {
                                    StatusBadge(
                                        "Verified Provider",
                                        OnStatusActive.copy(alpha = 0.1f),
                                        OnStatusActive
                                    )
                                }
                                val joinedDate = try {
                                    val iso = userProfile.createdAt.substringBefore("T")
                                    val parts = iso.split("-")
                                    val months = listOf(
                                        "Jan", "Feb", "Mar", "Apr", "May", "Jun",
                                        "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
                                    )
                                    "Joined ${months[parts[1].toInt() - 1]} ${parts[0]}"
                                } catch (e: Exception) {
                                    "Joined ${userProfile.createdAt.take(10)}"
                                }
                                StatusBadge(joinedDate, SurfaceContainerHigh, OnSurfaceVariant)
                            }
                        }
                    }
                }

        Spacer(modifier = Modifier.height(20.dp))

        // ── Profile Strength ──────────────────────
        Surface(
            color = SurfaceContainerLowest,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Outlined.AutoAwesome, null, tint = Primary)
                    Text(
                        "${ (strength * 100).toInt() }%",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontFamily = ManropeFamily,
                            color = Primary,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Profile Strength",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = OnSurface
                )
                Spacer(modifier = Modifier.height(8.dp))
                LinearProgressIndicator(
                    progress = { strength },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(PillShape),
                    color = Primary,
                    trackColor = SurfaceContainerLow
                )
                Spacer(modifier = Modifier.height(8.dp))
                val strengthTitle = when {
                    strength < 0.4f -> "Beginner Profile"
                    strength < 0.8f -> "Nearly Complete"
                    else -> "All Star Profile"
                }
                Text(
                    "Your profile is ${ (strength * 100).toInt() }% complete ($strengthTitle).\nAdding missing info increases client trust.",
                    style = MaterialTheme.typography.bodySmall,
                    color = OnSurfaceVariant
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // ── Security & Privacy ────────────────────
        Surface(
            color = SurfaceContainerLowest,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Outlined.Lock, null, tint = Primary)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "Security & Privacy",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = OnSurface
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                SettingsToggleRow(
                    title = "Two-Factor Auth",
                    subtitle = "SMS & Authenticator App",
                    checked = userProfile.twoFaEnabled ?: false,
                    onCheckedChange = { viewModel.toggleTwoFa(it) }
                )

                SettingsClickRow(
                    title = "Session Timeout",
                    subtitle = "Auto-logout after ${userProfile.sessionTimeoutMinutes ?: 15}m",
                    trailing = {
                        Icon(
                            Icons.Outlined.ChevronRight,
                            null,
                            tint = OnSurfaceVariant,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // ── Operational Ledger ────────────────────
        Surface(
            color = SurfaceContainerLowest,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    "Operational Ledger",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = OnSurface
                )

                Spacer(modifier = Modifier.height(16.dp))

                LedgerRow("PRIMARY TIMEZONE", userProfile.timezone ?: "UTC", Icons.Outlined.Schedule)
                Spacer(modifier = Modifier.height(12.dp))
                LedgerRow("DISPLAY LANGUAGE", userProfile.preferredLanguage ?: "en-US", Icons.Outlined.Language)
                Spacer(modifier = Modifier.height(12.dp))
                LedgerRow("COUNTRY", userProfile.country ?: "Not Set", Icons.Outlined.Public) {
                    showLocationDialog = true
                }
                Spacer(modifier = Modifier.height(12.dp))
                LedgerRow("CITY", userProfile.city ?: "Not Set", Icons.Outlined.LocationCity)
                Spacer(modifier = Modifier.height(12.dp))
                LedgerRow("CURRENCY FORMAT", userProfile.preferredCurrency ?: "USD", Icons.Outlined.AttachMoney)
            }
        }
    } // End Success block
} // End when block

        Spacer(modifier = Modifier.height(32.dp))

        // ── Log Out Button ────────────────────────
        OutlinedButton(
            onClick = onLogout,
            shape = PillShape,
            border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.error),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colorScheme.error
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Icon(
                Icons.Outlined.ExitToApp,
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                "Log Out",
                style = MaterialTheme.typography.labelLarge.copy(
                    letterSpacing = 1.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
private fun LedgerRow(
    label: String,
    value: String,
    icon: ImageVector,
    onClick: (() -> Unit)? = null
) {
    Column(
        modifier = if (onClick != null) Modifier.fillMaxWidth().clickable { onClick() } else Modifier.fillMaxWidth()
    ) {
        Text(
            label,
            style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 1.sp),
            color = OnSurfaceVariant
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(value, style = MaterialTheme.typography.bodyMedium, color = OnSurface)
            Icon(icon, null, tint = Primary, modifier = Modifier.size(20.dp))
        }
    }
}

@Composable
private fun ProfessionalTab(uiState: ProfileUiState, onNavigateToProfessional: () -> Unit) {
    val state = uiState as? ProfileUiState.Success
    val provider = state?.providerProfile

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        if (provider != null) {
            Text(
                "Practice Dashboard",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = OnSurface
            )

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                StatCard(
                    title = "Experience",
                    value = "${provider.yearsOfExperience}Y+",
                    icon = Icons.Outlined.WorkspacePremium,
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = "Rating",
                    value = "%.1f".format(provider.averageRating ?: 0.0),
                    icon = Icons.Outlined.Star,
                    modifier = Modifier.weight(1f),
                    badge = "${provider.totalReviews} REVIEWS",
                    badgeColor = Primary.copy(alpha = 0.1f),
                    badgeTextColor = Primary
                )
            }

            Surface(
                color = SurfaceContainerLowest,
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        "Consultation Fees",
                        style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 1.sp, fontWeight = FontWeight.Black),
                        color = OnSurfaceVariant
                    )
                    Spacer(Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        FeeItem("In-Person", provider.consultationFee, state.userProfile.preferredCurrency ?: "USD", Icons.Outlined.LocationOn)
                        FeeItem("Video Call", provider.videoConsultationFee ?: 0.0, state.userProfile.preferredCurrency ?: "USD", Icons.Outlined.Videocam)
                    }
                }
            }

            Surface(
                color = SurfaceContainerLowest,
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                "Practice Location",
                                style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 1.sp, fontWeight = FontWeight.Black),
                                color = OnSurfaceVariant
                            )
                            Spacer(Modifier.height(4.dp))
                            Text(provider.location ?: "Address not set", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
                        }
                        Icon(Icons.Outlined.Map, null, tint = Primary)
                    }
                }
            }
        }

        Surface(
            color = SurfaceContainerLowest,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    "Credential Management",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = OnSurface
                )
                Text(
                    "Manage your clinical certifications and professional history.",
                    style = MaterialTheme.typography.bodySmall,
                    color = OnSurfaceVariant
                )
                Spacer(modifier = Modifier.height(16.dp))
                BmsSecondaryButton(
                    text = "Edit Professional Profile",
                    onClick = onNavigateToProfessional
                )
            }
        }
    }
}

@Composable
private fun FeeItem(label: String, amount: Double, currency: String, icon: ImageVector) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier.size(32.dp).background(Primary.copy(alpha = 0.1f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, null, tint = Primary, modifier = Modifier.size(16.dp))
        }
        Spacer(Modifier.width(12.dp))
        Column {
            Text(label, style = MaterialTheme.typography.labelSmall, color = OnSurfaceVariant)
            Text("$amount $currency", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Black)
        }
    }
}

@Composable
private fun VisibilityTab(
    uiState: ProfileUiState,
    viewModel: SettingsViewModel,
    onNavigateToVisibility: () -> Unit
) {
    val state = uiState as? ProfileUiState.Success
    val provider = state?.providerProfile

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        if (provider != null) {
            Surface(
                color = SurfaceContainerLowest,
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        "Clinical Workflow",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = OnSurface
                    )
                    Spacer(Modifier.height(16.dp))

                    SettingsToggleRow(
                        title = "Auto-Approval",
                        subtitle = "Automatically accept bookings within availability",
                        checked = true, // Place-holder for UI demonstrating functionality
                        onCheckedChange = { /* viewModel.updateOperationalSettings(autoApproval = it) */ }
                    )

                    Spacer(Modifier.height(8.dp))

                    SettingsClickRow(
                        title = "Consultation Buffer",
                        subtitle = "${provider.bufferTimeAfter} minutes between sessions",
                        trailing = {
                            Text(
                                "Edit",
                                style = MaterialTheme.typography.labelMedium,
                                color = Primary,
                                fontWeight = FontWeight.Bold
                            )
                        },
                        onClick = { /* Could show a picker */ }
                    )
                }
            }
        }

        Surface(
            color = SurfaceContainerLowest,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    "Profile Discovery",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = OnSurface
                )
                Text(
                    "Control how clients find and interact with your public profile.",
                    style = MaterialTheme.typography.bodySmall,
                    color = OnSurfaceVariant
                )
                Spacer(modifier = Modifier.height(16.dp))
                BmsSecondaryButton(
                    text = "Search & Discovery Settings",
                    onClick = onNavigateToVisibility
                )
            }
        }
    }
}
