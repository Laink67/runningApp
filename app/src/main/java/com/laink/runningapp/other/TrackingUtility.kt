package com.laink.runningapp.other

import android.Manifest
import android.content.Context
import android.location.Location
import android.os.Build
import com.laink.runningapp.services.Polyline
import pub.devrel.easypermissions.EasyPermissions
import java.util.concurrent.TimeUnit
import kotlin.math.round

object TrackingUtility {

    // To check if the user already accepted location permission or not
    fun hasLocationPermissions(context: Context) =
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            EasyPermissions.hasPermissions(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        } else {
            EasyPermissions.hasPermissions(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            )
        }

    fun getFormattedStopWatchTime(ms: Long, includeMillis: Boolean = false): String {
        var milliseconds = ms

        val hours = TimeUnit.MILLISECONDS.toHours(milliseconds)
        milliseconds -= TimeUnit.HOURS.toMillis(hours)

        val minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds)
        milliseconds -= TimeUnit.MINUTES.toMillis(minutes)

        val seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds)

        if (!includeMillis) {
            return "${checkTheNeedZero(hours)}:" +
                    "${checkTheNeedZero(minutes)}:" +
                    checkTheNeedZero(seconds)
        }

        milliseconds -= TimeUnit.SECONDS.toMillis(seconds)
        // For getting two digit number (not three)
        milliseconds /= 10

        return "${checkTheNeedZero(hours)}:" +
                "${checkTheNeedZero(minutes)}:" +
                "${checkTheNeedZero(seconds)}:" +
                checkTheNeedZero(milliseconds)
    }

    private fun checkTheNeedZero(time: Long) =
        "${if (time < 10) "0" else ""}$time"

    fun calculatePolylineLength(polyline: Polyline): Float {
        var distance = 0f

        for (i in 0..polyline.size - 2) {
            val firstPosition = polyline[i]
            val nextPosition = polyline[i + 1]
            val result = FloatArray(1)

            Location.distanceBetween(
                firstPosition.latitude,
                firstPosition.longitude,
                nextPosition.latitude,
                nextPosition.longitude,
                result
            )

            distance += result[0]
        }

        return distance
    }

    // To get in seconds, then in minutes, then in hours
    fun getFloatHoursFromMS(ms: Long): Float = ms / 1000f / 60 / 60

    fun roundToDecimal(number: Float) = round(number * 10) / 10f
}