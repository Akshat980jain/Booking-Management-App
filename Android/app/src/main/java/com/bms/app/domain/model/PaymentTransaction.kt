package com.bms.app.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class PaymentTransaction(
    val id: String = "",
    val title: String = "",
    val subtitle: String = "",
    val amount: Double = 0.0,
    val type: String = "credit", // credit (incoming), debit (outgoing)
    val status: String = "completed",
    val date: String = "",
    val isIncoming: Boolean = true
)
