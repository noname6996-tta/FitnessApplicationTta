package com.tta.fitnessapplication.view.activity.tracker.watertracker.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tta.fitnessapplication.data.model.Meal
import com.tta.fitnessapplication.data.model.Water

@Dao
interface WaterDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addWater(water: Water)

    @Query("SELECT * FROM water_planner ORDER BY id ASC")
    fun readAllData(): LiveData<List<Water>>
}