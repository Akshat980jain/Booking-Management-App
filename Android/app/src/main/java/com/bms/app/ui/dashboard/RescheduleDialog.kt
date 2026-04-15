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
import androidx.compose.ui.window.Dialog
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

    var showDatePicker by remember { mutableStateOf(false) }
    var showStartTimePicker by remember { mutableStateOf(false) }
    var showEndTimePicker by remember { mutableStateOf(false) }

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = try {
            java.time.LocalDate.parse(selectedDate)
                .atStartOfDay(java.time.ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli()
        } catch (e: Exception) { System.currentTimeMillis() },
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return utcTimeMillis >= System.currentTimeMillis() - 86400000 // Today and future
            }
        }
    )

    // Helper to format time from state
    fun formatTime(hour: Int, minute: Int): String {
        return "%02d:%02d:00".format(hour, minute)
    }

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

                // Date Picker Interaction
                OutlinedCard(
                    onClick = { showDatePicker = true },
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

                // Time Pickers
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedCard(
                        onClick = { showStartTimePicker = true },
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
                        onClick = { showEndTimePicker = true },
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
                enabled = true
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

    // Material 3 Date Picker Dialog
    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { mills ->
                        selectedDate = java.time.Instant.ofEpochMilli(mills)
                            .atZone(java.time.ZoneId.of("UTC"))
                            .toLocalDate()
                            .toString()
                    }
                    showDatePicker = false
                }) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) { Text("Cancel") }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    // Material 3 Time Picker Dialogs
    if (showStartTimePicker) {
        val timePickerState = rememberTimePickerState(
            initialHour = try { selectedStartTime.split(":")[0].toInt() } catch (e: Exception) { 9 },
            initialMinute = try { selectedStartTime.split(":")[1].toInt() } catch (e: Exception) { 0 }
        )
        
        Dialog(onDismissRequest = { showStartTimePicker = false }) {
            Surface(
                shape = RoundedCornerShape(28.dp),
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 6.dp
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Select Start Time",
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp)
                    )
                    TimePicker(state = timePickerState)
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(top = 24.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = { showStartTimePicker = false }) { Text("Cancel") }
                        TextButton(onClick = {
                            selectedStartTime = formatTime(timePickerState.hour, timePickerState.minute)
                            // Auto-set end time to +30 mins
                            val endHour = (timePickerState.hour + (timePickerState.minute + 30) / 60) % 24
                            val endMin = (timePickerState.minute + 30) % 60
                            selectedEndTime = formatTime(endHour, endMin)
                            showStartTimePicker = false
                        }) { Text("OK") }
                    }
                }
            }
        }
    }

    if (showEndTimePicker) {
        val timePickerState = rememberTimePickerState(
            initialHour = try { selectedEndTime.split(":")[0].toInt() } catch (e: Exception) { 10 },
            initialMinute = try { selectedEndTime.split(":")[1].toInt() } catch (e: Exception) { 0 }
        )
        
        Dialog(onDismissRequest = { showEndTimePicker = false }) {
            Surface(
                shape = RoundedCornerShape(28.dp),
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 6.dp
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Select End Time",
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp)
                    )
                    TimePicker(state = timePickerState)
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(top = 24.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = { showEndTimePicker = false }) { Text("Cancel") }
                        TextButton(onClick = {
                            selectedEndTime = formatTime(timePickerState.hour, timePickerState.minute)
                            showEndTimePicker = false
                        }) { Text("OK") }
                    }
                }
            }
        }
    }
}
