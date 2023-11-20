package com.tta.fitnessapplication.view.activity.tracker.SleepTracker

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tta.fitnessapplication.data.model.Sleep
import com.tta.fitnessapplication.data.model.SleepPair
import com.tta.fitnessapplication.data.model.Water
import com.tta.fitnessapplication.view.activity.tracker.SleepTracker.db.SleepDatabase
import com.tta.fitnessapplication.view.activity.tracker.SleepTracker.db.SleepRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SleepViewModel(application: Application) : AndroidViewModel(application) {
    val readAllData: LiveData<List<Sleep>>
    private val repository: SleepRepository

    val _sleepList = MutableLiveData<List<Sleep>>()
    val item = MutableLiveData<List<Sleep>>()
    val sleepList: LiveData<List<Sleep>> get() = _sleepList

    val _sleepListA = MutableLiveData<List<SleepPair>>()
    val sleepListA: LiveData<List<SleepPair>> get() = _sleepListA
    // item check
    val sleepCheck = MutableLiveData<Sleep?>()
    val sleepCheckWake = MutableLiveData<Sleep?>()

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

    fun updateSleep(sleep: Sleep) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateSleep(sleep)
        }
    }

    fun getWaterListByDate(date: String) {
        viewModelScope.launch {
            val waterList = repository.getSleepListByDate(date)
            _sleepList.value = waterList
        }
    }

    fun getSleepByDateAndValue(date: String,value: String) {
        viewModelScope.launch {
            val waterList = repository.getSleepByDateAndValue(date,value)
            sleepCheck.value = waterList
        }
    }

    fun getSleepByDateAndValueWake(date: String,value: String) {
        viewModelScope.launch {
            val waterList = repository.getSleepByDateAndValue(date,value)
            sleepCheckWake.value = waterList
        }
    }

    fun getItemById(id1: Int,id2: Int) {
        viewModelScope.launch {
            item.value = repository.getItemById(id1, id2)
        }
    }

    fun getAdjacentSleeps() {
        viewModelScope.launch {
            val waterList = repository.getAdjacentSleeps()
            _sleepListA.value = waterList
        }
    }

    fun updateSleepTime(newDate: String,newTime : String, existingDate : String,existingValue: String) {
        viewModelScope.launch {
            repository.updateSleepTime(newDate, newTime, existingDate, existingValue)
        }
    }
}