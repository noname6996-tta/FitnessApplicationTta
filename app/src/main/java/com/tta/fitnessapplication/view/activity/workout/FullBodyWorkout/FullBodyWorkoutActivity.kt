package com.tta.fitnessapplication.view.activity.workout.FullBodyWorkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.tta.fitnessapplication.databinding.ActivityFullBodyWorkoutBinding
import com.tta.fitnessapplication.databinding.ActivityWaterTrackerBinding
import com.tta.fitnessapplication.view.MainActivity
import com.tta.fitnessapplication.view.activity.workout.DayFullBody.DayFullBodyActivity

class FullBodyWorkoutActivity : AppCompatActivity() {
    private var _binding: ActivityFullBodyWorkoutBinding? = null
    private val binding get() = _binding!!
    private val adapter = ToolAdapter()
    private val viewModel = FullBodyWorkoutViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFullBodyWorkoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        addObsever()
        initUi()
        addEvent()
    }

    private fun initUi() {
        binding.recItem.adapter = adapter
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.recItem.layoutManager = linearLayoutManager
    }

    private fun addObsever() {
        viewModel.getToolList()
        viewModel.message.observe(this){
            Snackbar.make(binding.root,it,Snackbar.LENGTH_SHORT).show()
        }
        viewModel.toolList.observe(this){
            adapter.setListHistory(it, context = this)
        }
    }

    private fun addEvent() {
        binding.tvDay1.setOnClickListener {
            var intent = Intent(this, DayFullBodyActivity::class.java)
            intent.putExtra("day",1)
            startActivity(intent)
        }
        binding.view16.setOnClickListener {
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            this.finish()
        }
    }
}