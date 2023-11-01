package com.tta.fitnessapplication.view.activity.register

import android.R
import android.app.DatePickerDialog
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import com.github.mikephil.charting.data.PieEntry
import com.tta.fitnessapplication.data.utils.Constant
import com.tta.fitnessapplication.databinding.ActivityAfterSigUpBinding
import com.tta.fitnessapplication.view.activity.chooseprogess.ChooseProgessActivity
import com.tta.fitnessapplication.view.activity.tracker.calortracker.calculateBMI
import com.tta.fitnessapplication.view.activity.tracker.watertracker.watercaculate.Gender
import com.tta.fitnessapplication.view.base.BaseActivity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AfterSigUpActivity : BaseActivity<ActivityAfterSigUpBinding>() {
    private var gender: Gender = Gender.MALE
    private var age = 0
    private var firstName = ""
    private var lastName = ""
    override fun getDataBinding(): ActivityAfterSigUpBinding {
        return ActivityAfterSigUpBinding.inflate(layoutInflater)
    }

    override fun addObservers() {
        super.addObservers()
        val emailUser = loginPreferences.getString(Constant.EMAIL_USER, "").toString()
        mainViewModel.getUserData(emailUser)
        mainViewModel.dataExercise.observe(this) {
            if (it.isSuccessful) {
                val dataProfile = mainViewModel.dataExercise.value?.body()?.data
                firstName = dataProfile!![0].firstname
                lastName = dataProfile!![0].lastname
            } else {
                Log.e("tta", it.errorBody().toString())
            }
        }
    }

    override fun addEvent() {
        binding.view.setOnClickListener {
            // update sau
            startActivity(Intent(this, DoneSingUpActivity::class.java))
            finish()
        }
    }

    override fun initView() {
        super.initView()
        val genderList = resources.getStringArray(com.tta.fitnessapplication.R.array.Gender)
        val adapterGender =
            ArrayAdapter(this, R.layout.simple_list_item_1, genderList)
        binding.tvGender.setAdapter(adapterGender)
        binding.apply {
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

            viewChooseCalender.setOnClickListener {
                // Get the current date
                val currentDate = Calendar.getInstance()

                // Create a DatePickerDialog
                val datePickerDialog = DatePickerDialog(this@AfterSigUpActivity, DatePickerDialog.OnDateSetListener { _, selectedYear, selectedMonth, selectedDay ->
                    // Create a calendar instance for the selected date
                    val selectedDate = Calendar.getInstance().apply {
                        set(selectedYear, selectedMonth, selectedDay)
                    }

                    // Calculate the age
                    age = currentDate.get(Calendar.YEAR) - selectedDate.get(Calendar.YEAR)
                    binding.tvAge.text = age.toString()
                    // Adjust the age if the current date is before the selected date
                    if (currentDate.get(Calendar.DAY_OF_YEAR) < selectedDate.get(Calendar.DAY_OF_YEAR)) {
                        age--
                    }
                }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DAY_OF_MONTH))

                // Show the DatePickerDialog
                datePickerDialog.show()
            }

            view.setOnClickListener {
                if (check()){
                    val emailUser = loginPreferences.getString(Constant.EMAIL_USER, "").toString()
                    mainViewModel.updateProfile(
                        emailUser,
                        gender.toString(),
                        age.toString(),
                        binding.edtYourHeight.text.toString().trim(),
                        binding.edtYourWeight.text.toString().trim(),
                        firstName,
                        lastName,
                        0
                    )
                }
            }
        }
    }

    private fun check() : Boolean{
        var canNext = true
        if (age ==0){
            Toast.makeText(this@AfterSigUpActivity,"You have to choose your age",Toast.LENGTH_SHORT).show()
            canNext = false
        }
        var weight = binding.edtYourWeight.text?.trim().toString()
        var height = binding.edtYourHeight.text?.trim().toString()
        if (weight.isEmpty()||height.isEmpty()){
            Toast.makeText(this@AfterSigUpActivity,"You have to put your height or your weight",Toast.LENGTH_SHORT).show()
            canNext = false
        }
        return canNext
    }
}