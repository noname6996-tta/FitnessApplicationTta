package com.tta.fitnessapplication.view.activity.tracker.calortracker

import com.tta.fitnessapplication.databinding.CaloCalculateFragmentBinding
import com.tta.fitnessapplication.view.base.BaseFragment

class CaloCalculateFragment : BaseFragment<CaloCalculateFragmentBinding>() {
    override fun getDataBinding(): CaloCalculateFragmentBinding {
        return CaloCalculateFragmentBinding.inflate(layoutInflater)
    }
}