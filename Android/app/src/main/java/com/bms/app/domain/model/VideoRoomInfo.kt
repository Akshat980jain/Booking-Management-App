package com.bms.app.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VideoRoomInfo(
    @SerialName("room_url") val url: String,       // Edge function returns "room_url"
    val token: String? = null,
    @SerialName("room_name") val roomName: String? = null,
    @SerialName("user_name") val userName: String? = null,
    @SerialName("is_provider") val isProvider: Boolean? = null
)
