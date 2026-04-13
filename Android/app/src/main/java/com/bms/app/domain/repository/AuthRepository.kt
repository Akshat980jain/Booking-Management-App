package com.bms.app.domain.repository

import com.bms.app.ui.auth.AccessLevel

interface AuthRepository {
    suspend fun signIn(email: String, password: String): Result<AccessLevel>
    suspend fun signUp(email: String, password: String, fullName: String, role: AccessLevel): Result<AccessLevel>
    suspend fun getUserRole(userId: String): Result<AccessLevel>
    fun getCurrentUserId(): String?
    suspend fun resetPassword(email: String): Result<Unit>
}
