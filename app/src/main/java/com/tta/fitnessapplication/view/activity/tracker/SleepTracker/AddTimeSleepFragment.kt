package com.tta.fitnessapplication.view.activity.tracker.SleepTracker

import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.tta.fitnessapplication.data.model.History
import com.tta.fitnessapplication.data.model.Hour
import com.tta.fitnessapplication.data.model.Sleep
import com.tta.fitnessapplication.data.model.SleepPair
import com.tta.fitnessapplication.data.utils.DateToString
import com.tta.fitnessapplication.data.utils.convertToDecimalTime
import com.tta.fitnessapplication.data.utils.formatDateToString
import com.tta.fitnessapplication.data.utils.formatDateToTimeString
import com.tta.fitnessapplication.databinding.FragmentAddTimeSleepBinding
import com.tta.fitnessapplication.view.activity.history.HistoryViewModel
import com.tta.fitnessapplication.view.base.BaseFragment
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.concurrent.CountDownLatch

class AddTimeSleepFragment : BaseFragment<FragmentAddTimeSleepBinding>() {
    private lateinit var viewModel: SleepViewModel
    private lateinit var historyViewModel: HistoryViewModel
    private lateinit var viewModelHour: HourViewModel
    private var timeSleep: Date = Date()
    private var timeWake: Date = Date()
    private var isSetSleep = false
    private var isSetWake = false
    private var list = listOf<Sleep>()
    val latch = CountDownLatch(2)
    override fun getDataBinding(): FragmentAddTimeSleepBinding {
        return FragmentAddTimeSleepBinding.inflate(layoutInflater)
    }

    override fun initViewModel() {
        super.initViewModel()
        viewModel = ViewModelProvider(this)[SleepViewModel::class.java]
        historyViewModel = ViewModelProvider(this)[HistoryViewModel::class.java]
        viewModelHour = ViewModelProvider(this)[HourViewModel::class.java]
        viewModel.getAdjacentSleeps()
    }

