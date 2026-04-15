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
    private var appointmentId: String? = null
    private var isProvider: Boolean = false

    private val clientListener = object : CallClientListener {
        override fun onParticipantJoined(participant: Participant) {
            _participants.update { it + (participant.id to participant) }
        }

        override fun onParticipantLeft(participant: Participant, reason: ParticipantLeftReason) {
            _participants.update { it - participant.id }
        }

        override fun onParticipantUpdated(participant: Participant) {
            _participants.update { it + (participant.id to participant) }
        }
        
        override fun onCallStateUpdated(state: co.daily.model.CallState) {
             // Handle call state if needed
        }
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
                if (newStatus == "admitted" && !isProvider) {
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
        this.isProvider = isProvider
        
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
                    if (isProvider) {
                        joinCall(info.url, info.token)
                    } else {
                        _uiState.value = VideoCallUiState.WaitingRoom
                    }
                }
                .onFailure {
                    _uiState.value = VideoCallUiState.Error(it.message ?: "Failed to load video room")
                }
        }
    }

    private fun joinCall(url: String? = null, token: String? = null) {
        val finalUrl = url ?: pendingRoomInfo?.url ?: return
        val finalToken = token ?: pendingRoomInfo?.token
        
        val urlWithToken = if (finalToken != null) "$finalUrl?t=$finalToken" else finalUrl
        
        _callClient.value?.join(url = urlWithToken) { result ->
            if (result.error == null) {
                _uiState.value = VideoCallUiState.InCall
                if (isProvider) {
                    viewModelScope.launch {
                        videoRepository.updateVideoStatus(appointmentId!!, "provider_ready")
                    }
                } else {
                    viewModelScope.launch {
                        videoRepository.updateVideoStatus(appointmentId!!, "patient_waiting")
                    }
                }
            } else {
                _uiState.value = VideoCallUiState.Error("Failed to join call")
            }
        }
    }

    fun toggleMute() {
        val client = _callClient.value ?: return
        val currentEnabled = client.inputs().microphone.isEnabled
        client.setInputsEnabled(microphone = !currentEnabled)
    }

    fun toggleVideo() {
        val client = _callClient.value ?: return
        val currentEnabled = client.inputs().camera.isEnabled
        client.setInputsEnabled(camera = !currentEnabled)
    }

    fun switchCamera() {
        // TODO: Implement camera switching using correct deviceId/facingMode API for version 0.37.0
        // Currently disabled to ensure build stability.
    }

    fun startScreenShare(mediaProjectionIntent: Intent) {
        _callClient.value?.startScreenShare(mediaProjectionIntent)
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
