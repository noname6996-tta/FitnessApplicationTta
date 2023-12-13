package com.tta.fitnessapplication.view.activity.user

import android.app.DatePickerDialog
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.tta.fitnessapplication.R
import com.tta.fitnessapplication.data.model.User
import com.tta.fitnessapplication.data.utils.Constant
import com.tta.fitnessapplication.data.utils.hideKeyboard
import com.tta.fitnessapplication.databinding.ActivityPersonDataBinding
import com.tta.fitnessapplication.view.activity.chooseprogess.ChooseProgessActivity
import com.tta.fitnessapplication.view.activity.tracker.watertracker.watercaculate.Gender
import com.tta.fitnessapplication.view.base.BaseFragment
import java.util.Calendar

class PersonDataActivity : BaseFragment<ActivityPersonDataBinding>() {
    private var gender: Gender = Gender.MALE
    private var progess = 0
    private var emailUser = ""
    private var age = 0
    override fun getDataBinding(): ActivityPersonDataBinding {
        return ActivityPersonDataBinding.inflate(layoutInflater)
    }

    override fun addObservers() {
        super.addObservers()
        val emailUser = loginPreferences.getString(Constant.EMAIL_USER, "").toString()
        showLoading()
        mainViewModel.getUserData(emailUser)
        mainViewModel.dataExercise.observe(viewLifecycleOwner) {
            if (it.isSuccessful) {
                hideLoading()
                val dataProfile = mainViewModel.dataExercise.value?.body()?.data
                setData(user = dataProfile!![0])
            } else {
                hideLoading()
                Log.e("tta", it.errorBody().toString())
            }
        }
        mainViewModel.error.observe(viewLifecycleOwner) {
            Log.e("dddddd", it.toString())
        }
        mainViewModel.updateUser.observe(this) {
            if (it.isSuccessful) {
                if (it.body()?.response == 1) {
                    hideLoading()
                    Toast.makeText(requireContext(),"Update success",Toast.LENGTH_SHORT).show()
                } else {
                    hideLoading()
                    Log.e("tta", it.body()?.data.toString())
                    Toast.makeText(requireContext(),"Update fail please try again later",Toast.LENGTH_SHORT).show()
                }
            } else {
                hideLoading()
                Log.e("tta", it.errorBody().toString())
            }
        }
    }

    private fun setData(user: User) {
        binding.edtEmail.setText(user.email)
        binding.edtUsernameLastname.setText(user.lastname)
        binding.edtUsernameFirstname.setText(user.firstname)
        binding.edtYourHeight.setText(user.tall)
        binding.tvGender.setText(user.gender)
        binding.edtYourWeight.setText(user.weight)
        binding.edtEmail.setText(user.email)
        binding.tvAge.text = user.age.toString()
        age = user.age

        val gender = resources.getStringArray(R.array.Gender)
        val adapterGender =
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, gender)
        binding.tvGender.setOnClickListener {
            this.hideKeyboard()
        }
        binding.tvGender.setAdapter(adapterGender)
    }

    override fun initView() {
        super.initView()
        val gender = resources.getStringArray(R.array.Gender)
        val adapterGender =
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, gender)
        binding.tvGender.setOnClickListener {
            this.hideKeyboard()
        }
        binding.tvGender.setAdapter(adapterGender)
        emailUser = loginPreferences.getString(Constant.EMAIL_USER, "").toString()
    }

    override fun addEvent() {
        super.addEvent()
        binding.viewChooseCalender.setOnClickListener {
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
                    binding.tvAge.text = age.toString()
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
        binding.view2Update.setOnClickListener {
            binding.textView10.text = "Edit Account"
            binding.edtEmail.isEnabled = false
            binding.edtUsernameLastname.isEnabled = false
            binding.edtUsernameFirstname.isEnabled = false
            binding.edtYourHeight.isEnabled = false
            binding.edtYourWeight.isEnabled = false
            binding.spinnerGender.isEnabled = false
            binding.edtEmail.isEnabled = false
            binding.viewChooseCalender.isEnabled = false
            binding.view2Update.visibility = View.GONE
            binding.textView12.visibility = View.GONE
            binding.view.visibility = View.VISIBLE
            binding.textView10.visibility = View.VISIBLE
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
            showLoading()
            mainViewModel.updateProfile(
                emailUser,
                gender.toString(),
                age.toString(),
                binding.edtYourHeight.text.toString().trim(),
                binding.edtYourWeight.text.toString().trim(),
                binding.edtUsernameFirstname.text.toString().trim(),
                binding.edtUsernameLastname.text.toString().trim(),
                progess
            )
        }

        binding.view.setOnClickListener {
            binding.edtEmail.isEnabled = true
            binding.edtUsernameLastname.isEnabled = true
            binding.edtUsernameFirstname.isEnabled = true
            binding.edtYourHeight.isEnabled = true
            binding.edtYourWeight.isEnabled = true
            binding.edtEmail.isEnabled = false
            binding.spinnerGender.isEnabled = true
            binding.viewChooseCalender.isEnabled = true
            binding.view2Update.visibility = View.VISIBLE
            binding.textView12.visibility = View.VISIBLE
            binding.view.visibility = View.GONE
            binding.textView10.visibility = View.GONE
        }

        binding.view13.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}