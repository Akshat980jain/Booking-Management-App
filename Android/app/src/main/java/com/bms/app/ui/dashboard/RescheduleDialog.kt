package com.bms.app.ui.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bms.app.domain.model.Appointment
import com.bms.app.ui.theme.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RescheduleDialog(
    appointment: Appointment,
    onDismiss: () -> Unit,
    onConfirm: (String, String, String, String) -> Unit
) {
    var selectedDate by remember { mutableStateOf(appointment.appointmentDate) }
    var selectedStartTime by remember { mutableStateOf(appointment.startTime) }
    var selectedEndTime by remember { mutableStateOf(appointment.endTime) }
    var reason by remember { mutableStateOf("") }

    // State for material3 pickers (simplifying for now with simple buttons/text for the selection flow)
    // In a real app, you'd show DatePickerDialog and TimePickerDialog
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                "Reschedule Appointment",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = OnSurface
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Text(
                    "Please select a new date and time for your appointment.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = OnSurfaceVariant
                )

                // Date Picker Interaction (Simplified)
                OutlinedCard(
                    onClick = { /* In a real app, trigger DatePickerDialog */ },
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.outlinedCardColors(containerColor = SurfaceContainerLow)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp).fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Outlined.CalendarMonth, null, tint = Primary)
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = try {
                                LocalDate.parse(selectedDate).format(DateTimeFormatter.ofPattern("MMM dd, yyyy"))
                            } catch (e: Exception) { selectedDate },
                            style = MaterialTheme.typography.bodyLarge,
                            color = OnSurface
                        )
                    }
                }

                // Time Pickers (Simplified)
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedCard(
                        onClick = { /* Trigger TimePickerDialog */ },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.outlinedCardColors(containerColor = SurfaceContainerLow)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text("Start Time", style = MaterialTheme.typography.labelSmall, color = OnSurfaceVariant)
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Outlined.AccessTime, null, tint = Primary, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(selectedStartTime.take(5), style = MaterialTheme.typography.bodyMedium)
                            }
                        }
                    }
                    OutlinedCard(
                        onClick = { /* Trigger TimePickerDialog */ },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.outlinedCardColors(containerColor = SurfaceContainerLow)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text("End Time", style = MaterialTheme.typography.labelSmall, color = OnSurfaceVariant)
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Outlined.AccessTime, null, tint = Primary, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(selectedEndTime.take(5), style = MaterialTheme.typography.bodyMedium)
                            }
                        }
                    }
                }

                OutlinedTextField(
                    value = reason,
                    onValueChange = { reason = it },
                    label = { Text("Reason for Rescheduling") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    placeholder = { Text("e.g., Unforeseen conflict") }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { 
                    onConfirm(selectedDate, selectedStartTime, selectedEndTime, reason.ifBlank { "User requested reschedule" }) 
                },
                enabled = true // Validate inputs here in production
            ) {
                Text("Send Request")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        },
        containerColor = SurfaceContainerLowest,
        shape = RoundedCornerShape(24.dp)
    )
}
