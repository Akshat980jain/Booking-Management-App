package com.bms.app.domain.repository

import com.bms.app.domain.model.ProviderProfile
import com.bms.app.domain.model.UserProfile

interface ProfileRepository {
    suspend fun getCurrentUserProfile(userId: String): Result<UserProfile>
    suspend fun getProviderProfile(userId: String): Result<ProviderProfile>
    suspend fun updateUserProfile(
        fullName: String, 
        phone: String? = null,
        insuranceProvider: String? = null,
        policyNumber: String? = null,
        insuranceCardUrl: String? = null
    ): Result<Unit>
    suspend fun updateProviderProfile(profile: ProviderProfile): Result<Unit>
    suspend fun getAllProfiles(): Result<List<UserProfile>>
    suspend fun getAllProviderProfiles(): Result<List<ProviderProfile>>
    suspend fun getProfileById(userId: String): Result<UserProfile>
    suspend fun deactivateUser(userId: String): Result<Unit>
    suspend fun suspendUser(userId: String): Result<Unit>
    suspend fun banUser(userId: String): Result<Unit>
    suspend fun changeUserRole(userId: String, newRole: String): Result<Unit>
    suspend fun updateUserSettings(userId: String, language: String? = null, currency: String? = null, timeout: Int? = null, city: String? = null, country: String? = null): Result<Unit>
    suspend fun updateTwoFa(userId: String, enabled: Boolean): Result<Unit>
    suspend fun getProfilesByIds(userIds: List<String>): Result<List<UserProfile>>
    /** Returns the userId of the first user whose role is ADMIN. */
    suspend fun findAdminUserId(): Result<String>
    
    // ── Favorites ──────────────────────────────────────
    suspend fun toggleFavorite(userId: String, providerProfileId: String): Result<Boolean>
    suspend fun getFavorites(userId: String): Result<List<String>>
}

