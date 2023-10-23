package com.tta.fitnessapplication.view.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.tta.fitnessapplication.R
import com.tta.fitnessapplication.databinding.ActivityForgotPasswordBinding
import com.tta.fitnessapplication.view.base.BaseActivity

class ForgotPasswordActivity : BaseActivity<ActivityForgotPasswordBinding>() {
    private var canClick = true
    override fun getDataBinding(): ActivityForgotPasswordBinding {
        return ActivityForgotPasswordBinding.inflate(layoutInflater)
    }

    override fun addEvent() {
        super.addEvent()
        binding.apply {
            viewBack.setOnClickListener{
                startActivity(Intent(this@ForgotPasswordActivity,LoginActivity::class.java))
                this@ForgotPasswordActivity.finish()
            }
            view.setOnClickListener {
                if (canClick){
                    canClick = false
                    binding.progessBarLogin.visibility = View.VISIBLE
                    val email = edtEmail.text.toString().trim()
                    if (email.isEmpty()){
                        Snackbar.make(binding.root,"You have input email",Snackbar.LENGTH_SHORT).show()
                    } else {
                        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    binding.progessBarLogin.visibility = View.GONE
                                    canClick = true
                                    Toast.makeText(this@ForgotPasswordActivity, "Email sent successfully, please check your email", Toast.LENGTH_SHORT).show()
                                    startActivity(Intent(this@ForgotPasswordActivity,LoginActivity::class.java))
                                    this@ForgotPasswordActivity.finish()
                                } else {
                                    canClick = true
                                    binding.progessBarLogin.visibility = View.GONE
                                    Toast.makeText(this@ForgotPasswordActivity, "Failed to send email", Toast.LENGTH_SHORT).show()
                                }
                            }
                    }
                }
            }
        }
    }
}