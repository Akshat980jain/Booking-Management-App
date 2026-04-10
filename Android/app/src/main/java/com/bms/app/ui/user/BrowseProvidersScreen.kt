package com.bms.app.ui.user

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bms.app.domain.model.ProviderProfile
import com.bms.app.domain.util.NameUtils
import com.bms.app.ui.components.*
import com.bms.app.ui.components.UserNavItems
import com.bms.app.ui.components.BmsBottomNavBar
import com.bms.app.ui.components.ProvidersSkeleton
import com.bms.app.ui.dashboard.UserDashboardUiState
import com.bms.app.ui.dashboard.UserDashboardViewModel
import com.bms.app.ui.theme.*

// ── Screen ────────────────────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BrowseProvidersScreen(
    onNavigate: (String) -> Unit = {},
    onBack: () -> Unit = {},
    onBookProvider: (providerId: String) -> Unit = {},
    viewModel: UserDashboardViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var query by remember { mutableStateOf("") }
    var selectedNav by remember { mutableStateOf("browse_providers") }
    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = {
            BmsTopBar(
                title = "Find a Provider",
                showBackButton = true,
                showAvatar = false,
                onNavigationClick = onBack,
                isLoading = uiState is UserDashboardUiState.Loading
            )
        },
        bottomBar = {
            BmsBottomNavBar(
                items = UserNavItems,
                selectedRoute = selectedNav,
                onItemSelected = { route ->
                    selectedNav = route
                    onNavigate(route)
                }
            )
        },
        containerColor = Background
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // ── Search bar ──────────────────────────────────────────────────
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = query,
                    onValueChange = { query = it },
                    placeholder = {
                        Text("Search by name or specialty…", style = MaterialTheme.typography.bodyMedium)
                    },
                    leadingIcon = {
                        Icon(Icons.Outlined.Search, contentDescription = null, tint = OnSurfaceVariant)
                    },
                    trailingIcon = {
                        if (query.isNotEmpty()) {
                            IconButton(onClick = { query = "" }) {
                                Icon(Icons.Outlined.Clear, contentDescription = "Clear", tint = OnSurfaceVariant)
                            }
                        }
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(28.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Primary,
                        unfocusedBorderColor = OutlineVariant,
                        focusedContainerColor = SurfaceContainerLowest,
                        unfocusedContainerColor = SurfaceContainerLowest
                    ),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = { focusManager.clearFocus() }),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // ── Provider list ───────────────────────────────────────────────
            when (val state = uiState) {
                is UserDashboardUiState.Loading -> {
                    ProvidersSkeleton()
                }

                is UserDashboardUiState.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                Icons.Outlined.ErrorOutline,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.error,
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(state.message, color = OnSurfaceVariant)
                        }
                    }
                }

                is UserDashboardUiState.Success -> {
                    val providers = state.providerMap.values
                        .filter { it.isActive }          // show all active providers
                        .filter { provider ->
                            if (query.isBlank()) true
                            else {
                                provider.profession.contains(query, ignoreCase = true) ||
                                    provider.specialty?.contains(query, ignoreCase = true) == true ||
                                    provider.location?.contains(query, ignoreCase = true) == true
                            }
                        }
                        .sortedWith(
                            compareByDescending<ProviderProfile> { it.isApproved }  // verified first
                                .thenByDescending { it.averageRating ?: 0.0 }
                        )

                    if (providers.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize().padding(32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(
                                    Icons.Outlined.SearchOff,
                                    contentDescription = null,
                                    tint = OnSurfaceVariant,
                                    modifier = Modifier.size(48.dp)
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                                Text(
                                    if (query.isBlank()) "No providers available yet"
                                    else "No providers match \"$query\"",
                                    style = MaterialTheme.typography.titleSmall,
                                    color = OnSurface
                                )
                            }
                        }

                    } else {
                        // Result count
                        Text(
                            text = "${providers.size} provider${if (providers.size == 1) "" else "s"} found",
                            style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 1.sp),
                            color = OnSurfaceVariant,
                            modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp)
                        )

                        LazyColumn(
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(providers, key = { it.id }) { provider ->
                                // Look up the provider's real name from the profiles table
                                val realName = state.userProfileMap[provider.userId]
                                    ?.fullName
                                    ?.takeIf { it.isNotBlank() }
                                ProviderCard(
                                    provider = provider,
                                    realName = realName,
                                    // Pass userId (auth user_id) — BookingViewModel.loadProviderBookingData
                                    // calls getProfileById(userId) and getProviderProfile(userId)
                                    // which both filter by the user_id column, NOT the PK id
                                    onBook = { onBookProvider(provider.userId) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

// ── Provider Card ─────────────────────────────────────────────────────────────

@Composable
private fun ProviderCard(
    provider: ProviderProfile,
    realName: String?,      // actual full name from profiles table
    onBook: () -> Unit
) {
    // Display name: real name if available, else fall back to profession
    val displayName = if (!realName.isNullOrBlank()) realName else provider.profession

    // Avatar initials from the real name (first+last initial), else profession
    val initials = NameUtils.getInitials(displayName)

    Surface(
        color = SurfaceContainerLowest,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            // Avatar circle
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape)
                    .background(PrimaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = initials,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = OnPrimaryContainer
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            // Info column
            Column(modifier = Modifier.weight(1f)) {
                // Primary headline: real name (e.g. "Dr. Akshat Jain")
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = if (!realName.isNullOrBlank()) "Dr. $displayName" else displayName,
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        color = OnSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f, fill = false)
                    )
                    if (provider.isApproved) {
                        Spacer(modifier = Modifier.width(6.dp))
                        Icon(
                            Icons.Outlined.Verified,
                            contentDescription = "Verified",
                            tint = Primary,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }

                // Profession + specialty as secondary line
                val subtitle = listOfNotNull(
                    provider.profession.takeIf { it.isNotBlank() },
                    provider.specialty?.takeIf { it.isNotBlank() }
                ).joinToString(" · ")
                if (subtitle.isNotBlank()) {
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = Primary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }


                Spacer(modifier = Modifier.height(6.dp))

                // Rating row
                val rating = provider.averageRating ?: 0.0
                val reviews = provider.totalReviews
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Outlined.Star,
                        contentDescription = null,
                        tint = if (rating > 0) StatusPending else OnSurfaceVariant,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        text = if (rating > 0) "%.1f".format(rating) else "New",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
                        color = OnSurface
                    )
                    if (reviews > 0) {
                        Text(
                            text = " ($reviews)",
                            style = MaterialTheme.typography.labelSmall,
                            color = OnSurfaceVariant
                        )
                    }

                    if (!provider.location.isNullOrBlank()) {
                        Text(
                            text = " · ${provider.location}",
                            style = MaterialTheme.typography.labelSmall,
                            color = OnSurfaceVariant,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                // Fee row
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Outlined.CurrencyRupee,
                        contentDescription = null,
                        tint = OnSurfaceVariant,
                        modifier = Modifier.size(14.dp)
                    )
                    Text(
                        text = "${provider.consultationFee.toInt()} / session",
                        style = MaterialTheme.typography.labelSmall,
                        color = OnSurfaceVariant
                    )
                    if (provider.videoEnabled) {
                        Spacer(modifier = Modifier.width(10.dp))
                        Surface(
                            color = PrimaryContainer,
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.Outlined.Videocam,
                                    contentDescription = null,
                                    tint = OnPrimaryContainer,
                                    modifier = Modifier.size(12.dp)
                                )
                                Spacer(modifier = Modifier.width(3.dp))
                                Text(
                                    text = "Video",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = OnPrimaryContainer
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Book button
            FilledTonalButton(
                onClick = onBook,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.filledTonalButtonColors(
                    containerColor = Primary,
                    contentColor = OnPrimary
                ),
                contentPadding = PaddingValues(horizontal = 14.dp, vertical = 8.dp)
            ) {
                Text(
                    "Book",
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                )
            }
        }
    }
}
