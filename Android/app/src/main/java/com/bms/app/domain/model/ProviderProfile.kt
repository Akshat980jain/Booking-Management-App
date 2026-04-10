package com.bms.app.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProviderProfile(
    val id: String = "",
    @SerialName("user_id") val userId: String = "",
    val profession: String = "",
    val specialty: String? = null,
    val bio: String? = null,
    val location: String? = null,
    @SerialName("years_of_experience") val yearsOfExperience: Int = 0,
    val phone: String? = null,
    @SerialName("consultation_fee") val consultationFee: Double = 0.0,
    @SerialName("video_consultation_fee") val videoConsultationFee: Double? = null,
    @SerialName("video_enabled") val videoEnabled: Boolean = false,
    @SerialName("is_active") val isActive: Boolean = true,
    @SerialName("is_approved") val isApproved: Boolean = false,
    @SerialName("is_verified") val isVerified: Boolean = false,
    @SerialName("buffer_time_after") val bufferTimeAfter: Int = 15,
    val timezone: String = "UTC",
    @SerialName("average_rating") val averageRating: Double? = null,
    @SerialName("total_reviews") val totalReviews: Int = 0,
    @SerialName("created_at") val createdAt: String = "",
    @SerialName("updated_at") val updatedAt: String = ""
)
