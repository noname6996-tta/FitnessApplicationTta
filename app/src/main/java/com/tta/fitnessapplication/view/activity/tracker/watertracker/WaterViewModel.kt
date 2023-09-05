package com.tta.fitnessapplication.view.activity.tracker.watertracker

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.tta.fitnessapplication.data.model.Water
import com.tta.fitnessapplication.view.activity.tracker.watertracker.db.WaterDatabase
import com.tta.fitnessapplication.view.activity.tracker.watertracker.db.WaterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WaterViewModel(application: Application) : AndroidViewModel(application) {
    val readAllData: LiveData<List<Water>>
    private val repository: WaterRepository

    init {
        val waterDao = WaterDatabase.getDatabase(application).waterDao()
        repository = WaterRepository(waterDao)
        readAllData = repository.readAllData
    }

    fun addWater(water: Water) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addWater(water)
        }
    }
}