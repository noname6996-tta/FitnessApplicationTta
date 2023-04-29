package com.tta.fitnessapplication.view.activity.DayFullBody

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.tta.fitnessapplication.data.model.Exercise
import com.tta.fitnessapplication.data.utils.Logger
import com.tta.fitnessapplication.databinding.ActivityDayFullBodyBinding

class DayFullBodyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDayFullBodyBinding
    private val viewModel = DayFullBodyViewModel()
    private val adapter = HomeWorkoutAdapter()
    private var day = 0
    private var listExercise = ArrayList<Exercise>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDayFullBodyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        day = intent.getIntExtra("day", 0)
        initUi()
        addObsever()
        addEvent()
    }

    private fun addEvent() {
        binding.imgBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
            this.finish()
        }
    }

    private fun initUi() {
        binding.recExercises.adapter = adapter
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.recExercises.layoutManager = linearLayoutManager
    }

    private fun addObsever() {
        if (day != 0) {
            viewModel.getDayFullBody(day.toString())
            viewModel.dataDayFullBody.observe(this) {
                binding.tvDay.text = it.name
                binding.tvDayFullBodyStatus.text = "Time : ${it.time}, workout :${it.workout}"
                var listIdExercise = it.idexercise
                val separated: List<String> = listIdExercise.split(",")
                Logger.logTest(separated.toString())
                for (i in 1..it.workout.toInt()) {
                    viewModel.getExercise(separated[i])
                }
            }
        }
        viewModel.dataExercise.observe(this) {
            listExercise.add(it)
            adapter.setListExercise(listExercise, this)
        }

        viewModel.message.observe(this) {
            Logger.logError(it)
        }
    }
}