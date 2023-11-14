package com.tta.fitnessapplication.view.activity.history

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tta.fitnessapplication.data.model.History
import com.tta.fitnessapplication.data.model.Sleep
import com.tta.fitnessapplication.data.model.Water
import com.tta.fitnessapplication.view.activity.history.db.HistoryDatabase
import com.tta.fitnessapplication.view.activity.history.db.HistoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistoryViewModel(application: Application) : AndroidViewModel(application) {
    val readAllData: LiveData<List<History>>
    private val repository: HistoryRepository
    val historyList = MutableLiveData<List<History>>()

    init {
        val sleepDao = HistoryDatabase.getDatabase(application).historyDao()
        repository = HistoryRepository(sleepDao)
        readAllData = repository.readAllData
    }

    fun addHistory(sleep: History) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addHistory(sleep)
        }
    }

    fun updateHistory(history: History) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateSleep(history)
        }
    }

    fun getWaterListByDate(date: String) {
        viewModelScope.launch {
            val waterList = repository.getHistoryListByDate(date)
            historyList.value = waterList
        }
    }

    fun updateSleepTime(newDate: String,newTime : String, existingDate : String,existingValue: String) {
        viewModelScope.launch {
            repository.updateSleepTime(newDate, newTime, existingDate, existingValue)
        }
    }
}