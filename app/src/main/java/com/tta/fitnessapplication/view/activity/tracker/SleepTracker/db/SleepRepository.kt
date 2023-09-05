package com.tta.fitnessapplication.view.activity.tracker.SleepTracker.db

import androidx.lifecycle.LiveData
import com.tta.fitnessapplication.data.model.Sleep

class SleepRepository(private val sleepDao: SleepDao) {

    val readAllData: LiveData<List<Sleep>> = sleepDao.readAllData()

    suspend fun addSleep(sleep: Sleep) {
        sleepDao.addSleep(sleep)
    }
}