package com.bms.app.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bms.app.ui.theme.SurfaceContainerLow

// ── Shimmer Brush ─────────────────────────────────────────────────────────────

@Composable
fun shimmerBrush(): Brush {
    val shimmerColors = listOf(
        SurfaceContainerLow,
        SurfaceContainerLow.copy(alpha = 0.4f),
        SurfaceContainerLow,
    )
    val transition = rememberInfiniteTransition(label = "shimmer")
    val translateAnim by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1200f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmer_translate"
    )
    return Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(translateAnim - 400f, 0f),
        end = Offset(translateAnim, 0f)
    )
}

// ── Primitive Skeleton Blocks ─────────────────────────────────────────────────

@Composable
fun SkeletonBox(
    modifier: Modifier = Modifier,
    height: Dp = 16.dp,
    width: Dp? = null,
    shape: androidx.compose.ui.graphics.Shape = RoundedCornerShape(8.dp)
) {
    val brush = shimmerBrush()
    val baseModifier = modifier
        .then(if (width != null) Modifier.width(width) else Modifier.fillMaxWidth())
        .height(height)
        .clip(shape)
        .background(brush)
    Box(modifier = baseModifier)
}

@Composable
fun SkeletonCircle(size: Dp = 44.dp, modifier: Modifier = Modifier) {
    val brush = shimmerBrush()
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(brush)
    )
}

// ── Stat Card Skeleton ────────────────────────────────────────────────────────

@Composable
fun SkeletonStatCard(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .height(110.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(SurfaceContainerLow)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            SkeletonCircle(size = 32.dp)
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                SkeletonBox(height = 10.dp, width = 70.dp)
                SkeletonBox(height = 24.dp, width = 40.dp)
            }
        }
    }
}

// ── User Row Skeleton ─────────────────────────────────────────────────────────

@Composable
fun SkeletonUserRow(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(SurfaceContainerLow)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SkeletonCircle(size = 44.dp)
            Spacer(modifier = Modifier.width(12.dp))
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SkeletonBox(height = 14.dp, width = 130.dp)
                SkeletonBox(height = 12.dp, width = 180.dp)
            }
            Spacer(modifier = Modifier.width(8.dp))
            SkeletonCircle(size = 20.dp)
        }
    }
}

// ── Appointment Card Skeleton ────────────────────────────────────────────────

@Composable
fun SkeletonAppointmentCard(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(SurfaceContainerLow)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                SkeletonCircle(size = 48.dp)
                Spacer(modifier = Modifier.width(16.dp))
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    SkeletonBox(height = 16.dp, width = 140.dp)
                    SkeletonBox(height = 12.dp, width = 80.dp)
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                SkeletonBox(height = 24.dp, width = 60.dp, shape = RoundedCornerShape(12.dp))
                SkeletonBox(height = 24.dp, width = 80.dp)
            }
        }
    }
}

// ── Form & Settings Skeletons ───────────────────────────────────────────────

@Composable
fun SkeletonForm() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SkeletonBox(height = 24.dp, width = 200.dp)
        SkeletonBox(height = 12.dp, width = 260.dp)
        Spacer(modifier = Modifier.height(8.dp))
        repeat(4) {
            SkeletonBox(height = 56.dp, shape = RoundedCornerShape(12.dp))
        }
        Spacer(modifier = Modifier.height(16.dp))
        SkeletonBox(height = 48.dp, shape = RoundedCornerShape(24.dp))
    }
}

@Composable
fun SkeletonSettings() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        repeat(6) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SkeletonCircle(size = 40.dp)
                Spacer(modifier = Modifier.width(16.dp))
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    SkeletonBox(height = 16.dp, width = 140.dp)
                    SkeletonBox(height = 12.dp, width = 220.dp)
                }
            }
            HorizontalDivider(color = SurfaceContainerLow)
        }
    }
}

// ── Provider Dashboard Skeleton ─────────────────────────────────────────────

