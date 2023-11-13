package com.tta.fitnessapplication.view.activity.workout.FullBodyWorkout

import android.content.Intent
import android.widget.TextView
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
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
    private val args : FullBodyWorkoutActivityArgs by navArgs()


    override fun getDataBinding(): ActivityFullBodyWorkoutBinding {
        return ActivityFullBodyWorkoutBinding.inflate(layoutInflater)
    }

    override fun initView() {
        super.initView()
        val type  = args.type
        binding.apply {
            when(type){
                0 -> {
                    imageView9.setImageResource(R.drawable.men10)
                    textView29.text = "Fullbody workout"
                    tvTimeWorkout.text = "Train for 32 days and have 4 days off"
                    textView30.text = "68 Exercises | 10hours 32mins | 20k Calories Burn"
                }
                1 -> {
                    imageView9.setImageResource(R.drawable.vector)
                    textView29.text = "Lowebody Workout"
                    tvTimeWorkout.text = "Train for 32 days and have 4 days off"
                    textView30.text = "68 Exercises | 10hours 32mins | 20k Calories Burn"
                }
                2 ->{
                    imageView9.setImageResource(R.drawable.group_10306)
                    textView29.text = "AB Workout"
                    tvTimeWorkout.text = "Train for 32 days and have 4 days off"
                    textView30.text = "68 Exercises | 10hours 32mins | 20k Calories Burn"
                }
            }
            recItem.adapter = adapter
            val linearLayoutManager = LinearLayoutManager(requireContext())
            linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
            recItem.layoutManager = linearLayoutManager
        }
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
        val textViewArray = arrayOf(
            binding.tvDay1,
            binding.tvDay2,
            binding.tvDay3,
            binding.tvDay4,
            binding.tvDay5,
            binding.tvDay6,
            binding.tvDay7,
            binding.tvDay8,
            binding.tvDay9,
            binding.tvDay10,
            binding.tvDay11,
            binding.tvDay12,
            binding.tvDay13,
            binding.tvDay14,
            binding.tvDay15,
            binding.tvDay17,
            binding.tvDay18,
            binding.tvDay19,
            binding.tvDay20,
            binding.tvDay21,
            binding.tvDay22,
            binding.tvDay23,
            binding.tvDay24,
            binding.tvDay25,
            binding.tvDay26,
            binding.tvDay27,
            binding.tvDay28,
            binding.tvDay29
        )
        textViewArray.forEachIndexed { index, textView ->
            textView.setOnClickListener {
                val dayNumber = index + 1
                var intent = Intent(requireContext(), DayFullBodyActivity::class.java)
                intent.putExtra("day", dayNumber)
                startActivity(intent)
            }
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