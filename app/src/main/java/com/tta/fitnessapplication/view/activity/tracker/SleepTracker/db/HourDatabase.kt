package com.tta.fitnessapplication.view.activity.tracker.SleepTracker.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tta.fitnessapplication.data.model.Hour

@Database(entities = [Hour::class], version = 1, exportSchema = false)
abstract class HourDatabase : RoomDatabase() {

    abstract fun sleepDao(): HourSleepDao

    companion object {
        @Volatile
        private var INSTANCE: HourDatabase? = null

        fun getDatabase(context: Context): HourDatabase {
            val tempInstant = INSTANCE
            if (tempInstant != null) {
                return tempInstant
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HourDatabase::class.java,
                    "hour_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}