package com.tta.fitnessapplication.view.activity.tracker.SleepTracker

import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.tta.fitnessapplication.data.model.History
import com.tta.fitnessapplication.data.model.Sleep
import com.tta.fitnessapplication.data.model.SleepPair
import com.tta.fitnessapplication.data.utils.DateToString
import com.tta.fitnessapplication.data.utils.formatDateToString
import com.tta.fitnessapplication.data.utils.formatDateToTimeString
import com.tta.fitnessapplication.databinding.FragmentAddTimeWakeBinding
import com.tta.fitnessapplication.view.activity.history.HistoryViewModel
import com.tta.fitnessapplication.view.base.BaseFragment
import java.util.Calendar
import java.util.Date

class AddTimeWakeFragment : BaseFragment<FragmentAddTimeWakeBinding>() {
    private lateinit var viewModel: SleepViewModel
    private lateinit var historyViewModel: HistoryViewModel
    private var timeWake: Date = Date()
    private var isSetWake = false
    override fun getDataBinding(): FragmentAddTimeWakeBinding {
        return FragmentAddTimeWakeBinding.inflate(layoutInflater)
    }

    override fun initViewModel() {
        super.initViewModel()
        viewModel = ViewModelProvider(this)[SleepViewModel::class.java]
        historyViewModel = ViewModelProvider(this)[HistoryViewModel::class.java]
    }

    override fun addObservers() {
        super.addObservers()
        viewModel.sleepCheckWake.observe(viewLifecycleOwner) { item ->
            if (item != null) {
                // trung bien thuc hien update
                viewModel.updateSleep(item)
                historyViewModel.updateHistory(History(null, idUser.toInt(), date = formatDateToString(timeWake), time = formatDateToTimeString(timeWake), "Wake up Time", 2, "Wake"))
                findNavController().popBackStack()
            } else {
                // chua co thuc hien add
                viewModel.addSleep(Sleep(0, date = formatDateToString(timeWake), time = formatDateToTimeString(timeWake), "Wake up Time", "2", "Wake"))
                val history2 = History(null, idUser.toInt(), date = formatDateToString(timeWake), time = formatDateToTimeString(timeWake), "Wake up Time", 2, "Wake")
                historyViewModel.addHistory(history2)
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

            dateAndTimePickerWakeup.setOnClickListener { showDateTimePicker() }

            btnDone.setOnClickListener {
                val wakeTime = Sleep(
                    0,
                    date = formatDateToString(timeWake),
                    time = formatDateToTimeString(timeWake),
                    "Wake up Time",
                    "2",
                    "Wake"
                )
                viewModel.getSleepByDateAndValueWake(wakeTime.date, wakeTime.value)
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
            timeWake = calendar.time
            binding.dateAndTimePickerWakeup.text = DateToString.convertDateToString(timeWake)
            timePicker.show(childFragmentManager, "TAG")
        }

        timePicker.addOnPositiveButtonClickListener {
            val cal = Calendar.getInstance()
            cal.time = timeWake
            cal.set(Calendar.HOUR_OF_DAY, timePicker.hour)
            cal.set(Calendar.MINUTE, timePicker.minute)
            cal.set(Calendar.SECOND, 0)
            timeWake = cal.time
            binding.dateAndTimePickerWakeup.text = DateToString.convertDateToString(timeWake)
            viewModel.getWaterListByDate(formatDateToString(timeWake))
            isSetWake = true
        }
        datePicker.show(childFragmentManager, "TAG")
    }
}