package com.tta.fitnessapplication.view.activity.tracker.SleepTracker.db

import androidx.lifecycle.LiveData
import com.tta.fitnessapplication.data.model.Sleep
import com.tta.fitnessapplication.data.model.SleepPair
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SleepRepository(private val sleepDao: SleepDao) {

    val readAllData: LiveData<List<Sleep>> = sleepDao.readAllData()

    suspend fun addSleep(sleep: Sleep) {
        sleepDao.addSleep(sleep)
    }

    suspend fun getAdjacentSleeps(): List<SleepPair> {
        return withContext(Dispatchers.IO) {
            sleepDao.getSleepPairs()
        }
    }

    suspend fun getItemById(id1: Int,id2: Int): List<Sleep> {
        return withContext(Dispatchers.IO) {
            sleepDao.getSleepsByIds(id1,id2)
        }
    }

    suspend fun getSleepListByDate(date: String): List<Sleep> {
        return withContext(Dispatchers.IO) {
            sleepDao.getSleepListByDate(date)
        }
    }

    suspend fun updateSleepTime(
        newDate: String,
        newTime: String,
        existingDate: String,
        existingValue: String
    ) {
        return withContext(Dispatchers.IO) {
            sleepDao.updateSleepTime(newDate, newTime, existingDate, existingValue)
        }
    }
}