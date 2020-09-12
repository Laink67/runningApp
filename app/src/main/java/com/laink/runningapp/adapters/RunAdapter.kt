package com.laink.runningapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.laink.runningapp.R
import com.laink.runningapp.db.Run
import com.laink.runningapp.other.Constants.DATE_FORMAT
import com.laink.runningapp.other.TrackingUtility
import kotlinx.android.synthetic.main.item_run.view.*
import java.text.SimpleDateFormat
import java.util.*

class RunAdapter : RecyclerView.Adapter<RunAdapter.RunViewHolder>() {

    inner class RunViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    val differCallback = object : DiffUtil.ItemCallback<Run>() {
        override fun areItemsTheSame(oldItem: Run, newItem: Run): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Run, newItem: Run): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    fun submitList(list: List<Run>) = differ.submitList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RunViewHolder {
        return RunViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_run,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RunViewHolder, position: Int) {
        val currentRun = differ.currentList[position]

        holder.itemView.apply {
            Glide.with(this).load(currentRun.img).into(
                ivRunImage
            )

            val calendar = Calendar.getInstance().apply {
                timeInMillis = currentRun.timestamp
            }
            val dateFormat = SimpleDateFormat(DATE_FORMAT, Locale("RU"))
            tvDate.text = dateFormat.format(calendar.time)

            val avgSpeed = "${currentRun.avgSpeedInKMH} km/h"
            tvAvgSpeed.text = avgSpeed

            val distanceInKm = "${currentRun.distanceInMeters / 1000f} km"
            tvDistance.text = distanceInKm

            tvTime.text = TrackingUtility.getFormattedStopWatchTime(currentRun.timeInMillis)

            val caloriesBurned = "${currentRun.caloriesBurned} kcal"
            tvCalories.text = caloriesBurned
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}