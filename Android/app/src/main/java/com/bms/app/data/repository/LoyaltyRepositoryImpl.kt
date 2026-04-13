package com.bms.app.data.repository

import com.bms.app.domain.model.LoyaltyTransaction
import com.bms.app.domain.model.UserLoyalty
import com.bms.app.domain.repository.LoyaltyRepository
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.rpc
import javax.inject.Inject

class LoyaltyRepositoryImpl @Inject constructor(
    private val postgrest: Postgrest
) : LoyaltyRepository {

    override suspend fun getUserLoyalty(userId: String): Result<UserLoyalty> {
        return try {
            val response = postgrest["user_loyalty"]
                .select {
                    filter {
                        eq("user_id", userId)
                    }
                }
            val loyalty = response.decodeSingleOrNull<UserLoyalty>() ?: UserLoyalty(userId = userId)
            Result.success(loyalty)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getLoyaltyTransactions(userId: String): Result<List<LoyaltyTransaction>> {
        return try {
            val response = postgrest["loyalty_transactions"]
                .select {
                    filter {
                        eq("user_id", userId)
                    }
                }
            val transactions = response.decodeList<LoyaltyTransaction>()
            Result.success(transactions.sortedByDescending { it.createdAt })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun earnPoints(userId: String, points: Int, description: String): Result<Unit> {
        return try {
            // In a real app, this might be handled by a DB trigger on appointment completion
            // For porting parity, we'll provide the logic to insert a transaction.
            postgrest["loyalty_transactions"].insert(
                LoyaltyTransaction(userId = userId, points = points, type = "EARNED", description = description)
            )
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun redeemPoints(userId: String, points: Int, description: String): Result<Unit> {
        return try {
            postgrest["loyalty_transactions"].insert(
                LoyaltyTransaction(userId = userId, points = -points, type = "REDEEMED", description = description)
            )
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
