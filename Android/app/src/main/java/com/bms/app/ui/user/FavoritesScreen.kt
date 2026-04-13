package com.bms.app.ui.user

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.SearchOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bms.app.ui.components.*
import com.bms.app.ui.dashboard.UserDashboardUiState
import com.bms.app.ui.dashboard.UserDashboardViewModel
import com.bms.app.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    onNavigate: (String) -> Unit = {},
    onBack: () -> Unit = {},
    onBookProvider: (providerId: String) -> Unit = {},
    viewModel: UserDashboardViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var selectedNav by remember { mutableStateOf("favorites") }

    Scaffold(
        topBar = {
            BmsTopBar(
                title = "My Favorites",
                showBackButton = true,
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
        containerColor = Background,
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            when (val state = uiState) {
                is UserDashboardUiState.Loading -> {
                    ProvidersSkeleton()
                }
                is UserDashboardUiState.Error -> {
                    ErrorMessage(message = state.message, onRetry = { viewModel.loadDashboard() })
                }
                is UserDashboardUiState.Success -> {
                    val favoriteProviders = state.providerMap.filterKeys { state.favoriteProviderIds.contains(it) }.values.toList()

                    Column(modifier = Modifier.fillMaxSize()) {
                        if (favoriteProviders.isEmpty()) {
                            EmptyFavorites(onExplore = { onNavigate("browse_providers") })
                        } else {
                            Text(
                                text = "${favoriteProviders.size} saved provider${if (favoriteProviders.size == 1) "" else "s"}",
                                style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 1.sp),
                                color = OnSurfaceVariant,
                                modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)
                            )

                            LazyColumn(
                                modifier = Modifier.weight(1f),
                                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                items(favoriteProviders, key = { it.id }) { provider ->
                                    val realName = state.userProfileMap[provider.userId]?.fullName
                                    ProviderCard(
                                        provider = provider,
                                        realName = realName,
                                        isFavorite = true,
                                        isSelectedForComparison = state.selectedComparisonIds.contains(provider.id),
                                        onToggleFavorite = { viewModel.toggleFavorite(provider.id) },
                                        onToggleComparison = { viewModel.toggleComparisonSelection(provider.id) },
                                        onBook = { onBookProvider(provider.userId) }
                                    )
                                }
                            }
                        }
                    }

                    // Comparison Bar
                    if (state.selectedComparisonIds.isNotEmpty()) {
                        Surface(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(16.dp)
                                .fillMaxWidth(),
                            color = Primary,
                            shape = RoundedCornerShape(16.dp),
                            shadowElevation = 8.dp
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "${state.selectedComparisonIds.size}/3 selected",
                                    color = OnPrimary,
                                    style = MaterialTheme.typography.titleSmall
                                )
                                TextButton(
                                    onClick = { onNavigate("compare_providers") },
                                    colors = ButtonDefaults.textButtonColors(contentColor = OnPrimary)
                                ) {
                                    Text("Compare Now", fontWeight = FontWeight.Bold)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Icon(Icons.AutoMirrored.Outlined.ArrowForward, null, modifier = Modifier.size(16.dp))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun EmptyFavorites(onExplore: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            Icons.Outlined.Favorite,
            contentDescription = null,
            tint = OnSurfaceVariant.copy(alpha = 0.3f),
            modifier = Modifier.size(64.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "No Favorites Yet",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = OnSurface
        )
        Text(
            "Bookmark providers you like to find them quickly later.",
            style = MaterialTheme.typography.bodyMedium,
            color = OnSurfaceVariant,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))
        BmsButton(
            text = "Explore Providers",
            onClick = onExplore
        )
    }
}
