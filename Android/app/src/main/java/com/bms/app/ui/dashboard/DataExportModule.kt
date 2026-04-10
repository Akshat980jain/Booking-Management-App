package com.bms.app.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bms.app.ui.theme.*
import com.bms.app.ui.components.*
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun DataExportModule(viewModel: com.bms.app.ui.dashboard.AdminViewModel = hiltViewModel()) {
    val exportState by viewModel.exportState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(exportState) {
        when (val state = exportState) {
            is ExportState.Success -> {
                snackbarHostState.showSnackbar("Export successful! JSON data generated.")
                viewModel.resetExportState()
            }
            is ExportState.Error -> {
                snackbarHostState.showSnackbar("Export failed: ${state.message}")
            }
            else -> {}
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = Color.Transparent
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding)
        ) {
            // ── Header ──────────────────────────────────
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Outlined.Storage, null, tint = OnSurface, modifier = Modifier.size(24.dp))
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "Developer Data Export",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Export database tables as MongoDB Extended JSON for backup or analysis",
                        style = MaterialTheme.typography.bodySmall,
                        color = OnSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ── Format Toggle Card ───────────────────────
            Surface(
                color = Color(0xFFF1F5F9), 
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "MongoDB Extended JSON Format",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "Uses \$oid, \$date formatting for MongoDB Compass compatibility",
                            style = MaterialTheme.typography.bodySmall,
                            color = OnSurfaceVariant
                        )
                    }
                    var formatToggle by remember { mutableStateOf(true) }
                    Switch(
                        checked = formatToggle,
                        onCheckedChange = { formatToggle = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = Color(0xFF1F2937)
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ── Export All Button ────────────────────────
            Button(
                onClick = { viewModel.exportToJSON("all") },
                enabled = exportState !is ExportState.Processing,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1F2937)),
                contentPadding = PaddingValues(16.dp)
            ) {
                if (exportState is ExportState.Processing) {
                    CircularProgressIndicator(modifier = Modifier.size(18.dp), color = Color.White, strokeWidth = 2.dp)
                } else {
                    Icon(Icons.Outlined.Download, null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Export All System Tables", fontWeight = FontWeight.SemiBold)
                }
            }
            Text(
                text = "Downloads a comprehensive JSON package of users, appointments, and transactions",
                style = MaterialTheme.typography.labelSmall,
                color = OnSurfaceVariant,
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            // ── Individual Tables ────────────────────────
            Text(
                text = "Export Individual Tables",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Surface(
                color = SurfaceContainerLowest,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(8.dp)) {
                    val tables = listOf("appointments", "users", "transactions")
                    tables.forEach { table ->
                        ExportTableRow(table, onExport = { viewModel.exportToJSON(table) }, isProcessing = exportState is ExportState.Processing)
                        if (table != tables.last()) HorizontalDivider(color = GhostBorder.copy(alpha=0.5f))
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ── Import Guide ─────────────────────────────
            Surface(
                color = Color(0xFFF1F5F9),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Import to MongoDB Compass:",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    val steps = listOf(
                        "1. Open MongoDB Compass and connect to your DB",
                        "2. Select or create a collection",
                        "3. Click \"Add Data\" -> \"Import JSON\"",
                        "4. Select the exported JSON file from your device",
                        "5. Choose \"JSON\" format and confirm import"
                    )
                    steps.forEach { step ->
                        Text(
                            text = step,
                            style = MaterialTheme.typography.labelSmall,
                            color = OnSurfaceVariant,
                            modifier = Modifier.padding(vertical = 2.dp)
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}

@Composable
fun ExportTableRow(tableName: String, onExport: () -> Unit, isProcessing: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Outlined.TableView, null, tint = OnSurfaceVariant, modifier = Modifier.size(16.dp))
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = tableName,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f)
        )
        OutlinedButton(
            onClick = onExport,
            enabled = !isProcessing,
            shape = RoundedCornerShape(8.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, GhostBorder),
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
            modifier = Modifier.height(32.dp)
        ) {
            if (isProcessing) {
                CircularProgressIndicator(modifier = Modifier.size(12.dp), strokeWidth = 1.dp)
            } else {
                Icon(Icons.Outlined.Download, null, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Export", fontSize = 11.sp)
            }
        }
    }
}
