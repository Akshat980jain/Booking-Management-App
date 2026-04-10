package com.bms.app.ui.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bms.app.ui.theme.*

/**
 * SupportScreen — resolves the admin user ID dynamically, then
 * transitions directly into the regular ChatScreen.
 *
 * If admin lookup fails (e.g. no admin in DB yet) it shows a clear
 * error state so the provider knows what happened.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SupportScreen(
    onBack: () -> Unit,
    onOpenChat: (adminUserId: String) -> Unit,
    viewModel: SupportViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    // As soon as admin ID is resolved, auto-navigate to ChatScreen
    LaunchedEffect(state) {
        if (state is SupportUiState.Ready) {
            onOpenChat((state as SupportUiState.Ready).adminUserId)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Platform Support", style = MaterialTheme.typography.titleMedium) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Outlined.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Surface)
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Background)
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            when (val s = state) {
                is SupportUiState.Loading -> {
                    // Animated connecting state
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(24.dp),
                        modifier = Modifier.padding(48.dp)
                    ) {
                        // Gradient pulsing icon
                        Box(
                            modifier = Modifier
                                .size(96.dp)
                                .clip(CircleShape)
                                .background(
                                    Brush.radialGradient(
                                        listOf(PrimaryContainer, PrimaryContainer.copy(alpha = 0.3f))
                                    )
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Outlined.SupportAgent,
                                contentDescription = null,
                                tint = Primary,
                                modifier = Modifier.size(48.dp)
                            )
                        }

                        Text(
                            "Connecting to support…",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                            color = OnSurface
                        )
                        Text(
                            "Finding the platform administrator for your conversation.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = OnSurfaceVariant,
                            textAlign = TextAlign.Center
                        )
                        CircularProgressIndicator(color = Primary, strokeWidth = 3.dp)
                    }
                }

                is SupportUiState.Ready -> {
                    // Will auto-navigate via LaunchedEffect above;
                    // show a brief "Opening chat…" as fallback
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator(color = Primary)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Opening chat…", color = OnSurfaceVariant)
                    }
                }

                is SupportUiState.Error -> {
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = SurfaceContainerLowest,
                        modifier = Modifier.padding(24.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(32.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Icon(
                                Icons.Outlined.ErrorOutline,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.error,
                                modifier = Modifier.size(48.dp)
                            )
                            Text(
                                "Support Unavailable",
                                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                                color = OnSurface
                            )
                            Text(
                                s.message,
                                style = MaterialTheme.typography.bodyMedium,
                                color = OnSurfaceVariant,
                                textAlign = TextAlign.Center
                            )
                            Button(
                                onClick = { viewModel.resolve() },
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Primary)
                            ) {
                                Icon(Icons.Outlined.Refresh, null)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Try Again")
                            }
                        }
                    }
                }
            }
        }
    }
}
