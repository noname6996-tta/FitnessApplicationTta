package com.tta.fitnessapplication.view.activity.tracker.SleepTracker

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.BarChart
import com.skydoves.balloon.ArrowPositionRules
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.BalloonSizeSpec
import com.skydoves.balloon.showAlignTop
import com.skydoves.balloon.showAsDropDown
import com.tta.fitnessapplication.databinding.ActivitySleepTrackerBinding

class SleepTrackerActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySleepTrackerBinding
    private val viewModel = SleepTrackerViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySleepTrackerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        addObsever()
        addEvent()
    }

    private fun addObsever() {
        viewModel.getData()
        viewModel.listSleepTracker.observe(this) {
            if (it.isEmpty()) {
                binding.tvTimeSleep.text = "No data"
            } else {

            }
        }
        viewModel.message.observe(this) {

        }
    }

    private fun addEvent() {
        binding.view13.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
            finish()
        }

        binding.view22.setOnClickListener {
            startActivity(Intent(this, SleepScheduleActivity::class.java))
        }
        val balloonSleep = Balloon.Builder(this)
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

        val balloonInfoColumnone = Balloon.Builder(this)
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