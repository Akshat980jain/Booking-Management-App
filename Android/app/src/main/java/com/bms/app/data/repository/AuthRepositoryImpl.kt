package com.bms.app.data.repository

import com.bms.app.domain.repository.AuthRepository
import com.bms.app.domain.model.UserRoleRow
import com.bms.app.ui.auth.AccessLevel
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.postgrest.Postgrest
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val auth: Auth,
    private val postgrest: Postgrest
) : AuthRepository {

    override suspend fun signIn(email: String, password: String): Result<AccessLevel> {
        return try {
            auth.signInWith(Email) {
                this.email = email
                this.password = password
            }

            val userId = auth.currentSessionOrNull()?.user?.id
                ?: return Result.failure(Exception("Login succeeded but no user ID found."))

            val role = getUserRole(userId).getOrNull() ?: AccessLevel.USER
            Result.success(role)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun signUp(
        email: String,
        password: String,
        fullName: String,
        role: AccessLevel
    ): Result<AccessLevel> {
        return try {
            // Pass role and full_name via metadata — the DB trigger `handle_new_user`
            // auto-creates the profile row and user_roles row from raw_user_meta_data.
            val supabaseRole = when (role) {
                AccessLevel.ADMIN -> "admin"
                AccessLevel.PROVIDER -> "provider"
                AccessLevel.USER -> "user"
            }

            auth.signUpWith(Email) {
                this.email = email
                this.password = password
                this.data = buildJsonObject {
                    put("full_name", fullName)
                    put("role", supabaseRole)
                }
            }

            Result.success(role)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getUserRole(userId: String): Result<AccessLevel> {
        return try {
            val res = postgrest["user_roles"]
                .select {
                    filter { eq("user_id", userId) }
                }
                .decodeSingleOrNull<UserRoleRow>()

            val level = when (res?.role) {
                "admin" -> AccessLevel.ADMIN
                "provider" -> AccessLevel.PROVIDER
                else -> AccessLevel.USER
            }
            Result.success(level)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getCurrentUserId(): String? {
        return auth.currentSessionOrNull()?.user?.id
    }

    override suspend fun resetPassword(email: String): Result<Unit> {
        return try {
            auth.resetPasswordForEmail(email)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
