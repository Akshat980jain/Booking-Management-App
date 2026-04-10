package com.bms.app.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Appointment(
    val id: String = "",
    @SerialName("user_id") val userId: String = "",
    @SerialName("provider_id") val providerId: String = "",
    @SerialName("appointment_date") val appointmentDate: String = "",
    @SerialName("start_time") val startTime: String = "",
    @SerialName("end_time") val endTime: String = "",
    val status: String = "pending",
    val notes: String? = null,
    @SerialName("cancellation_reason") val cancellationReason: String? = null,
    @SerialName("is_video_consultation") val isVideoConsultation: Boolean? = false,
    @SerialName("created_at") val createdAt: String = "",
    @SerialName("updated_at") val updatedAt: String = ""
)

@Serializable
data class AvailabilitySlot(
    val id: String = "",
    @SerialName("provider_id") val providerId: String = "",
    @SerialName("day_of_week") val dayOfWeek: Int = 0,
    @SerialName("start_time") val startTime: String = "09:00",
    @SerialName("end_time") val endTime: String = "17:00",
    @SerialName("slot_duration") val slotDuration: Int = 30,
    @SerialName("is_active") val isActive: Boolean = true
)

@Serializable
data class BlockedDate(
    val id: String = "",
    @SerialName("provider_id") val providerId: String = "",
    @SerialName("blocked_date") val blockedDate: String = "",
    val reason: String? = null
)
