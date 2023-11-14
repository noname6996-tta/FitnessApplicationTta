package com.tta.fitnessapplication.view.activity.tracker.SleepTracker.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.tta.fitnessapplication.data.model.Sleep
import com.tta.fitnessapplication.data.model.SleepPair
import com.tta.fitnessapplication.data.model.Water
import java.util.Date

@Dao
interface SleepDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addSleep(sleep: Sleep)

    @Update
    suspend fun updateSleep(sleep: Sleep)

    @Query("SELECT * FROM sleep_planner ORDER BY date ASC")
    fun readAllData(): LiveData<List<Sleep>>

    @Query("SELECT * FROM sleep_planner WHERE date = :inputDate")
    fun getSleepListByDate(inputDate: String): List<Sleep>

    @Query("SELECT * FROM sleep_planner WHERE id IN (:id1, :id2)")
    fun getSleepsByIds(id1: Int, id2: Int): List<Sleep>

    @Query("UPDATE sleep_planner " +
            "SET date = :newDate, time = :newTime " +
            "WHERE date = :existingDate AND value = :existingValue")
    fun updateSleepTime(newDate: String,newTime : String, existingDate : String,existingValue: String)

    @Query("SELECT s1.id AS id1, s2.id AS id2 FROM sleep_planner AS s1 JOIN sleep_planner AS s2 ON DATE(s1.date, '+1 day') = s2.date WHERE s1.value = 'Bed' AND s2.value = 'Wake'")
    fun getSleepPairs(): List<SleepPair>

    @Query("SELECT * FROM sleep_planner WHERE date = :date AND value = :value LIMIT 1")
    fun getSleepByDateAndValue(date : String,value : String) : Sleep?
}