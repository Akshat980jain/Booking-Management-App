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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.bms.app.domain.model.Appointment
import com.bms.app.domain.model.ProviderProfile
import com.bms.app.domain.util.NameUtils
import com.bms.app.ui.components.StatusBadge
import com.bms.app.ui.theme.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun AppointmentDetailDialog(
    appointment: Appointment,
    provider: ProviderProfile?,
    providerName: String,
    onDismiss: () -> Unit,
    onReschedule: () -> Unit = {}
) {
    val isVideo = appointment.isVideoConsultation == true
    val statusColor = when (appointment.status) {
        "confirmed", "approved" -> StatusActive
        "pending"   -> StatusPending
        "completed" -> StatusInfo
        "cancelled", "rejected" -> MaterialTheme.colorScheme.error
        else        -> OnSurfaceVariant
    }
    val statusText = appointment.status.replaceFirstChar { it.uppercase() }

    val canReschedule = appointment.status != "cancelled" && appointment.status != "rejected" && appointment.status != "completed"

    // Try to format date nicely
    val formattedDate = try {
        LocalDate.parse(appointment.appointmentDate).format(DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy"))
    } catch (e: Exception) {
        appointment.appointmentDate
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .wrapContentHeight(),
            shape = RoundedCornerShape(24.dp),
            color = SurfaceContainerLowest
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                Text(
                    text = "Appointment Details",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = OnSurface
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Full details for your appointment",
                    style = MaterialTheme.typography.bodyMedium,
                    color = OnSurfaceVariant
                )
                Spacer(modifier = Modifier.height(20.dp))

                // Provider Box
                val initials = NameUtils.getInitials(providerName)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(SurfaceContainerLow, RoundedCornerShape(12.dp))
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(SecondaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = initials,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = OnSecondaryContainer
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = providerName,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = OnSurface,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        if (!provider?.profession.isNullOrBlank()) {
                            Text(
                                text = provider?.profession ?: "",
                                style = MaterialTheme.typography.bodyMedium,
                                color = OnSurfaceVariant
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Grid of details
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    DetailRow(
                        icon = Icons.Outlined.CalendarMonth,
                        text = formattedDate
                    )
                    DetailRow(
                        icon = Icons.Outlined.AccessTime,
                        text = "${appointment.startTime.take(5)} - ${appointment.endTime.take(5)}"
                    )
                    
                    if (isVideo) {
                        DetailRow(
                            icon = Icons.Outlined.Videocam,
                            text = "Video Consultation",
                            iconTint = Primary
                        )
                    } else {
                        DetailRow(
                            icon = Icons.Outlined.LocationOn,
                            text = provider?.location?.takeIf { it.isNotBlank() } ?: "In-Person Consultation"
                        )
                    }

                    // Fee and Payment
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Outlined.CreditCard,
                            contentDescription = null,
                            tint = OnSurfaceVariant,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        
                        val fee = if (isVideo) {
                            provider?.videoConsultationFee ?: provider?.consultationFee ?: 0.0
                        } else {
                            provider?.consultationFee ?: 0.0
                        }
                        
                        Text(
                            text = "Fee: ₹$fee",
                            style = MaterialTheme.typography.bodyMedium,
                            color = OnSurface
                        )
                        
                        Spacer(modifier = Modifier.weight(1f))
                        
                        when (appointment.paymentStatus) {
                            "paid" -> {
                                StatusBadge(text = "Paid", backgroundColor = OnStatusActive.copy(alpha=0.15f), textColor = OnStatusActive)
                            }
                            "waived" -> {
                                StatusBadge(text = "Waived", backgroundColor = PrimaryContainer, textColor = OnPrimaryContainer)
                            }
                            else -> {
                                StatusBadge(text = "Unpaid", backgroundColor = SurfaceContainerHigh, textColor = OnSurfaceVariant)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
                HorizontalDivider(color = OutlineVariant)
                Spacer(modifier = Modifier.height(16.dp))

                // Status
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Status", style = MaterialTheme.typography.bodyMedium, color = OnSurfaceVariant)
                    StatusBadge(text = statusText, backgroundColor = statusColor.copy(alpha=0.15f), textColor = statusColor)
                }

                // Notes
                if (!appointment.notes.isNullOrBlank()) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Notes", style = MaterialTheme.typography.bodyMedium, color = OnSurfaceVariant)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = appointment.notes,
                        style = MaterialTheme.typography.bodyMedium,
                        color = OnSurface
                    )
                }
                
                // Cancellation Reason
                if (!appointment.cancellationReason.isNullOrBlank()) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Reason", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.error)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = appointment.cancellationReason,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Buttons
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    if (canReschedule) {
                        Button(
                            onClick = {
                                onDismiss()
                                onReschedule()
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Primary, contentColor = OnPrimary)
                        ) {
                            Icon(Icons.Outlined.EditCalendar, null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Reschedule Appointment", modifier = Modifier.padding(vertical = 4.dp))
                        }
                    }
                    
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Close", modifier = Modifier.padding(vertical = 4.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun DetailRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String,
    iconTint: androidx.compose.ui.graphics.Color = OnSurfaceVariant
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconTint,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = OnSurface
        )
    }
}
