package com.bms.app.ui.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bms.app.domain.model.UserProfile
import com.bms.app.ui.components.BmsTopBar
import com.bms.app.ui.components.RoleBadge
import com.bms.app.ui.components.StatusBadge
import com.bms.app.ui.components.*
import com.bms.app.ui.theme.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailScreen(
    userId: String,
    onBack: () -> Unit,
    onNavigate: (String) -> Unit,
    viewModel: UserDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(userId) {
        viewModel.loadUserDetail(userId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("User Profile", style = MaterialTheme.typography.titleLarge) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Outlined.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Surface,
                    titleContentColor = OnSurface
                )
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
                is UserDetailUiState.Loading -> {
                    SkeletonForm()
                }
                is UserDetailUiState.Error -> {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(state.message, color = MaterialTheme.colorScheme.error)
                        Button(onClick = { viewModel.loadUserDetail(userId) }) {
                            Text("Retry")
                        }
                    }
                }
                is UserDetailUiState.Success -> {
                    UserDetailContent(
                        profile = state.profile,
                        onSendMessage = { onNavigate("chat/${state.profile.userId}") },
                        onDeactivate = { viewModel.deactivateUser(userId) }
                    )
                }
            }
        }
    }
}

@Composable
private fun UserDetailContent(
    profile: UserProfile,
    onSendMessage: () -> Unit,
    onDeactivate: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Avatar Circle
        val initials = profile.fullName
            .split(" ")
            .take(2)
            .mapNotNull { it.firstOrNull()?.toString() }
            .joinToString("")
            .uppercase()

        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(SurfaceContainerLow),
            contentAlignment = Alignment.Center
        ) {
            Text(
                initials,
                style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                color = Primary
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            profile.fullName,
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            color = OnSurface
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            RoleBadge(role = profile.role ?: "USER")
            if (profile.status == "inactive") {
                StatusBadge("Deactivated", MaterialTheme.colorScheme.errorContainer, MaterialTheme.colorScheme.error)
            } else {
                StatusBadge("Active", StatusActive, OnStatusActive)
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Info Sections
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    "Contact Information",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = OnSurface
                )
                Spacer(modifier = Modifier.height(16.dp))
                
                DetailRow("Email Address", profile.email, Icons.Outlined.MailOutline)
                Spacer(modifier = Modifier.height(12.dp))
                DetailRow("Phone Number", profile.phone ?: "Not provided", Icons.Outlined.Phone)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    "Account Details",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = OnSurface
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Member Since — formatted nicely
                val formattedDate = try {
                    val raw = profile.createdAt.take(10) // "2026-02-28"
                    val date = LocalDate.parse(raw)
                    date.format(DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.ENGLISH))
                } catch (e: Exception) {
                    profile.createdAt.take(10)
                }
                DetailRow("Member Since", formattedDate, Icons.Outlined.CalendarToday)

                Spacer(modifier = Modifier.height(16.dp))

                // Account ID — styled chip with copy button
                val clipboardManager = LocalClipboardManager.current
                var copied by remember { mutableStateOf(false) }

                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            Icons.Outlined.Badge,
                            contentDescription = null,
                            tint = Primary,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                "Account ID",
                                style = MaterialTheme.typography.labelSmall,
                                color = OnSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            // Styled monospace ID chip
                            Row(
                                modifier = Modifier
                                    .background(
                                        color = PrimaryContainer.copy(alpha = 0.35f),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .border(
                                        width = 1.dp,
                                        color = Primary.copy(alpha = 0.2f),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .padding(horizontal = 10.dp, vertical = 5.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                // Dot indicator
                                Box(
                                    modifier = Modifier
                                        .size(6.dp)
                                        .clip(CircleShape)
                                        .background(Primary)
                                )
                                Text(
                                    text = profile.userId
                                        .replace("-", "")
                                        .uppercase()
                                        .chunked(4)
                                        .take(4)
                                        .joinToString("-"),
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        fontFamily = FontFamily.Monospace,
                                        fontWeight = FontWeight.Medium,
                                        letterSpacing = 0.5.sp
                                    ),
                                    color = Primary
                                )
                            }
                        }
                        // Copy button
                        IconButton(
                            onClick = {
                                clipboardManager.setText(AnnotatedString(profile.userId))
                                copied = true
                            },
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                if (copied) Icons.Outlined.CheckCircle else Icons.Outlined.ContentCopy,
                                contentDescription = if (copied) "Copied" else "Copy ID",
                                tint = if (copied) OnStatusActive else OnSurfaceVariant,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Actions
        Button(
            onClick = onSendMessage,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Primary)
        ) {
            Icon(Icons.Outlined.ChatBubbleOutline, null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Send Message")
        }

        Spacer(modifier = Modifier.height(12.dp))

        if (profile.status != "inactive") {
            OutlinedButton(
                onClick = onDeactivate,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error),
                border = ButtonDefaults.outlinedButtonBorder.copy(brush = androidx.compose.ui.graphics.SolidColor(MaterialTheme.colorScheme.error))
            ) {
                Icon(Icons.Outlined.Block, null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Deactivate User")
            }
        }
    }
}

@Composable
private fun DetailRow(label: String, value: String, icon: ImageVector) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            icon,
            contentDescription = null,
            tint = Primary,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(label, style = MaterialTheme.typography.labelSmall, color = OnSurfaceVariant)
            Text(value, style = MaterialTheme.typography.bodyMedium, color = OnSurface)
        }
    }
}
