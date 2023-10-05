package com.tta.fitnessapplication.view.noti

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import com.tta.fitnessapplication.R
import com.tta.fitnessapplication.data.model.noti.Notification
import com.tta.fitnessapplication.databinding.ActivityNotificationBinding
import com.tta.fitnessapplication.view.base.BaseFragment
import com.tta.fitnessapplication.view.br.AlarmReceiver
import com.tta.fitnessapplication.view.br.ClockAlarmManager
import java.util.Calendar

class NewNotificationFragment : BaseFragment<ActivityNotificationBinding>() {
    private lateinit var viewModel: NewNotificationViewModel
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent
    override fun getDataBinding(): ActivityNotificationBinding {
        return ActivityNotificationBinding.inflate(layoutInflater)
    }

    override fun initViewModel() {
        super.initViewModel()
        viewModel = ViewModelProvider(this)[NewNotificationViewModel::class.java]
    }

    override fun addEvent() {
        super.addEvent()
        // water
        var noti = Notification(
            0,"semo","Drink water", R.drawable.icon_notif,"200",3,53,1,true
        )
        binding.btnAddSchedule.setOnClickListener {
            viewModel.addNotification(noti)
            setAlarm(noti)
        }
    }


    private fun setAlarm(notification: Notification){
        // Initialize AlarmManager
        alarmManager = requireActivity().getSystemService(Context.ALARM_SERVICE) as AlarmManager

        // Create intent for your BroadcastReceiver
        val intent = Intent(requireContext(), ClockAlarmManager::class.java)
        intent.putExtra("noti_info", notification)
        pendingIntent = PendingIntent.getBroadcast(requireContext(), notification.id, intent,
            PendingIntent.FLAG_IMMUTABLE)

        // Set the alarm to trigger at 8:00 AM (replace with your desired time)
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.set(Calendar.HOUR_OF_DAY, notification.hour)
        calendar.set(Calendar.MINUTE, notification.min)

        // Set the repeating interval to 24 hours
        val interval = AlarmManager.INTERVAL_DAY
        if (notification.enable){
            // Schedule the alarm
            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                interval,
                pendingIntent
            )
        }
        else {
            // To cancel the alarm
            alarmManager.cancel(pendingIntent)
        }

    }

}