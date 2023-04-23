package com.tta.fitnessapplication.view.activity.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.tta.fitnessapplication.databinding.ActivityLoginBinding
import com.tta.fitnessapplication.view.activity.MainActivity.MainActivity
import com.tta.fitnessapplication.view.activity.signup.SignUpActivity

class LoginActivity : AppCompatActivity() {
    companion object{
        var emailUser : String = ""
    }
    private lateinit var binding: ActivityLoginBinding
    private val viewModel = LoginViewModel()
    private var check = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        addObsever()
        addEvent()
    }
    private fun addObsever() {
        viewModel.message.observe(this){
            Toast.makeText(this,it,Toast.LENGTH_LONG).show()
        }
        viewModel.checkLogin.observe(this){
            check = it
            if (check){
                emailUser = binding.edtEmail.text.toString()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
    }

    private fun addEvent() {
        binding.view.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()
            if (email.isEmpty()){
                binding.tvEmail.error = "Vui lòng nhập"
            }
            if (password.isEmpty()){
                binding.tvPassword.error = "Vui lòng nhập"
            }
            binding.edtEmail.doOnTextChanged { text, start, before, count ->
                if (text!!.isEmpty()){
                    binding.tvEmail.error = "Vui lòng nhập"
                } else {
                    binding.tvEmail.error = ""
                }
            }

            binding.edtPassword.doOnTextChanged { text, start, before, count ->
                if (text!!.isEmpty()){
                    binding.tvPassword.error = "Vui lòng nhập"
                } else {
                    binding.tvPassword.error = ""
                }
            }
            if (email.isEmpty()||password.isEmpty()){

            } else {
                viewModel.login(email,password)
            }
        }

        binding.textView3.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()

        }
    }
}