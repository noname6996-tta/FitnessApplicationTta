package com.tta.fitnessapplication.view.activity.tracker.calortracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tta.fitnessapplication.R
import com.tta.fitnessapplication.databinding.ActivityMealInfoBinding
import com.tta.fitnessapplication.view.base.BaseFragment

class MealInfoActivity : BaseFragment<ActivityMealInfoBinding>() {
    override fun getDataBinding(): ActivityMealInfoBinding {
        return ActivityMealInfoBinding.inflate(layoutInflater)
    }
}