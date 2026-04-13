package com.bms.app.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoyaltyTransaction(
    val id: String = "",
    @SerialName("user_id") val userId: String = "",
    val points: Int = 0,
    val type: String = "EARNED", // EARNED, REDEEMED
    val description: String = "",
    @SerialName("created_at") val createdAt: String = ""
)

@Serializable
data class UserLoyalty(
    @SerialName("user_id") val userId: String = "",
    @SerialName("total_points") val totalPoints: Int = 0,
    @SerialName("tier") val tier: String = "Bronze" // Bronze, Silver, Gold, Platinum
)
