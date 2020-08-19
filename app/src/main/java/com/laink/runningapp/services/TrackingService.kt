package com.laink.runningapp.services

import android.content.Intent
import androidx.lifecycle.LifecycleService
import com.laink.runningapp.other.Constants.ACTION_PAUSE_SERVICE
import com.laink.runningapp.other.Constants.ACTION_START_OR_RESUME_SERVICE
import com.laink.runningapp.other.Constants.ACTION_STOP_SERVICE
import timber.log.Timber

// By inheriting from LifecycleService() we can tell our LiveData observed function
// in which state our TrackingService currently is
class TrackingService : LifecycleService() {

    // Get called whenever we send a command to our service so whenever we send an intent to this service class
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            when (it.action) {
                ACTION_START_OR_RESUME_SERVICE -> {
                    Timber.d("Started or resumed service")
                }
                ACTION_PAUSE_SERVICE -> {
                    Timber.d("Paused service")
                }
                ACTION_STOP_SERVICE -> {
                    Timber.d("Stop service")
                }
            }
        }

        return super.onStartCommand(intent, flags, startId)
    }
}