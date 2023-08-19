package com.tta.fitnessapplication.view.activity.tracker.calortracker

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.tta.fitnessapplication.data.model.Meal
import com.tta.fitnessapplication.view.activity.tracker.calortracker.db.MealDatabase
import com.tta.fitnessapplication.view.activity.tracker.calortracker.db.MealRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MealViewModel(application: Application) : AndroidViewModel(application) {
    val readAllData: LiveData<List<Meal>>
    private val repository: MealRepository

    init {
        val mealDao = MealDatabase.getDatabase(application).mealDao()
        repository = MealRepository(mealDao)
        readAllData = repository.readAllData
    }

    fun addMeal(meal: Meal) {
        viewModelScope.launch(Dispatchers.IO){
            repository.addMeal(meal)
        }
    }
}