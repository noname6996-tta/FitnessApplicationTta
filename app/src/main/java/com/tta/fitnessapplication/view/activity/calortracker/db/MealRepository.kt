package com.tta.fitnessapplication.view.activity.calortracker.db

import androidx.lifecycle.LiveData
import com.tta.fitnessapplication.data.model.Meal

class MealRepository(private val mealDao: MealDao) {

    val readAllData : LiveData<List<Meal>> = mealDao.readAllData()

    suspend fun addMeal(meal: Meal){
        mealDao.addMeal(meal)
    }
}