package com.alifwanoon.v72

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AlifApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // Init K constants check
        require(com.alifwanoon.v72.core.K.ADMIN_TOKEN == "ADMIN-ELITE-2024")
    }
}
