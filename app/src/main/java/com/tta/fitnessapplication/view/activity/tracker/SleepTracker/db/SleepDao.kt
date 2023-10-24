package com.tta.fitnessapplication.view.activity.tracker.SleepTracker.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tta.fitnessapplication.data.model.Sleep
import com.tta.fitnessapplication.data.model.Water

@Dao
interface SleepDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addSleep(sleep: Sleep)

    @Query("SELECT * FROM sleep_planner ORDER BY id ASC")
    fun readAllData(): LiveData<List<Sleep>>

    @Query("SELECT * FROM sleep_planner WHERE date = :inputDate")
    fun getSleepListByDate(inputDate: String): List<Sleep>
}