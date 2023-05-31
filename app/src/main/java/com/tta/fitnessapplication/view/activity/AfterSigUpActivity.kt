package com.tta.fitnessapplication.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tta.fitnessapplication.R
import com.tta.fitnessapplication.databinding.ActivityAfterSigUpBinding
import com.tta.fitnessapplication.databinding.FragmentHistoryBinding
import com.tta.fitnessapplication.view.activity.ChooseProgess.ChooseProgessActivity

class AfterSigUpActivity : AppCompatActivity() {
    private var _binding : ActivityAfterSigUpBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAfterSigUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        addEvent()
    }

    private fun addEvent() {
        binding.view.setOnClickListener {
            startActivity(Intent(this, ChooseProgessActivity::class.java))
            finish()
        }
    }
}