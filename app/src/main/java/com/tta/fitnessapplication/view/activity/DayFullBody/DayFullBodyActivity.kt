package com.tta.fitnessapplication.view.activity.DayFullBody

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tta.fitnessapplication.databinding.ActivityDayFullBodyBinding

class DayFullBodyActivity : AppCompatActivity() {
    private lateinit var binding  : ActivityDayFullBodyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDayFullBodyBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}