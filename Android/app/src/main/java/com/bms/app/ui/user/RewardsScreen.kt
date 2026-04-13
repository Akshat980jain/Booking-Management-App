package com.bms.app.ui.user

import androidx.compose.foundation.background
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bms.app.domain.model.LoyaltyTransaction
import com.bms.app.ui.components.*
import com.bms.app.ui.theme.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RewardsScreen(
    onBack: () -> Unit = {},
    viewModel: RewardsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            BmsTopBar(
                title = "Rewards & Loyalty",
                showBackButton = true,
                onNavigationClick = onBack,
                isLoading = uiState is RewardsUiState.Loading
            )
        },
        containerColor = Background
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            when (val state = uiState) {
                is RewardsUiState.Loading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = Primary)
                    }
                }
                is RewardsUiState.Error -> {
                    ErrorMessage(message = state.message, onRetry = { viewModel.loadRewards() })
                }
                is RewardsUiState.Success -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(24.dp)
                    ) {
                        // ── Points Overview ───────────────────
                        item {
                            PointsOverviewCard(
                                totalPoints = state.loyalty.totalPoints,
                                tier = state.loyalty.tier
                            )
                            Spacer(modifier = Modifier.height(32.dp))
                        }

                        // ── Tier Progress ─────────────────────
                        item {
                            TierProgressSection(currentPoints = state.loyalty.totalPoints, currentTier = state.loyalty.tier)
                            Spacer(modifier = Modifier.height(32.dp))
                        }

                        // ── Recent Activity ───────────────────
                        item {
                            Text(
                                "Recent Activity",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = OnSurface
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                        }

                        if (state.transactions.isEmpty()) {
                            item {
                                EmptyActivity()
                            }
                        } else {
                            items(state.transactions) { transaction ->
                                TransactionListItem(transaction)
                                Spacer(modifier = Modifier.height(12.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PointsOverviewCard(totalPoints: Int, tier: String) {
    Surface(
        color = Primary,
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier.fillMaxWidth(),
        shadowElevation = 8.dp
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "CURRENT BALANCE",
                style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 2.sp),
                color = OnPrimary.copy(alpha = 0.7f)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Outlined.MilitaryTech, null, tint = OnPrimary, modifier = Modifier.size(32.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = totalPoints.toString(),
                    style = MaterialTheme.typography.headlineLarge.copy(fontSize = 42.sp, fontWeight = FontWeight.ExtraBold),
                    color = OnPrimary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("pts", style = MaterialTheme.typography.titleMedium, color = OnPrimary.copy(alpha = 0.8f))
            }
            Spacer(modifier = Modifier.height(16.dp))
            Surface(
                color = Color.White.copy(alpha = 0.2f),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "$tier Member",
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
                    style = MaterialTheme.typography.labelLarge,
                    color = OnPrimary,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun TierProgressSection(currentPoints: Int, currentTier: String) {
    val nextTier = when(currentTier) {
        "Bronze" -> "Silver"
        "Silver" -> "Gold"
        "Gold" -> "Platinum"
        else -> "Platinum"
    }
    val targetPoints = when(currentTier) {
        "Bronze" -> 500
        "Silver" -> 1500
        "Gold" -> 5000
        else -> 5000
    }
    val progress = (currentPoints.toFloat() / targetPoints).coerceIn(0f, 1f)

    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("$currentTier Tier", style = MaterialTheme.typography.bodySmall, color = OnSurfaceVariant)
            Text("Next: $nextTier", style = MaterialTheme.typography.bodySmall, color = Primary, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(8.dp))
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier.fillMaxWidth().height(8.dp).clip(CircleShape),
            color = Primary,
            trackColor = SurfaceContainerLow
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "${targetPoints - currentPoints} pts to $nextTier",
            style = MaterialTheme.typography.labelSmall,
            color = OnSurfaceVariant,
            textAlign = TextAlign.End,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun TransactionListItem(transaction: LoyaltyTransaction) {
    val isEarned = transaction.type == "EARNED"
    Surface(
        color = SurfaceContainerLowest,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(if (isEarned) StatusActive.copy(alpha = 0.1f) else Primary.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (isEarned) Icons.Outlined.AddCircleOutline else Icons.Outlined.RemoveCircleOutline,
                    contentDescription = null,
                    tint = if (isEarned) StatusActive else Primary,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    transaction.description,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = OnSurface
                )
                Text(
                    text = try {
                        val dt = LocalDateTime.parse(transaction.createdAt.take(19))
                        dt.format(DateTimeFormatter.ofPattern("d MMM, hh:mm a"))
                    } catch (_: Exception) { transaction.createdAt },
                    style = MaterialTheme.typography.bodySmall,
                    color = OnSurfaceVariant
                )
            }
            Text(
                text = "${if (isEarned) "+" else ""}${transaction.points}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.ExtraBold,
                color = if (isEarned) StatusActive else OnSurface
            )
        }
    }
}

@Composable
private fun EmptyActivity() {
    Column(
        modifier = Modifier.fillMaxWidth().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(Icons.Outlined.History, null, tint = OnSurfaceVariant.copy(alpha = 0.3f), modifier = Modifier.size(48.dp))
        Spacer(modifier = Modifier.height(16.dp))
        Text("No Activity Yet", style = MaterialTheme.typography.titleSmall, color = OnSurfaceVariant)
    }
}
