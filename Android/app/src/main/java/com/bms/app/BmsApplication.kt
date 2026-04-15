package com.bms.app

import android.app.Application
import com.bms.app.util.NotificationHelper
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BmsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Register notification channels (required for Android 8+)
        NotificationHelper.createNotificationChannels(this)
        // Initialize any other SDKs (e.g., Daily, Stripe) here
    }
}
