package com.tta.fitnessapplication.view.activity.watertracker.watercaculate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.tta.fitnessapplication.R
import com.tta.fitnessapplication.databinding.ActivityWaterCaculateBinding
import com.tta.fitnessapplication.databinding.ActivityWaterTrackerBinding

class WaterCaculateActivity : AppCompatActivity() {
    private var _binding: ActivityWaterCaculateBinding? = null
    private val binding get() = _binding!!
    private var gender: Gender = Gender.MALE
    private var activityLevel: ActivityLevel = ActivityLevel.SEDENTARY
    private var climate: Climate = Climate.COLD
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityWaterCaculateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        addobsever()
        initUi()
        addEvent()
    }

    private fun addEvent() {
        binding.imageView38.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
        binding.btnCaculate.setOnClickListener {
            var healthInformation: HealthInformation = HealthInformation(
                age = 22,
                gender = gender,
                weight = binding.edtWeight.text.toString().trim().toDouble(),
                climate = climate,
                activityLevel = activityLevel
            )
           binding.tvYourDailyGoal.text = calculateWaterIntake(healthInformation).toString()
        }
        binding.btnSetTarget.setOnClickListener {

        }
        binding.spinnerGender.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                gender = if (position == 0) {
                    Gender.MALE
                } else {
                    Gender.FEMALE
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                gender = Gender.MALE
            }
        }
        binding.spinnerFitness.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                when (position) {
                    0 -> activityLevel = ActivityLevel.SEDENTARY
                    1 -> activityLevel = ActivityLevel.MODERATE
                    2 -> activityLevel = ActivityLevel.ACTIVE
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                activityLevel = ActivityLevel.SEDENTARY
            }
        }
        binding.spinnerWeather.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                when (position) {
                    0 -> climate = Climate.COLD
                    1 -> climate = Climate.MILD
                    2 -> climate = Climate.HOT
                    3 -> climate = Climate.VERY_HOT
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                climate = Climate.COLD
            }
        }
    }

    private fun initUi() {
        val gender = resources.getStringArray(R.array.Gender)
        val activityLevel = resources.getStringArray(R.array.ActivityLevel)
        val climate = resources.getStringArray(R.array.Climate)

        val adapterGender = ArrayAdapter(this, android.R.layout.simple_spinner_item, gender)
        val adapterActivityLevel =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, activityLevel)
        val adapterClimate = ArrayAdapter(this, android.R.layout.simple_spinner_item, climate)

        binding.spinnerGender.adapter = adapterGender
        binding.spinnerFitness.adapter = adapterActivityLevel
        binding.spinnerWeather.adapter = adapterClimate

    }

    private fun addobsever() {

    }


}