package com.tta.fitnessapplication.view.activity.tracker.SleepTracker.db

import androidx.lifecycle.LiveData
import com.tta.fitnessapplication.data.model.Hour
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HourRepository(private val sleepDao: HourSleepDao) {

    val readAllData: LiveData<List<Hour>> = sleepDao.readAllData()

    suspend fun addSleep(sleep: Hour) {
        sleepDao.addSleep(sleep)
    }

    suspend fun clearAll(){
        sleepDao.clearData()
    }

    suspend fun getSleepListByDate(date: String): List<Hour> {
        return withContext(Dispatchers.IO) {
            sleepDao.getSleepListByDate(date)
        }
    }

}