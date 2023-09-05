package com.tta.fitnessapplication.view.activity.tracker.SleepTracker

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.tta.fitnessapplication.data.model.Sleep
import com.tta.fitnessapplication.view.activity.tracker.SleepTracker.db.SleepDatabase
import com.tta.fitnessapplication.view.activity.tracker.SleepTracker.db.SleepRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SleepViewModel(application: Application) : AndroidViewModel(application) {
    val readAllData: LiveData<List<Sleep>>
    private val repository: SleepRepository

    init {
        val sleepDao = SleepDatabase.getDatabase(application).sleepDao()
        repository = SleepRepository(sleepDao)
        readAllData = repository.readAllData
    }

    fun addSleep(sleep: Sleep) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addSleep(sleep)
        }
    }
}