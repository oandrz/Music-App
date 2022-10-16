package com.dre.musicapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import eu.krzdabrowski.starter.BuildConfig
import timber.log.Timber
import timber.log.Timber.DebugTree

@HiltAndroidApp
class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }
}
