package com.bms.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bms.app.domain.model.Appointment
import com.bms.app.domain.model.UserProfile
import com.bms.app.ui.theme.*

@Composable
fun HistoryItem(
    appointment: Appointment,
    users: List<UserProfile>,
    currencySymbol: String = "$",
    modifier: Modifier = Modifier
) {
    val client = users.find { it.userId == appointment.userId }
    val clientName = client?.fullName ?: "Unknown Client"
    
    Surface(
        color = SurfaceContainerLowest,
        shape = RoundedCornerShape(16.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar
            BmsAvatar(
                name = clientName,
                size = AvatarSize.SMALL
            )
            
            Spacer(Modifier.width(12.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = clientName,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = OnSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = appointment.appointmentDate,
                        style = MaterialTheme.typography.labelSmall,
                        color = OnSurfaceVariant
                    )
                    Text(" • ", color = OnSurfaceVariant)
                    Text(
                        text = appointment.startTime.take(5),
                        style = MaterialTheme.typography.labelSmall,
                        color = OnSurfaceVariant
                    )
                }
            }
            
            Column(horizontalAlignment = Alignment.End) {
                val statusColor = when(appointment.status.lowercase()) {
                    "completed" -> Color(0xFF10B981)
                    "cancelled" -> Error
                    else -> OnSurfaceVariant
                }
                
                Text(
                    text = appointment.status.uppercase(),
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Black,
                    color = statusColor,
                    letterSpacing = 0.5.sp
                )
                
                if (appointment.status.lowercase() == "completed") {
                    val displayAmount = (appointment.paymentAmount?.toDouble())
                        ?: (if (appointment.isVideoConsultation == true) 50.0 else 30.0) // Fallback to assumed fees if null

                    Text(
                        text = "$currencySymbol${String.format("%.2f", displayAmount)}",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF10B981)
                    )
                }
            }
        }
    }
}
