package com.bms.app.data.repository

import com.bms.app.domain.model.VideoRoomInfo
import com.bms.app.domain.repository.VideoRepository
import io.github.jan.supabase.functions.Functions
import io.github.jan.supabase.postgrest.Postgrest
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import io.ktor.client.call.body
import io.github.jan.supabase.functions.functions
import io.github.jan.supabase.SupabaseClient
import javax.inject.Inject
import javax.inject.Singleton

@Serializable
private data class VideoStatusUpdate(
    @SerialName("video_status") val status: String
)

@Singleton
class VideoRepositoryImpl @Inject constructor(
    private val postgrest: Postgrest,
    private val supabaseClient: SupabaseClient
) : VideoRepository {

    override suspend fun createOrJoinRoom(appointmentId: String, isProvider: Boolean): Result<VideoRoomInfo> {
        return try {
            // Build the request as a JsonObject — no ContentNegotiation/setBody needed
            val body = buildJsonObject {
                put("appointment_id", appointmentId)
                put("action", if (isProvider) "create" else "join")
            }

            val response = supabaseClient.functions.invoke(
                function = "create-video-room",
                body = body
            )
            val info = response.body<VideoRoomInfo>()
            Result.success(info)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun admitPatient(appointmentId: String): Result<Unit> {
        return try {
            val body = buildJsonObject {
                put("appointment_id", appointmentId)
            }
            supabaseClient.functions.invoke(
                function = "admit-patient",
                body = body
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
