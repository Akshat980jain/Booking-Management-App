package com.bms.app.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VideoRoomInfo(
    val url: String,
    val token: String? = null,
    @SerialName("room_name") val roomName: String? = null
)
