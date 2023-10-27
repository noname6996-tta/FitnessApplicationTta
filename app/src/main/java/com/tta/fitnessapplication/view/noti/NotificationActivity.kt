package com.tta.fitnessapplication.view.noti

import android.app.AlarmManager
import android.app.Dialog
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.flask.colorpicker.ColorPickerView
import com.flask.colorpicker.builder.ColorPickerDialogBuilder
import com.google.android.material.button.MaterialButton
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.skydoves.balloon.ArrowPositionRules
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.BalloonSizeSpec
import com.skydoves.balloon.showAsDropDown
import com.tta.fitnessapplication.R
import com.tta.fitnessapplication.data.model.Food
import com.tta.fitnessapplication.data.model.noti.CategoryInfo
import com.tta.fitnessapplication.data.model.noti.TaskCategoryInfo
import com.tta.fitnessapplication.data.utils.DateToString
import com.tta.fitnessapplication.databinding.ActivityNotificationBinding
import com.tta.fitnessapplication.view.base.BaseActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date
import java.util.Objects
import java.util.Random
import com.tta.fitnessapplication.data.model.noti.TaskInfo
import com.tta.fitnessapplication.databinding.FragmentFoodNotificationBinding
import com.tta.fitnessapplication.view.MainActivity
import com.tta.fitnessapplication.view.base.BaseFragment
import com.tta.fitnessapplication.view.br.AlarmReceiver

class NotificationActivity() : BaseFragment<FragmentFoodNotificationBinding>() {
    private lateinit var viewModelNotificationViewModel: NotificationViewModel
    val args: NotificationActivityArgs by navArgs()
    var idMeal = 0
    var foodName = ""
    private var taskInfo = TaskInfo(
        0,
        "",
        Date(8640000000000000),
        1,
        false,
        "tta",
        "",
        0,
        "",
        ""
    )
    private var categoryInfo = CategoryInfo("tta","#000000")
    override fun getDataBinding(): FragmentFoodNotificationBinding {
        return FragmentFoodNotificationBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModelNotificationViewModel = (activity as MainActivity).viewModelNoti
    }

    override fun initViewModel() {
        super.initViewModel()
        idMeal = args.typeNoti
        mainViewModel.getFoodById(idMeal.toString())
        createNotification()
        setInitialValues()
    }

    override fun addEvent() {
        with(binding) {
            viewBack.setOnClickListener {
                findNavController().popBackStack()
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
            viewInfo.setOnClickListener {
                viewInfo.showAsDropDown(ballonInfo)
            }
        }
    }

    private fun setInitialValues() {
        var str = DateToString.convertDateToString(taskInfo.date)
        if(str=="N/A")str="Due Date"

        binding.apply {
            dateAndTimePicker.text = str

            //ClickListeners
            dateAndTimePicker.setOnClickListener { showDateTimePicker()}
            btnAdd.setOnClickListener{ addTask()}
        }
    }

    private fun createNotification() {
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel("to_do_list", "Tasks Notification Channel", importance).apply {
            description = "Notification for Tasks"
        }
        val notificationManager = activity?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun addTask() {
        val date = Date()
        if(taskInfo.description.isNullOrBlank())
        {
            Snackbar.make(binding.root, "Please choose when you eat", Snackbar.LENGTH_SHORT).setAction("Action", null).show()
        }
        else {
            if(binding.btnAdd.text.equals("Update")) {
//                updateTask()
            }else {
                val diff = (Date().time/1000) - 1640908800
                taskInfo.id = diff.toInt()
                viewModelNotificationViewModel.insertTaskAndCategory(taskInfo, categoryInfo)
                if(!taskInfo.status && taskInfo.date>date && taskInfo.date.seconds == 5)
                    setAlarm(taskInfo)
            }
        }
        Snackbar.make(binding.root, "Add Notification Success", Snackbar.LENGTH_SHORT).show()
        findNavController().popBackStack()
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
            taskInfo.date = calendar.time
            binding.dateAndTimePicker.text = DateToString.convertDateToString(taskInfo.date)
            timePicker.show(childFragmentManager, "TAG")
        }

        timePicker.addOnPositiveButtonClickListener{
            val cal = Calendar.getInstance()
            cal.time = taskInfo.date
            cal.set(Calendar.HOUR_OF_DAY, timePicker.hour)
            cal.set(Calendar.MINUTE, timePicker.minute)
            cal.set(Calendar.SECOND, 5)
            taskInfo.date = cal.time
            binding.dateAndTimePicker.text = DateToString.convertDateToString(taskInfo.date)
        }
        datePicker.show(childFragmentManager,"TAG")
    }

    private fun setAlarm(taskInfo: TaskInfo) {
        val alarmManager = activity?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(requireContext(), AlarmReceiver::class.java)
        intent.putExtra("task_info", taskInfo)
        val pendingIntent = PendingIntent.getBroadcast(requireContext(), taskInfo.id, intent, PendingIntent.FLAG_IMMUTABLE)
        val mainActivityIntent = Intent(requireContext(), MainActivity::class.java)
        val basicPendingIntent = PendingIntent.getActivity(requireContext(), taskInfo.id, mainActivityIntent, PendingIntent.FLAG_IMMUTABLE)
        val clockInfo = AlarmManager.AlarmClockInfo(taskInfo.date.time, basicPendingIntent)
        alarmManager.setAlarmClock(clockInfo, pendingIntent)
    }

    override fun addObservers() {
        super.addObservers()
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
            // category == value
            taskInfo.category = food.calo
            // priority == id
            taskInfo.priority = food.id
            taskInfo.foodName = food.name
            taskInfo.detail = food.desc
            taskInfo.image = food.image
            taskInfo.time = food.type.toInt()
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
                    taskInfo.description = "Breakfast, Time to eat ${foodName}"
                }

                "Lunch" -> {
                    taskInfo.description = "Lunch, Time to eat ${foodName}"
                }

                "Dinner" -> {
                    taskInfo.description = "Dinner, Time to eat ${foodName}"
                }
            }
        }
    }

}