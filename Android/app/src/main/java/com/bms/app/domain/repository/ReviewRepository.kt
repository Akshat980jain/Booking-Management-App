package com.bms.app.domain.repository

import com.bms.app.domain.model.Review

interface ReviewRepository {
    suspend fun getReviewsForProvider(providerId: String): Result<List<Review>>
    suspend fun submitReview(review: Review): Result<Unit>
    suspend fun deleteReview(reviewId: String): Result<Unit>
}
