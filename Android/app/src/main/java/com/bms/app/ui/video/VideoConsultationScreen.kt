package com.bms.app.ui.video

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.media.projection.MediaProjectionManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Mic
import androidx.compose.material.icons.outlined.Videocam
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.bms.app.ui.theme.OnSurface
import com.bms.app.ui.theme.OnSurfaceVariant
import com.bms.app.ui.theme.Primary

@Composable
fun VideoConsultationScreen(
    appointmentId: String,
    isProvider: Boolean,
    onNavigateBack: () -> Unit,
    viewModel: VideoCallViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val participants by viewModel.participants.collectAsState()

    // ── Track whether camera + mic are granted ──────────────────────────────
    val requiredPermissions = buildList {
        add(Manifest.permission.CAMERA)
        add(Manifest.permission.RECORD_AUDIO)
        // POST_NOTIFICATIONS required on Android 13+ for the foreground service notification
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            add(Manifest.permission.POST_NOTIFICATIONS)
        }
    }.toTypedArray()

    fun allGranted() = requiredPermissions.all {
        ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }

    var permissionsGranted by remember { mutableStateOf(allGranted()) }

    // Launcher that fires once the user responds to the permission dialog
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { results ->
        permissionsGranted = results.values.all { it }
    }

    // Screen Share Launcher
    val screenShareLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.let { viewModel.startScreenShare(it) }
        }
    }

    // On first composition: request permissions if needed, else start call immediately
    LaunchedEffect(Unit) {
        if (!allGranted()) {
            permissionLauncher.launch(requiredPermissions)
        }
    }

    // Start the call only once permissions are confirmed granted
    LaunchedEffect(permissionsGranted) {
        if (permissionsGranted) {
            viewModel.initCall(appointmentId, isProvider)
        }
    }

    Scaffold { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            when {
                // ── Permissions not yet granted ───────────────────────────
                !permissionsGranted -> {
                    PermissionDeniedContent(
                        onRequestAgain = { permissionLauncher.launch(requiredPermissions) },
                        onBack = onNavigateBack
                    )
                }

                // ── Normal video call flow ────────────────────────────────
                else -> {
                    when (val state = uiState) {
                        is VideoCallUiState.Initial,
                        is VideoCallUiState.Loading -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    CircularProgressIndicator(color = Primary)
                                    Spacer(modifier = Modifier.height(16.dp))
                                    Text("Connecting…", color = OnSurfaceVariant)
                                }
                            }
                        }

                        is VideoCallUiState.WaitingRoom -> {
                            WaitingRoomScreen(onExit = onNavigateBack)
                        }

                        is VideoCallUiState.InCall -> {
                            val client = viewModel.callClient.collectAsState().value
                            val providerState by viewModel.isProvider.collectAsState()
                            val localParticipant by viewModel.localParticipant.collectAsState()
                            if (client != null) {
                                InCallScreen(
                                    callClient = client,
                                    participants = participants,
                                    localParticipant = localParticipant,
                                    isProvider = providerState,
                                    onMuteToggle = viewModel::toggleMute,
                                    onVideoToggle = viewModel::toggleVideo,
                                    onFlipCamera = viewModel::switchCamera,
                                    onAdmitPatient = viewModel::admitPatient,
                                    onShareScreen = {
                                        val mgr = context.getSystemService(Context.MEDIA_PROJECTION_SERVICE)
                                                as MediaProjectionManager
                                        screenShareLauncher.launch(mgr.createScreenCaptureIntent())
                                    },
                                    onEndCall = {
                                        viewModel.endCall()
                                        onNavigateBack()
                                    }
                                )
                            } else {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator(color = Primary)
                                }
                            }
                        }

                        is VideoCallUiState.Error -> {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(32.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = state.message,
                                    color = MaterialTheme.colorScheme.error,
                                    textAlign = TextAlign.Center
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Button(onClick = onNavigateBack) { Text("Go Back") }
                            }
                        }
                    }
                }
            }
        }
    }
}

// ── Permission rationale UI ────────────────────────────────────────────────────

@Composable
private fun PermissionDeniedContent(
    onRequestAgain: () -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row {
            Icon(Icons.Outlined.Videocam, null, tint = Primary, modifier = Modifier.size(32.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Icon(Icons.Outlined.Mic, null, tint = Primary, modifier = Modifier.size(32.dp))
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Camera & Microphone Required",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = OnSurface
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Please grant camera and microphone permissions to start the video consultation.",
            style = MaterialTheme.typography.bodyMedium,
            color = OnSurfaceVariant,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = onRequestAgain,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Grant Permissions")
        }
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedButton(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Go Back")
        }
    }
}
