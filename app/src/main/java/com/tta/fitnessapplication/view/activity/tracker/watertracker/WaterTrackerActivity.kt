package com.tta.fitnessapplication.view.activity.tracker.watertracker

import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.skydoves.balloon.ArrowPositionRules
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.BalloonSizeSpec
import com.skydoves.balloon.showAsDropDown
import com.tta.fitnessapplication.R
import com.tta.fitnessapplication.data.utils.Constant
import com.tta.fitnessapplication.data.utils.Constant.DATE.fullDateFormatter
import com.tta.fitnessapplication.data.utils.Constant.DATE.today
import com.tta.fitnessapplication.data.utils.getCurrentTime
import com.tta.fitnessapplication.data.utils.showAnimatedAlertDialog
import com.tta.fitnessapplication.databinding.ActivityWaterTrackerBinding
import com.tta.fitnessapplication.view.base.BaseFragment

class WaterTrackerActivity : BaseFragment<ActivityWaterTrackerBinding>() {

    private lateinit var idUser: String
    private var drink = 0
    private val dailyWater = 2000
    override fun getDataBinding(): ActivityWaterTrackerBinding {
        return ActivityWaterTrackerBinding.inflate(layoutInflater)
    }

    override fun initViewModel() {
        super.initViewModel()
        idUser = loginPreferences.getString(Constant.PREF.IDUSER, "").toString()
        mainViewModel.getListHistoryByDateAndType(idUser, fullDateFormatter.format(today), "1")
        mainViewModel.listHistoryByDateAndType.observe(viewLifecycleOwner) {
            if (it.isSuccessful) {
                if (it.body()?.response == 0) {
//                    Snackbar.make(binding.root, "No data", Snackbar.LENGTH_SHORT).show()
                } else {
                    var value = 0
                    if (it.body()?.data != null) {
                        for (i in 0 until it.body()?.data!!.size) {
                            value += it.body()?.data!![i].value!!.toInt()
                        }
                        binding.textView60.text = "$value ml"
                        var percent: Float = ((value / dailyWater * 100).toFloat())
                        binding.tvPercentDrink.text = "$percent%"
                        binding.seekBar.progress = percent.toInt()
                    }
                }
            } else {
                Snackbar.make(binding.root, it.errorBody().toString(), Snackbar.LENGTH_SHORT).show()
            }
        }
        mainViewModel.createHistoryStatus.observe(viewLifecycleOwner) {
            if (it.isSuccessful) {
                mainViewModel.getListHistoryByDateAndType(
                    idUser,
                    fullDateFormatter.format(today),
                    "1"
                )
                showAnimatedAlertDialog(requireContext(), "Successful", "Good jobs my friend")
            } else {
                showAnimatedAlertDialog(
                    requireContext(),
                    "Notification",
                    "Oh some thing went wrong!"
                )
            }
        }
    }

    override fun initView() {
        super.initView()
        binding.seekBar.max = 100
        binding.seekBar.isEnabled = false
    }


    override fun addEvent() {
        with(binding){
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
                mainViewModel.createHistory(
                    idUser,
                    fullDateFormatter.format(today),
                    getCurrentTime(),
                    "Drink water",
                    "1",
                    drink.toString()
                )
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
                findNavController().navigate(R.id.action_waterTrackerActivity_to_notificationActivity)
            }
        }
    }
}