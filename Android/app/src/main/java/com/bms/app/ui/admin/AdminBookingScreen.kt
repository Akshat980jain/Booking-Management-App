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
fun AdminBookingScreen(
    onNavigate: (String) -> Unit = {},
    onAvatarClick: () -> Unit = {},
    viewModel: AdminViewModel = hiltViewModel()
) {
    var selectedNav by remember { mutableStateOf("admin_booking") }
    var searchQuery by remember { mutableStateOf("") }

    val uiState by viewModel.uiState.collectAsState()
    val isLoading = uiState is AdminUiState.Loading

    val adminInitials = if (uiState is AdminUiState.Success) {
        (uiState as AdminUiState.Success).adminInitials
    } else ""

    Scaffold(
        topBar = {
            BmsTopBar(
                title = "Quick Book",
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
                text = "Book Appointment",
                style = MaterialTheme.typography.headlineLarge,
                color = OnSurface
            )
            Text(
                text = "Select a professional to schedule a session",
                style = MaterialTheme.typography.bodyMedium,
                color = OnSurfaceVariant
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Search bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = {
                    Text("Search professionals...", color = Outline.copy(alpha = 0.5f))
                },
                leadingIcon = {
                    Icon(
                        Icons.Outlined.Search,
                        null,
                        tint = Outline,
                        modifier = Modifier.size(20.dp)
                    )
                },
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = SurfaceContainerLowest,
                    focusedContainerColor = SurfaceContainerLowest,
                    unfocusedBorderColor = GhostBorder,
                    focusedBorderColor = Primary
                ),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            when (val state = uiState) {
                is AdminUiState.Loading -> {
                    ProvidersSkeleton()
                }
                is AdminUiState.Error -> {
                    Text(state.message, color = MaterialTheme.colorScheme.error)
                }
                is AdminUiState.Success -> {
                    val providers = state.users.filter { 
                        it.role?.uppercase() == "PROVIDER" &&
                        (it.fullName.contains(searchQuery, ignoreCase = true) || 
                         it.email.contains(searchQuery, ignoreCase = true))
                    }

                    if (providers.isEmpty()) {
                        EmptyState()
                    } else {
                        providers.forEach { provider ->
                            BookingProviderCard(
                                provider = provider,
                                onClick = {
                                    // In a real app, this would navigate to a specific provider's booking page
                                    // For now, let's navigate to their profile where they can book or show a toast
                                    onNavigate("book_service/${provider.userId}")
                                }
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
private fun BookingProviderCard(provider: UserProfile, onClick: () -> Unit) {
    Surface(
        color = SurfaceContainerLowest,
        shape = RoundedCornerShape(16.dp),
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
                    .size(52.dp)
                    .clip(CircleShape)
                    .background(PrimaryContainer.copy(alpha = 0.4f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = provider.fullName.take(1).uppercase(),
                    style = MaterialTheme.typography.titleLarge,
                    color = Primary
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = provider.fullName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = OnSurface
                )
                Text(
                    text = "Doctor | Consultant", // Example specialty
                    style = MaterialTheme.typography.bodySmall,
                    color = OnSurfaceVariant
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Outlined.Star, null, tint = Primary, modifier = Modifier.size(14.dp))
                    Text(
                        text = " 4.8 (120+ reviews)",
                        style = MaterialTheme.typography.labelSmall,
                        color = OnSurfaceVariant
                    )
                }
            }
            
            Button(
                onClick = onClick,
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text("Book", style = MaterialTheme.typography.labelLarge)
            }
        }
    }
}

@Composable
private fun EmptyState() {
    Column(
        modifier = Modifier.fillMaxWidth().padding(vertical = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            Icons.Outlined.SearchOff,
            null,
            modifier = Modifier.size(48.dp),
            tint = OnSurfaceVariant.copy(alpha = 0.5f)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text("No professionals found", color = OnSurfaceVariant)
    }
}
