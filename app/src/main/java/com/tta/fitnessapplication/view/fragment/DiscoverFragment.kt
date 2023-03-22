package com.tta.fitnessapplication.view.fragment

import com.tta.fitnessapplication.databinding.FragmentDiscoverBinding
import com.tta.fitnessapplication.databinding.FragmentHomeBinding
import com.tta.fitnessapplication.view.base.BaseFragment

class DiscoverFragment : BaseFragment<FragmentDiscoverBinding>() {
    override fun getDataBinding(): FragmentDiscoverBinding {
        return FragmentDiscoverBinding.inflate(layoutInflater)
    }
}