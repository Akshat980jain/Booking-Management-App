package com.bms.app.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserProfile(
    val id: String = "",
    @SerialName("user_id") val userId: String = "",
    @SerialName("full_name") val fullName: String = "",
    val email: String = "",
    val phone: String? = null,
    val role: String? = null,
    @SerialName("avatar_url") val avatarUrl: String? = null,
    val city: String? = null,
    val country: String? = null,
    @SerialName("preferred_language") val preferredLanguage: String? = "en-US",
    @SerialName("preferred_currency") val preferredCurrency: String? = "USD",
    @SerialName("session_timeout_minutes") val sessionTimeoutMinutes: Int? = 15,
    @SerialName("two_fa_enabled") val twoFaEnabled: Boolean? = false,
    val timezone: String? = "UTC",
    @SerialName("created_at") val createdAt: String = "",
    @SerialName("updated_at") val updatedAt: String = "",
    val status: String? = "active",
    @SerialName("insurance_provider") val insuranceProvider: String? = null,
    @SerialName("policy_number") val policyNumber: String? = null,
    @SerialName("insurance_card_url") val insuranceCardUrl: String? = null
)