@Composable
fun ProviderDashboardSkeleton() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        SkeletonBox(height = 12.dp, width = 80.dp)
        Spacer(modifier = Modifier.height(8.dp))
        SkeletonBox(height = 40.dp, width = 200.dp)
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            SkeletonStatCard(modifier = Modifier.weight(1f))
            SkeletonStatCard(modifier = Modifier.weight(1f))
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            SkeletonStatCard(modifier = Modifier.weight(1f))
            SkeletonStatCard(modifier = Modifier.weight(1f))
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        SkeletonBox(height = 24.dp, width = 180.dp)
        Spacer(modifier = Modifier.height(16.dp))
        repeat(2) {
            SkeletonAppointmentCard()
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

// ── Provider Schedule Skeleton ──────────────────────────────────────────────

@Composable
fun ProviderScheduleSkeleton() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        // Tab row shimmer
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            SkeletonBox(height = 4.dp, width = 100.dp)
            SkeletonBox(height = 4.dp, width = 100.dp)
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Large Pulse Header shimmer
        SkeletonBox(height = 160.dp, shape = RoundedCornerShape(24.dp))
        
        Spacer(modifier = Modifier.height(24.dp))
        SkeletonBox(height = 20.dp, width = 140.dp)
        Spacer(modifier = Modifier.height(16.dp))
        
        repeat(3) {
            SkeletonAppointmentCard()
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

// ── Admin Dashboard Skeleton ───────────────────────────────────────────────────

@Composable
fun AdminDashboardSkeleton() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        // Header text lines
        SkeletonBox(height = 34.dp, width = 220.dp)
        Spacer(modifier = Modifier.height(8.dp))
        SkeletonBox(height = 14.dp, width = 260.dp)

        Spacer(modifier = Modifier.height(24.dp))

        // Stat cards row 1
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SkeletonStatCard(modifier = Modifier.weight(1f))
            SkeletonStatCard(modifier = Modifier.weight(1f))
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Stat cards row 2
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SkeletonStatCard(modifier = Modifier.weight(1f))
            SkeletonStatCard(modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(32.dp))

        // "All Users" section header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            SkeletonBox(height = 22.dp, width = 140.dp)
            SkeletonBox(height = 16.dp, width = 70.dp)
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Search bar skeleton
        SkeletonBox(height = 52.dp, shape = RoundedCornerShape(28.dp))

        Spacer(modifier = Modifier.height(16.dp))

        // User rows
        repeat(4) {
            SkeletonUserRow()
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

// ── Providers Screen Skeleton ────────────────────────────────────────────────

@Composable
fun ProvidersSkeleton() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        // Title and subtitle
        SkeletonBox(height = 34.dp, width = 160.dp)
        Spacer(modifier = Modifier.height(8.dp))
        SkeletonBox(height = 14.dp, width = 220.dp)

        Spacer(modifier = Modifier.height(20.dp))

        // Search bar
        SkeletonBox(height = 52.dp, shape = RoundedCornerShape(28.dp))

        Spacer(modifier = Modifier.height(24.dp))

        // List header
        SkeletonBox(height = 18.dp, width = 120.dp)

        Spacer(modifier = Modifier.height(16.dp))

        // Provider rows
        repeat(5) {
            SkeletonUserRow()
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

// ── Schedule Screen Skeleton ──────────────────────────────────────────────────

@Composable
fun ScheduleSkeleton() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        // Title and sub
        SkeletonBox(height = 34.dp, width = 200.dp)
        Spacer(modifier = Modifier.height(8.dp))
        SkeletonBox(height = 14.dp, width = 240.dp)

        Spacer(modifier = Modifier.height(20.dp))

        // Filter chips row
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            repeat(4) {
                SkeletonBox(height = 32.dp, width = 70.dp, shape = RoundedCornerShape(16.dp))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Large summary card
        SkeletonBox(height = 92.dp, shape = RoundedCornerShape(16.dp))
        
        Spacer(modifier = Modifier.height(12.dp))

        // Info banner
        SkeletonBox(height = 56.dp, shape = RoundedCornerShape(12.dp))
    }
}
