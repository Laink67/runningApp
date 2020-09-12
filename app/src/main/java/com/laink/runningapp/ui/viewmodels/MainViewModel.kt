package com.laink.runningapp.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.laink.runningapp.db.Run
import com.laink.runningapp.repositories.MainRepository
import kotlinx.coroutines.launch

// To pass parameters to viewModels we need to create a viewModelFactory,
// that's why we need to use @ViewModelInject instead @Inject
class MainViewModel @ViewModelInject constructor(
    val mainRepository: MainRepository // We don't need to define new function to provide repository in AppModule because
// in mainRepository we need only one parameter runDao and Dagger Hilt know how to get this (we have provideRunDao function)
) : ViewModel() {

    val runsByDate = mainRepository.getAllRunsByDate()

    fun insertRun(run: Run) = viewModelScope.launch {
        mainRepository.insertRun(run)
    }
}