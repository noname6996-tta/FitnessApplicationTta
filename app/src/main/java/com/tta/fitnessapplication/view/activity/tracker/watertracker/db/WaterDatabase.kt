package com.tta.fitnessapplication.view.activity.tracker.watertracker.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tta.fitnessapplication.data.model.Water

@Database(entities = [Water::class], version = 1, exportSchema = false)
abstract class WaterDatabase : RoomDatabase() {

    abstract fun waterDao(): WaterDao

    companion object {
        @Volatile
        private var INSTANCE: WaterDatabase? = null

        fun getDatabase(context: Context): WaterDatabase {
            val tempInstant = INSTANCE
            if (tempInstant != null) {
                return tempInstant
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WaterDatabase::class.java,
                    "water_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}