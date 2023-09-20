package com.tta.fitnessapplication.view.fragment

import androidx.navigation.fragment.findNavController
import com.tta.fitnessapplication.R
import com.tta.fitnessapplication.databinding.FragmentTodayTargetBinding
import com.tta.fitnessapplication.view.base.BaseFragment

class TodayTarget : BaseFragment<FragmentTodayTargetBinding>() {
    override fun getDataBinding(): FragmentTodayTargetBinding {
        return FragmentTodayTargetBinding.inflate(layoutInflater)
    }

    override fun addEvent() {
        super.addEvent()
        with(binding){
            view13.setOnClickListener {
                findNavController().popBackStack()
            }
            tvShowHistory.setOnClickListener {
                findNavController().navigate(R.id.action_todayTarget_to_historyActivity)
            }
        }
    }
}