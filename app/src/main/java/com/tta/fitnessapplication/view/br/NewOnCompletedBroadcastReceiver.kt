package com.tta.fitnessapplication.view.br

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationManagerCompat
import com.tta.fitnessapplication.R
import com.tta.fitnessapplication.data.model.History
import com.tta.fitnessapplication.data.model.Sleep
import com.tta.fitnessapplication.data.model.Water
import com.tta.fitnessapplication.data.model.noti.Notification
import com.tta.fitnessapplication.data.utils.Constant
import com.tta.fitnessapplication.data.utils.formatDateToString
import com.tta.fitnessapplication.data.utils.formatDateToTimeString
import com.tta.fitnessapplication.data.utils.getCurrentTime
import com.tta.fitnessapplication.view.activity.history.db.HistoryDatabase
import com.tta.fitnessapplication.view.activity.tracker.SleepTracker.db.SleepDatabase
import com.tta.fitnessapplication.view.activity.tracker.watertracker.db.WaterDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewOnCompletedBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        val noti = p1?.getSerializableExtra("noti_info") as? Notification
        if (noti != null) {
            CoroutineScope(Dispatchers.Main).launch {
                val historyDao = HistoryDatabase.getDatabase(p0!!).historyDao()
                when (noti.type) {
                    1 -> {
                        val history = History(
                            null,
                            null,
                            Constant.DATE.fullDateFormatter.format(Constant.DATE.today),
                            getCurrentTime(),
                            "Drink water",
                            noti.type,
                            noti.value.toString()
                        )
                        historyDao.addHistory(history)
                        val waterDao = WaterDatabase.getDatabase(p0!!).waterDao()
                        waterDao.addWater(
                            Water(
                                0,
                                Constant.DATE.fullDateFormatter.format(Constant.DATE.today),
                                getCurrentTime(),
                                "Drink water",
                                noti.type.toString(),
                                noti.value.toString()
                            )
                        )
                    }

                    2 -> {
                        val sleepDao = SleepDatabase.getDatabase(p0!!).sleepDao()
                        if (noti.icon == R.drawable.icon_bed) {
                            sleepDao.addSleep(
                                Sleep(
                                    0,
                                    Constant.DATE.fullDateFormatter.format(Constant.DATE.today),
                                    getCurrentTime(),
                                    noti.text.toString(),
                                    noti.type.toString(),
                                    noti.value.toString()
                                )
                            )
                            val history1 = History(
                                null,
                                null,
                                Constant.DATE.fullDateFormatter.format(Constant.DATE.today),
                                getCurrentTime(),
                                noti.text.toString(),
                                noti.type,
                                noti.value.toString()
                            )
                            historyDao.addHistory(history1)
                        } else {
                            sleepDao.addSleep(
                                Sleep(
                                    0,
                                    Constant.DATE.fullDateFormatter.format(Constant.DATE.today),
                                    getCurrentTime(),
                                    noti.text.toString(),
                                    noti.type.toString(),
                                    noti.value.toString()
                                )
                            )
                            val history1 = History(
                                null,
                                null,
                                Constant.DATE.fullDateFormatter.format(Constant.DATE.today),
                                getCurrentTime(),
                                noti.text.toString(),
                                noti.type,
                                noti.value.toString()
                            )
                            historyDao.addHistory(history1)
                        }
                    }
                }

            }
        }
        if (p0 != null && noti != null) {
            NotificationManagerCompat.from(p0).cancel(null, noti.id)
        }
    }
}