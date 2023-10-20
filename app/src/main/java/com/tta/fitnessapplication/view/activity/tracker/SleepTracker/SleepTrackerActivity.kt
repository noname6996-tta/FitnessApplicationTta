package com.tta.fitnessapplication.view.activity.tracker.SleepTracker

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.skydoves.balloon.ArrowPositionRules
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.BalloonSizeSpec
import com.skydoves.balloon.showAlignTop
import com.skydoves.balloon.showAsDropDown
import com.tta.fitnessapplication.R
import com.tta.fitnessapplication.data.model.noti.Notification
import com.tta.fitnessapplication.databinding.ActivitySleepTrackerBinding
import com.tta.fitnessapplication.view.base.BaseFragment
import com.tta.fitnessapplication.view.br.ClockAlarmManager
import com.tta.fitnessapplication.view.noti.NewNotificationViewModel
import java.util.Calendar

class SleepTrackerActivity : BaseFragment<ActivitySleepTrackerBinding>() {
    private lateinit var viewModelNoti: NewNotificationViewModel
    private lateinit var viewModel : SleepViewModel

    override fun getDataBinding(): ActivitySleepTrackerBinding {
        return ActivitySleepTrackerBinding.inflate(layoutInflater)
    }


    override fun initViewModel() {
        super.initViewModel()
        viewModelNoti = ViewModelProvider(this)[NewNotificationViewModel::class.java]
        viewModel = ViewModelProvider(this)[SleepViewModel::class.java]
    }

    override fun addObservers() {
        super.addObservers()
        viewModelNoti.readAllData.observe(viewLifecycleOwner){
            for (item in it){
                if (item.type == 2){
                    binding.cardView2.visibility = View.VISIBLE
                    binding.cardView.visibility = View.VISIBLE
                    binding.textView79.visibility = View.GONE
                    binding.btnAddNotification.visibility = View.GONE
                    for (item in it){
                        if (item.icon == R.drawable.alarm_clock)
                        {
                            binding.textView55.text = "${item.hour}:${item.min} AM"
                            val calendar = Calendar.getInstance()
                            val currentTime = calendar.timeInMillis
                            calendar.add(Calendar.DAY_OF_MONTH, 1)
                            calendar.set(Calendar.HOUR_OF_DAY, item.hour)
                            calendar.set(Calendar.MINUTE, 0)
                            val targetTime = calendar.timeInMillis
                            val timeDifference = targetTime - (currentTime + item.min * 60 * 1000) // Adding 5 minutes in milliseconds
                            val remainingMinutes = (timeDifference / (1000 * 60)).toInt()
                            val remainingHours = remainingMinutes / 60
                            val remainingMinutesOnly = remainingMinutes % 60
                            binding.textView56.text = "In $remainingHours hours $remainingMinutesOnly minutes"
                            binding.switchWakeup.isChecked = item.enable
                        }
                        else if (item.icon == R.drawable.icon_bed){
                            binding.textView53.text = "${item.hour}:${item.min} PM"
                            val calendar = Calendar.getInstance()
                            val currentTime = calendar.timeInMillis
                            calendar.set(Calendar.HOUR_OF_DAY, item.hour)
                            calendar.set(Calendar.MINUTE, 0)
                            val targetTime = calendar.timeInMillis
                            val timeDifference = targetTime - (currentTime + item.min * 60 * 1000) // Adding 5 minutes in milliseconds
                            val remainingMinutes = (timeDifference / (1000 * 60)).toInt()
                            val remainingHours = remainingMinutes / 60
                            val remainingMinutesOnly = remainingMinutes % 60
                            binding.textView54.text = "In $remainingHours hours $remainingMinutesOnly minutes"
                            binding.switchBedTime.isChecked = item.enable
                        }
                    }
                }
            }
        }
    }

    override fun addEvent() {
        binding.apply {
            view13.setOnClickListener {
                findNavController().popBackStack()
            }

            view22.setOnClickListener {
                findNavController().navigate(R.id.action_sleepTrackerActivity_to_sleepScheduleActivity)
            }
            val balloonSleep = Balloon.Builder(requireContext())
                .setWidthRatio(1.0f)
                .setHeight(BalloonSizeSpec.WRAP)
                .setText("Track your daily sleep history. Try to sleep enough every day!")
                .setTextColorResource(com.tta.fitnessapplication.R.color.white)
                .setTextSize(15f)
                .setArrowPositionRules(ArrowPositionRules.ALIGN_BALLOON)
                .setArrowSize(10)
                .setArrowPosition(0.5f)
                .setPadding(12)
                .setCornerRadius(8f)
                .setBackgroundColorResource(R.color.text)
                .setBalloonAnimation(BalloonAnimation.ELASTIC)
                .build()

            viewInfo.setOnClickListener {
                viewInfo.showAsDropDown(balloonSleep)
            }

            val balloonInfoColumnone = Balloon.Builder(requireContext())
                .setWidthRatio(1.0f)
                .setHeight(BalloonSizeSpec.WRAP)
                .setText("2h")
                .setTextColorResource(com.tta.fitnessapplication.R.color.white)
                .setTextSize(15f)
                .setArrowPositionRules(ArrowPositionRules.ALIGN_BALLOON)
                .setArrowSize(10)
                .setArrowPosition(0.5f)
                .setPadding(2)
                .setCornerRadius(8f)
                .setBackgroundColorResource(com.tta.fitnessapplication.R.color.text)
                .setBalloonAnimation(BalloonAnimation.ELASTIC)
                .build()

            chart.viewColume1.setOnClickListener {
                chart.viewColume1.showAlignTop(balloonInfoColumnone)
            }

            btnAddNotification.setOnClickListener {
                var action = SleepTrackerActivityDirections.actionSleepTrackerActivityToManagerNotification(2)
                findNavController().navigate(action)
            }
        }
    }
}