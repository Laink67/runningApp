package com.laink.runningapp.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.laink.runningapp.R
import com.laink.runningapp.other.CustomMarkerView
import com.laink.runningapp.other.TrackingUtility
import com.laink.runningapp.ui.viewmodels.StatisticsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_statistics.*

@AndroidEntryPoint
class StatisticsFragment : Fragment(R.layout.fragment_statistics) {

    private val viewModel: StatisticsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeToObservers()
        setUpBarChart()
    }

    private fun setUpBarChart() {
        barChart.xAxis.apply {
            position = XAxis.XAxisPosition.BOTTOM
            setDrawLabels(false) // disable labels
            axisLineColor = Color.WHITE
            textColor = Color.WHITE
            setDrawGridLines(false) // disable grid in our graph
        }

        barChart.axisLeft.apply {
            axisLineColor = Color.WHITE
            textColor = Color.WHITE
            setDrawGridLines(false)
        }

        barChart.axisRight.apply {
            axisLineColor = Color.WHITE
            textColor = Color.WHITE
            setDrawGridLines(false)
        }

        barChart.apply {
            description.text = "Avg speed"
            legend.isEnabled = false
        }
    }

    private fun subscribeToObservers() {
        viewModel.totalTimeRun.observe(viewLifecycleOwner, Observer {
            it?.let {
                val totalTimeRun = TrackingUtility.getFormattedStopWatchTime(it)
                tvTotalTime.text = totalTimeRun
            }
        })

        viewModel.totalDistance.observe(viewLifecycleOwner, Observer {
            it?.let {
                // We get total distance in meters
                val km = it / 1000F
                val totalDistance = TrackingUtility.roundToDecimal(km)
                val totalDistanceText = "$totalDistance km"
                tvTotalDistance.text = totalDistanceText
            }
        })

        viewModel.totalAvgSpeed.observe(viewLifecycleOwner, Observer {
            it?.let {
                val avgSpeed = TrackingUtility.roundToDecimal(it)
                val avgSpeedText = "$avgSpeed km/h"
                tvAverageSpeed.text = avgSpeedText
            }
        })

        viewModel.totalCaloriesBurned.observe(viewLifecycleOwner, Observer {
            it?.let {
                val totalCaloriesText = "$it kcal"
                tvTotalCalories.text = totalCaloriesText
            }
        })

        viewModel.runsByDate.observe(viewLifecycleOwner, Observer {
            it?.let {
                val allAvgSpeeds =
                    it.indices.map { i -> BarEntry(i.toFloat(), it[i].avgSpeedInKMH) }
                val barDataSet = BarDataSet(allAvgSpeeds, "Avg Speed").apply {
                    valueTextColor = Color.WHITE
                    color = ContextCompat.getColor(requireContext(), R.color.colorAccent)
                }

                barChart.data = BarData(barDataSet)
                // By initially order
                barChart.marker =
                    CustomMarkerView(it.reversed(), requireContext(), R.layout.marker_view)
                barChart.invalidate() // Updating barChart
            }
        })
    }
}