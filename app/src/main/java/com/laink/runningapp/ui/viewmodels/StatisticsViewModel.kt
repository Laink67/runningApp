package com.laink.runningapp.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.laink.runningapp.repositories.MainRepository

// To pass parameters to viewModels we need to create a viewModelFactory,
// that's why we need to use @ViewModelInject instead @Inject
class StatisticsViewModel @ViewModelInject constructor(
    val mainRepository: MainRepository // We don't need to define new function to provide repository in AppModule because
// in mainRepository we need only one parameter runDao and Dagger Hilt know how to get this (we have provideRunDao function)
) : ViewModel() {
}