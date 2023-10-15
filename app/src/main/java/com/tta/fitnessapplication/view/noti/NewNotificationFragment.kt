package com.tta.fitnessapplication.view.noti

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.skydoves.balloon.ArrowPositionRules
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.BalloonSizeSpec
import com.skydoves.balloon.showAsDropDown
import com.tta.fitnessapplication.R
import com.tta.fitnessapplication.data.model.noti.Notification
import com.tta.fitnessapplication.data.utils.DateToString
import com.tta.fitnessapplication.databinding.ActivityNotificationBinding
import com.tta.fitnessapplication.view.base.BaseFragment
import com.tta.fitnessapplication.view.br.ClockAlarmManager
import java.util.Calendar

class NewNotificationFragment : BaseFragment<ActivityNotificationBinding>() {
    private lateinit var viewModel: NewNotificationViewModel
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent
    private var noti = Notification(
        0, "", "Drink water", R.drawable.icon_notif, "200", 3, 53, 1, true
    )

    override fun getDataBinding(): ActivityNotificationBinding {
        return ActivityNotificationBinding.inflate(layoutInflater)
    }

    override fun initViewModel() {
        super.initViewModel()
        viewModel = ViewModelProvider(this)[NewNotificationViewModel::class.java]
    }

    override fun initView() {
        super.initView()
        val ballonInfo = Balloon.Builder(requireContext())
            .setWidthRatio(1.0f)
            .setHeight(BalloonSizeSpec.WRAP)
            .setText("Once you add your schedule we will notify you when it's time you choose!")
            .setTextColorResource(R.color.white)
            .setTextSize(15f)
            .setArrowPositionRules(ArrowPositionRules.ALIGN_BALLOON)
            .setArrowSize(10)
            .setArrowPosition(0.5f)
            .setPadding(12)
            .setCornerRadius(8f)
            .setBackgroundColorResource(R.color.text)
            .setBalloonAnimation(BalloonAnimation.ELASTIC)
            .build()
        binding.viewInfo.setOnClickListener {
            binding.viewInfo.showAsDropDown(ballonInfo)
        }
        binding.tvContentTitle.setText(noti.title.toString())
    }

    override fun addEvent() {
        super.addEvent()
        binding.btnAddSchedule.setOnClickListener {
            Log.e("aaaa",noti.toString())
            viewModel.addNotification(noti)
            setAlarm(noti)
            findNavController().popBackStack()
        }
        binding.dateAndTimePicker.setOnClickListener { showDateTimePicker() }
    }

    private fun showDateTimePicker() {
        val timePicker = MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_24H).build()
        timePicker.addOnPositiveButtonClickListener {
            val cal = Calendar.getInstance()
            cal.set(Calendar.HOUR_OF_DAY, timePicker.hour)
            cal.set(Calendar.MINUTE, timePicker.minute)
            noti.hour = timePicker.hour
            noti.min = timePicker.minute
            binding.dateAndTimePicker.text = DateToString.convertDateToString(cal.time)
        }
        timePicker.show(childFragmentManager, "TAG")
    }


    private fun setAlarm(notification: Notification) {
        // Initialize AlarmManager
        alarmManager = requireActivity().getSystemService(Context.ALARM_SERVICE) as AlarmManager

        // Create intent for your BroadcastReceiver
        val intent = Intent(requireContext(), ClockAlarmManager::class.java)
        intent.putExtra("noti_info", notification)
        pendingIntent = PendingIntent.getBroadcast(
            requireContext(), notification.id, intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        // Set the alarm to trigger at 8:00 AM (replace with your desired time)
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.set(Calendar.HOUR_OF_DAY, notification.hour)
        calendar.set(Calendar.MINUTE, notification.min)

        // Set the repeating interval to 24 hours
        val interval = AlarmManager.INTERVAL_DAY
        if (notification.enable) {
            // Schedule the alarm
            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                interval,
                pendingIntent
            )
        } else {
            // To cancel the alarm
            alarmManager.cancel(pendingIntent)
        }
    }
}