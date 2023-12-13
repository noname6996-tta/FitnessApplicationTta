package com.tta.fitnessapplication.view.fragment

import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tta.fitnessapplication.R
import com.tta.fitnessapplication.data.model.History
import com.tta.fitnessapplication.data.utils.Constant
import com.tta.fitnessapplication.data.utils.Constant.DATE.today
import com.tta.fitnessapplication.databinding.FragmentTodayTargetBinding
import com.tta.fitnessapplication.view.activity.history.HistoryAdapter
import com.tta.fitnessapplication.view.activity.history.HistoryViewModel
import com.tta.fitnessapplication.view.base.BaseFragment

class TodayTarget : BaseFragment<FragmentTodayTargetBinding>() {
    private lateinit var dailyWater: String
    private lateinit var dailySleep: String
    private lateinit var doExercise: String
    private lateinit var calorEat: String
    private val eventsAdapter = HistoryAdapter()
    private lateinit var historyViewModel: HistoryViewModel
    override fun getDataBinding(): FragmentTodayTargetBinding {
        return FragmentTodayTargetBinding.inflate(layoutInflater)
    }

    override fun initViewModel() {
        super.initViewModel()
        historyViewModel = ViewModelProvider(this)[HistoryViewModel::class.java]
        historyViewModel.getWaterListByDate(Constant.DATE.fullDateFormatter.format(today))
    }

    override fun addObservers() {
        super.addObservers()
        historyViewModel.historyList.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                eventsAdapter.events.clear()
                eventsAdapter.events.addAll(it.take(5))
                eventsAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun addEvent() {
        super.addEvent()
        with(binding) {
            view13.setOnClickListener {
                findNavController().popBackStack()
            }
            tvShowHistory.setOnClickListener {
                findNavController().navigate(R.id.action_todayTarget_to_historyActivity)
            }
        }
    }

    override fun initView() {
        super.initView()
        dailyWater = loginPreferences.getString(Constant.PREF.WATER_INNEED, "No data").toString()
        calorEat = loginPreferences.getString(Constant.PREF.CALO_INNEED, "No data").toString()
        dailySleep = loginPreferences.getString(Constant.PREF.SLEEP_TIME, "No data").toString()
        with(binding) {
            tvWaterValue.text = dailyWater + "ml"
            tvFoodValue.text = calorEat + " calo"
            tvExerciseValue.text = "Not done"
            tvSleepValue.text = dailySleep

            recHistory.apply {
                layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                adapter = eventsAdapter
            }
        }
    }
}