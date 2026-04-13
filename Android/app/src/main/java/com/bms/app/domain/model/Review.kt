package com.bms.app.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Review(
    val id: String = "",
    @SerialName("user_id") val userId: String = "",
    @SerialName("provider_id") val providerId: String = "",
    @SerialName("appointment_id") val appointmentId: String = "",
    val rating: Int = 0,
    @SerialName("review_text") val reviewText: String? = null,
    @SerialName("is_visible") val isVisible: Boolean = true,
    @SerialName("provider_response") val providerResponse: String? = null,
    @SerialName("provider_response_at") val providerResponseAt: String? = null,
    @SerialName("created_at") val createdAt: String = "",
    @SerialName("updated_at") val updatedAt: String = ""
)
