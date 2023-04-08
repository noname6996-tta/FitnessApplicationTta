package com.tta.fitnessapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tta.fitnessapplication.databinding.ActivitySignUpBinding
import com.tta.fitnessapplication.view.activity.MainActivity.MainActivity

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        addEvent()
    }

    private fun addEvent(){
        binding.btnRegester.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        binding.textView3.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }
    }
}