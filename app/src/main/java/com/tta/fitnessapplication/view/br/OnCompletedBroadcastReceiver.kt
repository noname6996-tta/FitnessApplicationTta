package com.tta.fitnessapplication.view.br

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationManagerCompat
import com.tta.fitnessapplication.data.model.History
import com.tta.fitnessapplication.data.model.noti.TaskInfo
import com.tta.fitnessapplication.data.repository.TaskCategoryRepositoryImpl
import com.tta.fitnessapplication.data.utils.Constant
import com.tta.fitnessapplication.data.utils.getCurrentTime
import com.tta.fitnessapplication.view.activity.history.db.HistoryDatabase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@AndroidEntryPoint
class OnCompletedBroadcastReceiver : BroadcastReceiver() {
    @Inject
    lateinit var repository: TaskCategoryRepositoryImpl

    override fun onReceive(p0: Context?, p1: Intent?) {
        val taskInfo = p1?.getSerializableExtra("task_info") as? TaskInfo
        if (taskInfo != null) {
            taskInfo.status = true
        }
        CoroutineScope(IO).launch {
            val historyDao = HistoryDatabase.getDatabase(p0!!).historyDao()
            val history = History(
                null,
                null,
                Constant.DATE.fullDateFormatter.format(Constant.DATE.today),
                getCurrentTime(),
                taskInfo?.description,
                4,
                taskInfo?.category
            )
            historyDao.addHistory(history)
        }
        if (p0 != null && taskInfo != null) {
            NotificationManagerCompat.from(p0).cancel(null, taskInfo.id)
        }
    }
}