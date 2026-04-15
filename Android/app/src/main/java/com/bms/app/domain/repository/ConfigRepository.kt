package com.bms.app.domain.repository

import com.bms.app.domain.model.AppConfig

interface ConfigRepository {
    suspend fun getAppConfig(): Result<AppConfig>
}
