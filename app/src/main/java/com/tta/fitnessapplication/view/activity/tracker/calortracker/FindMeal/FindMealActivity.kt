package com.tta.fitnessapplication.view.activity.tracker.calortracker.FindMeal

import android.os.Bundle
import com.tta.fitnessapplication.R
import com.tta.fitnessapplication.databinding.ActivityMealTrackerBinding
import com.tta.fitnessapplication.view.base.BaseActivity

class FindMealActivity : BaseActivity<ActivityMealTrackerBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meal_tracker)
    }

    override fun getDataBinding(): ActivityMealTrackerBinding {
        return ActivityMealTrackerBinding.inflate(layoutInflater)
    }

    override fun addEvent() {
        super.addEvent()
        binding.viewback.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
            this.finish()
        }
    }
}