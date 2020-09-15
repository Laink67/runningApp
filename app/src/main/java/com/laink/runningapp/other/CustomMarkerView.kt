package com.laink.runningapp.other

import android.content.Context
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import com.laink.runningapp.db.Run
import kotlinx.android.synthetic.main.marker_view.view.*
import java.text.SimpleDateFormat
import java.util.*

class CustomMarkerView(
    val runs: List<Run>,
    context: Context,
    layoutId: Int
) : MarkerView(context, layoutId) {

    override fun getOffset(): MPPointF {
        // To good showing and not cut off
        return MPPointF(-width / 2f, -height.toFloat())
    }

    override fun refreshContent(entry: Entry?, highlight: Highlight?) {
        super.refreshContent(entry, highlight)

        // entry - (key, value)

        if (entry == null) {
            return
        } else {
            val currentRunId = entry.x.toInt()
            val currentRun = runs[currentRunId]

            val calendar = Calendar.getInstance().apply {
                timeInMillis = currentRun.timestamp
            }
            val dateFormat = SimpleDateFormat(Constants.DATE_FORMAT, Locale("RU"))
            tvDate.text = dateFormat.format(calendar.time)

            val avgSpeed = "${currentRun.avgSpeedInKMH} km/h"
            tvAvgSpeed.text = avgSpeed

            val distanceInKm = "${currentRun.distanceInMeters / 1000f} km"
            tvDistance.text = distanceInKm

            tvDuration.text = TrackingUtility.getFormattedStopWatchTime(currentRun.timeInMillis)

            val caloriesBurned = "${currentRun.caloriesBurned} kcal"
            tvCaloriesBurned.text = caloriesBurned

        }
    }

}