package com.tta.fitnessapplication.view.noti

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tta.fitnessapplication.data.db.NotiDatabase
import com.tta.fitnessapplication.data.db.NotiRespository
import com.tta.fitnessapplication.data.model.noti.Notification
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewNotificationViewModel(application: Application) : AndroidViewModel(application) {
    val readAllData: LiveData<List<Notification>>
    private val repository: NotiRespository

    val _notiList = MutableLiveData<List<Notification>>()
    val notiList: LiveData<List<Notification>> get() = _notiList

    init {
        val waterDao = NotiDatabase.getDatabase(application).notiDao()
        repository = NotiRespository(waterDao)
        readAllData = repository.readAllData
    }

    fun addNotification(water: Notification) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addNoti(water)
        }
    }

    fun updateNotification(notification: Notification){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateNoti(notification)
        }
    }

    fun deleteNotification(notification: Notification){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteNoti(notification)
        }
    }
}