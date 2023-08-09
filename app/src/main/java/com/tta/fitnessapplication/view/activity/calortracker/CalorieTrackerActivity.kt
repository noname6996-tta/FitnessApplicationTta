package com.tta.fitnessapplication.view.activity.calortracker

import com.skydoves.balloon.ArrowPositionRules
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.BalloonSizeSpec
import com.skydoves.balloon.showAsDropDown
import com.tta.fitnessapplication.R
import com.tta.fitnessapplication.databinding.ActivityCalorTrackerBinding
import com.tta.fitnessapplication.view.HistoryViewModelGoogleData
import com.tta.fitnessapplication.view.base.BaseActivity

class CalorieTrackerActivity : BaseActivity<ActivityCalorTrackerBinding>() {
    private val viewModel = HistoryViewModelGoogleData()
    private val mealAdapter = ItemTodayMealAdapter()
    private val somethingToEatAdapter = ItemFindSomethingToEatAdapter()
    override fun getDataBinding(): ActivityCalorTrackerBinding {
        return ActivityCalorTrackerBinding.inflate(layoutInflater)
    }

    override fun initViewModel() {
        super.initViewModel()
        viewModel.listCaloriesExpended.observe(this) {
            binding.tvKcalBurn.text = it.last().value + "\nBURN"
        }
    }

    override fun initView() {
        super.initView()
        val balloonProtein = Balloon.Builder(this)
            .setWidthRatio(1.0f)
            .setHeight(BalloonSizeSpec.WRAP)
            .setText("balloonProtein!")
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
        binding.tvProtein.setOnClickListener {
            binding.tvProtein.showAsDropDown(balloonProtein)
        }
        val balloonCarb = Balloon.Builder(this)
            .setWidthRatio(1.0f)
            .setHeight(BalloonSizeSpec.WRAP)
            .setText("balloonCarb!")
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
        binding.tvCarbohydrate.setOnClickListener {
            binding.tvCarbohydrate.showAsDropDown(balloonCarb)
        }

        val balloonFat = Balloon.Builder(this)
            .setWidthRatio(1.0f)
            .setHeight(BalloonSizeSpec.WRAP)
            .setText("balloonCarb!")
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
        binding.tvFat.setOnClickListener {
            binding.tvFat.showAsDropDown(balloonFat)
        }
    }

    override fun addEvent() {
        super.addEvent()
        binding.viewBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
            this.finish()
        }

        binding.view22.setOnClickListener {

        }
    }
}