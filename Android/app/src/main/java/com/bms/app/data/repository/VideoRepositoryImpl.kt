package com.bms.app.data.repository

import com.bms.app.domain.model.VideoRoomInfo
import com.bms.app.domain.repository.VideoRepository
import io.github.jan.supabase.functions.Functions
import io.github.jan.supabase.postgrest.Postgrest
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import io.ktor.client.call.body
import io.ktor.client.request.setBody
import io.github.jan.supabase.functions.functions
import javax.inject.Inject
import javax.inject.Singleton

@Serializable
private data class VideoRoomRequest(
    @SerialName("appointment_id") val appointmentId: String,
    val action: String? = null
)

@Serializable
private data class AdmitPatientRequest(
    @SerialName("appointment_id") val appointmentId: String
)

@Serializable
private data class VideoStatusUpdate(
    @SerialName("video_status") val status: String
)

@Singleton
class VideoRepositoryImpl @Inject constructor(
    private val postgrest: Postgrest,
    private val functions: Functions
) : VideoRepository {

    override suspend fun createOrJoinRoom(appointmentId: String, isProvider: Boolean): Result<VideoRoomInfo> {
        return try {
            val response = functions.invoke("create-video-room") {
                setBody(VideoRoomRequest(
                    appointmentId = appointmentId,
                    action = if (isProvider) "create" else "join"
                ))
            }
            val info = response.body<VideoRoomInfo>()
            Result.success(info)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun admitPatient(appointmentId: String): Result<Unit> {
        return try {
            functions.invoke(
                function = "admit-patient",
                body = AdmitPatientRequest(appointmentId = appointmentId)
            )
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateVideoStatus(appointmentId: String, status: String): Result<Unit> {
        return try {
            postgrest["appointments"].update(
                VideoStatusUpdate(status)
            ) {
                filter {
                    eq("id", appointmentId)
                }
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
