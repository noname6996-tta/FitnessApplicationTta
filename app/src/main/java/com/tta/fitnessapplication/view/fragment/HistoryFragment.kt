package com.tta.fitnessapplication.view.fragment

import com.tta.fitnessapplication.databinding.FragmentHistoryBinding
import com.tta.fitnessapplication.view.base.BaseFragment

class HistoryFragment : BaseFragment<FragmentHistoryBinding>() {
    private val viewModel = HistoryViewModel()
    override fun getDataBinding(): FragmentHistoryBinding {
        return FragmentHistoryBinding.inflate(layoutInflater)
    }
}