package com.bms.app.ui.video

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import co.daily.CallClient
import co.daily.model.Participant
import co.daily.model.ParticipantId
import co.daily.view.VideoView
import com.bms.app.ui.theme.Error
import com.bms.app.ui.theme.Primary
import com.bms.app.ui.theme.SurfaceContainerHighest

@Composable
fun InCallScreen(
    callClient: CallClient,
    participants: Map<ParticipantId, Participant>,
    localParticipant: Participant? = null,          // Explicit local — avoids isLocal flag unreliability
    isProvider: Boolean = false,
    onMuteToggle: () -> Unit,
    onVideoToggle: () -> Unit,
    onFlipCamera: () -> Unit,
    onShareScreen: () -> Unit,
    onAdmitPatient: () -> Unit = {},
    onEndCall: () -> Unit
) {
    // Three-tier local participant resolution:
    // 1. Explicit StateFlow from ViewModel (most reliable once populated)
    // 2. isLocal flag scan on participants map (backup)
    // 3. Direct SDK query (ultimate fallback — always works when in a call)
    val effectiveLocal = localParticipant
        ?: participants.values.find { it.info.isLocal }
        ?: callClient.participants().local
    // Remote participants are everyone except the known local participant ID
    val remoteParticipants = participants.values.filter { it.id != effectiveLocal?.id }
    val primaryRemote = remoteParticipants.firstOrNull()

    val micEnabled = effectiveLocal?.media?.microphone?.track != null
    val videoEnabled = effectiveLocal?.media?.camera?.track != null

    var showParticipantPanel by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {

        // ── Main Video Area ────────────────────────────────────────────────
        Row(modifier = Modifier.fillMaxSize()) {

            // Primary remote video (full left) or waiting message
            Box(
                modifier = Modifier
                    .weight(if (showParticipantPanel && isProvider) 0.65f else 1f)
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                if (primaryRemote != null) {
                    AndroidView(
                        factory = { context ->
                            VideoView(context).apply {
                                videoScaleMode = VideoView.VideoScaleMode.FILL
                            }
                        },
                        update = { view -> view.track = primaryRemote.media?.camera?.track },
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            Icons.Default.HourglassEmpty,
                            contentDescription = null,
                            tint = Color.White.copy(alpha = 0.5f),
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = if (isProvider) "Waiting for patient to join…" else "Waiting for doctor…",
                            color = Color.White.copy(alpha = 0.7f),
                            fontSize = 16.sp
                        )
                        if (isProvider && remoteParticipants.isEmpty()) {
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(
                                onClick = onAdmitPatient,
                                colors = ButtonDefaults.buttonColors(containerColor = Primary),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Icon(Icons.Default.PersonAdd, null, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Admit Patient")
                            }
                        }
                    }
                }
            }

            // ── Participants Panel (provider only) ─────────────────────────
            if (showParticipantPanel && isProvider) {
                Surface(
                    modifier = Modifier
                        .weight(0.35f)
                        .fillMaxHeight(),
                    color = Color(0xFF1A1A2E),
                    shape = RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp)
                ) {
                    Column(modifier = Modifier.fillMaxSize().padding(8.dp)) {
                        Text(
                            text = "Participants (${remoteParticipants.size})",
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                            color = Color.White,
                            modifier = Modifier.padding(8.dp)
                        )
                        HorizontalDivider(color = Color.White.copy(alpha = 0.1f))
                        Spacer(modifier = Modifier.height(4.dp))

                        if (remoteParticipants.isEmpty()) {
                            Box(
                                modifier = Modifier.fillMaxWidth().padding(vertical = 24.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Icon(
                                        Icons.Default.PeopleOutline,
                                        null,
                                        tint = Color.White.copy(alpha = 0.3f),
                                        modifier = Modifier.size(32.dp)
                                    )
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        "No one yet",
                                        color = Color.White.copy(alpha = 0.4f),
                                        fontSize = 12.sp
                                    )
                                    Spacer(modifier = Modifier.height(12.dp))
                                    // Admit button inside empty panel
                                    OutlinedButton(
                                        onClick = onAdmitPatient,
                                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Primary),
                                        shape = RoundedCornerShape(8.dp),
                                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Icon(Icons.Default.PersonAdd, null, modifier = Modifier.size(14.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("Admit", fontSize = 12.sp)
                                    }
                                }
                            }
                        } else {
                            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                items(remoteParticipants) { participant ->
                                    ParticipantTile(participant = participant)
                                }
                            }
                        }
                    }
                }
            }
        }

        // ── Local PIP (top-right when panel hidden, top-left when panel shown) ──
        Box(
            modifier = Modifier
                .align(if (showParticipantPanel && isProvider) Alignment.TopStart else Alignment.TopEnd)
                .padding(16.dp)
                .size(100.dp, 140.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(SurfaceContainerHighest)
        ) {
            effectiveLocal?.let { local ->
                if (videoEnabled) {
                    AndroidView(
                        factory = { context ->
                            VideoView(context).apply {
                                videoScaleMode = VideoView.VideoScaleMode.FILL
                            }
                        },
                        update = { view -> view.track = local.media?.camera?.track },
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.VideocamOff, contentDescription = null, tint = Color.Gray)
                    }
                }
            }
            // Local label
            Surface(
                color = Color.Black.copy(alpha = 0.5f),
                modifier = Modifier.align(Alignment.BottomStart).fillMaxWidth()
            ) {
                Text(
                    "You",
                    color = Color.White,
                    fontSize = 10.sp,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }
        }

        // ── Bottom Controls ────────────────────────────────────────────────
        Surface(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 28.dp),
            shape = RoundedCornerShape(32.dp),
            color = Color.Black.copy(alpha = 0.7f),
            tonalElevation = 8.dp
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Mic
                CallControlButton(
                    icon = if (micEnabled) Icons.Default.Mic else Icons.Default.MicOff,
                    active = micEnabled,
                    onClick = onMuteToggle
                )

                // Camera
                CallControlButton(
                    icon = if (videoEnabled) Icons.Default.Videocam else Icons.Default.VideocamOff,
                    active = videoEnabled,
                    onClick = onVideoToggle
                )

                // Flip Camera
                CallControlButton(
                    icon = Icons.Default.Cameraswitch,
                    onClick = onFlipCamera
                )

                // Screen Share
                CallControlButton(
                    icon = Icons.Default.ScreenShare,
                    onClick = onShareScreen
                )

                // Participants toggle (provider only)
                if (isProvider) {
                    CallControlButton(
                        icon = Icons.Default.People,
                        active = showParticipantPanel,
                        onClick = { showParticipantPanel = !showParticipantPanel },
                        badge = remoteParticipants.size.takeIf { it > 0 }
                    )
                }

                Spacer(modifier = Modifier.width(4.dp))

                // End Call
                FloatingActionButton(
                    onClick = onEndCall,
                    containerColor = Error,
                    contentColor = Color.White,
                    shape = CircleShape,
                    modifier = Modifier.size(52.dp)
                ) {
                    Icon(Icons.Default.CallEnd, contentDescription = "End Call")
                }
            }
        }
    }
}

