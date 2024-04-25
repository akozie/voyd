package com.example.voyd

import android.app.Application
import com.dsofttech.dprefs.utils.DPrefs
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class VoydApp(): Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize the Shared Preference Library
        DPrefs.initializeDPrefs(this)
    }
}