package com.bms.app.ui.user

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bms.app.domain.model.ProviderProfile
import com.bms.app.domain.util.NameUtils
import com.bms.app.ui.components.*
import com.bms.app.ui.dashboard.UserDashboardUiState
import com.bms.app.ui.dashboard.UserDashboardViewModel
import com.bms.app.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompareProvidersScreen(
    onNavigate: (String) -> Unit = {},
    onBack: () -> Unit = {},
    onBookProvider: (providerId: String) -> Unit = {},
    viewModel: UserDashboardViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            BmsTopBar(
                title = "Compare Providers",
                showBackButton = true,
                onNavigationClick = onBack,
                isLoading = uiState is UserDashboardUiState.Loading
            )
        },
        containerColor = Background
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
                    val selectedProviders = state.providerMap.filterKeys { state.selectedComparisonIds.contains(it) }.values.toList()

                    if (selectedProviders.isEmpty()) {
                        EmptyComparison(onAdd = { onNavigate("browse_providers") })
                    } else {
                        Column(modifier = Modifier.fillMaxSize().verticalScroll(scrollState)) {
                            ComparisonGrid(
                                providers = selectedProviders,
                                userProfileMap = state.userProfileMap,
                                onRemove = { viewModel.toggleComparisonSelection(it) },
                                onBook = { onBookProvider(it) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ComparisonGrid(
    providers: List<ProviderProfile>,
    userProfileMap: Map<String, com.bms.app.domain.model.UserProfile>,
    onRemove: (String) -> Unit,
    onBook: (String) -> Unit
) {
    Card(
        modifier = Modifier.padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header: Avatars and Names
            Row(modifier = Modifier.fillMaxWidth()) {
                Box(modifier = Modifier.width(100.dp)) // Label column
                
                providers.forEach { provider ->
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopEnd) {
                            IconButton(onClick = { onRemove(provider.id) }, modifier = Modifier.size(24.dp)) {
                                Icon(Icons.Outlined.Cancel, contentDescription = "Remove", tint = OnSurfaceVariant, modifier = Modifier.size(16.dp))
                            }
                        }
                        
                        val name = userProfileMap[provider.userId]?.fullName ?: provider.profession
                        val initials = NameUtils.getInitials(name)
                        
                        Box(
                            modifier = Modifier.size(48.dp).clip(CircleShape).background(PrimaryContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(initials, style = MaterialTheme.typography.titleSmall, color = OnPrimaryContainer)
                        }
                        
                        Text(
                            text = if (name.length > 10) name.take(8) + ".." else name,
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
                
                // Fill up to 3 columns if needed
                repeat(3 - providers.size) {
                    Box(modifier = Modifier.weight(1f))
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            HorizontalDivider(color = OutlineVariant.copy(alpha = 0.2f))

            // Rows
            ComparisonRow("Rating", Icons.Outlined.Star, providers) { p ->
                val rating = p.averageRating ?: 0.0
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Outlined.Star, null, tint = StatusPending, modifier = Modifier.size(12.dp))
                    Text(if (rating > 0) "%.1f".format(rating) else "New", style = MaterialTheme.typography.bodySmall)
                }
            }

            ComparisonRow("Experience", Icons.Outlined.History, providers) { p ->
                Text("${p.yearsOfExperience}y", style = MaterialTheme.typography.bodySmall)
            }

            ComparisonRow("Fee", Icons.Outlined.CurrencyRupee, providers) { p ->
                Text("${p.consultationFee.toInt()}", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
            }

            ComparisonRow("Location", Icons.Outlined.Map, providers) { p ->
                Text(p.location ?: "N/A", style = MaterialTheme.typography.bodySmall, maxLines = 1, overflow = TextOverflow.Ellipsis)
            }

            ComparisonRow("Video", Icons.Outlined.Videocam, providers) { p ->
                Icon(
                    if (p.videoEnabled) Icons.Outlined.CheckCircle else Icons.Outlined.Cancel,
                    null,
                    tint = if (p.videoEnabled) Primary else OnSurfaceVariant.copy(alpha = 0.5f),
                    modifier = Modifier.size(16.dp)
                )
            }

            // Action row
            Row(modifier = Modifier.fillMaxWidth().padding(top = 16.dp)) {
                Box(modifier = Modifier.width(100.dp))
                providers.forEach { provider ->
                    Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                        BmsButton(
                            text = "Book",
                            onClick = { onBook(provider.userId) },
                            modifier = Modifier.height(36.dp).padding(horizontal = 4.dp)
                        )
                    }
                }
                repeat(3 - providers.size) {
                    Box(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
private fun ComparisonRow(
    label: String,
    icon: ImageVector,
    providers: List<ProviderProfile>,
    content: @Composable (ProviderProfile) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(modifier = Modifier.width(100.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, null, tint = OnSurfaceVariant, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(label, style = MaterialTheme.typography.labelSmall, color = OnSurfaceVariant)
        }
        
        providers.forEach { provider ->
            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                content(provider)
            }
        }
        
        repeat(3 - providers.size) {
            Box(modifier = Modifier.weight(1f))
        }
    }
    HorizontalDivider(color = OutlineVariant.copy(alpha = 0.1f))
}

@Composable
private fun EmptyComparison(onAdd: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            Icons.Outlined.Compare,
            contentDescription = null,
            tint = OnSurfaceVariant.copy(alpha = 0.3f),
            modifier = Modifier.size(64.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "Compare Providers",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = OnSurface
        )
        Text(
            "Select up to 3 providers from the browser to see them side-by-side.",
            style = MaterialTheme.typography.bodyMedium,
            color = OnSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))
        BmsButton(
            text = "Go to Browser",
            onClick = onAdd
        )
    }
}
