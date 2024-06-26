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
    private var timeSleep: Date = Date()
    private var isSetSleep = false
    override fun getDataBinding(): FragmentAddTimeSleepBinding {
        return FragmentAddTimeSleepBinding.inflate(layoutInflater)
    }

    override fun initViewModel() {
        super.initViewModel()
        viewModel = ViewModelProvider(this)[SleepViewModel::class.java]
        historyViewModel = ViewModelProvider(this)[HistoryViewModel::class.java]
    }

    override fun addObservers() {
        super.addObservers()
        viewModel.sleepCheck.observe(viewLifecycleOwner) { item ->
            if (item != null) {
                // trung bien thuc hien update
                viewModel.updateSleep(Sleep(item.id, formatDateToString(timeSleep), formatDateToTimeString(timeSleep), item.activity, item.type, item.value))
                val history1 = History(null, idUser.toInt(), date = formatDateToString(timeSleep), time = formatDateToTimeString(timeSleep), "Bed Time", 2, "Bed")
                historyViewModel.updateHistory(history1)
                findNavController().popBackStack()
            } else {
                // chua co thuc hien add
                viewModel.addSleep(Sleep(0, date = formatDateToString(timeSleep), time = formatDateToTimeString(timeSleep), "Bed Time", "2", "Bed"))
                val history1 = History(null, idUser.toInt(), date = formatDateToString(timeSleep), time = formatDateToTimeString(timeSleep), "Bed Time", 2, "Bed")
                historyViewModel.addHistory(history1)
                findNavController().popBackStack()
            }
        }
    }

    override fun addEvent() {
        super.addEvent()
        binding.apply {
            view13.setOnClickListener {
                findNavController().popBackStack()
            }

            dateAndTimePickerSleep.setOnClickListener { showDateTimePicker() }

            btnDone.setOnClickListener {
                val bedTime = Sleep(
                    0,
                    date = formatDateToString(timeSleep),
                    time = formatDateToTimeString(timeSleep),
                    "Bed Time",
                    "2",
                    "Bed"
                )
                if (isSetSleep) {
                    viewModel.getSleepByDateAndValue(bedTime.date, bedTime.value)
                }
            }
        }
    }

    private fun showDateTimePicker() {
        val datePicker = MaterialDatePicker.Builder.datePicker().build()
        val timePicker = MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_24H).build()
        datePicker.addOnPositiveButtonClickListener {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = it
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            timeSleep = calendar.time
            binding.dateAndTimePickerSleep.text = DateToString.convertDateToString(timeSleep)
            timePicker.show(childFragmentManager, "TAG")
        }

        timePicker.addOnPositiveButtonClickListener {
            val cal = Calendar.getInstance()
            cal.time = timeSleep
            cal.set(Calendar.HOUR_OF_DAY, timePicker.hour)
            cal.set(Calendar.MINUTE, timePicker.minute)
            cal.set(Calendar.SECOND, 0)
            timeSleep = cal.time
            binding.dateAndTimePickerSleep.text = DateToString.convertDateToString(timeSleep)
            viewModel.getWaterListByDate(formatDateToString(timeSleep))
            isSetSleep = true
        }
        datePicker.show(childFragmentManager, "TAG")
    }
}