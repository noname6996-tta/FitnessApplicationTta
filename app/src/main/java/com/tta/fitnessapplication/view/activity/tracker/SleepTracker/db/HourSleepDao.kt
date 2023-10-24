package com.tta.fitnessapplication.view.activity.tracker.SleepTracker.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tta.fitnessapplication.data.model.Hour

@Dao
interface HourSleepDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addSleep(sleep: Hour)

    @Query("SELECT * FROM hour_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Hour>>

    @Query("SELECT * FROM hour_table WHERE date = :inputDate")
    fun getSleepListByDate(inputDate: String): List<Hour>
}