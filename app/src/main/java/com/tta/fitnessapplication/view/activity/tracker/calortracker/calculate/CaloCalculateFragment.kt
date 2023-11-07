package com.tta.fitnessapplication.view.activity.tracker.calortracker.calculate

import android.app.DatePickerDialog
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
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
import com.tta.fitnessapplication.view.activity.tracker.watertracker.watercaculate.Gender
import com.tta.fitnessapplication.view.base.BaseFragment
import java.util.Calendar

class CaloCalculateFragment : BaseFragment<CaloCalculateFragmentBinding>() {
    private var gender: Gender = Gender.MALE
    private var activityLevel: ActivityLevel = ActivityLevel.SEDENTARY
    var value = ""
    var age = 0
    var result = 0.0

    override fun getDataBinding(): CaloCalculateFragmentBinding {
        return CaloCalculateFragmentBinding.inflate(layoutInflater)
    }

    override fun addEvent() {
        with(binding) {
            imgBack.setOnClickListener { findNavController().popBackStack() }
            btnCalculate.setOnClickListener {
                var weight = edtWeight.text.toString().trim()
                if (weight.isNotEmpty()) {
                    var a = when (activityLevel) {
                        ActivityLevel.SEDENTARY -> 1.0
                        ActivityLevel.MODERATE -> 1.2
                        ActivityLevel.ACTIVE -> 1.5
                    }
                    var isMale = true
                    isMale = gender != Gender.FEMALE
                    var weight = edtWeight.text.toString().trim().toDouble()
                    var height = edtHeight.text.toString().trim().toDouble()
                    if (age != 0) {
                        result = calculateDailyCalories(weight, height, age, isMale, a)
                    } else Toast.makeText(
                        requireContext(),
                        "You have to type your age",
                        Toast.LENGTH_SHORT
                    ).show()

                    if (result != 0.0) {
                        btnSaveDailyWater.visibility = View.VISIBLE
                        tvTitleShouldDrink.visibility = View.VISIBLE
                        tvTitleShouldDrink.text = "You should eat: $result calo"
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
                        findNavController().popBackStack()
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

        val adapterGender =
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, gender)
        val adapterActivityLevel =
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, activityLevel)
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

        binding.tilinputAge.setOnClickListener {
            // Get the current date
            val currentDate = Calendar.getInstance()

            // Create a DatePickerDialog
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                DatePickerDialog.OnDateSetListener { _, selectedYear, selectedMonth, selectedDay ->
                    // Create a calendar instance for the selected date
                    val selectedDate = Calendar.getInstance().apply {
                        set(selectedYear, selectedMonth, selectedDay)
                    }

                    // Calculate the age
                    age = currentDate.get(Calendar.YEAR) - selectedDate.get(Calendar.YEAR)
                    binding.edtHeight.setText(age.toString())
                    // Adjust the age if the current date is before the selected date
                    if (currentDate.get(Calendar.DAY_OF_YEAR) < selectedDate.get(Calendar.DAY_OF_YEAR)) {
                        age--
                    }
                },
                currentDate.get(Calendar.YEAR),
                currentDate.get(Calendar.MONTH),
                currentDate.get(Calendar.DAY_OF_MONTH)
            )

            // Show the DatePickerDialog
            datePickerDialog.show()
        }
    }
}