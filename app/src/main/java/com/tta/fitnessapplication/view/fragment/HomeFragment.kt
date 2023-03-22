package com.tta.fitnessapplication.view.fragment

import com.tta.fitnessapplication.databinding.FragmentHomeBinding
import com.tta.fitnessapplication.view.base.BaseFragment

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override fun getDataBinding(): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(layoutInflater)
    }
}