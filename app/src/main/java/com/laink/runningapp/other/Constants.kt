package com.laink.runningapp.other

import android.graphics.Color

object Constants {

    const val TITLE_APP = "Running app"

    const val RUNNING_DATABASE_NAME = "running_db"

    const val REQUEST_CODE_LOCATION_PERMISSION = 100
    const val MESSAGE_LOCATION_PERMISSION =
        "You need to accept location permission for tracking your run"

    const val ACTION_START_OR_RESUME_SERVICE = "ACTION_START_OR_RESUME_SERVICE"
    const val ACTION_PAUSE_SERVICE = "ACTION_PAUSE_SERVICE"
    const val ACTION_STOP_SERVICE = "ACTION_STOP_SERVICE"
    const val ACTION_SHOW_TRACKING_FRAGMENT = "ACTION_SHOW_TRACKING_FRAGMENT"

    const val TIMER_UPDATE_INTERVAL = 50L
    const val ONE_SECOND_IN_MILLIS = 1000L

    const val LOCATION_UPDATE_INTERVAL = 5000L
    const val FASTEST_LOCATION_INTERVAL = 2000L

    const val POLYLINE_COLOR = Color.RED
    const val POLYLINE_WIDTH = 8F
    const val MAP_ZOOM = 15F

    const val BTN_TOGGLE_RUN_START = "Start"
    const val BTN_TOGGLE_RUN_STOP = "Stop"

    const val NOTIFICATION_CHANNEL_ID = "tracking_channel"
    const val NOTIFICATION_CHANNEL_NAME = "Tracking"
    const val NOTIFICATION_ID = 1
    const val NOTIFICATION_PAUSE_TEXT = "Pause"
    const val NOTIFICATION_RESUME_TEXT = "Resume"

    const val M_ACTIONS = "mActions"

    const val PADDING_MAP_FOR_SCREENSHOT = 0.05F

    const val SUCCESSFUL_RUN_SAVING_MESSAGE = "Run saved successfully"

    const val DATE_FORMAT = "dd.MM.yy"
}