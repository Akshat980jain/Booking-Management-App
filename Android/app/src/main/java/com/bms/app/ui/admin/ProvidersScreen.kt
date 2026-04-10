package com.bms.app.ui.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bms.app.domain.model.UserProfile
import com.bms.app.ui.components.*
import com.bms.app.ui.dashboard.AdminUiState
import com.bms.app.ui.dashboard.AdminViewModel
import com.bms.app.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProvidersScreen(
    onNavigate: (String) -> Unit = {},
    onAvatarClick: () -> Unit = {},
    viewModel: AdminViewModel = hiltViewModel()
) {
    var selectedNav by remember { mutableStateOf("providers") }
    var searchQuery by remember { mutableStateOf("") }

    val uiState by viewModel.uiState.collectAsState()
    val isLoading = uiState is AdminUiState.Loading

    val adminInitials = if (uiState is AdminUiState.Success) {
        (uiState as AdminUiState.Success).adminInitials
    } else ""

    Scaffold(
        topBar = {
            BmsTopBar(
                title = "BookEase24X7",
                avatarInitials = adminInitials,
                onAvatarClick = onAvatarClick,
                isLoading = isLoading
            )
        },
        bottomBar = {
            BmsBottomNavBar(
                items = AdminNavItems,
                selectedRoute = selectedNav,
                onItemSelected = { route ->
                    selectedNav = route
                    onNavigate(route)
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Background)
                .padding(padding)
        ) {
            when (val state = uiState) {
                is AdminUiState.Loading -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    ) {
                        ProvidersSkeleton()
                    }
                }
                is AdminUiState.Error -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(state.message, color = MaterialTheme.colorScheme.error)
                        Spacer(modifier = Modifier.height(12.dp))
                        Button(onClick = { viewModel.loadAdminDashboard() }) {
                            Text("Retry")
                        }
                    }
                }
                is AdminUiState.Success -> {
                    val providers = state.users.filter {
                        it.role?.uppercase() == "PROVIDER"
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(horizontal = 24.dp)
                    ) {
                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Providers",
                            style = MaterialTheme.typography.headlineLarge,
                            color = OnSurface
                        )
                        Text(
                            text = "All registered service providers",
                            style = MaterialTheme.typography.bodyMedium,
                            color = OnSurfaceVariant
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        // Search bar
                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            placeholder = {
                                Text("Search providers...", color = Outline.copy(alpha = 0.5f))
                            },
                            leadingIcon = {
                                Icon(
                                    Icons.Outlined.Search,
                                    null,
                                    tint = Outline,
                                    modifier = Modifier.size(20.dp)
                                )
                            },
                            shape = InputShape,
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedContainerColor = SurfaceContainerLowest,
                                focusedContainerColor = SurfaceContainerLowest,
                                unfocusedBorderColor = GhostBorder,
                                focusedBorderColor = Primary,
                                cursorColor = Primary
                            ),
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        val filtered = providers.filter {
                            it.fullName.contains(searchQuery, ignoreCase = true) ||
                            it.email.contains(searchQuery, ignoreCase = true)
                        }

                        if (filtered.isEmpty()) {
                            Box(
                                modifier = Modifier.fillMaxWidth().height(150.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Icon(
                                        Icons.Outlined.Groups,
                                        null,
                                        tint = OnSurfaceVariant.copy(alpha = 0.4f),
                                        modifier = Modifier.size(48.dp)
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        "No providers found",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = OnSurfaceVariant
                                    )
                                }
                            }
                        } else {
                            Text(
                                text = "Providers (${filtered.size})",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = OnSurface
                            )
                            Spacer(modifier = Modifier.height(12.dp))

                            filtered.forEach { provider ->
                                ProviderListItem(
                                    provider = provider,
                                    onClick = { onNavigate("user_detail/${provider.userId}") }
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }

                        Spacer(modifier = Modifier.height(80.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun ProviderListItem(
    provider: UserProfile,
    onClick: () -> Unit
) {
    val initials = provider.fullName
        .split(" ")
        .take(2)
        .mapNotNull { it.firstOrNull()?.toString() }
        .joinToString("")
        .uppercase()
        .ifBlank { "P" }

    Surface(
        color = SurfaceContainerLowest,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(PrimaryContainer.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = initials,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = Primary
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = provider.fullName,
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                    color = OnSurface
                )
                Text(
                    text = provider.email,
                    style = MaterialTheme.typography.bodySmall,
                    color = OnSurfaceVariant
                )
            }

            if (provider.status == "inactive") {
                StatusBadge("Inactive", MaterialTheme.colorScheme.errorContainer, MaterialTheme.colorScheme.error)
            } else {
                Icon(
                    Icons.Outlined.ChevronRight,
                    contentDescription = null,
                    tint = OnSurfaceVariant,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}
