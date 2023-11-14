package com.tta.fitnessapplication.view.activity.history.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.tta.fitnessapplication.data.model.History
import com.tta.fitnessapplication.data.model.Sleep
import com.tta.fitnessapplication.data.model.Water

@Dao
interface HistoryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addHistory(history: History)

    @Update
    suspend fun updateHistory(history: History)

    @Query("SELECT * FROM history_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<History>>

    @Query("SELECT * FROM history_table WHERE date = :inputDate")
    fun getHistoryListByDate(inputDate: String): List<History>

    @Query("UPDATE history_table " +
            "SET date = :newDate, time = :newTime " +
            "WHERE date = :existingDate AND value = :existingValue")
    fun updateSleepTime(newDate: String,newTime : String, existingDate : String,existingValue: String)
}