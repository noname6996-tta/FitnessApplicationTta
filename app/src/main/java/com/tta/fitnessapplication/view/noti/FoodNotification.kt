package com.tta.fitnessapplication.view.noti

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RadioButton
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.skydoves.balloon.ArrowPositionRules
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.BalloonSizeSpec
import com.skydoves.balloon.showAsDropDown
import com.tta.fitnessapplication.R
import com.tta.fitnessapplication.data.model.Food
import com.tta.fitnessapplication.data.model.noti.Notification
import com.tta.fitnessapplication.data.utils.DateToString
import com.tta.fitnessapplication.databinding.FragmentFoodNotificationBinding
import com.tta.fitnessapplication.view.base.BaseFragment
import com.tta.fitnessapplication.view.br.ClockAlarmManager
import java.util.Calendar

class FoodNotification : BaseFragment<FragmentFoodNotificationBinding>() {
    var idMeal = 0
    var foodName = ""
    override fun getDataBinding(): FragmentFoodNotificationBinding {
        return FragmentFoodNotificationBinding.inflate(layoutInflater)
    }

    private lateinit var viewModel: NewNotificationViewModel
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent
    private var canAddNoti = false

    private var noti = Notification(
        0, "Time to eat", "Time to eat", R.drawable.baseline_fastfood_24, "", 0, 0, 4, true
    )

    override fun initViewModel() {
        super.initViewModel()
        mainViewModel.getFoodById(idMeal.toString())
        viewModel = ViewModelProvider(this)[NewNotificationViewModel::class.java]
    }

    override fun addObservers() {
        super.addObservers()
        viewModel.readAllData.observe(viewLifecycleOwner) {
            if (it.isNotEmpty() && canAddNoti) {
                noti = it.last()
                Log.e("addd", it.last().toString())
                setAlarm(noti)
                Snackbar.make(binding.root, "Set up notification success", Snackbar.LENGTH_LONG)
                    .show()
                findNavController().popBackStack()
            }
        }
        mainViewModel.listFoodById.observe(viewLifecycleOwner) {
            setData(it[0])
        }
    }

    private fun setData(food: Food) {
        binding.apply {
            Glide.with(requireContext())
                .load(food.image)
                .error(R.drawable.ic_breafast)
                .into(imgFood)
            tvNameMeal.text = food.name
            noti.value = food.calo + "Calo"
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
                "Breakfast" -> {
                    noti.title = "Breakfast time! Time to eat ${foodName}"
                    noti.text = "Breakfast time! Time to eat ${foodName}"
                }

                "Lunch" -> {
                    noti.title = "Lunch time! Time to eat ${foodName}"
                    noti.text = "Lunch time! Time to eat ${foodName}"
                }

                "Dinner" -> {
                    noti.title = "Dinner time! Time to eat ${foodName}"
                    noti.text = "Dinner time! Time to eat ${foodName}"
                }
            }
        }
    }

    override fun addEvent() {
        super.addEvent()
        binding.btnAdd.setOnClickListener {
            viewModel.addNotification(noti)
        }
        binding.dateAndTimePicker.setOnClickListener { showDateTimePicker() }
        binding.viewBack.setOnClickListener {
            findNavController().popBackStack()
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

            timePicker.show(childFragmentManager, "TAG")
        }
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


    @SuppressLint("ScheduleExactAlarm")
    private fun setAlarm(notification: Notification) {
        // Initialize AlarmManager
        alarmManager = requireActivity().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        // Set the alarm to trigger at 8:00 AM (replace with your desired time)
        val calendar = Calendar.getInstance()
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

        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
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