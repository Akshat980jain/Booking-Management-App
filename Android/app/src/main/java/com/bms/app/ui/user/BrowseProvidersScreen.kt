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
import com.bms.app.ui.components.ProviderCard
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
                    // ── Filtering Logic ───────────────────────────────────────
                    val providers = state.providerMap.values
                        .filter { it.isActive }
                        .filter { provider ->
                            val matchesQuery = if (query.isBlank()) true
                                else {
                                    provider.profession.contains(query, ignoreCase = true) ||
                                    provider.specialty?.contains(query, ignoreCase = true) == true ||
                                    provider.location?.contains(query, ignoreCase = true) == true
                                }
                            val matchesVideo = !state.showVideoOnly || provider.videoEnabled
                            val matchesProfession = state.selectedProfession == null || provider.profession == state.selectedProfession
                            
                            matchesQuery && matchesVideo && matchesProfession
                        }
                        .sortedWith(
                            compareByDescending<ProviderProfile> { it.isApproved }
                                .thenByDescending { it.averageRating ?: 0.0 }
                        )

                    Column(modifier = Modifier.fillMaxSize()) {
                        // ── Advanced Filters ──────────────────────────────────
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            var showProfessionMenu by remember { mutableStateOf(false) }
                            val professions = remember(state.providerMap) {
                                state.providerMap.values.map { it.profession }.distinct().sorted()
                            }

                            Box {
                                FilterChip(
                                    selected = state.selectedProfession != null,
                                    onClick = { showProfessionMenu = true },
                                    label = { Text(state.selectedProfession ?: "All Professions") },
                                    trailingIcon = { Icon(Icons.Outlined.ArrowDropDown, null, Modifier.size(18.dp)) },
                                    leadingIcon = if (state.selectedProfession != null) {
                                        { Icon(Icons.Outlined.WorkOutline, null, Modifier.size(16.dp)) }
                                    } else null
                                )

                                DropdownMenu(
                                    expanded = showProfessionMenu,
                                    onDismissRequest = { showProfessionMenu = false },
                                    modifier = Modifier.background(SurfaceContainerLowest)
                                ) {
                                    DropdownMenuItem(
                                        text = { Text("All Professions") },
                                        onClick = {
                                            viewModel.updateFilters(null, state.minRating, state.showVideoOnly)
                                            showProfessionMenu = false
                                        }
                                    )
                                    professions.forEach { prof ->
                                        DropdownMenuItem(
                                            text = { Text(prof) },
                                            onClick = {
                                                viewModel.updateFilters(prof, state.minRating, state.showVideoOnly)
                                                showProfessionMenu = false
                                            }
                                        )
                                    }
                                }
                            }

                            FilterChip(
                                selected = state.showVideoOnly,
                                onClick = { viewModel.updateFilters(state.selectedProfession, state.minRating, !state.showVideoOnly) },
                                label = { Text("Video") },
                                leadingIcon = if (state.showVideoOnly) {
                                    { Icon(Icons.Outlined.Check, null, Modifier.size(16.dp)) }
                                } else null
                            )
                            
                            // High Rating filter
                            FilterChip(
                                selected = state.minRating >= 4f,
                                onClick = { 
                                    val newRating = if (state.minRating >= 4f) 0f else 4f
                                    viewModel.updateFilters(state.selectedProfession, newRating, state.showVideoOnly)
                                },
                                label = { Text("4.0+") },
                                leadingIcon = { Icon(Icons.Outlined.Star, null, Modifier.size(16.dp), tint = Gold) }
                            )
                        }

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
                                    val realName = state.userProfileMap[provider.userId]
                                        ?.fullName
                                        ?.takeIf { it.isNotBlank() }
                                    ProviderCard(
                                        provider = provider,
                                        realName = realName,
                                        isFavorite = state.favoriteProviderIds.contains(provider.id),
                                        isSelectedForComparison = state.selectedComparisonIds.contains(provider.id),
                                        onToggleFavorite = { viewModel.toggleFavorite(provider.id) },
                                        onToggleComparison = { viewModel.toggleComparisonSelection(provider.id) },
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
}
