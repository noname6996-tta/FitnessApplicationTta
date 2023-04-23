package com.tta.fitnessapplication.view.activity.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.tta.fitnessapplication.view.activity.login.LoginActivity
import com.tta.fitnessapplication.databinding.ActivitySignUpBinding
import com.tta.fitnessapplication.view.activity.MainActivity.MainActivity

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private val viewModel = RegisterViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        addObsever()
        addEvent()
    }

    private fun addObsever() {
        viewModel.message.observe(this) {
            Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT)
        }
        viewModel.success.observe(this) {
            if (it == 1) {
                Toast.makeText(this,"Đăng ký thành công vui lòng đăng nhập",Toast.LENGTH_SHORT)
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
    }

    private fun addEvent() {
        binding.btnRegester.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()
            val firstName = binding.edtUsernameFirstname.text.toString()
            val lastName = binding.edtUsernameLastname.text.toString()
            if (email.isEmpty()) {
                binding.tvEmail.error = "Vui lòng nhập"
            }
            if (password.isEmpty()) {
                binding.tvPassword.error = "Vui lòng nhập"
            }
            if (firstName.isEmpty()) {
                binding.tvUsernameFirstname.error = "Vui lòng nhập"
            }
            if (lastName.isEmpty()) {
                binding.tvUsernameLastname.error = "Vui lòng nhập"
            }
            binding.edtEmail.doOnTextChanged { text, start, before, count ->
                if (text!!.isEmpty()) {
                    binding.tvEmail.error = "Vui lòng nhập"
                } else {
                    binding.tvEmail.error = ""
                }
            }

            binding.edtPassword.doOnTextChanged { text, start, before, count ->
                if (text!!.isEmpty()) {
                    binding.tvPassword.error = "Vui lòng nhập"
                } else {
                    binding.tvPassword.error = ""
                }
            }
            binding.edtUsernameFirstname.doOnTextChanged { text, start, before, count ->
                if (text!!.isEmpty()) {
                    binding.tvUsernameFirstname.error = "Vui lòng nhập"
                } else {
                    binding.tvUsernameFirstname.error = ""
                }
            }

            binding.edtUsernameLastname.doOnTextChanged { text, start, before, count ->
                if (text!!.isEmpty()) {
                    binding.tvUsernameLastname.error = "Vui lòng nhập"
                } else {
                    binding.tvUsernameLastname.error = ""
                }
            }
            if (email.isEmpty() || password.isEmpty()||firstName.isEmpty() || lastName.isEmpty()) {

            } else {
                viewModel.register(email, password,firstName,lastName)
            }

        }
        binding.textView3.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}