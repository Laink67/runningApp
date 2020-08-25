package com.laink.runningapp.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_LOW
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import com.laink.runningapp.R
import com.laink.runningapp.other.Constants.ACTION_PAUSE_SERVICE
import com.laink.runningapp.other.Constants.ACTION_SHOW_TRACKING_FRAGMENT
import com.laink.runningapp.other.Constants.ACTION_START_OR_RESUME_SERVICE
import com.laink.runningapp.other.Constants.ACTION_STOP_SERVICE
import com.laink.runningapp.other.Constants.NOTIFICATION_CHANNEL_ID
import com.laink.runningapp.other.Constants.NOTIFICATION_CHANNEL_NAME
import com.laink.runningapp.other.Constants.NOTIFICATION_ID
import com.laink.runningapp.other.Constants.TITLE_APP
import com.laink.runningapp.ui.MainActivity
import timber.log.Timber

// By inheriting from LifecycleService() we can tell our LiveData observed function
// in which state our TrackingService currently is
class TrackingService : LifecycleService() {

    var isFirstRun = true

    // Get called whenever we send a command to our service so whenever we send an intent to this service class
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            when (it.action) {
                ACTION_START_OR_RESUME_SERVICE -> {

                    if (isFirstRun) {
                        startForegroundService()
                        isFirstRun = false
                    } else {
                        Timber.d("Resuming service...")
                    }
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

    private fun startForegroundService() {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE)
                as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(notificationManager)
        }

        val notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setAutoCancel(false) // If the user clicks on notification
            // then "true" - notification disappears, "false" - notification is always active
            .setOngoing(true) // Notification can't be swiped away
            .setSmallIcon(R.drawable.ic_directions_run_black_24dp)
            .setContentTitle(TITLE_APP)
            .setContentText("00:00:00")
            .setContentIntent(getMainActivityPendingIntent())

        startForeground(NOTIFICATION_ID, notificationBuilder.build()) // start ForegroundService
    }

    private fun getMainActivityPendingIntent() = PendingIntent.getActivity(
        this,
        0,
        Intent(this, MainActivity::class.java).also {
            it.action = ACTION_SHOW_TRACKING_FRAGMENT
        },
        FLAG_UPDATE_CURRENT // Whenever we launch that pending intent it will update
    )

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(notificationManager: NotificationManager) {
        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            NOTIFICATION_CHANNEL_NAME,
            IMPORTANCE_LOW // if we choose importance above IMPORTANCE_LOW
            // we will get phone ring each time we update our notification
        )

        notificationManager.createNotificationChannel(channel)
    }
}