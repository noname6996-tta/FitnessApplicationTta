package com.tta.fitnessapplication.view.br

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationManagerCompat
import com.tta.fitnessapplication.data.model.History
import com.tta.fitnessapplication.data.model.noti.Notification
import com.tta.fitnessapplication.data.utils.Constant
import com.tta.fitnessapplication.data.utils.getCurrentTime
import com.tta.fitnessapplication.view.activity.history.db.HistoryDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewOnCompletedBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        val noti = p1?.getSerializableExtra("noti_info") as? Notification
        if (noti != null) {
            CoroutineScope(Dispatchers.Main).launch {
                val history = History(
                    null,
                    null,
                    Constant.DATE.fullDateFormatter.format(Constant.DATE.today),
                    getCurrentTime(),
                    noti.text,
                    noti.type,
                    noti.value
                )
                when(noti.type){
                    1 -> {
                        val waterDao = HistoryDatabase.getDatabase(p0!!).historyDao()
                        waterDao.addHistory(history)
                    }
                    2 -> {
                        val waterDao = HistoryDatabase.getDatabase(p0!!).historyDao()
                        waterDao.addHistory(history)
                    }
                }

            }
        }
        if (p0 != null && noti != null) {
            NotificationManagerCompat.from(p0).cancel(null, noti.id)
        }
    }
}