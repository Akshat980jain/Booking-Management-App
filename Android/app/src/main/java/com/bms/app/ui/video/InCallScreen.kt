package com.bms.app.ui.video

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import co.daily.CallClient
import co.daily.model.ParticipantId
import co.daily.view.VideoView
import com.bms.app.ui.theme.Error
import com.bms.app.ui.theme.Primary
import com.bms.app.ui.theme.SurfaceContainerHighest

@Composable
fun InCallScreen(
    callClient: CallClient,
    participants: Map<ParticipantId, co.daily.model.Participant>,
    onMuteToggle: () -> Unit,
    onVideoToggle: () -> Unit,
    onFlipCamera: () -> Unit,
    onShareScreen: () -> Unit,
    onEndCall: () -> Unit
) {
    val localParticipant = participants.values.find { it.info.isLocal }
    val remoteParticipant = participants.values.find { !it.info.isLocal }
    
    val micEnabled = localParticipant?.media?.microphone?.track != null
    val videoEnabled = localParticipant?.media?.camera?.track != null

    Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        
        // Remote Participant Video (Full Screen)
        remoteParticipant?.let { remote ->
            AndroidView(
                factory = { context ->
                    VideoView(context).apply {
                        videoScaleMode = VideoView.VideoScaleMode.FILL
                    }
                },
                update = { view ->
                    view.track = remote.media?.camera?.track
                },
                modifier = Modifier.fillMaxSize()
            )
        } ?: Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Waiting for other participant...", color = Color.White)
        }

        // Local Participant Video (PIP)
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
                .size(120.dp, 180.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(SurfaceContainerHighest)
        ) {
            localParticipant?.let { local ->
                if (videoEnabled) {
                    AndroidView(
                        factory = { context ->
                            VideoView(context).apply {
                                videoScaleMode = VideoView.VideoScaleMode.FILL
                            }
                        },
                        update = { view ->
                            view.track = local.media?.camera?.track
                        },
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.VideocamOff, contentDescription = null, tint = Color.Gray)
                    }
                }
            }
        }

        // Bottom Controls
        Surface(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp),
            shape = RoundedCornerShape(32.dp),
            color = Color.Black.copy(alpha = 0.6f),
            tonalElevation = 8.dp
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CallControlButton(
                    icon = if (micEnabled) Icons.Default.Mic else Icons.Default.MicOff,
                    active = micEnabled,
                    onClick = onMuteToggle
                )
                
                CallControlButton(
                    icon = if (videoEnabled) Icons.Default.Videocam else Icons.Default.VideocamOff,
                    active = videoEnabled,
                    onClick = onVideoToggle
                )

                CallControlButton(
                    icon = Icons.Default.Cameraswitch,
                    onClick = onFlipCamera
                )

                CallControlButton(
                    icon = Icons.Default.ScreenShare,
                    onClick = onShareScreen
                )

                Spacer(modifier = Modifier.width(8.dp))

                // End Call Button
                FloatingActionButton(
                    onClick = onEndCall,
                    containerColor = Error,
                    contentColor = Color.White,
                    shape = CircleShape,
                    modifier = Modifier.size(56.dp)
                ) {
                    Icon(Icons.Default.CallEnd, contentDescription = "End Call")
                }
            }
        }
    }
}

@Composable
fun CallControlButton(
    icon: ImageVector,
    active: Boolean = true,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(if (active) Color.White.copy(alpha = 0.2f) else Error.copy(alpha = 0.2f))
    ) {
        Icon(
            icon,
            contentDescription = null,
            tint = if (active) Color.White else Color.Red
        )
    }
}
