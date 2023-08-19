package com.tta.fitnessapplication.view.activity.workout.DayFullBody

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.tta.fitnessapplication.data.model.Exercise
import com.tta.fitnessapplication.databinding.ActivityDayFullBodyBinding
import com.tta.fitnessapplication.view.activity.workout.doexercise.DoExerciseActivity

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
        adapter.setClcickSendData {
            var bottomSheetFragment = ExerciseBottomSheetFragment(listExercise[it])
            bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
        }
        binding.view24.setOnClickListener {
            var intent = Intent(this, DoExerciseActivity::class.java)
            intent.putExtra("listExercise", listExercise)
            startActivity(intent)
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
        }
        viewModel.dataDayFullBody.observe(this) {
            binding.tvDay.text = it.name
            binding.tvDayFullBodyStatus.text = "Time : ${it.time}, workout :${it.workout}"
        }
        viewModel.dataExercise.observe(this) {
            listExercise.addAll(it)
            adapter.setListExercise(listExercise, this)
        }

        viewModel.message.observe(this) {

        }
    }
}