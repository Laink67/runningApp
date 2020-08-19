package com.laink.runningapp.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.gms.maps.GoogleMap
import com.laink.runningapp.R
import com.laink.runningapp.other.Constants.ACTION_START_OR_RESUME_SERVICE
import com.laink.runningapp.services.TrackingService
import com.laink.runningapp.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_tracking.*

@AndroidEntryPoint
class TrackingFragment : Fragment(R.layout.fragment_tracking) {

    // Use "by viewModels()" and Dagger are going to select correct viewModel for us
    private val viewModel: MainViewModel by viewModels()
    private var map: GoogleMap? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView.onCreate(savedInstanceState)

        btnToggleRun.setOnClickListener {
            sendCommandServiceToService(ACTION_START_OR_RESUME_SERVICE)
        }

        mapView.getMapAsync {
            map = it
        }
    }

    private fun sendCommandServiceToService(action:String) =
        Intent(requireContext(), TrackingService::class.java).also {
            it.action = action

            requireContext().startService(it)
        }

    override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }

    override fun onStart() {
        super.onStart()
        mapView?.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView?.onStop()
    }

    override fun onPause() {
        super.onPause()
        mapView?.onPause()
    }

    // For saving some resources
    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }

    override fun onDestroy() {
        mapView?.onDestroy()
        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView?.onSaveInstanceState(outState) // For cache our map (we don't need to load it every time when we open the device)
    }
}