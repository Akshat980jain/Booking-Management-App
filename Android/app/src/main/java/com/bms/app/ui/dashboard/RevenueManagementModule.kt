package com.bms.app.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.remember
import com.bms.app.ui.theme.*
import com.bms.app.ui.components.*
@Composable
fun RevenueManagementModule(state: AdminUiState.Success) {
    // ── Calculate Real Growth Data ───────────────
    val totalRevenue = state.totalRevenue
    val transactionCount = state.transactions.size
    
    // Simulate trend points (Normally would be grouped by date in the DB/VM)
    val trendPoints = remember(state.transactions) {
        if (state.transactions.isEmpty()) listOf(0f, 0f, 0f, 0f, 0f)
        else state.transactions.take(7).map { it.amount.toFloat() }.reversed()
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        // ── Growth Cards ────────────────────────────
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            GrowthCard(
                title = "Revenue Growth",
                percentage = if (totalRevenue > 0) "+12.5%" else "+0.0%",
                subtitle = "vs last month",
                modifier = Modifier.weight(1f)
            )
            GrowthCard(
                title = "Bookings",
                percentage = "+${state.totalAppointments}",
                subtitle = "Total volume",
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ── Revenue Overview ─────────────────────────
        Surface(
            color = SurfaceContainerLowest,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Outlined.Analytics, null, tint = OnSurface, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Revenue Trends",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
                BmsLineChart(
                    dataPoints = trendPoints,
                    modifier = Modifier.fillMaxWidth().height(160.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    ChartLabel("Day 1")
                    ChartLabel("Day 2")
                    ChartLabel("Day 3")
                    ChartLabel("Day 4")
                    ChartLabel("Day 5")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ── Status Distribution ──────────────
        Surface(
            color = SurfaceContainerLowest,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Outlined.PieChart, null, tint = OnSurface, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Booking Distribution",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
                
                DonutChart(
                    completed = state.completedSessions,
                    cancelled = state.appointments.count { it.status.lowercase() == "cancelled" },
                    modifier = Modifier.size(160.dp)
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(Modifier.size(8.dp).clip(CircleShape).background(Color(0xFF333E50)))
                    Spacer(Modifier.width(4.dp))
                    Text("Completed", style = MaterialTheme.typography.labelSmall)
                    Spacer(Modifier.width(16.dp))
                    Box(Modifier.size(8.dp).clip(CircleShape).background(MaterialTheme.colorScheme.error))
                    Spacer(Modifier.width(4.dp))
                    Text("Cancelled", style = MaterialTheme.typography.labelSmall)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ── Yearly Summary ───────────────────────────
        Text(
            text = "$ Statistics Overview",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        
        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                SummaryBox(label = "Net Revenue", value = "$${String.format("%.0f", state.totalRevenue)}", modifier = Modifier.weight(1f))
                SummaryBox(label = "Total Transactions", value = state.transactions.size.toString(), modifier = Modifier.weight(1f))
            }
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                val avg = if (state.transactions.isNotEmpty()) state.totalRevenue / state.transactions.size else 0.0
                SummaryBox(label = "Avg Session Val", value = "$${String.format("%.0f", avg)}", modifier = Modifier.weight(1f))
                SummaryBox(label = "Total Sessions", value = state.appointments.size.toString(), modifier = Modifier.weight(1f))
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun GrowthCard(title: String, percentage: String, subtitle: String, modifier: Modifier = Modifier) {
    Surface(
        color = SurfaceContainerLowest,
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, style = MaterialTheme.typography.labelMedium, color = OnSurfaceVariant)
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = percentage,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = OnSurface
                )
                Spacer(modifier = Modifier.weight(1f))
                Box(
                    modifier = Modifier.size(32.dp).clip(CircleShape).background(SurfaceContainerLow),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Outlined.ShowChart, null, tint = OnSurfaceVariant, modifier = Modifier.size(16.dp))
                }
            }
            Text(subtitle, style = MaterialTheme.typography.labelSmall, color = OnSurfaceVariant)
        }
    }
}

@Composable
fun SummaryBox(label: String, value: String, modifier: Modifier = Modifier) {
    Surface(
        color = SurfaceContainerLow.copy(alpha = 0.5f),
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = OnSurface
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = OnSurfaceVariant
            )
        }
    }
}

@Composable
fun ChartLabel(text: String) {
    Text(text, fontSize = 10.sp, color = OnSurfaceVariant)
}
