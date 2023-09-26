package com.tta.fitnessapplication.view.activity.tracker.calortracker.FindMeal

import androidx.navigation.fragment.findNavController
import com.tta.fitnessapplication.databinding.ActivityMealTrackerBinding
import com.tta.fitnessapplication.view.activity.tracker.calortracker.adapter.CategoryMealAdapter
import com.tta.fitnessapplication.view.activity.tracker.calortracker.adapter.ItemRecommendMealAdapter
import com.tta.fitnessapplication.view.base.BaseFragment

class FindMealActivity : BaseFragment<ActivityMealTrackerBinding>() {
    val categoryAdapter = CategoryMealAdapter()
    val recommendMealAdapter = ItemRecommendMealAdapter()
    override fun getDataBinding(): ActivityMealTrackerBinding {
        return ActivityMealTrackerBinding.inflate(layoutInflater)
    }

    override fun addEvent() {
        super.addEvent()
        binding.viewback.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}