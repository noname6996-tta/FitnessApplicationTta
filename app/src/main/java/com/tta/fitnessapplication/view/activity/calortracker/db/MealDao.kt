package com.tta.fitnessapplication.view.activity.calortracker.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tta.fitnessapplication.data.model.Meal

@Dao
interface MealDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addMeal(meal: Meal)

    @Query("SELECT * FROM meal_planner ORDER BY id ASC")
    fun readAllData(): LiveData<List<Meal>>
}