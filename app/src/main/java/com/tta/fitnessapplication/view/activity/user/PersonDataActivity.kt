package com.tta.fitnessapplication.view.activity.user

import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.tta.fitnessapplication.R
import com.tta.fitnessapplication.data.model.User
import com.tta.fitnessapplication.data.utils.Constant
import com.tta.fitnessapplication.data.utils.hideKeyboard
import com.tta.fitnessapplication.databinding.ActivityPersonDataBinding
import com.tta.fitnessapplication.view.activity.tracker.watertracker.watercaculate.Gender
import com.tta.fitnessapplication.view.base.BaseFragment
import java.util.Calendar

class PersonDataActivity : BaseFragment<ActivityPersonDataBinding>() {
    private var gender: Gender = Gender.MALE
    override fun getDataBinding(): ActivityPersonDataBinding {
        return ActivityPersonDataBinding.inflate(layoutInflater)
    }

    override fun addObservers() {
        super.addObservers()
        val emailUser = loginPreferences.getString(Constant.EMAIL_USER, "").toString()
        mainViewModel.getUserData(emailUser)
        mainViewModel.dataExercise.observe(viewLifecycleOwner) {
            if (it.isSuccessful) {
                val dataProfile = mainViewModel.dataExercise.value?.body()?.data
                setData(user = dataProfile!![0])
            } else {
                Log.e("tta", it.errorBody().toString())
            }
        }
        mainViewModel.error.observe(viewLifecycleOwner) {
            Log.e("dddddd", it.toString())
        }
    }

    private fun setData(user: User) {
        binding.edtEmail.setText(user.email)
        binding.edtUsernameLastname.setText(user.lastname)
        binding.edtUsernameFirstname.setText(user.firstname)
        binding.edtYourHeight.setText(user.tall)
        binding.edtYourWeight.setText(user.weight)
        binding.edtEmail.setText(user.email)
        binding.tvAge.text = user.age.toString()
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
    }

    override fun addEvent() {
        super.addEvent()
        binding.view2Update.setOnClickListener {
            binding.textView10.text = "Edit Account"
            binding.edtEmail.isEnabled = false
            binding.edtUsernameLastname.isEnabled = false
            binding.edtUsernameFirstname.isEnabled = false
            binding.edtYourHeight.isEnabled = false
            binding.edtYourWeight.isEnabled = false
            binding.edtEmail.isEnabled = false
//            val emailUser = loginPreferences.getString(Constant.EMAIL_USER, "").toString()
//            mainViewModel.updateProfile(
//                emailUser,
//                "Male",
//                "22",
//                binding.edtYourHeight.text.toString().trim(),
//                binding.edtYourWeight.text.toString().trim(),
//                binding.edtUsernameFirstname.text.toString().trim(),
//                binding.edtUsernameLastname.text.toString().trim(),
//                0
//            )
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
        }

        binding.view.setOnClickListener {
            binding.edtEmail.isEnabled = true
            binding.edtUsernameLastname.isEnabled = true
            binding.edtUsernameFirstname.isEnabled = true
            binding.edtYourHeight.isEnabled = true
            binding.edtYourWeight.isEnabled = true
            binding.edtEmail.isEnabled = false
            binding.view2Update.visibility = View.VISIBLE
            binding.textView12.visibility = View.VISIBLE
            binding.view.visibility = View.GONE
            binding.textView10.visibility = View.GONE
        }
        binding.viewChooseCalender.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker().build()
            datePicker.addOnPositiveButtonClickListener {
                val calendar = Calendar.getInstance()
                calendar.timeInMillis = it
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
                Log.e("aaaa", calendar.time.toString())
            }
        }

        binding.view13.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}