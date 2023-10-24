package com.tta.fitnessapplication.view.noti

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.RadioButton
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
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
import java.util.TimeZone

class NewNotificationFragment : BaseFragment<ActivityNotificationBinding>() {
    private lateinit var viewModel: NewNotificationViewModel
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent
    private var isHasBedTime = false
    private var isHasWakeUp = false
    private var canClick = true
    private var canAddNoti = false

    private var noti = Notification(
        0, "", "", R.drawable.icon_notif, "200", 0, 0, 1, true
    )

    override fun getDataBinding(): ActivityNotificationBinding {
        return ActivityNotificationBinding.inflate(layoutInflater)
    }

    override fun initViewModel() {
        super.initViewModel()
        viewModel = ViewModelProvider(this)[NewNotificationViewModel::class.java]
    }

    override fun addObservers() {
        super.addObservers()
        viewModel.readAllData.observe(viewLifecycleOwner) {
            for (item in it) {
                if (item.type == 2) {
                    when (item.icon) {
                        R.drawable.icon_bed -> isHasBedTime = true
                        R.drawable.alarm_clock -> isHasWakeUp = true
                    }
                }
            }
            if (it.isNotEmpty()&&canAddNoti){
                noti = it.last()
                Log.e("addd",it.last().toString())
                setAlarm(noti)
                Snackbar.make(binding.root, "Set up notification success", Snackbar.LENGTH_LONG).show()
                findNavController().popBackStack()
            }
        }
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
        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val selectedRadioButton = group.findViewById<RadioButton>(checkedId)
            val selectedOption = selectedRadioButton.text.toString()
            // Do something with the selected option
            when (selectedOption) {
                "Drink Water" -> {
                    canClick = true
                    binding.materialTextView3.visibility = View.VISIBLE
                    binding.materialTextView.visibility = View.VISIBLE
                    binding.tvContentTitle2.visibility = View.VISIBLE
                    noti.value = "200"
                    if (binding.tvContentTitle2.length()==0) {
                        noti.value = "200"
                    } else {
                        noti.value = binding.tvContentTitle2.text.toString().trim()
                    }
                    noti.title = "Drink water"
                    noti.text = "Drink water"
                    noti.icon = R.drawable.ic_cup_400ml
                    noti.type = 1
                }

                "Wake up" -> {
                    canClick = true
                    binding.materialTextView3.visibility = View.GONE
                    binding.materialTextView.visibility = View.GONE
                    binding.tvContentTitle2.visibility = View.GONE
                    if (isHasWakeUp) {
                        Snackbar.make(
                            binding.root,
                            "You already set up time to wake up",
                            Snackbar.LENGTH_LONG
                        ).show()
                        canClick = false
                    } else {
                        noti.title = "Wake up"
                        noti.text = "Wake up"
                        noti.icon = R.drawable.alarm_clock
                        noti.type = 2
                    }
                }

                "Bed time" -> {
                    canClick = true
                    binding.materialTextView3.visibility = View.GONE
                    binding.materialTextView.visibility = View.GONE
                    binding.tvContentTitle2.visibility = View.GONE
                    if (isHasBedTime) {
                        Snackbar.make(
                            binding.root,
                            "You already set up bed time",
                            Snackbar.LENGTH_LONG
                        ).show()
                        canClick = false
                    } else {
                        noti.title = "Bed time"
                        noti.text = "Bed time"
                        noti.icon = R.drawable.icon_bed
                        noti.type = 2
                    }
                }

                "Do exercise" -> {
                    canClick = true
                    binding.materialTextView3.visibility = View.GONE
                    binding.materialTextView.visibility = View.GONE
                    binding.tvContentTitle2.visibility = View.GONE
                    noti.title = "Do exercise"
                    noti.text = "Do exercise"
                    noti.icon = R.drawable.ic_logo
                    noti.type = 3
                }
            }
        }
    }

    override fun addEvent() {
        super.addEvent()
        binding.btnAdd.setOnClickListener {
            if (noti.text == "") {
                Snackbar.make(
                    binding.root,
                    "You have to choose type notification",
                    Snackbar.LENGTH_LONG
                ).show()
            } else {
                if (canClick) {
                    canAddNoti = true
                    viewModel.addNotification(noti)
                } else {
                    if (isHasBedTime) {
                        Snackbar.make(
                            binding.root,
                            "You already set up bed time",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }

                    if (isHasWakeUp) {
                        Snackbar.make(
                            binding.root,
                            "You already set up time to wake up",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
        binding.dateAndTimePicker.setOnClickListener { showDateTimePicker() }
        binding.viewBack.setOnClickListener {
            findNavController().popBackStack()
        }
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
        // Set the alarm to trigger at 8:00 AM (replace with your desired time)
        val calendar = Calendar.getInstance()
        calendar.timeZone = TimeZone.getDefault()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.set(Calendar.HOUR_OF_DAY, notification.hour)
        calendar.set(Calendar.MINUTE, notification.min)
        calendar.set(Calendar.SECOND, 0)
        // Set the repeating interval to 24 hours
        val interval = AlarmManager.INTERVAL_DAY
        // Create intent for your BroadcastReceiver
        val intent = Intent(requireContext(), ClockAlarmManager::class.java)
        intent.putExtra("noti_info", notification)
        pendingIntent = PendingIntent.getBroadcast(
            requireContext(),
            notification.id,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            interval,
            pendingIntent
        )
//        if (notification.enable) {
//            // Schedule the alarm
//
//        } else {
//            // To cancel the alarm
//            alarmManager.cancel(pendingIntent)
//        }
    }
}