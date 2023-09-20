package com.tta.fitnessapplication.view.activity.tracker.watertracker.db

import androidx.lifecycle.LiveData
import com.tta.fitnessapplication.data.model.Water
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WaterRepository(private val waterDao: WaterDao) {

    val readAllData: LiveData<List<Water>> = waterDao.readAllData()
    suspend fun addWater(water: Water) {
        waterDao.addWater(water)
    }

    suspend fun getWaterListByDate(date: String): List<Water> {
        return withContext(Dispatchers.IO) {
            waterDao.getWaterListByDate(date)
        }
    }
}