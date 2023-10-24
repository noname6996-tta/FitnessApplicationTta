package com.tta.fitnessapplication.view.activity.tracker.SleepTracker.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tta.fitnessapplication.data.model.Sleep
import com.tta.fitnessapplication.data.model.Water
import java.util.Date

@Dao
interface SleepDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addSleep(sleep: Sleep)

    @Query("SELECT * FROM sleep_planner ORDER BY date ASC")
    fun readAllData(): LiveData<List<Sleep>>

    @Query("SELECT * FROM sleep_planner WHERE date = :inputDate")
    fun getSleepListByDate(inputDate: String): List<Sleep>

    @Query("UPDATE sleep_planner " +
            "SET date = :newDate, time = :newTime " +
            "WHERE date = :existingDate AND value = :existingValue")
    fun updateSleepTime(newDate: String,newTime : String, existingDate : String,existingValue: String)

    @Query("""
        SELECT * FROM sleep_planner 
        WHERE value IN ('Bed', 'Wake') AND
        date IN (
            SELECT date FROM sleep_planner 
            WHERE value = 'Bed'
        ) 
        ORDER BY date ASC
        LIMIT 2
    """)
    fun getAdjacentSleeps(): List<Sleep>
}