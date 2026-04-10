package com.bms.app.data.local

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("bms_session", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_USER_ROLE = "USER_ROLE"
        private const val ROLE_ADMIN = "ADMIN"
        private const val ROLE_PROVIDER = "PROVIDER"
        private const val ROLE_USER = "USER"
    }

    fun saveUserRole(role: String) {
        prefs.edit().putString(KEY_USER_ROLE, role).apply()
    }

    fun getUserRole(): String? {
        return prefs.getString(KEY_USER_ROLE, null)
    }

    fun clearSession() {
        prefs.edit().clear().apply()
    }
}
