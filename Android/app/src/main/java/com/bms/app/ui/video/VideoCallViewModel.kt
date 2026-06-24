package com.bms.app.ui.video

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.daily.CallClient
import co.daily.CallClientListener
import co.daily.model.Participant
import co.daily.model.ParticipantId
import co.daily.model.ParticipantLeftReason
import co.daily.settings.*
import com.bms.app.domain.repository.VideoRepository
import com.bms.app.domain.model.VideoRoomInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import io.github.jan.supabase.realtime.Realtime
import io.github.jan.supabase.realtime.PostgresAction
import io.github.jan.supabase.realtime.channel
import io.github.jan.supabase.realtime.postgresChangeFlow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoCallViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val videoRepository: VideoRepository,
    private val realtime: Realtime
) : ViewModel() {

    private val _uiState = MutableStateFlow<VideoCallUiState>(VideoCallUiState.Initial)
    val uiState: StateFlow<VideoCallUiState> = _uiState.asStateFlow()

    private val _participants = MutableStateFlow<Map<ParticipantId, Participant>>(emptyMap())
    val participants: StateFlow<Map<ParticipantId, Participant>> = _participants.asStateFlow()

    private val _callClient = MutableStateFlow<CallClient?>(null)
    val callClient: StateFlow<CallClient?> = _callClient.asStateFlow()

    /** Separate StateFlow for the LOCAL participant — never mixed into remoteParticipants.
     *  This prevents the local user from appearing in the main remote video slot. */
    private val _localParticipant = MutableStateFlow<Participant?>(null)
    val localParticipant: StateFlow<Participant?> = _localParticipant.asStateFlow()

    private val _isProvider = MutableStateFlow(false)
    val isProvider: StateFlow<Boolean> = _isProvider.asStateFlow()

    private val _currentAppointmentId = MutableStateFlow<String?>(null)
    val currentAppointmentId: StateFlow<String?> = _currentAppointmentId.asStateFlow()

    private var appointmentId: String? = null
    private var isProviderFlag: Boolean = false

    // Tracks which camera is currently active (true = front/user)
    private var isFrontCamera = true

    // Tracks the local participant's ID once known, for robust filtering
    private var localParticipantId: ParticipantId? = null

    private val clientListener = object : CallClientListener {
        override fun onParticipantJoined(participant: Participant) {
            if (participant.info.isLocal) {
                // This IS the local user — capture them directly
                localParticipantId = participant.id
                _localParticipant.value = participant
                // Also remove from remote map in case of race condition
                _participants.update { it - participant.id }
            } else {
                _participants.update { it + (participant.id to participant) }
            }
        }

        override fun onParticipantLeft(participant: Participant, reason: ParticipantLeftReason) {
            _participants.update { it - participant.id }
        }

        override fun onParticipantUpdated(participant: Participant) {
            if (participant.info.isLocal || participant.id == localParticipantId) {
                // Local participant updated (e.g. mic/camera toggle)
                localParticipantId = participant.id
                _localParticipant.value = participant
            } else {
                _participants.update { it + (participant.id to participant) }
            }
        }

        override fun onCallStateUpdated(state: co.daily.model.CallState) {
            refreshLocal()
        }
    }

    /** Backup sync from the SDK's .local property — used on call state changes. */
    private fun refreshLocal() {
        val local = _callClient.value?.participants()?.local ?: return
        localParticipantId = local.id
        _localParticipant.value = local
        // Ensure local is NOT in the remote map
        _participants.update { it - local.id }
    }

    // Managed in initCall below

    private fun observeVideoStatus(id: String) {
        viewModelScope.launch {
            val channel = realtime.channel("appointment_status_$id")
            val statusFlow = channel.postgresChangeFlow<PostgresAction.Update>(schema = "public") {
                table = "appointments"
                filter = "id=eq.$id"
            }
            
            statusFlow.onEach { action ->
                val newStatus = action.record["video_status"]?.toString()
                if (newStatus == "admitted" && !isProviderFlag) {
                    // Patient is admitted, join the call
                    joinCall()
                }
            }.launchIn(viewModelScope)
            
            channel.subscribe()
        }
    }

    private var videoService: VideoCallService? = null
    
    private val serviceConnection = object : android.content.ServiceConnection {
        override fun onServiceConnected(name: android.content.ComponentName?, service: android.os.IBinder?) {
            val binder = service as VideoCallService.LocalBinder
            videoService = binder.getService()
            val client = videoService?.callClient
            _callClient.value = client
            client?.addListener(clientListener)
            
            // If we have room info pending, join now
            pendingRoomInfo?.let { joinCall(it.url, it.token) }
        }

        override fun onServiceDisconnected(name: android.content.ComponentName?) {
            _callClient.value?.removeListener(clientListener)
            _callClient.value = null
            videoService = null
        }
    }

    private var pendingRoomInfo: com.bms.app.domain.model.VideoRoomInfo? = null

    fun initCall(appointmentId: String, isProvider: Boolean) {
        this.appointmentId = appointmentId
        this.isProviderFlag = isProvider
        _isProvider.value = isProvider
        _currentAppointmentId.value = appointmentId

        val serviceIntent = Intent(context, VideoCallService::class.java).apply {
            putExtra(VideoCallService.EXTRA_APPOINTMENT_ID, appointmentId)
        }
        context.startForegroundService(serviceIntent)
        context.bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE)

        observeVideoStatus(appointmentId)
        loadRoomInfo(appointmentId, isProvider)
    }

    private fun loadRoomInfo(id: String, isProvider: Boolean) {
        viewModelScope.launch {
            _uiState.value = VideoCallUiState.Loading
            videoRepository.createOrJoinRoom(id, isProvider)
                .onSuccess { info ->
                    pendingRoomInfo = info
                    // Both provider and consumer join immediately using the returned room_url
                    // (which already embeds the token as ?t=TOKEN)
                    // Consumer will see "Waiting for other participant" on InCallScreen until
                    // the provider's video appears — no manual "admit" gate needed.
                    joinCall(info.url, null)
                }
                .onFailure {
                    _uiState.value = VideoCallUiState.Error(it.message ?: "Failed to load video room")
                }
        }
    }

    private fun joinCall(url: String? = null, token: String? = null) {
        // room_url from the edge function already contains the token embedded as ?t=...
        // Use it directly — no need to append again
        val finalUrl = url ?: pendingRoomInfo?.url ?: return
        
        // Force audio routing to Speakerphone instead of Earpiece
        try {
            val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as android.media.AudioManager
            audioManager.mode = android.media.AudioManager.MODE_IN_COMMUNICATION
            audioManager.isSpeakerphoneOn = true
        } catch (e: Exception) {
            e.printStackTrace()
        }

        _callClient.value?.join(url = finalUrl) { result ->
            if (result.error == null) {
                _uiState.value = VideoCallUiState.InCall

                // Explicitly enable camera and microphone — some rooms default to muted
                _callClient.value?.setInputsEnabled(camera = true)
                _callClient.value?.setInputsEnabled(microphone = true)

                // Force front camera — SDK defaults to back camera regardless of isFrontCamera flag
                enforceFrontCamera()

                // Seed the local participant — retry because SDK may not have it ready immediately
                viewModelScope.launch {
                    repeat(10) { attempt ->
                        refreshLocal()
                        if (_localParticipant.value != null) return@launch
                        kotlinx.coroutines.delay(500L) // Wait 500ms between retries
                    }
                }

                if (isProviderFlag) {
                    viewModelScope.launch {
                        videoRepository.updateVideoStatus(appointmentId!!, "provider_ready")
                    }
                } else {
                    viewModelScope.launch {
                        videoRepository.updateVideoStatus(appointmentId!!, "patient_waiting")
                    }
                }
            } else {
                _uiState.value = VideoCallUiState.Error("Failed to join call: ${result.error}")
            }
        }
    }

    /** Explicitly applies front camera facing mode. 
     *  The Daily SDK always starts with the device default (back camera) regardless 
     *  of our isFrontCamera flag, so we must push this setting after joining. */
    private fun enforceFrontCamera() {
        val client = _callClient.value ?: return
        try {
            val trackSettings = VideoMediaTrackSettingsUpdate.Builder()
                .withFacingMode(FacingModeUpdate.user)
                .build()
            val cameraSettings = CameraInputSettingsUpdate.Builder()
                .withSettings(trackSettings)
                .build()
            val inputUpdate = InputSettingsUpdate.Builder()
                .withCamera(cameraSettings)
                .build()
            client.updateInputs(inputUpdate) { /* no-op on error; user can flip manually */ }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun toggleMute() {
        val client = _callClient.value ?: return
        // Get the active local participant state to check the current mic status
        val localParticipant = client.participants().local
        val currentEnabled = localParticipant?.media?.microphone?.track != null
        client.setInputsEnabled(microphone = !currentEnabled)
    }

    fun toggleVideo() {
        val client = _callClient.value ?: return
        // Get the active local participant state to check the current video status
        val localParticipant = client.participants().local
        val currentEnabled = localParticipant?.media?.camera?.track != null
        client.setInputsEnabled(camera = !currentEnabled)
    }

    fun switchCamera() {
        val client = _callClient.value ?: return
        try {
            isFrontCamera = !isFrontCamera
            val newFacingMode = if (isFrontCamera) FacingModeUpdate.user else FacingModeUpdate.environment
            val trackSettings = VideoMediaTrackSettingsUpdate.Builder()
                .withFacingMode(newFacingMode)
                .build()
            val cameraSettings = CameraInputSettingsUpdate.Builder()
                .withSettings(trackSettings)
                .build()
            val inputUpdate = InputSettingsUpdate.Builder()
                .withCamera(cameraSettings)
                .build()
            client.updateInputs(inputUpdate) { result ->
                if (result.error != null) {
                    isFrontCamera = !isFrontCamera // Revert on failure
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            isFrontCamera = !isFrontCamera
        }
    }

    fun admitPatient() {
        val id = appointmentId ?: return
        viewModelScope.launch {
            videoRepository.admitPatient(id)
        }
    }

    fun startScreenShare(mediaProjectionIntent: Intent) {
        try {
            _callClient.value?.setScreenShareProjectionIntent(mediaProjectionPermissionResultData = mediaProjectionIntent)
            _callClient.value?.startScreenShare(mediaProjectionPermissionResultData = mediaProjectionIntent)
        } catch (e: Exception) {
            e.printStackTrace()
            // Some versions might use different signatures, but we'll try to be consistent
            _callClient.value?.startScreenShare(mediaProjectionIntent)
        }
    }

    fun stopScreenShare() {
        _callClient.value?.stopScreenShare()
    }

    fun endCall() {
        _callClient.value?.leave {
             viewModelScope.launch {
                 appointmentId?.let { videoRepository.updateVideoStatus(it, "ended") }
                 try {
                     context.unbindService(serviceConnection)
                 } catch (e: Exception) {}
                 context.stopService(Intent(context, VideoCallService::class.java))
             }
        }
    }

    override fun onCleared() {
        super.onCleared()
        try {
            context.unbindService(serviceConnection)
        } catch (e: Exception) {}
    }
}

sealed class VideoCallUiState {
    object Initial : VideoCallUiState()
    object Loading : VideoCallUiState()
    object WaitingRoom : VideoCallUiState()
    object InCall : VideoCallUiState()
    data class Error(val message: String) : VideoCallUiState()
}
