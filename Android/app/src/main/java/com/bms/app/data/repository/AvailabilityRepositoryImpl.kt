package com.bms.app.data.repository

import com.bms.app.domain.model.AvailabilitySlot
import com.bms.app.domain.model.BlockedDate
import com.bms.app.domain.repository.AvailabilityRepository
import io.github.jan.supabase.postgrest.Postgrest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AvailabilityRepositoryImpl @Inject constructor(
    private val postgrest: Postgrest
) : AvailabilityRepository {

    override suspend fun getWeeklySchedule(providerProfileId: String): Result<List<AvailabilitySlot>> {
        return try {
            val slots = postgrest["provider_availability"]
                .select {
                    filter { eq("provider_id", providerProfileId) }
                }
                .decodeList<AvailabilitySlot>()
            Result.success(slots)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateWeeklySchedule(slots: List<AvailabilitySlot>): Result<Unit> {
        return try {
            // Note: Upsert implies handling of conflict, if any
            if (slots.isNotEmpty()) {
                postgrest["provider_availability"].upsert(slots)
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getBlockedDates(providerProfileId: String): Result<List<BlockedDate>> {
        return try {
            val dates = postgrest["provider_blocked_dates"]
                .select {
                    filter { eq("provider_id", providerProfileId) }
                }
                .decodeList<BlockedDate>()
            Result.success(dates)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun addBlockedDate(blockedDate: BlockedDate): Result<Unit> {
        return try {
            postgrest["provider_blocked_dates"].insert(blockedDate)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun removeBlockedDate(blockedDateId: String): Result<Unit> {
        return try {
            postgrest["provider_blocked_dates"].delete {
                filter { eq("id", blockedDateId) }
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
