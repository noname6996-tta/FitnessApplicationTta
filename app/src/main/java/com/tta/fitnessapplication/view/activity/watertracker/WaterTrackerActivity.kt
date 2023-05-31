package com.tta.fitnessapplication.view.activity.watertracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tta.fitnessapplication.R
import com.tta.fitnessapplication.databinding.ActivityWaterTrackerBinding
import com.tta.fitnessapplication.databinding.FragmentHistoryBinding

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
            this.finish()
            onBackPressedDispatcher.onBackPressed()
        }
    }
}