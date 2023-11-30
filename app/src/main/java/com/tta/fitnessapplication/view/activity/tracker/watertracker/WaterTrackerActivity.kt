package com.tta.fitnessapplication.view.activity.tracker.watertracker

import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.skydoves.balloon.ArrowPositionRules
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.BalloonSizeSpec
import com.skydoves.balloon.showAsDropDown
import com.tta.fitnessapplication.R
import com.tta.fitnessapplication.data.model.History
import com.tta.fitnessapplication.data.model.Water
import com.tta.fitnessapplication.data.utils.Constant
import com.tta.fitnessapplication.data.utils.Constant.DATE.fullDateFormatter
import com.tta.fitnessapplication.data.utils.Constant.DATE.today
import com.tta.fitnessapplication.data.utils.getCurrentTime
import com.tta.fitnessapplication.data.utils.getWeekDates
import com.tta.fitnessapplication.data.utils.showAnimatedAlertDialog
import com.tta.fitnessapplication.databinding.ActivityWaterTrackerBinding
import com.tta.fitnessapplication.view.activity.history.HistoryViewModel
import com.tta.fitnessapplication.view.activity.tracker.calortracker.MealViewModel
import com.tta.fitnessapplication.view.base.BaseFragment
import java.text.SimpleDateFormat
import java.util.Calendar

class WaterTrackerActivity : BaseFragment<ActivityWaterTrackerBinding>() {
    private lateinit var waterViewModel: WaterViewModel
    private lateinit var historyViewModel: HistoryViewModel
    private var drink = 0
    private lateinit var dailyWater: String
    override fun getDataBinding(): ActivityWaterTrackerBinding {
        return ActivityWaterTrackerBinding.inflate(layoutInflater)
    }

    override fun initViewModel() {
        super.initViewModel()
        idUser = loginPreferences.getString(Constant.PREF.IDUSER, "").toString()
        dailyWater = loginPreferences.getString(Constant.PREF.WATER_INNEED, "2000").toString()
        waterViewModel = ViewModelProvider(this)[WaterViewModel::class.java]
        historyViewModel = ViewModelProvider(this)[HistoryViewModel::class.java]
    }

    override fun addObservers() {
        super.addObservers()
        historyViewModel.readAllData.observe(viewLifecycleOwner) {
            val listDataChart = arrayOf(0, 0, 0, 0, 0, 0, 0)
            var valueWater = 0
            for (item in it) {
                if (item.date == fullDateFormatter.format(today) && item.type == 1) {
                    if (item.value.toString().trim().toInt() is Int){
                        valueWater += item.value!!.trim().toInt()
                    }
                }
                for (i in 0 until getWeekDates().size) {
                    if (getWeekDates()[i].toString() == item.date) {
                        listDataChart[i] = item.value!!.toInt() + listDataChart[i]
                        listDataChart[i] = listDataChart[i]
                    }
                }
                binding.chart.viewColume1.setProgress((listDataChart[0] * 0.05).toInt())
                binding.chart.viewColume2.setProgress((listDataChart[1] * 0.05).toInt())
                binding.chart.viewColume3.setProgress((listDataChart[2] * 0.05).toInt())
                binding.chart.viewColume4.setProgress((listDataChart[3] * 0.05).toInt())
                binding.chart.viewColume5.setProgress((listDataChart[4] * 0.05).toInt())
                binding.chart.viewColume6.setProgress((listDataChart[5] * 0.05).toInt())
                binding.chart.viewColume7.setProgress((listDataChart[6] * 0.05).toInt())
            }
            binding.textView60.text = "$valueWater ml"
            val result =
                valueWater.toFloat() / dailyWater.toInt().toFloat()  // Calculate the value
            // Convert the result to a SeekBar progress value (0-100)
            val progress = (result * 100).toInt()
            binding.tvPercentDrink.text = "${progress}%"
            binding.seekBar.progress = progress.toInt()
        }
    }

    override fun initView() {
        super.initView()
        with(binding) {
            seekBar.max = 100
            seekBar.isEnabled = false
            chart.tvValueY0.text = "ml"
            chart.tvValueY1.text = "400"
            chart.tvValueY2.text = "800"
            chart.tvValueY3.text = "1200"
            chart.tvValueY4.text = "1600"
            chart.tvValueY5.text = "2000"
            tvTarget.text = dailyWater + "ml"
        }
    }

    override fun addEvent() {
        with(binding) {
            view13.setOnClickListener {
                findNavController().popBackStack()
            }
            cardViewWaterCaculate.setOnClickListener {
                findNavController().navigate(R.id.action_waterTrackerActivity_to_waterCaculateActivity)
            }
            cardViewHistoryWaterTracker.setOnClickListener {
                findNavController().navigate(R.id.action_waterTrackerActivity_to_waterHistoryActivity)
            }
            imgCup100ml.setOnClickListener {
                drink = 100
                appCompatButton.text = "+$drink ml"
            }
            imgCup125ml.setOnClickListener {
                drink = 125
                appCompatButton.text = "+$drink ml"
            }
            imgCup150ml.setOnClickListener {
                drink = 150
                appCompatButton.text = "+$drink ml"
            }
            imgCup200ml.setOnClickListener {
                drink = 200
                appCompatButton.text = "+$drink ml"
            }
            imgCup250ml.setOnClickListener {
                drink = 250
                appCompatButton.text = "+$drink ml"
            }
            imgCup300ml.setOnClickListener {
                drink = 300
                appCompatButton.text = "+$drink ml"
            }
            imgCup350ml.setOnClickListener {
                drink = 350
                appCompatButton.text = "+$drink ml"
            }
            imgCup400ml.setOnClickListener {
                drink = 400
                appCompatButton.text = "+$drink ml"
            }
            imgCupCustomize.setOnClickListener {
                drink = 500
                appCompatButton.text = "+$drink ml"
            }

            appCompatButton.setOnClickListener {
//                val water = Water(
//                    0,
//                    fullDateFormatter.format(today),
//                    getCurrentTime(),
//                    "Drink water",
//                    "1",
//                    drink.toString()
//                )
                val history = History(
                    null,
                    idUser.toInt(),
                    fullDateFormatter.format(today),
                    getCurrentTime(),
                    "Drink water",
                    1,
                    drink.toString()
                )
//                waterViewModel.addWater(water)
                historyViewModel.addHistory(history)
                showAnimatedAlertDialog(requireContext(), "Successful", "Good jobs my friend")
            }

            val balloonWater = Balloon.Builder(requireContext())
                .setWidthRatio(1.0f)
                .setHeight(BalloonSizeSpec.WRAP)
                .setText("Track your daily water drinking history. Try to drink water every day!")
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
                viewInfo.showAsDropDown(balloonWater)
            }

            clSettingNoti.setOnClickListener {
                // go to noti activity
                // 0 type all, 1 water, 2 sleep, 3 eat
                val action = WaterTrackerActivityDirections.actionWaterTrackerActivityToManagerNotification(1)
                findNavController().navigate(action)
            }
        }
    }
}