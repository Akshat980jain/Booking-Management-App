package com.bms.app.ui.video

import android.app.Activity
import android.content.Context
import android.media.projection.MediaProjectionManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

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

    // Screen Share Launcher
    val screenShareLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.let { intent ->
                viewModel.startScreenShare(intent)
            }
        }
    }

    LaunchedEffect(appointmentId) {
        viewModel.initCall(appointmentId, isProvider)
    }

    Scaffold { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            when (val state = uiState) {
                is VideoCallUiState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                is VideoCallUiState.WaitingRoom -> {
                    WaitingRoomScreen(onExit = onNavigateBack)
                }
                is VideoCallUiState.InCall -> {
                    val client = viewModel.callClient.collectAsState().value
                    if (client != null) {
                        InCallScreen(
                            callClient = client,
                            participants = participants,
                            onMuteToggle = viewModel::toggleMute,
                            onVideoToggle = viewModel::toggleVideo,
                            onFlipCamera = viewModel::switchCamera,
                            onShareScreen = {
                                val mediaProjectionManager = context.getSystemService(Context.MEDIA_PROJECTION_SERVICE) as MediaProjectionManager
                                screenShareLauncher.launch(mediaProjectionManager.createScreenCaptureIntent())
                            },
                            onEndCall = {
                                viewModel.endCall()
                                onNavigateBack()
                            }
                        )
                    } else {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator()
                        }
                    }
                }
                is VideoCallUiState.Error -> {
                    Column(
                        modifier = Modifier.fillMaxSize().padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(state.message, color = MaterialTheme.colorScheme.error)
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = onNavigateBack) {
                            Text("Go Back")
                        }
                    }
                }
                else -> {}
            }
        }
    }
}
