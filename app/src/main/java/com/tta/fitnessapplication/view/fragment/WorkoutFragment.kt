package com.tta.fitnessapplication.view.fragment

import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.tta.fitnessapplication.data.utils.getWeekDates
import com.tta.fitnessapplication.databinding.FragmentDiscoverBinding
import com.tta.fitnessapplication.view.activity.history.HistoryViewModel
import com.tta.fitnessapplication.view.base.BaseFragment
import java.time.LocalDate

class WorkoutFragment : BaseFragment<FragmentDiscoverBinding>() {
    private lateinit var historyViewModel: HistoryViewModel
    override fun getDataBinding(): FragmentDiscoverBinding {
        return FragmentDiscoverBinding.inflate(layoutInflater)
    }

    override fun initViewModel() {
        super.initViewModel()
        historyViewModel = ViewModelProvider(this)[HistoryViewModel::class.java]
    }

    override fun addObservers() {
        super.addObservers()
        historyViewModel.readAllData.observe(viewLifecycleOwner) {
            val daysDoExercise = ArrayList<String>()
            if (it.isNotEmpty()) {
                for (item in it) {
                    // exercise
                    if (item.type == 0) {
                        daysDoExercise.add(item.date.toString())
                        Log.e("daysDoExercise", daysDoExercise.toString())
                    }
                }
                val days = ArrayList<String>()
                for (i in 0 until getWeekDates().size) {
                    val date = LocalDate.parse(getWeekDates()[i])
                    val dayOfMonth = date.dayOfMonth
                    days.add(dayOfMonth.toString())
                    for (item in daysDoExercise) {
                        if (getWeekDates()[i] == item) {
                            days[i] = "✔"
                        }
                    }
                }
                Log.e("daysDoExercise", days.toString())
                binding.tvDay1.text = days[0]
                binding.tvDay2.text = days[1]
                binding.tvDay3.text = days[2]
                binding.tvDay4.text = days[3]
                binding.tvDay5.text = days[4]
                binding.tvDay6.text = days[5]
                binding.tvDay7.text = days[6]
            }
        }
    }

    override fun initView() {
        super.initView()
        val days = ArrayList<String>()
        for (i in 0 until getWeekDates().size) {
            val date = LocalDate.parse(getWeekDates()[i])
            val dayOfMonth = date.dayOfMonth
            days.add(dayOfMonth.toString())
        }
        binding.tvDay1.text = days[0]
        binding.tvDay2.text = days[1]
        binding.tvDay3.text = days[2]
        binding.tvDay4.text = days[3]
        binding.tvDay5.text = days[4]
        binding.tvDay6.text = days[5]
        binding.tvDay7.text = days[6]
    }

    override fun addEvent() {
        binding.view12.setOnClickListener {
            findNavController().navigate(
                WorkoutFragmentDirections.actionDiscoverFragmentToFullBodyWorkoutActivity(
                    0
                )
            )
        }
        binding.view13.setOnClickListener {
            findNavController().navigate(
                WorkoutFragmentDirections.actionDiscoverFragmentToFullBodyWorkoutActivity(
                    1
                )
            )
        }
        binding.view14.setOnClickListener {
            findNavController().navigate(
                WorkoutFragmentDirections.actionDiscoverFragmentToFullBodyWorkoutActivity(
                    2
                )
            )
        }
    }
}