// ── Participant Tile (panel item) ─────────────────────────────────────────────

@Composable
private fun ParticipantTile(participant: Participant) {
    val hasVideo = participant.media?.camera?.track != null
    val hasMic  = participant.media?.microphone?.track != null
    val name = participant.info.userName?.takeIf { it.isNotBlank() } ?: "Patient"

    Surface(
        color = Color.White.copy(alpha = 0.08f),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.fillMaxWidth().height(120.dp)
    ) {
        Box {
            if (hasVideo) {
                AndroidView(
                    factory = { context ->
                        VideoView(context).apply { videoScaleMode = VideoView.VideoScaleMode.FILL }
                    },
                    update = { view -> view.track = participant.media?.camera?.track },
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Icon(Icons.Default.VideocamOff, null, tint = Color.Gray, modifier = Modifier.size(24.dp))
                }
            }
            // Name + mic badge
            Row(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth()
                    .background(Color.Black.copy(alpha = 0.5f))
                    .padding(horizontal = 6.dp, vertical = 3.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    if (hasMic) Icons.Default.Mic else Icons.Default.MicOff,
                    null,
                    tint = if (hasMic) Color.White else Color.Red,
                    modifier = Modifier.size(10.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(name, color = Color.White, fontSize = 10.sp, maxLines = 1)
            }
        }
    }
}

// ── Control Button ────────────────────────────────────────────────────────────

@Composable
fun CallControlButton(
    icon: ImageVector,
    active: Boolean = true,
    onClick: () -> Unit,
    badge: Int? = null
) {
    Box {
        IconButton(
            onClick = onClick,
            modifier = Modifier
                .size(46.dp)
                .clip(CircleShape)
                .background(if (active) Color.White.copy(alpha = 0.2f) else Error.copy(alpha = 0.2f))
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = if (active) Color.White else Color.Red
            )
        }
        if (badge != null && badge > 0) {
            Surface(
                color = Primary,
                shape = CircleShape,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(16.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(badge.toString(), color = Color.White, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
