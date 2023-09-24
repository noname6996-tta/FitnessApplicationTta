package com.tta.fitnessapplication.view.activity.tracker.SleepTracker

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.github.mikephil.charting.charts.BarChart
import com.skydoves.balloon.ArrowPositionRules
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.BalloonSizeSpec
import com.skydoves.balloon.showAlignTop
import com.skydoves.balloon.showAsDropDown
import com.tta.fitnessapplication.R
import com.tta.fitnessapplication.databinding.ActivitySleepTrackerBinding
import com.tta.fitnessapplication.view.base.BaseFragment

class SleepTrackerActivity : BaseFragment<ActivitySleepTrackerBinding>() {
    private val viewModel = SleepTrackerViewModel()

    override fun getDataBinding(): ActivitySleepTrackerBinding {
        return ActivitySleepTrackerBinding.inflate(layoutInflater)
    }

    override fun initViewModel() {
        super.initViewModel()
        viewModel.getData()
    }

    override fun addObservers() {
        super.addObservers()
        viewModel.listSleepTracker.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.tvTimeSleep.text = "No data"
            } else {

            }
        }
        viewModel.message.observe(viewLifecycleOwner) {

        }
    }

     override fun addEvent() {
        binding.view13.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.view22.setOnClickListener {
            findNavController().navigate(R.id.action_sleepTrackerActivity_to_sleepScheduleActivity)
        }
        val balloonSleep = Balloon.Builder(requireContext())
            .setWidthRatio(1.0f)
            .setHeight(BalloonSizeSpec.WRAP)
            .setText("Track your daily sleep history. Try to sleep enough every day!")
            .setTextColorResource(com.tta.fitnessapplication.R.color.white)
            .setTextSize(15f)
            .setArrowPositionRules(ArrowPositionRules.ALIGN_BALLOON)
            .setArrowSize(10)
            .setArrowPosition(0.5f)
            .setPadding(12)
            .setCornerRadius(8f)
            .setBackgroundColorResource(com.tta.fitnessapplication.R.color.text)
            .setBalloonAnimation(BalloonAnimation.ELASTIC)
            .build()

        binding.viewInfo.setOnClickListener {
            binding.viewInfo.showAsDropDown(balloonSleep)
        }

        val balloonInfoColumnone = Balloon.Builder(requireContext())
            .setWidthRatio(1.0f)
            .setHeight(BalloonSizeSpec.WRAP)
            .setText("2h")
            .setTextColorResource(com.tta.fitnessapplication.R.color.white)
            .setTextSize(15f)
            .setArrowPositionRules(ArrowPositionRules.ALIGN_BALLOON)
            .setArrowSize(10)
            .setArrowPosition(0.5f)
            .setPadding(2)
            .setCornerRadius(8f)
            .setBackgroundColorResource(com.tta.fitnessapplication.R.color.text)
            .setBalloonAnimation(BalloonAnimation.ELASTIC)
            .build()

        binding.chart.viewColume1.setOnClickListener {
            binding.chart.viewColume1.showAlignTop(balloonInfoColumnone)
        }
    }
}