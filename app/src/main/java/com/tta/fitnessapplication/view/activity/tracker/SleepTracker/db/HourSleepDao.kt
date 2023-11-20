package com.tta.fitnessapplication.view.activity.tracker.SleepTracker.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.tta.fitnessapplication.data.model.Hour

@Dao
interface HourSleepDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addSleep(sleep: Hour)

    @Update
    suspend fun updateHour(hour: Hour)

    @Query("DELETE FROM hour_table")
    suspend fun clearData()

    @Query("SELECT * FROM hour_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Hour>>

    @Query("SELECT * FROM hour_table WHERE date = :inputDate")
    fun getSleepListByDate(inputDate: String): List<Hour>

    @Query("SELECT * FROM hour_table WHERE date = :date")
    suspend fun getHourByDate(date: String): Hour?
    @Transaction
    suspend fun insertOrUpdateHour(hour: Hour) {
        val existingHour = getHourByDate(hour.date)
        if (existingHour != null) {
            hour.id = existingHour.id // Make sure the ID is set for update
            updateHour(hour)
        } else {
            addSleep(hour)
        }
    }
}