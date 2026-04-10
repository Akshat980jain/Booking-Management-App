package com.bms.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BmsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize any other SDKs (e.g., Daily, Stripe) here
    }
}
