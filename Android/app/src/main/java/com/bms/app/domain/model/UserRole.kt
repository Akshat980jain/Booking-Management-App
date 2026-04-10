package com.bms.app.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class UserRoleRow(
    val user_id: String,
    val role: String
)