    override fun addObservers() {
        super.addObservers()
        viewModel.sleepList.observe(viewLifecycleOwner) { it ->
            list = it
        }
        viewModel.sleepListA.observe(viewLifecycleOwner) { list ->
            val distinctList = HashSet<SleepPair>(list)
            if (distinctList.isNotEmpty()) {
                Log.e("aaaaa", distinctList.toString())
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
            var idWake = 0
            var idBed = 0
            for (item in it) {
                if (item.value == "Wake") {
                    idWake = item.id
                    date = item.date
                    timeWake = "${item.date} ${item.time}"
                } else {
                    idBed = item.id
                    timeSleep = "${item.date} ${item.time}"
                }
            }
            val sleepTime = calculateSleepTime(timeSleep, timeWake)
            viewModelHour.addSleep(
                Hour(
                    0,
                    date,
                    convertToDecimalTime(sleepTime.hours, sleepTime.minutes).toString(),
                    idBed,
                    idWake
                )
            )
        }

        viewModel.sleepCheck.observe(viewLifecycleOwner) { item ->
            if (item != null) {
                // trung bien thuc hien update
                viewModel.updateSleep(
                    Sleep(
                        item.id,
                        formatDateToString(timeSleep),
                        formatDateToTimeString(timeSleep),
                        item.activity,
                        item.type,
                        item.value
                    )
                )
                historyViewModel.updateSleepTime(
                    formatDateToString(timeWake),
                    formatDateToTimeString(timeSleep),
                    item.date,
                    "Bed"
                )
            } else {
                // chua co thuc hien add
                viewModel.addSleep(
                    Sleep(
                        0,
                        date = formatDateToString(timeSleep),
                        time = formatDateToTimeString(timeSleep),
                        "Bed Time",
                        "2",
                        "Bed"
                    )
                )
                val history1 = History(
                    null,
                    idUser.toInt(),
                    date = formatDateToString(timeSleep),
                    time = formatDateToTimeString(timeSleep),
                    "Bed Time",
                    2,
                    "Bed"
                )
                historyViewModel.addHistory(history1)
            }
            if (isSetSleep && !isSetWake) {
                hideLoading()
                findNavController().popBackStack()
            } else {
                latch.countDown() // Giảm cờ đếm khi hoàn thành
                tryPopBackStack(latch) // Thử popBackStack
            }
        }
        viewModel.sleepCheckWake.observe(viewLifecycleOwner) { item ->
            if (item != null) {
                // trung bien thuc hien update
                viewModel.updateSleepTime(
                    formatDateToString(timeWake),
                    formatDateToTimeString(timeSleep),
                    item.date,
                    "Wake"
                )
                historyViewModel.updateSleepTime(
                    formatDateToString(timeWake),
                    formatDateToTimeString(timeSleep),
                    item.date,
                    "Wake"
                )
            } else {
                // chua co thuc hien add
                viewModel.addSleep(
                    Sleep(
                        0,
                        date = formatDateToString(timeWake),
                        time = formatDateToTimeString(timeWake),
                        "Wake up Time",
                        "2",
                        "Wake"
                    )
                )
                val history2 = History(
                    null,
                    idUser.toInt(),
                    date = formatDateToString(timeWake),
                    time = formatDateToTimeString(timeWake),
                    "Wake up Time",
                    2,
                    "Wake"
                )
                historyViewModel.addHistory(history2)
            }
            if (isSetWake && !isSetSleep) {
                hideLoading()
                findNavController().popBackStack()
            } else {
                latch.countDown() // Giảm cờ đếm khi hoàn thành
                tryPopBackStack(latch) // Thử popBackStack
            }
        }
    }

    private fun tryPopBackStack(latch: CountDownLatch) {
        if (latch.count.toInt() == 0) {
            // Nếu cả hai observe đã hoàn thành, thực hiện popBackStack
            hideLoading()
            findNavController().popBackStack()
        }
    }

    fun calculateSleepTime(startTime: String, endTime: String): SleepTrackerActivity.SleepTime {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val startDate = inputFormat.parse(startTime)
        val endDate = inputFormat.parse(endTime)

        val sleepTimeMillis = endDate.time - startDate.time
        val sleepTimeHours = sleepTimeMillis / (1000 * 60 * 60)
        val sleepTimeMinutes = (sleepTimeMillis % (1000 * 60 * 60)) / (1000 * 60)

        return SleepTrackerActivity.SleepTime(sleepTimeHours.toInt(), sleepTimeMinutes.toInt())
    }

    override fun addEvent() {
        super.addEvent()
        binding.apply {
            view13.setOnClickListener {
                findNavController().popBackStack()
            }

            dateAndTimePickerSleep.setOnClickListener { showDateTimePicker(0) }
            dateAndTimePickerWakeup.setOnClickListener { showDateTimePicker(1) }

            btnDone.setOnClickListener {
//                val itemExists = list.any { it.value == "Bed" }
//                if (itemExists) {
//                    // nếu đã tồn tại
//                    for (item in list) {
//                        if (item.value == "Bed") { viewModel.updateSleepTime(formatDateToString(timeWake), formatDateToTimeString(timeSleep), item.date, "Bed")
//                            historyViewModel.updateSleepTime(formatDateToString(timeWake), formatDateToTimeString(timeSleep), item.date, "Bed") }
//                    }
//                } else {
//                    // nếu chưa tồn tại
//                    if (isSetSleep) {
//                        viewModel.addSleep(Sleep(0, date = formatDateToString(timeSleep), time = formatDateToTimeString(timeSleep), "Bed Time", "2", "Bed"))
//                        val history1 = History(null, null, date = formatDateToString(timeSleep), time = formatDateToTimeString(timeSleep), "Bed Time", 2, "Bed")
//                        historyViewModel.addHistory(history1)
//                    }
//                }
//
//                val itemExists2 = list.any { it.value == "Wake" }
//                if (itemExists2) {
//                    // nếu đã tồn tại
//                    for (item in list) {
//                        if (item.value == "Wake") {
//                            viewModel.updateSleepTime(formatDateToString(timeWake), formatDateToTimeString(timeSleep), item.date, "Wake")
//                            historyViewModel.updateSleepTime(formatDateToString(timeWake), formatDateToTimeString(timeSleep), item.date, "Wake") }
//                    }
//                } else {
//                    // nếu chưa tồn tại
//                    if (isSetWake) { viewModel.addSleep(Sleep(0, date = formatDateToString(timeWake), time = formatDateToTimeString(timeWake), "Wake up Time", "2", "Wake"))
//                        val history2 = History(null, null, date = formatDateToString(timeWake), time = formatDateToTimeString(timeWake), "Wake up Time", 2, "Wake")
//                        historyViewModel.addHistory(history2)
//                    }
//                }
                val bedTime = Sleep(
                    0,
                    date = formatDateToString(timeSleep),
                    time = formatDateToTimeString(timeSleep),
                    "Bed Time",
                    "2",
                    "Bed"
                )
                val wakeTime = Sleep(
                    0,
                    date = formatDateToString(timeWake),
                    time = formatDateToTimeString(timeWake),
                    "Wake up Time",
                    "2",
                    "Wake"
                )
                if (isSetSleep) {
                    viewModel.getSleepByDateAndValue(bedTime.date, bedTime.value)
                }
                if (isSetWake) {
                    viewModel.getSleepByDateAndValueWake(wakeTime.date, wakeTime.value)
                }
                showLoading()
            }
        }
    }

    private fun showDateTimePicker(id: Int) {
        val datePicker = MaterialDatePicker.Builder.datePicker().build()
        val timePicker = MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_24H).build()
        datePicker.addOnPositiveButtonClickListener {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = it
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            if (id == 0) {
                timeSleep = calendar.time
                binding.dateAndTimePickerSleep.text = DateToString.convertDateToString(timeSleep)
            } else {
                timeWake = calendar.time
                binding.dateAndTimePickerWakeup.text = DateToString.convertDateToString(timeWake)
            }
            timePicker.show(childFragmentManager, "TAG")
        }

        timePicker.addOnPositiveButtonClickListener {
            val cal = Calendar.getInstance()
            if (id == 0) {
                cal.time = timeSleep
            } else {
                cal.time = timeWake
            }
            cal.set(Calendar.HOUR_OF_DAY, timePicker.hour)
            cal.set(Calendar.MINUTE, timePicker.minute)
            cal.set(Calendar.SECOND, 0)
            if (id == 0) {
                timeSleep = cal.time
                binding.dateAndTimePickerSleep.text = DateToString.convertDateToString(timeSleep)
                viewModel.getWaterListByDate(formatDateToString(timeSleep))
                isSetSleep = true
            } else {
                timeWake = cal.time
                binding.dateAndTimePickerWakeup.text = DateToString.convertDateToString(timeWake)
                viewModel.getWaterListByDate(formatDateToString(timeWake))
                isSetWake = true
            }
        }
        datePicker.show(childFragmentManager, "TAG")
    }
}