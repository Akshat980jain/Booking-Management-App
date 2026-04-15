package com.bms.app.data.repository

import com.bms.app.domain.model.AppConfig
import com.bms.app.domain.repository.ConfigRepository
import io.github.jan.supabase.postgrest.Postgrest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConfigRepositoryImpl @Inject constructor(
    private val postgrest: Postgrest
) : ConfigRepository {

    override suspend fun getAppConfig(): Result<AppConfig> {
        return try {
            val response = postgrest["app_config"]
                .select {
                    filter {
                        eq("id", 1) // We assume a single row for global config
                    }
                }
            val config = response.decodeSingleOrNull<AppConfig>() 
            if (config != null) {
                Result.success(config)
            } else {
                // Return default config if not found in DB
                Result.success(AppConfig())
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
