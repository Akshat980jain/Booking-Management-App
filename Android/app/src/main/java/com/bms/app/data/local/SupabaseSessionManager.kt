package com.bms.app.data.local

import android.content.Context
import io.github.jan.supabase.gotrue.SessionManager
import io.github.jan.supabase.gotrue.user.UserSession
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * Custom SessionManager for Supabase Auth that persists the UserSession
 * to Android SharedPreferences. This ensures the user stays logged in
 * across app restarts.
 */
class SupabaseSessionManager(context: Context) : SessionManager {
    private val prefs = context.getSharedPreferences("supabase_session_prefs", Context.MODE_PRIVATE)
    private val json = Json { 
        ignoreUnknownKeys = true
        isLenient = true
        encodeDefaults = true
    }

    override suspend fun saveSession(session: UserSession) {
        val sessionJson = json.encodeToString(session)
        prefs.edit().putString("current_session", sessionJson).apply()
    }

    override suspend fun loadSession(): UserSession? {
        val sessionJson = prefs.getString("current_session", null) ?: return null
        return try {
            json.decodeFromString<UserSession>(sessionJson)
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun deleteSession() {
        prefs.edit().remove("current_session").apply()
    }

    /**
     * Manual rescue method to get the raw string if the SDK loading fails.
     */
    fun getRawSession(): String? = prefs.getString("current_session", null)
}
