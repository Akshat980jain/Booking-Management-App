package com.bms.app.domain.repository

import com.bms.app.domain.model.AvailabilitySlot
import com.bms.app.domain.model.BlockedDate

interface AvailabilityRepository {
    suspend fun getWeeklySchedule(providerProfileId: String): Result<List<AvailabilitySlot>>
    suspend fun updateWeeklySchedule(slots: List<AvailabilitySlot>): Result<Unit>
    suspend fun getBlockedDates(providerProfileId: String): Result<List<BlockedDate>>
    suspend fun addBlockedDate(blockedDate: BlockedDate): Result<Unit>
    suspend fun removeBlockedDate(blockedDateId: String): Result<Unit>
}
