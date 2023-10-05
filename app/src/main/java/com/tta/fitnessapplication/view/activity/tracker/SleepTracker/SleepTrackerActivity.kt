package com.tta.fitnessapplication.view.activity.tracker.SleepTracker

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.getSystemService
import androidx.navigation.fragment.findNavController
import com.skydoves.balloon.ArrowPositionRules
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.BalloonSizeSpec
import com.skydoves.balloon.showAlignTop
import com.skydoves.balloon.showAsDropDown
import com.tta.fitnessapplication.R
import com.tta.fitnessapplication.databinding.ActivitySleepTrackerBinding
import com.tta.fitnessapplication.view.base.BaseFragment
import com.tta.fitnessapplication.view.br.ClockAlarmManager
import java.util.Calendar

class SleepTrackerActivity : BaseFragment<ActivitySleepTrackerBinding>() {
    private val viewModel = SleepTrackerViewModel()
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent

    override fun getDataBinding(): ActivitySleepTrackerBinding {
        return ActivitySleepTrackerBinding.inflate(layoutInflater)
    }

    override fun initView() {
        super.initView()
        addAlarm()
    }

    override fun initViewModel() {
        super.initViewModel()
        viewModel.getData()
    }

    override fun addObservers() {
        super.addObservers()
        viewModel.listSleepTracker.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.tvTimeSleep.text = "No data"
            } else {

            }
        }
        viewModel.message.observe(viewLifecycleOwner) {

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
                .setBackgroundColorResource(com.tta.fitnessapplication.R.color.text)
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

            imageView32.setOnClickListener {

            }
        }
    }

    private fun addAlarm(){
        // Initialize AlarmManager
        alarmManager = requireActivity().getSystemService(Context.ALARM_SERVICE) as AlarmManager

        // Create intent for your BroadcastReceiver
        val intent = Intent(requireContext(), ClockAlarmManager::class.java)
        pendingIntent = PendingIntent.getBroadcast(requireContext(), 0, intent,
            PendingIntent.FLAG_IMMUTABLE)

        // Set the alarm to trigger at 8:00 AM (replace with your desired time)
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.set(Calendar.HOUR_OF_DAY, 11)
        calendar.set(Calendar.MINUTE, 25)

        // Set the repeating interval to 24 hours
        val interval = AlarmManager.INTERVAL_DAY

        // Schedule the alarm
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            interval,
            pendingIntent
        )

        // To cancel the alarm
//        alarmManager.cancel(pendingIntent)
    }
}