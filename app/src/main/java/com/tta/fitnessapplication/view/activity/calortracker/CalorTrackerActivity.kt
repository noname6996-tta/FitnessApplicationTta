package com.tta.fitnessapplication.view.activity.calortracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.skydoves.balloon.ArrowPositionRules
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.BalloonSizeSpec
import com.skydoves.balloon.showAlignTop
import com.tta.fitnessapplication.R
import com.tta.fitnessapplication.databinding.ActivityCalorTrackerBinding

class CalorTrackerActivity : AppCompatActivity() {
    private var _binding: ActivityCalorTrackerBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCalorTrackerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val balloon = Balloon.Builder(this)
            .setWidthRatio(1.0f)
            .setHeight(BalloonSizeSpec.WRAP)
            .setText("Edit your profile here!")
            .setTextColorResource(R.color.white)
            .setTextSize(15f)
            .setArrowPositionRules(ArrowPositionRules.ALIGN_ANCHOR)
            .setArrowSize(10)
            .setArrowPosition(0.5f)
            .setPadding(12)
            .setCornerRadius(8f)
            .setBackgroundColorResource(R.color.text)
            .setBalloonAnimation(BalloonAnimation.ELASTIC)
            .build()
        binding.tvProtein.setOnClickListener {
            binding.tvProtein.showAlignTop(balloon)
        }
    }
}