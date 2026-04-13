package com.bms.app.data.repository

import com.bms.app.domain.model.Review
import com.bms.app.domain.repository.ReviewRepository
import io.github.jan.supabase.postgrest.Postgrest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReviewRepositoryImpl @Inject constructor(
    private val postgrest: Postgrest
) : ReviewRepository {

    override suspend fun getReviewsForProvider(providerId: String): Result<List<Review>> {
        return try {
            val reviews = postgrest["reviews"]
                .select {
                    filter { eq("provider_id", providerId) }
                }
                .decodeList<Review>()
            Result.success(reviews)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun submitReview(review: Review): Result<Unit> {
        return try {
            postgrest["reviews"].insert(review)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteReview(reviewId: String): Result<Unit> {
        return try {
            postgrest["reviews"].delete {
                filter { eq("id", reviewId) }
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
