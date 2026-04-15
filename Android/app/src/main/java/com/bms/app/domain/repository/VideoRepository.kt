package com.bms.app.domain.repository

import com.bms.app.domain.model.VideoRoomInfo

interface VideoRepository {
    /**
     * Invokes the create-video-room edge function to get meeting details.
     */
    suspend fun createOrJoinRoom(appointmentId: String, isProvider: Boolean): Result<VideoRoomInfo>

    /**
     * Invokes the admit-patient edge function to allow a patient into the room.
     * Only relevant for providers.
     */
    suspend fun admitPatient(appointmentId: String): Result<Unit>

    /**
     * Directly updates the video_status of an appointment.
     */
    suspend fun updateVideoStatus(appointmentId: String, status: String): Result<Unit>
}
