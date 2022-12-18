package com.dre.money

import android.app.Application
import com.dre.money.BuildConfig.*
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import timber.log.Timber.DebugTree

@HiltAndroidApp
class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        if (DEBUG) {
            Timber.plant(DebugTree())
        }
    }
}
