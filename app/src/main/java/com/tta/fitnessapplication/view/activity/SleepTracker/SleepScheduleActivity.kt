package com.tta.fitnessapplication.view.activity.SleepTracker

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tta.fitnessapplication.databinding.ActivitySleepScheduleBinding
import com.tta.fitnessapplication.view.activity.WebViewActivity

class SleepScheduleActivity : AppCompatActivity() {
    private var _binding: ActivitySleepScheduleBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySleepScheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        addEvent()
    }

    private fun addEvent() {
        binding.view13.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
            finish()
        }
        binding.view22.setOnClickListener {
            var intent = Intent(this, WebViewActivity::class.java)
            intent.putExtra("url", "")
            startActivity(intent)
        }
    }
}