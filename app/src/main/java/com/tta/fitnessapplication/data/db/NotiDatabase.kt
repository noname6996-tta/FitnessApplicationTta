package com.tta.fitnessapplication.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tta.fitnessapplication.data.model.noti.Notification

@Database(entities = [Notification::class], version = 1, exportSchema = false)
abstract class NotiDatabase : RoomDatabase() {

    abstract fun notiDao(): NotiDao

    companion object {
        @Volatile
        private var INSTANCE: NotiDatabase? = null

        fun getDatabase(context: Context): NotiDatabase {
            val tempInstant = INSTANCE
            if (tempInstant != null) {
                return tempInstant
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NotiDatabase::class.java,
                    "noti_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}