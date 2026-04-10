package com.bms.app.data.repository

import com.bms.app.domain.model.ProviderProfile
import com.bms.app.domain.model.UserProfile
import com.bms.app.domain.repository.ProfileRepository
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.rpc
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val postgrest: Postgrest
) : ProfileRepository {

    override suspend fun getCurrentUserProfile(userId: String): Result<UserProfile> {
        return try {
            val response = postgrest["profiles"]
                .select {
                    filter {
                        eq("user_id", userId)
                    }
                }
            val profile = response.decodeSingleOrNull<UserProfile>() ?: throw Exception("Profile not found")
            Result.success(profile)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getProviderProfile(userId: String): Result<ProviderProfile> {
        return try {
            val response = postgrest["provider_profiles"]
                .select {
                    filter {
                        eq("user_id", userId)
                    }
                }
            val profile = response.decodeSingleOrNull<ProviderProfile>() ?: throw Exception("Provider profile not found")
            Result.success(profile)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateUserProfile(fullName: String, phone: String?): Result<Unit> {
        return try {
            postgrest["profiles"].update({
                set("full_name", fullName)
                if (phone != null) set("phone", phone)
            }) {
                // filter by current user id
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateProviderProfile(profile: ProviderProfile): Result<Unit> {
        return try {
            postgrest["provider_profiles"].upsert(profile) {
                filter {
                    eq("user_id", profile.userId)
                }
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getAllProfiles(): Result<List<UserProfile>> {
        return try {
            val response = postgrest["profiles"]
                .select()
            val profiles = response.decodeList<UserProfile>()
            Result.success(profiles)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getAllProviderProfiles(): Result<List<ProviderProfile>> {
        return try {
            val response = postgrest["provider_profiles"]
                .select()
            val profiles = response.decodeList<ProviderProfile>()
            Result.success(profiles)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getProfileById(userId: String): Result<UserProfile> {
        return try {
            val response = postgrest["profiles"]
                .select {
                    filter {
                        eq("user_id", userId)
                    }
                }
            val profile = response.decodeSingle<UserProfile>()
            Result.success(profile)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deactivateUser(userId: String): Result<Unit> {
        return try {
            postgrest["profiles"].update({
                set("status", "inactive")
            }) {
                filter {
                    eq("user_id", userId)
                }
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun suspendUser(userId: String): Result<Unit> {
        return try {
            postgrest["profiles"].update({
                set("status", "suspended")
            }) {
                filter { eq("user_id", userId) }
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun banUser(userId: String): Result<Unit> {
        return try {
            postgrest["profiles"].update({
                set("status", "banned")
            }) {
                filter { eq("user_id", userId) }
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun changeUserRole(userId: String, newRole: String): Result<Unit> {
        return try {
            postgrest["profiles"].update({
                set("role", newRole)
            }) {
                filter { eq("user_id", userId) }
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateUserSettings(
        userId: String,
        language: String?,
        currency: String?,
        timeout: Int?,
        city: String?,
        country: String?
    ): Result<Unit> {
        return try {
            postgrest["profiles"].update({
                if (language != null) set("preferred_language", language)
                if (currency != null) set("preferred_currency", currency)
                if (timeout != null) set("session_timeout_minutes", timeout)
                if (city != null) set("city", city)
                if (country != null) set("country", country)
            }) {
                filter { eq("user_id", userId) }
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateTwoFa(userId: String, enabled: Boolean): Result<Unit> {
        return try {
            postgrest["profiles"].update({
                set("two_fa_enabled", enabled)
            }) {
                filter { eq("user_id", userId) }
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getProfilesByIds(userIds: List<String>): Result<List<UserProfile>> {
        if (userIds.isEmpty()) return Result.success(emptyList())
        return try {
            val response = postgrest["profiles"]
                .select {
                    filter {
                        isIn("user_id", userIds)
                    }
                }
            val profiles = response.decodeList<UserProfile>()
            Result.success(profiles)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun findAdminUserId(): Result<String> {
        return try {
            // The `user_roles` table uses RLS — providers can only read their OWN row.
            // We call a SECURITY DEFINER Postgres function which bypasses RLS safely.
            // To set it up, run this SQL once in your Supabase dashboard → SQL Editor:
            //
            //   CREATE OR REPLACE FUNCTION get_admin_user_id()
            //   RETURNS TEXT LANGUAGE SQL SECURITY DEFINER AS $$
            //     SELECT user_id FROM user_roles WHERE role = 'admin' LIMIT 1;
            //   $$;
            //
            // Grant access:
            //   GRANT EXECUTE ON FUNCTION get_admin_user_id() TO authenticated;

            val adminId = postgrest.rpc("get_admin_user_id")
                .decodeAs<String>()
                .trim('"') // Supabase wraps scalar results in quotes

            if (adminId.isBlank()) throw Exception("Admin user ID returned empty.")
            Result.success(adminId)
        } catch (e: Exception) {
            Result.failure(
                Exception(
                    "Could not reach platform support.\n\n" +
                    "Admin setup required: Run the following SQL in your Supabase " +
                    "dashboard (SQL Editor):\n\n" +
                    "CREATE OR REPLACE FUNCTION get_admin_user_id()\n" +
                    "RETURNS TEXT LANGUAGE SQL SECURITY DEFINER AS \$\$\n" +
                    "  SELECT user_id FROM user_roles WHERE role = 'admin' LIMIT 1;\n" +
                    "\$\$;\n" +
                    "GRANT EXECUTE ON FUNCTION get_admin_user_id() TO authenticated;"
                )
            )
        }
    }
}
