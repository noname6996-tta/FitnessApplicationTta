package com.tta.fitnessapplication.view.activity.tracker.calortracker.FindMeal

import com.tta.fitnessapplication.databinding.FragmentMealBycategoryBinding
import com.tta.fitnessapplication.view.base.BaseFragment

class FindMealByCategory : BaseFragment<FragmentMealBycategoryBinding>(){
    override fun getDataBinding(): FragmentMealBycategoryBinding {
        return FragmentMealBycategoryBinding.inflate(layoutInflater)
    }
}