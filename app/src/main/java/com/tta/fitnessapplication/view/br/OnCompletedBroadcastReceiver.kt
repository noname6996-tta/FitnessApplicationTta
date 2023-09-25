package com.tta.fitnessapplication.view.br

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationManagerCompat
import com.tta.fitnessapplication.data.model.noti.TaskInfo
import com.tta.fitnessapplication.data.repository.TaskCategoryRepositoryImpl
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
//            taskInfo.status = true
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DAY_OF_MONTH, 1)
            calendar.set(Calendar.HOUR_OF_DAY, taskInfo?.date!!.hours)
            calendar.set(Calendar.MINUTE, taskInfo.date.minutes)
            calendar.set(Calendar.SECOND, taskInfo.date.seconds)
            taskInfo.date = calendar.time
            Log.e("ttanext",taskInfo.date.toString())
        }
        CoroutineScope(IO).launch {
            taskInfo?.let {
                repository.updateTaskStatus(it)
            }
        }
        if (p0 != null && taskInfo != null) {
            NotificationManagerCompat.from(p0).cancel(null, taskInfo.id)
        }
    }
}