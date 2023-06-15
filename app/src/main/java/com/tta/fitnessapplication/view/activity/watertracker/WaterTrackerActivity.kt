package com.tta.fitnessapplication.view.activity.watertracker

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tta.fitnessapplication.databinding.ActivityWaterTrackerBinding
import com.tta.fitnessapplication.view.activity.watertracker.waterHistory.WaterHistoryActivity
import com.tta.fitnessapplication.view.activity.watertracker.watercaculate.WaterCaculateActivity

class WaterTrackerActivity : AppCompatActivity() {
    private var _binding : ActivityWaterTrackerBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityWaterTrackerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUi()
        addEvent()
    }

    private fun initUi() {

    }

    private fun addEvent(){
        binding.view13.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
            this.finish()
        }
        binding.cardViewWaterCaculate.setOnClickListener {
            startActivity(Intent(this,WaterCaculateActivity::class.java))
        }
        binding.cardViewHistoryWaterTracker.setOnClickListener {
            startActivity(Intent(this,WaterHistoryActivity::class.java))
        }
    }
}