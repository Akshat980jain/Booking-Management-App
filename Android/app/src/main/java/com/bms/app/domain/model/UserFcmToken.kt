package com.bms.app.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserFcmToken(
    @SerialName("user_id") val userId: String,
    @SerialName("fcm_token") val fcmToken: String,
    @SerialName("device_name") val deviceName: String? = null
)
