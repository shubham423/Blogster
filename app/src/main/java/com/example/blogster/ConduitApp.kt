package com.example.blogster

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ConduitApp : Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: ConduitApp? = null
        fun applicationContext(): ConduitApp {
            return instance as ConduitApp
        }
    }
}