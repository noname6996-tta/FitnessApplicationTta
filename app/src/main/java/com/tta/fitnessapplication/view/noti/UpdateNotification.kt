package com.tta.fitnessapplication.view.noti

import android.app.AlarmManager
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.RadioButton
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
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
import com.tta.fitnessapplication.databinding.FragmentUpdateNotiBinding
import com.tta.fitnessapplication.view.base.BaseFragment
import com.tta.fitnessapplication.view.br.ClockAlarmManager
import java.util.Calendar
import java.util.TimeZone

class UpdateNotification : BaseFragment<FragmentUpdateNotiBinding>() {
    private val args: UpdateNotificationArgs by navArgs()
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

    override fun getDataBinding(): FragmentUpdateNotiBinding {
        return FragmentUpdateNotiBinding.inflate(layoutInflater)
    }

    override fun initViewModel() {
        super.initViewModel()
        noti = args.noti
        Log.e("noti", noti.toString())
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
            if (it.isNotEmpty() && canAddNoti) {
//                setAlarm(noti)
                Snackbar.make(binding.root, "Update notification success", Snackbar.LENGTH_LONG)
                    .show()
                findNavController().popBackStack()
            }
        }

        // Observe the updateStatus LiveData
        viewModel.updateStatus.observe(viewLifecycleOwner) { updated ->
            if (updated) {
                // The update is completed, you can perform any necessary action here
                // For example, you can call the setAlarm function to update the notification alarm
                setAlarm(noti)
            }
        }

    }

    override fun initView() {
        super.initView()
        binding.dateAndTimePicker.text = "${noti.hour}h ${noti.min}"
        when (noti.type) {
            1 -> binding.radioButton1.isChecked = true
            2 -> {
                if (noti.icon == R.drawable.alarm_clock) {
                    binding.radioButton2.isChecked = true
                } else binding.radioButton3.isChecked = true
            }

            3 -> binding.radioButton4.isChecked = true
        }
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
                    if (binding.tvContentTitle2.length() == 0) {
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
        binding.btnUpdate.setOnClickListener {
            viewModel.updateNotification(noti)
        }

        binding.btnDelete.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Notification")
                .setMessage("Do you want delete this notification ?")
                .setPositiveButton("Yes",
                    DialogInterface.OnClickListener { _, _ ->
                        viewModel.deleteNotification(noti)
                        findNavController().popBackStack()
                    })
                .setNegativeButton("No", null)
                .show()
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
        val alarmManager = requireActivity().getSystemService(Context.ALARM_SERVICE) as AlarmManager

        // Cancel the existing alarm if it's already set
        val intent = Intent(requireContext(), ClockAlarmManager::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            requireContext(),
            notification.id,
            intent,
            PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
        )
        pendingIntent?.let {
            alarmManager.cancel(it)
        }

        // Set the alarm to trigger at the updated time
        val calendar = Calendar.getInstance()
        calendar.timeZone = TimeZone.getDefault()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.set(Calendar.HOUR_OF_DAY, notification.hour)
        calendar.set(Calendar.MINUTE, notification.min)
        calendar.set(Calendar.SECOND, 0)

        // Set the repeating interval to 24 hours
        val interval = AlarmManager.INTERVAL_DAY

        // Create intent for your BroadcastReceiver
        val newIntent = Intent(requireContext(), ClockAlarmManager::class.java)
        newIntent.putExtra("noti_info", notification)
        val newPendingIntent = PendingIntent.getBroadcast(
            requireContext(),
            notification.id,
            newIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Set the new repeating alarm with the updated notification
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            interval,
            newPendingIntent
        )

        findNavController().popBackStack()
    }
}