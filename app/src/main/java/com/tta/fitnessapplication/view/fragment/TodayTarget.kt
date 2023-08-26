package com.tta.fitnessapplication.view.fragment

import com.tta.fitnessapplication.databinding.FragmentTodayTargetBinding
import com.tta.fitnessapplication.view.base.BaseFragment

class TodayTarget : BaseFragment<FragmentTodayTargetBinding>() {
    override fun getDataBinding(): FragmentTodayTargetBinding {
        return FragmentTodayTargetBinding.inflate(layoutInflater)
    }
}