package com.laink.runningapp.repositories

import com.laink.runningapp.db.Run
import com.laink.runningapp.db.RunDAO
import javax.inject.Inject

class MainRepository @Inject constructor(
    val runDao: RunDAO
) {

    suspend fun insertRun(run: Run) = runDao.insert(run)

    suspend fun deleteRun(run: Run) = runDao.delete(run)

    fun getAllRunsByDate() =
        runDao.getAllByDate() // Not a suspend fun because we turns a livedata objects and livedata is asynchronous

    fun getAllRunsByDistance() = runDao.getAllByDistance()

    fun getAllRunsaByAvgSpeed() = runDao.getAllByAvgSpeed()

    fun getAllRunsByTimeInMillis() = runDao.getAllByTimeInMillis()

    fun getAllRunsByCaloriesBurned() = runDao.getAllByCaloriesBurned()

    fun getTotalAvgSpeed() = runDao.getAvgSpeed()

    fun getTotalDistance() = runDao.getTotalDistance()

    fun getTotalCaloriesBurned() = runDao.getTotalCaloriesBurned()

    fun getTotalTimeInMillis() = runDao.getTotalTimeInMillis()
}