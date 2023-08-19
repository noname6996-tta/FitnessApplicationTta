package com.tta.fitnessapplication.view.activity.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tta.fitnessapplication.databinding.ActivityAfterSigUpBinding
import com.tta.fitnessapplication.view.activity.chooseprogess.ChooseProgessActivity

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