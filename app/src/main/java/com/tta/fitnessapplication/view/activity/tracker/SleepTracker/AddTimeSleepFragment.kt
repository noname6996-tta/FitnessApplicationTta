package com.tta.fitnessapplication.view.activity.tracker.SleepTracker

import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.tta.fitnessapplication.data.model.History
import com.tta.fitnessapplication.data.model.Sleep
import com.tta.fitnessapplication.data.utils.DateToString
import com.tta.fitnessapplication.data.utils.formatDateToString
import com.tta.fitnessapplication.data.utils.formatDateToTimeString
import com.tta.fitnessapplication.databinding.FragmentAddTimeSleepBinding
import com.tta.fitnessapplication.view.activity.history.HistoryViewModel
import com.tta.fitnessapplication.view.base.BaseFragment
import java.util.Calendar
import java.util.Date
import java.util.concurrent.TimeUnit

class AddTimeSleepFragment : BaseFragment<FragmentAddTimeSleepBinding>() {
    private lateinit var viewModel: SleepViewModel
    private lateinit var historyViewModel: HistoryViewModel
    private var timeSleep: Date = Date()
    private var timeWake: Date = Date()
    private var isSetSleep = false
    private var isSetWake = false
    override fun getDataBinding(): FragmentAddTimeSleepBinding {
        return FragmentAddTimeSleepBinding.inflate(layoutInflater)
    }

    override fun initViewModel() {
        super.initViewModel()
        viewModel = ViewModelProvider(this)[SleepViewModel::class.java]
        historyViewModel = ViewModelProvider(this)[HistoryViewModel::class.java]
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
                if (isSetSleep and isSetWake) {
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
                    val history1 = History(
                        null,
                        null,
                        date = formatDateToString(timeSleep),
                        time = formatDateToTimeString(timeSleep),
                        "Bed Time",
                        2,
                        "Bed"
                    )
                    val history2 = History(
                        null,
                        null,
                        date = formatDateToString(timeWake),
                        time = formatDateToTimeString(timeWake),
                        "Wake up Time",
                        2,
                        "Wake"
                    )
                    historyViewModel.addHistory(history1)
                    historyViewModel.addHistory(history2)
                    findNavController().popBackStack()
                } else {
                    Snackbar.make(
                        binding.root,
                        "You have to choose both bed time and wake up time",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
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
            cal.set(Calendar.SECOND, 5)
            if (id == 0) {
                timeSleep = cal.time
                binding.dateAndTimePickerSleep.text = DateToString.convertDateToString(timeSleep)
                isSetSleep = true
            } else {
                timeWake = cal.time
                binding.dateAndTimePickerWakeup.text = DateToString.convertDateToString(timeWake)
                isSetWake = true
            }
        }
        datePicker.show(childFragmentManager, "TAG")
    }
}