package com.laink.runningapp.ui.fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.laink.runningapp.R
import com.laink.runningapp.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TrackingFragment : Fragment(R.layout.fragment_tracking) {

    // Use "by viewModels()" and Dagger are going to select correct viewModel for us
    private val viewModel: MainViewModel by viewModels()

}