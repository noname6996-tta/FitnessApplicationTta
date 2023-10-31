package com.tta.fitnessapplication.view.activity.tracker.SleepTracker

import android.util.Log
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
import com.tta.fitnessapplication.data.model.Hour
import com.tta.fitnessapplication.data.model.SleepPair
import com.tta.fitnessapplication.data.utils.Constant
import com.tta.fitnessapplication.data.utils.Constant.DATE.getYesterdayDate
import com.tta.fitnessapplication.data.utils.convertToDecimalTime
import com.tta.fitnessapplication.data.utils.getWeekDates
import com.tta.fitnessapplication.databinding.ActivitySleepTrackerBinding
import com.tta.fitnessapplication.view.base.BaseFragment
import com.tta.fitnessapplication.view.noti.NewNotificationViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class SleepTrackerActivity : BaseFragment<ActivitySleepTrackerBinding>() {
    private lateinit var viewModelNoti: NewNotificationViewModel
    private lateinit var viewModel: SleepViewModel
    private lateinit var viewModelHour: HourViewModel

    override fun getDataBinding(): ActivitySleepTrackerBinding {
        return ActivitySleepTrackerBinding.inflate(layoutInflater)
    }

    override fun initView() {
        super.initView()
        binding.chart.viewColume1.setProgress(0)
        binding.chart.viewColume2.setProgress(0)
        binding.chart.viewColume3.setProgress(0)
        binding.chart.viewColume4.setProgress(0)
        binding.chart.viewColume5.setProgress(0)
        binding.chart.viewColume6.setProgress(0)
        binding.chart.viewColume7.setProgress(0)
    }

    override fun initViewModel() {
        super.initViewModel()
        viewModelNoti = ViewModelProvider(this)[NewNotificationViewModel::class.java]
        viewModel = ViewModelProvider(this)[SleepViewModel::class.java]
        viewModelHour = ViewModelProvider(this)[HourViewModel::class.java]
        viewModel.getAdjacentSleeps()
    }

    override fun addObservers() {
        super.addObservers()
        viewModelHour.clearAll()
        viewModelNoti.readAllData.observe(viewLifecycleOwner) {
            for (item in it) {
                if (item.type == 2) {
                    binding.cardView2.visibility = View.VISIBLE
                    binding.cardView.visibility = View.VISIBLE
                    binding.textView79.visibility = View.GONE
                    binding.btnAddNotification.visibility = View.GONE
                    for (item in it) {
                        if (item.icon == R.drawable.alarm_clock) {
                            binding.textView55.text = "${item.hour}:${item.min} AM"
                            val calendar = Calendar.getInstance()
                            val currentTime = calendar.timeInMillis
                            calendar.add(Calendar.DAY_OF_MONTH, 1)
                            calendar.set(Calendar.HOUR_OF_DAY, item.hour)
                            calendar.set(Calendar.MINUTE, 0)
                            val targetTime = calendar.timeInMillis
                            val timeDifference =
                                targetTime - (currentTime + item.min * 60 * 1000) // Adding 5 minutes in milliseconds
                            val remainingMinutes = (timeDifference / (1000 * 60)).toInt()
                            val remainingHours = remainingMinutes / 60
                            val remainingMinutesOnly = remainingMinutes % 60
                            binding.textView56.text =
                                "In $remainingHours hours $remainingMinutesOnly minutes"
                            binding.switchWakeup.isChecked = item.enable
                        } else if (item.icon == R.drawable.icon_bed) {
                            binding.textView53.text = "${item.hour}:${item.min} PM"
                            val calendar = Calendar.getInstance()
                            val currentTime = calendar.timeInMillis
                            calendar.set(Calendar.HOUR_OF_DAY, item.hour)
                            calendar.set(Calendar.MINUTE, 0)
                            val targetTime = calendar.timeInMillis
                            val timeDifference =
                                targetTime - (currentTime + item.min * 60 * 1000) // Adding 5 minutes in milliseconds
                            val remainingMinutes = (timeDifference / (1000 * 60)).toInt()
                            val remainingHours = remainingMinutes / 60
                            val remainingMinutesOnly = remainingMinutes % 60
                            binding.textView54.text =
                                "In $remainingHours hours $remainingMinutesOnly minutes"
                            binding.switchBedTime.isChecked = item.enable
                        }
                    }
                }
            }
        }

        viewModel.readAllData.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                var date = Constant.DATE.fullDateFormatter.format(Constant.DATE.today)
                val yesterday = Constant.DATE.fullDateFormatter.format(getYesterdayDate())
                var timeWake = ""
                var timeSleep = ""
                for (item in it) {
                    if (item.date == date && item.value == "Wake") {
                        timeWake = "${item.date} ${item.time}"
                    }
                    if (item.date == yesterday && item.value == "Bed") {
                        timeSleep = "${item.date} ${item.time}"
                    }
                }
                if (timeSleep.isNotEmpty() and timeWake.isNotEmpty()) {
                    val sleepTime = calculateSleepTime(timeSleep, timeWake)
                    binding.tvTimeSleep.text = "${sleepTime.hours}h ${sleepTime.minutes}m"
                    binding.tvTimeSleep.isEnabled = false
                }
            }
        }

        viewModelHour.readAllData.observe(viewLifecycleOwner) {
            val listDataChart = arrayOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0)
            for (item in it) {
                for (i in 0 until getWeekDates().size) {
                    if (getWeekDates()[i] == item.date) {
                        listDataChart[i] = item.time.toDouble() + listDataChart[i]
                    }
                }
            }
            for (item in listDataChart){
                Log.e("dsdsss",item.toString())
            }
            binding.chart.viewColume1.setProgress((listDataChart[0] * 10).toInt())
            binding.chart.viewColume2.setProgress((listDataChart[1] * 10).toInt())
            binding.chart.viewColume3.setProgress((listDataChart[2] * 10).toInt())
            binding.chart.viewColume4.setProgress((listDataChart[3] * 10).toInt())
            binding.chart.viewColume5.setProgress((listDataChart[4] * 10).toInt())
            binding.chart.viewColume6.setProgress((listDataChart[5] * 10).toInt())
            binding.chart.viewColume7.setProgress((listDataChart[6] * 10).toInt())
        }

        viewModel.sleepListA.observe(viewLifecycleOwner) {list->
            val distinctList = HashSet<SleepPair>(list)
            if (distinctList.isNotEmpty()) {
                Log.e("aaaaa",distinctList.toString())
                for (item in distinctList) {
                    viewModel.getItemById(item.id1, item.id2)
                }
            }
        }
        viewModel.item.observe(viewLifecycleOwner) {
            Log.e("Aaaaa", it.toString())
            var date = ""
            var timeWake = ""
            var timeSleep = ""
            for (item in it){
                if (item.value == "Wake"){
                    date = item.date
                    timeWake = "${item.date} ${item.time}"
                } else {
                    timeSleep = "${item.date} ${item.time}"
                }
            }
            val sleepTime = calculateSleepTime(timeSleep, timeWake)
            viewModelHour.addSleep(
                Hour(0, date, convertToDecimalTime(sleepTime.hours, sleepTime.minutes).toString())
            )
        }
    }

    data class SleepTime(val hours: Int, val minutes: Int)

    fun calculateSleepTime(startTime: String, endTime: String): SleepTime {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val startDate = inputFormat.parse(startTime)
        val endDate = inputFormat.parse(endTime)

        val sleepTimeMillis = endDate.time - startDate.time
        val sleepTimeHours = sleepTimeMillis / (1000 * 60 * 60)
        val sleepTimeMinutes = (sleepTimeMillis % (1000 * 60 * 60)) / (1000 * 60)

        return SleepTime(sleepTimeHours.toInt(), sleepTimeMinutes.toInt())
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
                var action =
                    SleepTrackerActivityDirections.actionSleepTrackerActivityToManagerNotification(2)
                findNavController().navigate(action)
            }

            btnSetSchedule.setOnClickListener {
                findNavController().navigate(SleepTrackerActivityDirections.actionSleepTrackerActivityToAddTimeSleepFragment())
            }
        }
    }
}