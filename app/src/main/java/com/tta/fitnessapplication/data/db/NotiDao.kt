package com.tta.fitnessapplication.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.tta.fitnessapplication.data.model.noti.Notification

@Dao
interface NotiDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNoti(noti: Notification)

    @Query("SELECT * FROM noti_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Notification>>

    @Update
    suspend fun updateNoti(noti : Notification)

    @Delete
    suspend fun deleteNoti(noti : Notification)
}