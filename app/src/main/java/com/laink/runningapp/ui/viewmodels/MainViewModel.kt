package com.laink.runningapp.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.laink.runningapp.db.Run
import com.laink.runningapp.other.SortedType
import com.laink.runningapp.repositories.MainRepository
import kotlinx.coroutines.launch

// To pass parameters to viewModels we need to create a viewModelFactory,
// that's why we need to use @ViewModelInject instead @Inject
class MainViewModel @ViewModelInject constructor(
    val mainRepository: MainRepository // We don't need to define new function to provide repository in AppModule because
// in mainRepository we need only one parameter runDao and Dagger Hilt know how to get this (we have provideRunDao function)
) : ViewModel() {

    private val runsByDate = mainRepository.getAllRunsByDate()
    private val runsByDistance = mainRepository.getAllRunsByDistance()
    private val runsByCalories = mainRepository.getAllRunsByCaloriesBurned()
    private val runsByAvgSpeed = mainRepository.getAllRunsByAvgSpeed()
    private val runsByTime = mainRepository.getAllRunsByTimeInMillis()

    val runs = MediatorLiveData<List<Run>>()

    var sortedType = SortedType.DATE

    init {
        // When runsByDate or others change, we will observe
        runs.addSource(runsByDate) { result ->
            if (sortedType == SortedType.DATE) {
                result?.let { runs.value = it }
            }
        }

        runs.addSource(runsByAvgSpeed) { result ->
            if (sortedType == SortedType.AVG_SPEED) {
                result?.let { runs.value = it }
            }
        }

        runs.addSource(runsByTime) { result ->
            if (sortedType == SortedType.RUNNING_TIME) {
                result?.let { runs.value = it }
            }
        }

        runs.addSource(runsByCalories) { result ->
            if (sortedType == SortedType.CALORIES_BURNED) {
                result?.let { runs.value = it }
            }
        }

        runs.addSource(runsByDistance) { result ->
            if (sortedType == SortedType.DISTANCE) {
                result?.let { runs.value = it }
            }
        }
    }

    fun sortRuns(sortedType: SortedType) = when (sortedType) {
        SortedType.DATE -> runsByDate.value?.let { runs.value = it }
        SortedType.CALORIES_BURNED -> runsByCalories.value?.let { runs.value = it }
        SortedType.RUNNING_TIME -> runsByTime.value?.let { runs.value = it }
        SortedType.AVG_SPEED -> runsByAvgSpeed.value?.let { runs.value = it }
        SortedType.DISTANCE -> runsByDistance.value?.let { runs.value = it }
    }.also {
        this.sortedType = sortedType
    }

    fun insertRun(run: Run) = viewModelScope.launch {
        mainRepository.insertRun(run)
    }


}