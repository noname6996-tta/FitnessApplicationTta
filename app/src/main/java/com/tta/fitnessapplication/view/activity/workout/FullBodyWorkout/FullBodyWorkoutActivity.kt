package com.tta.fitnessapplication.view.activity.workout.FullBodyWorkout

import android.content.Intent
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.skydoves.balloon.ArrowPositionRules
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.BalloonSizeSpec
import com.skydoves.balloon.showAsDropDown
import com.tta.fitnessapplication.R
import com.tta.fitnessapplication.databinding.ActivityFullBodyWorkoutBinding
import com.tta.fitnessapplication.view.activity.workout.DayFullBody.DayFullBodyActivity
import com.tta.fitnessapplication.view.base.BaseFragment

class FullBodyWorkoutActivity : BaseFragment<ActivityFullBodyWorkoutBinding>() {
    private val adapter = ToolAdapter()
    private val viewModel = FullBodyWorkoutViewModel()


    override fun getDataBinding(): ActivityFullBodyWorkoutBinding {
        return ActivityFullBodyWorkoutBinding.inflate(layoutInflater)
    }

    override fun initView() {
        super.initView()
        binding.recItem.adapter = adapter
        val linearLayoutManager = LinearLayoutManager(requireContext())
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.recItem.layoutManager = linearLayoutManager
    }

    override fun addObservers() {
        super.addObservers()
        viewModel.getToolList()
        viewModel.message.observe(this) {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
        }
        viewModel.toolList.observe(this) {
            adapter.setListHistory(it, context = requireContext())
        }
    }

    override fun addEvent() {
        binding.tvDay1.setOnClickListener {
            var intent = Intent(requireContext(), DayFullBodyActivity::class.java)
            intent.putExtra("day", 1)
            startActivity(intent)
        }
        binding.view16.setOnClickListener {
            findNavController().popBackStack()
        }

        val balloonFullBody = Balloon.Builder(requireContext())
            .setWidthRatio(1.0f)
            .setHeight(BalloonSizeSpec.WRAP)
            .setText("!")
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
            binding.viewInfo.showAsDropDown(balloonFullBody)
        }
    }
}