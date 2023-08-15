package com.tta.fitnessapplication.view.activity.calortracker.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tta.fitnessapplication.data.model.Meal

@Database(entities = [Meal::class], version = 1, exportSchema = false)
abstract class MealDatabase : RoomDatabase() {

    abstract fun mealDao(): MealDao

    companion object {
        @Volatile
        private var INSTANCE: MealDatabase? = null

        fun getDatabase(context: Context): MealDatabase {
            val tempInstant = INSTANCE
            if (tempInstant != null) {
                return tempInstant
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MealDatabase::class.java,
                    "meal_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}