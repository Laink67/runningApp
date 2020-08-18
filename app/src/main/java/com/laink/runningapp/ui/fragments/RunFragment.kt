package com.laink.runningapp.ui.fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.laink.runningapp.R
import com.laink.runningapp.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RunFragment : Fragment(R.layout.fragment_run) {

    // Use "by viewModels()" and Dagger are going to select correct viewModel for us
    private val viewModel: MainViewModel by viewModels()
}