package com.bms.app.ui.dashboard
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.bms.app.domain.model.Appointment
import com.bms.app.domain.model.ProviderProfile
import com.bms.app.ui.theme.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.ui.platform.LocalContext
import com.bms.app.util.FileDownloader

@Composable
fun InvoicePreviewDialog(
    appointment: Appointment,
    provider: ProviderProfile?,
    providerName: String,
    onDismiss: () -> Unit,
    onDownloadStart: () -> Unit = {}
) {
    var isDownloading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val formattedDate = try {
        LocalDate.parse(appointment.appointmentDate).format(DateTimeFormatter.ofPattern("MMM dd, yyyy"))
    } catch (e: Exception) {
        appointment.appointmentDate
    }

    // Calculation logic
    val totalAmount = if (appointment.isVideoConsultation == true) {
        provider?.videoConsultationFee ?: provider?.consultationFee ?: 500.0
    } else {
        provider?.consultationFee ?: 500.0
    }

    Dialog(
        onDismissRequest = { if (!isDownloading) onDismiss() },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .wrapContentHeight(),
            shape = RoundedCornerShape(20.dp),
            color = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "INVOICE",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.ExtraBold,
                            color = Primary,
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = "#INV-${appointment.id.take(8).uppercase()}",
                            style = MaterialTheme.typography.labelMedium,
                            color = OnSurfaceVariant
                        )
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Outlined.Close, contentDescription = "Close")
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Billing Details
                Row(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Billed To:", style = MaterialTheme.typography.labelSmall, color = OnSurfaceVariant)
                        Text("Patient", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
                    }
                    Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.End) {
                        Text("Date:", style = MaterialTheme.typography.labelSmall, color = OnSurfaceVariant)
                        Text(formattedDate, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Provider Detail
                Surface(
                    color = SurfaceContainerLow,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = providerName,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = provider?.profession ?: "Healthcare Provider",
                            style = MaterialTheme.typography.bodySmall,
                            color = OnSurfaceVariant
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Table Header
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Description", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = OnSurfaceVariant)
                    Text("Amount", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = OnSurfaceVariant)
                }
                HorizontalDivider(color = GhostBorder)
                
                // Table Row: Consultation
                Spacer(modifier = Modifier.height(12.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Column {
                        val type = if (appointment.isVideoConsultation == true) "Video" else "In-Person"
                        Text("Consultation Fee ($type)", style = MaterialTheme.typography.bodyMedium)
                        Text(formattedDate, style = MaterialTheme.typography.labelSmall, color = OnSurfaceVariant)
                    }
                    Text("₹${String.format("%.2f", totalAmount)}", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Summary
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(SurfaceContainerLowest, RoundedCornerShape(12.dp))
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Total Amount Due", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
                        Text(
                            text = "₹${String.format("%.2f", totalAmount)}",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.ExtraBold,
                            color = Primary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Action Button
                if (isDownloading) {
                    LinearProgressIndicator(
                        modifier = Modifier.fillMaxWidth(),
                        color = Primary,
                        trackColor = PrimaryContainer
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Preparing PDF...",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.labelSmall,
                        color = Primary
                    )
                } else {
                    Button(
                        onClick = {
                            isDownloading = true
                            scope.launch {
                                onDownloadStart()
                                delay(1500) // Simulate generation delay
                                
                                // Real download logic
                                val fileName = "Invoice_${appointment.id.take(8)}"
                                val dummyContent = "Invoice for appointment ${appointment.id}\nProvider: $providerName\nAmount: ₹$totalAmount"
                                val result = FileDownloader.downloadPdf(context, dummyContent.toByteArray(), fileName)
                                
                                isDownloading = false
                                if (result.isSuccess) {
                                    onDismiss()
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Primary)
                    ) {
                        Icon(Icons.Outlined.FileDownload, null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Download PDF Invoice")
                    }
                }
            }
        }
    }
}
