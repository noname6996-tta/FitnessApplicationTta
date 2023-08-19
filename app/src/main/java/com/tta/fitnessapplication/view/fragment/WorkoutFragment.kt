package com.tta.fitnessapplication.view.fragment

import android.content.Intent
import androidx.core.content.ContextCompat
import com.tta.fitnessapplication.R
import com.tta.fitnessapplication.databinding.FragmentDiscoverBinding
import com.tta.fitnessapplication.databinding.FragmentSettingBinding
import com.tta.fitnessapplication.view.activity.workout.FullBodyWorkout.FullBodyWorkoutActivity
import com.tta.fitnessapplication.view.base.BaseFragment
import java.util.Calendar

class WorkoutFragment : BaseFragment<FragmentDiscoverBinding>() {
    override fun getDataBinding(): FragmentDiscoverBinding {
        return FragmentDiscoverBinding.inflate(layoutInflater)
    }
    override fun initView() {
        super.initView()
        var arrayDay = ArrayList<String>()
        val calendar = Calendar.getInstance()
        // Set the calendar to the current week
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
        // Print each day number of the week
        for (i in 1..7) {
            println(calendar.get(Calendar.DAY_OF_MONTH))
            arrayDay.add(calendar.get(Calendar.DAY_OF_MONTH).toString())
            calendar.add(Calendar.DAY_OF_WEEK, 1) // Move to the next day
        }
        val dayNumber = calendar.get(Calendar.DAY_OF_MONTH).toString()
        binding.tvDay1.text = arrayDay[0]
        binding.tvDay2.text = arrayDay[1]
        binding.tvDay3.text = arrayDay[2]
        binding.tvDay4.text = arrayDay[3]
        binding.tvDay5.text = arrayDay[4]
        binding.tvDay6.text = arrayDay[5]
        binding.tvDay7.text = arrayDay[6]
        when (dayNumber) {
            arrayDay[0] -> {
                binding.tvDay1.setTextColor(ContextCompat.getColor(requireContext(), R.color.text))
            }

            arrayDay[1] -> {
                binding.tvDay2.setTextColor(ContextCompat.getColor(requireContext(), R.color.text))
            }

            arrayDay[2] -> {
                binding.tvDay3.setTextColor(ContextCompat.getColor(requireContext(), R.color.text))
            }

            arrayDay[3] -> {
                binding.tvDay4.setTextColor(ContextCompat.getColor(requireContext(), R.color.text))
            }

            arrayDay[4] -> {
                binding.tvDay5.setTextColor(ContextCompat.getColor(requireContext(), R.color.text))
            }

            arrayDay[5] -> {
                binding.tvDay6.setTextColor(ContextCompat.getColor(requireContext(), R.color.text))
            }

            arrayDay[6] -> {
                binding.tvDay7.setTextColor(ContextCompat.getColor(requireContext(), R.color.text))
            }
        }
    }

    override fun addEvent() {
        binding.view12.setOnClickListener {
            startActivity(Intent(activity, FullBodyWorkoutActivity::class.java))
            requireActivity().finish()
        }
    }
}