package com.tta.fitnessapplication.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tta.fitnessapplication.view.viewmodel.MainViewModel
import com.tta.fitnessapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val viewModel = MainViewModel()
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.getData()
    }
}