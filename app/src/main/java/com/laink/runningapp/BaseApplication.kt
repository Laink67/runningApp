package com.laink.runningapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp // that means when we launch our app it is alredy clear which dependencies will be injected into which classes
class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree()) // to enable debug logging with a timber library
    }
}