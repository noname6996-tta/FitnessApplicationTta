package com.tta.fitnessapplication.view.activity.tracker.calortracker

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.example.awesomedialog.AwesomeDialog
import com.example.awesomedialog.body
import com.example.awesomedialog.onPositive
import com.example.awesomedialog.position
import com.example.awesomedialog.title
import com.tta.fitnessapplication.R
import com.tta.fitnessapplication.data.utils.Constant
import com.tta.fitnessapplication.data.utils.hideKeyboard
import com.tta.fitnessapplication.databinding.CaloCalculateFragmentBinding
import com.tta.fitnessapplication.view.activity.tracker.watertracker.watercaculate.ActivityLevel
import com.tta.fitnessapplication.view.activity.tracker.watertracker.watercaculate.Climate
import com.tta.fitnessapplication.view.activity.tracker.watertracker.watercaculate.Gender
import com.tta.fitnessapplication.view.activity.tracker.watertracker.watercaculate.HealthInformation
import com.tta.fitnessapplication.view.activity.tracker.watertracker.watercaculate.calculateWaterIntake
import com.tta.fitnessapplication.view.base.BaseFragment

class CaloCalculateFragment : BaseFragment<CaloCalculateFragmentBinding>() {
    private var gender: Gender = Gender.MALE
    private var isGender = true
    private var activityLevel: ActivityLevel = ActivityLevel.SEDENTARY
    private var climate: Climate = Climate.COLD
    var value = ""

    override fun getDataBinding(): CaloCalculateFragmentBinding {
        return CaloCalculateFragmentBinding.inflate(layoutInflater)
    }

    override fun addEvent() {
        with(binding) {
            imgBack.setOnClickListener { findNavController().popBackStack() }
            btnCalculate.setOnClickListener {
                var weight = edtWeight.text.toString().trim()
                if (weight.isNotEmpty()) {
                    var healthInformation: HealthInformation = HealthInformation(
                        age = 22,
                        gender = gender,
                        weight = edtWeight.text.toString().trim().toDouble(),
                        climate = climate,
                        activityLevel = activityLevel
                    )
                    value = (calculateWaterIntake(healthInformation) * 10).toInt().toString()
                    if (!value.isNullOrEmpty()) {
                        btnSaveDailyWater.visibility = View.VISIBLE
                        tvTitleShouldDrink.visibility = View.VISIBLE
                        tvTitleShouldDrink.text = "You should dink: $value ml"
                        btnCalculate.visibility = View.GONE
                    }
                } else {
                    AwesomeDialog.build(requireActivity())
                        .title("Alert")
                        .body("You have to fill all blanks")
                        .onPositive("cancel") {

                        }
                        .position(AwesomeDialog.POSITIONS.CENTER)
                }
            }

            btnSaveDailyWater.setOnClickListener {
                if (!value.isNullOrEmpty()) {
                    loginPrefsEditor.putString(Constant.PREF.CALO_INNEED, value)
                    loginPrefsEditor.commit()
                }
                AwesomeDialog.build(requireActivity())
                    .title("Notification")
                    .body("Done")
                    .onPositive("ok") {

                    }
                    .position(AwesomeDialog.POSITIONS.CENTER)
            }

            tvGender.onItemSelectedListener = object :
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
            tvFitness.onItemSelectedListener = object :
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
        binding.tvGender.setOnClickListener {
            this.hideKeyboard()
        }
        binding.tvFitness.setOnClickListener {
            this.hideKeyboard()
        }
        binding.tvGender.setAdapter(adapterGender)
        binding.tvFitness.setAdapter(adapterActivityLevel)
    }
}