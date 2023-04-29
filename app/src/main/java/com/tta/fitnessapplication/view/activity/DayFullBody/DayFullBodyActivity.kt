package com.tta.fitnessapplication.view.activity.DayFullBody

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tta.fitnessapplication.data.utils.Logger
import com.tta.fitnessapplication.databinding.ActivityDayFullBodyBinding

class DayFullBodyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDayFullBodyBinding
    private val viewModel = DayFullBodyViewModel()
    private var day = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDayFullBodyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        day = intent.getIntExtra("day", 0)
        addObsever()
        initUi()

    }

    private fun initUi() {

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
                for (i in 1..it.workout.toInt()){
                    viewModel.getExercise(separated[i])
                }
            }

            viewModel.message.observe(this){
                Logger.logError(it)
            }
        }
    }
}