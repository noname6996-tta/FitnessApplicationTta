package com.tta.fitnessapplication.view.fragment

import com.tta.fitnessapplication.databinding.FragmentSettingBinding
import com.tta.fitnessapplication.view.base.BaseFragment

class SettingFragment : BaseFragment<FragmentSettingBinding>() {
    override fun getDataBinding(): FragmentSettingBinding {
        return FragmentSettingBinding.inflate(layoutInflater)
    }
}