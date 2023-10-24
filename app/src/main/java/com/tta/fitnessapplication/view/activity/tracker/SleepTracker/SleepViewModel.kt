package com.tta.fitnessapplication.view.activity.tracker.SleepTracker

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tta.fitnessapplication.data.model.Sleep
import com.tta.fitnessapplication.data.model.Water
import com.tta.fitnessapplication.view.activity.tracker.SleepTracker.db.SleepDatabase
import com.tta.fitnessapplication.view.activity.tracker.SleepTracker.db.SleepRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SleepViewModel(application: Application) : AndroidViewModel(application) {
    val readAllData: LiveData<List<Sleep>>
    private val repository: SleepRepository

    val _sleepList = MutableLiveData<List<Sleep>>()
    val sleepList: LiveData<List<Sleep>> get() = _sleepList
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

    fun getWaterListByDate(date: String) {
        viewModelScope.launch {
            val waterList = repository.getSleepListByDate(date)
            _sleepList.value = waterList
        }
    }
}