package com.bms.app.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FavoriteProvider(
    val id: String = "",
    @SerialName("user_id") val userId: String = "",
    @SerialName("provider_id") val providerId: String = "",
    @SerialName("created_at") val createdAt: String = ""
)
