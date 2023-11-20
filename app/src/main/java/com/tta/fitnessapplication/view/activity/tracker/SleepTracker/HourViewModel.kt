package com.tta.fitnessapplication.view.activity.tracker.SleepTracker

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tta.fitnessapplication.data.model.Hour
import com.tta.fitnessapplication.view.activity.tracker.SleepTracker.db.HourDatabase
import com.tta.fitnessapplication.view.activity.tracker.SleepTracker.db.HourRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HourViewModel(application: Application) : AndroidViewModel(application) {
    val readAllData: LiveData<List<Hour>>
    private val repository: HourRepository

    val _sleepList = MutableLiveData<List<Hour>>()
    val sleepList: LiveData<List<Hour>> get() = _sleepList

    init {
        val sleepDao = HourDatabase.getDatabase(application).sleepDao()
        repository = HourRepository(sleepDao)
        readAllData = repository.readAllData
    }

    fun addSleep(sleep: Hour) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addSleep(sleep)
        }
    }

    fun insertOrUpdateHour(sleep: Hour) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertOrUpdateHour(sleep)
        }
    }

    fun clearAll() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.clearAll()
        }
    }

    fun getWaterListByDate(date: String) {
        viewModelScope.launch {
            val waterList = repository.getSleepListByDate(date)
            _sleepList.value = waterList
        }
    }
}