package com.tta.fitnessapplication.data.db

import androidx.lifecycle.LiveData
import com.tta.fitnessapplication.data.model.noti.Notification

class NotiRespository(private val notiDao: NotiDao) {

    val readAllData: LiveData<List<Notification>> = notiDao.readAllData()

    suspend fun addNoti(meal: Notification) {
        notiDao.addNoti(meal)
    }

    suspend fun updateNoti(noti: Notification) {
        notiDao.updateNoti(noti)
    }

    suspend fun deleteNoti(noti: Notification) {
        notiDao.deleteNoti(noti)
    }
}