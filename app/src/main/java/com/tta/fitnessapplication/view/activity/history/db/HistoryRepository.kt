package com.tta.fitnessapplication.view.activity.history.db

import androidx.lifecycle.LiveData
import com.tta.fitnessapplication.data.model.History
import com.tta.fitnessapplication.data.model.Water
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HistoryRepository(private val historyDao: HistoryDao) {

    val readAllData: LiveData<List<History>> = historyDao.readAllData()

    suspend fun addHistory(history: History) {
        historyDao.addHistory(history)
    }

    suspend fun getHistoryListByDate(date: String): List<History> {
        return withContext(Dispatchers.IO) {
            historyDao.getHistoryListByDate(date)
        }
    }
}