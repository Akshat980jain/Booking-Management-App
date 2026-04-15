package com.bms.app.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AppConfig(
    val id: Int = 1,
    @SerialName("min_required_version_name") val minRequiredVersionName: String = "1.0",
    @SerialName("latest_version_name") val latestVersionName: String = "1.0",
    @SerialName("update_url") val updateUrl: String? = null,
    @SerialName("maintenance_mode") val maintenanceMode: Boolean = false,
    @SerialName("welcome_message") val welcomeMessage: String? = null
)
