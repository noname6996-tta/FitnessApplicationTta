package com.tta.fitnessapplication.view.activity.watertracker.watercaculate

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.tta.fitnessapplication.R
import com.tta.fitnessapplication.data.utils.hideKeyboard
import com.tta.fitnessapplication.data.utils.showAnimatedAlertDialog
import com.tta.fitnessapplication.databinding.ActivityWaterCaculateBinding
import com.tta.fitnessapplication.view.base.BaseFragment

class WaterCaculateActivity : BaseFragment<ActivityWaterCaculateBinding>() {
    private var gender: Gender = Gender.MALE
    private var activityLevel: ActivityLevel = ActivityLevel.SEDENTARY
    private var climate: Climate = Climate.COLD

    override fun getDataBinding(): ActivityWaterCaculateBinding {
        return ActivityWaterCaculateBinding.inflate(layoutInflater)
    }

    override fun addEvent() {
        binding.imageView38.setOnClickListener { findNavController().popBackStack() }
        binding.btnCaculate.setOnClickListener {
            var weight = binding.edtWeight.text.toString().trim()
            if (weight.isNotEmpty()) {
                var healthInformation: HealthInformation = HealthInformation(
                    age = 22,
                    gender = gender,
                    weight = binding.edtWeight.text.toString().trim().toDouble(),
                    climate = climate,
                    activityLevel = activityLevel
                )
                binding.tvYourDailyGoal.text =
                    (calculateWaterIntake(healthInformation) * 10).toInt().toString() + " ml"
            } else {
                showAnimatedAlertDialog(
                    requireContext(),
                    "Notification",
                    "You must fill the weight blank"
                )
            }

        }
        binding.btnSetTarget.setOnClickListener {

        }
        binding.tvGender.onItemSelectedListener = object :
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
        binding.tvFitness.onItemSelectedListener = object :
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
        binding.tvWeather.onItemSelectedListener = object :
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

    override fun initView() {
        super.initView()
        val gender = resources.getStringArray(R.array.Gender)
        val activityLevel = resources.getStringArray(R.array.ActivityLevel)
        val climate = resources.getStringArray(R.array.Climate)

        val adapterGender =
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, gender)
        val adapterActivityLevel =
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, activityLevel)
        val adapterClimate =
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, climate)
        binding.spinnerFitness.setOnClickListener {
            this.hideKeyboard()
        }
        binding.spinnerGender.setOnClickListener {
            this.hideKeyboard()
        }
        binding.spinnerWeather.setOnClickListener {
            this.hideKeyboard()
        }
        binding.tvGender.setOnClickListener {
            this.hideKeyboard()
        }
        binding.tvFitness.setOnClickListener {
            this.hideKeyboard()
        }
        binding.tvWeather.setOnClickListener {
            this.hideKeyboard()
        }
        binding.tvGender.setAdapter(adapterGender)
        binding.tvFitness.setAdapter(adapterActivityLevel)
        binding.tvWeather.setAdapter(adapterClimate)
    }
}