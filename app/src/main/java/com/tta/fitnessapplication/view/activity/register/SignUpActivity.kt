package com.tta.fitnessapplication.view.activity.register

import android.content.Intent
import com.tta.fitnessapplication.databinding.ActivitySignUpBinding
import com.tta.fitnessapplication.view.base.BaseActivity
import com.tta.fitnessapplication.view.login.LoginActivity

class SignUpActivity : BaseActivity<ActivitySignUpBinding>() {
    override fun getDataBinding(): ActivitySignUpBinding {
        return ActivitySignUpBinding.inflate(layoutInflater)
    }

    override fun initViewModel() {
        super.initViewModel()
//        mainViewModel.message.observe(viewLifecycleOwner) {
//            AwesomeDialog.build(requireActivity())
//                .title("Notification !")
//                .body(it)
//                .icon(R.drawable.alarm_clock)
//                .onPositive("ok") {
//                }
//        }
//        mainViewModel.success.observe(viewLifecycleOwner) {
//            if (it == 1) {
//                AwesomeDialog.build(requireActivity())
//                    .title("Notification !")
//                    .body("Register success, but i need some information for your profiles")
//                    .icon(R.drawable.alarm_clock)
//                    .onPositive("ok") {
//                    }
////                startActivity(Intent(this, AfterSigUpActivity::class.java))
////                finish()
//            }
//        }
    }


    override fun addEvent() {
        binding.btnRegester.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()
            val firstName = binding.edtUsernameFirstname.text.toString()
            val lastName = binding.edtUsernameLastname.text.toString()
//            if (email.isEmpty()) {
//                binding.tvEmail.error = "Vui lòng nhập"
//            }
//            if (password.isEmpty()) {
//                binding.tvPassword.error = "Vui lòng nhập"
//            }
//            if (firstName.isEmpty()) {
//                binding.tvUsernameFirstname.error = "Vui lòng nhập"
//            }
//            if (lastName.isEmpty()) {
//                binding.tvUsernameLastname.error = "Vui lòng nhập"
//            }
//            binding.edtEmail.doOnTextChanged { text, start, before, count ->
//                if (text!!.isEmpty()) {
//                    binding.tvEmail.error = "Vui lòng nhập"
//                } else {
//                    binding.tvEmail.error = ""
//                }
//            }
//
//            binding.edtPassword.doOnTextChanged { text, start, before, count ->
//                if (text!!.isEmpty()) {
//                    binding.tvPassword.error = "Vui lòng nhập"
//                } else {
//                    binding.tvPassword.error = ""
//                }
//            }
//            binding.edtUsernameFirstname.doOnTextChanged { text, start, before, count ->
//                if (text!!.isEmpty()) {
//                    binding.tvUsernameFirstname.error = "Vui lòng nhập"
//                } else {
//                    binding.tvUsernameFirstname.error = ""
//                }
//            }
//
//            binding.edtUsernameLastname.doOnTextChanged { text, start, before, count ->
//                if (text!!.isEmpty()) {
//                    binding.tvUsernameLastname.error = "Vui lòng nhập"
//                } else {
//                    binding.tvUsernameLastname.error = ""
//                }
//            }
//            if (email.isEmpty() || password.isEmpty()||firstName.isEmpty() || lastName.isEmpty()) {
//
//            } else {
//                mainViewModel.register(email, password,firstName,lastName)
//            }
//            Toast.makeText(this,"Đăng ký thành công vui lòng đăng nhập",Toast.LENGTH_SHORT)
//            startActivity(Intent(this, AfterSigUpActivity::class.java))
//            finish()
        }
        binding.textView3.setOnClickListener {
            this.finish()
            startActivity(Intent(this@SignUpActivity, LoginActivity::class.java))
        }
    }
}