package com.tta.fitnessapplication.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tta.fitnessapplication.R
import com.tta.fitnessapplication.databinding.ActivityFullBodyWorkoutBinding
import com.tta.fitnessapplication.view.activity.DayFullBody.DayFullBodyActivity

class FullBodyWorkoutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFullBodyWorkoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFullBodyWorkoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        addEvent()
    }

    private fun addEvent() {
        binding.tvDay1.setOnClickListener {
            var intent = Intent(this, DayFullBodyActivity::class.java)
            intent.putExtra("day",1)
            startActivity(intent)
        }
    }
}