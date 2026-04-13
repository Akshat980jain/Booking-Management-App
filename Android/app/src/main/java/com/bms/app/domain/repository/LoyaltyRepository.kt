package com.bms.app.domain.repository

import com.bms.app.domain.model.LoyaltyTransaction
import com.bms.app.domain.model.UserLoyalty

interface LoyaltyRepository {
    suspend fun getUserLoyalty(userId: String): Result<UserLoyalty>
    suspend fun getLoyaltyTransactions(userId: String): Result<List<LoyaltyTransaction>>
    suspend fun earnPoints(userId: String, points: Int, description: String): Result<Unit>
    suspend fun redeemPoints(userId: String, points: Int, description: String): Result<Unit>
}
