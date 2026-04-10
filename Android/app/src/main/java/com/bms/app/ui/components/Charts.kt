package com.bms.app.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bms.app.ui.theme.Primary
import com.bms.app.ui.theme.Secondary
import com.bms.app.ui.theme.OnSurface
import com.bms.app.ui.theme.OnSurfaceVariant

@Composable
fun DonutChart(
    completed: Int,
    cancelled: Int,
    modifier: Modifier = Modifier
) {
    val total = completed + cancelled
    if (total == 0) {
        Box(modifier = modifier, contentAlignment = Alignment.Center) {
            Text("No data", color = OnSurfaceVariant)
        }
        return
    }
    
    val completedAngle = (completed.toFloat() / total) * 360f
    val cancelledAngle = (cancelled.toFloat() / total) * 360f
    
    val currentThemePrimary = Primary
    val currentThemeError = MaterialTheme.colorScheme.error
    
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(140.dp)) {
            val strokeWidth = 20.dp.toPx()
            
            // Draw completed arc (Dark primary/blueish)
            drawArc(
                color = Color(0xFF333E50), // Matches web dashboard screenshot "dark color"
                startAngle = -90f,
                sweepAngle = completedAngle,
                useCenter = false,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Butt),
                size = Size(size.width, size.height)
            )
            
            // Draw cancelled arc (Red)
            drawArc(
                color = currentThemeError,
                startAngle = -90f + completedAngle,
                sweepAngle = cancelledAngle,
                useCenter = false,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Butt),
                size = Size(size.width, size.height)
            )
        }
    }
}

@Composable
fun BmsLineChart(
    dataPoints: List<Float>,
    modifier: Modifier = Modifier,
    lineColor: Color = Primary
) {
    if (dataPoints.isEmpty()) {
        Box(modifier = modifier, contentAlignment = Alignment.Center) {
            Text("No trend data", color = OnSurfaceVariant, style = MaterialTheme.typography.labelSmall)
        }
        return
    }

    Canvas(modifier = modifier) {
        val width = size.width
        val height = size.height
        val spacing = width / (if (dataPoints.size > 1) dataPoints.size - 1 else 1)
        val maxVal = (dataPoints.maxOrNull() ?: 1f).coerceAtLeast(1f)
        
        val points = dataPoints.mapIndexed { index, value ->
            Offset(
                x = index * spacing,
                y = height - (value / maxVal * height)
            )
        }

        // Draw grid lines
        val gridLines = 4
        for (i in 0..gridLines) {
            val y = height * (i.toFloat() / gridLines)
            drawLine(
                color = Color.LightGray.copy(alpha = 0.3f),
                start = Offset(0f, y),
                end = Offset(width, y),
                strokeWidth = 1.dp.toPx()
            )
        }

        // Draw the line
        for (i in 0 until points.size - 1) {
            drawLine(
                color = lineColor,
                start = points[i],
                end = points[i+1],
                strokeWidth = 3.dp.toPx(),
                cap = StrokeCap.Round
            )
        }
        
        // Draw points
        points.forEach { point ->
            drawCircle(
                color = lineColor,
                center = point,
                radius = 4.dp.toPx()
            )
            drawCircle(
                color = Color.White,
                center = point,
                radius = 2.dp.toPx()
            )
        }
    }
}
