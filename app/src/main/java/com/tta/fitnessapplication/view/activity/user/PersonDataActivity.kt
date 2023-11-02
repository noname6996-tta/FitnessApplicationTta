package com.tta.fitnessapplication.view.activity.user

import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.tta.fitnessapplication.data.model.User
import com.tta.fitnessapplication.data.utils.Constant
import com.tta.fitnessapplication.databinding.ActivityPersonDataBinding
import com.tta.fitnessapplication.view.base.BaseFragment
import java.util.Calendar

class PersonDataActivity : BaseFragment<ActivityPersonDataBinding>(),
    AdapterView.OnItemSelectedListener {
    private lateinit var adapter: ArrayAdapter<String>
    val spinnerData = arrayOf("Male", "Female")
    var gender = ""
    var updateAccount = false
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
        val position = adapter.getPosition(user.gender)
//        binding.spinner.setSelection(position)
    }

    override fun initView() {
        super.initView()
//        adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, spinnerData)
//        binding.spinner.onItemSelectedListener = this
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        binding.spinner.adapter = adapter
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
//            binding.spinner.isEnabled = false
//            binding.spinner.visibility = View.GONE
            val emailUser = loginPreferences.getString(Constant.EMAIL_USER, "").toString()
            mainViewModel.updateProfile(
                emailUser,
                "Male",
                "22",
                binding.edtYourHeight.text.toString().trim(),
                binding.edtYourWeight.text.toString().trim(),
                binding.edtUsernameFirstname.text.toString().trim(),
                binding.edtUsernameLastname.text.toString().trim(),
                0
            )
            binding.view2Update.visibility = View.GONE
            binding.textView12.visibility = View.GONE
            binding.view.visibility = View.VISIBLE
            binding.textView10.visibility = View.VISIBLE
        }

        binding.view.setOnClickListener {
            binding.edtEmail.isEnabled = true
            binding.edtUsernameLastname.isEnabled = true
            binding.edtUsernameFirstname.isEnabled = true
            binding.edtYourHeight.isEnabled = true
            binding.edtYourWeight.isEnabled = true
            binding.edtEmail.isEnabled = false
//            binding.spinner.isEnabled = true
//            binding.gender.visibility = View.GONE
//            binding.imageView3.visibility = View.GONE
//            binding.spinner.visibility = View.VISIBLE
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

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        gender = p0?.getItemAtPosition(p2).toString()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        gender = "Male"
    }

}