package com.tta.fitnessapplication.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tta.fitnessapplication.R
import com.tta.fitnessapplication.databinding.ActivityFullBodyWorkoutBinding

class FullBodyWorkoutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFullBodyWorkoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFullBodyWorkoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}