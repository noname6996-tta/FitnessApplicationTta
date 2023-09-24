package com.tta.fitnessapplication.view.activity.history.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tta.fitnessapplication.data.model.History
import com.tta.fitnessapplication.data.model.Water

@Dao
interface HistoryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addHistory(history: History)

    @Query("SELECT * FROM history_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<History>>

    @Query("SELECT * FROM history_table WHERE date = :inputDate")
    fun getHistoryListByDate(inputDate: String): List<History>
}