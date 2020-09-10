package com.laink.runningapp.di

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.laink.runningapp.R
import com.laink.runningapp.other.Constants
import com.laink.runningapp.ui.MainActivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ServiceScoped
import javax.inject.Inject
import javax.inject.Singleton

// There are all dependencies for our TrackingService
@Module
@InstallIn(ServiceComponent::class)
object ServiceModule {

    @ServiceScoped  // It's like @Singleton but only for services
    @Provides
    fun provideFusedLocationProviderClient(
        @ApplicationContext context: Context
    ) = FusedLocationProviderClient(context)

    @ServiceScoped
    @Provides
    fun provideMainActivityPendingIntent(
        @ApplicationContext context: Context
    ) = PendingIntent.getActivity(
        context,
        0,
        Intent(context, MainActivity::class.java).also {
            it.action = Constants.ACTION_SHOW_TRACKING_FRAGMENT
        },
        PendingIntent.FLAG_UPDATE_CURRENT // Whenever we launch that pending intent it will update
    )

    @ServiceScoped
    @Provides
    fun provideBaseNotificationBuilder(
        @ApplicationContext context: Context,
        pendingIntent: PendingIntent
    ) = NotificationCompat.Builder(context, Constants.NOTIFICATION_CHANNEL_ID)
        .setAutoCancel(false) // If the user clicks on notification
        // then "true" - notification disappears, "false" - notification is always active
        .setOngoing(true) // Notification can't be swiped away
        .setSmallIcon(R.drawable.ic_directions_run_black_24dp)
        .setContentTitle(Constants.TITLE_APP)
        .setContentText("00:00:00")
        .setContentIntent(pendingIntent)
}