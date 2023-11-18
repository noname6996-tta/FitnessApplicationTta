package com.tta.fitnessapplication.view.login

import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.tta.fitnessapplication.R
import com.tta.fitnessapplication.data.utils.Constant.Companion.EMAIL_USER
import com.tta.fitnessapplication.data.utils.Constant.Companion.LOGIN_PREFS
import com.tta.fitnessapplication.data.utils.Constant.Companion.SAVE_LOGIN
import com.tta.fitnessapplication.data.utils.Constant.Companion.SAVE_USER
import com.tta.fitnessapplication.data.utils.Constant.PREF.CALO_INNEED
import com.tta.fitnessapplication.data.utils.Constant.PREF.IDUSER
import com.tta.fitnessapplication.data.utils.Constant.PREF.PROCESS_USER
import com.tta.fitnessapplication.data.utils.Constant.PREF.SLEEP_TIME
import com.tta.fitnessapplication.data.utils.Constant.PREF.WAKEUP_TIME
import com.tta.fitnessapplication.data.utils.Constant.PREF.WATER_INNEED
import com.tta.fitnessapplication.databinding.ActivityLoginBinding
import com.tta.fitnessapplication.view.MainActivity
import com.tta.fitnessapplication.view.activity.register.SignUpActivity
import com.tta.fitnessapplication.view.base.BaseActivity

class LoginActivity : BaseActivity<ActivityLoginBinding>() {
    private var check = false
    private var saveLogin: Boolean = false
    private var canClick = true

    // Initialize Firebase Auth
    private lateinit var auth: FirebaseAuth
    override fun initView() {
        super.initView()
        auth = FirebaseAuth.getInstance()
        binding.edtEmail.setText("theanh682001@gmail.com")
        binding.edtPassword.setText("123456")
        loginPreferences = this.getSharedPreferences(LOGIN_PREFS, MODE_PRIVATE)
        loginPrefsEditor = loginPreferences.edit()
        saveLogin = loginPreferences.getBoolean(SAVE_LOGIN, false)
        if (saveLogin) {
            binding.progessBarLogin.visibility = View.GONE
            this.finish()
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
        }
    }

    override fun getDataBinding(): ActivityLoginBinding {
        return ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun initViewModel() {
        super.initViewModel()
        mainViewModel.dataExercise.observe(this) {
            if (it.isSuccessful) {
                check = true
                loginPrefsEditor.putString(IDUSER, it.body()!!.data[0].id.toString())
                loginPrefsEditor.commit()
                loginPrefsEditor.putString(EMAIL_USER, it.body()!!.data[0].email.toString())
                loginPrefsEditor.commit()
            } else {
                check = false
                Snackbar.make(binding.root, it.errorBody().toString(), Snackbar.LENGTH_SHORT).show()
            }
            if (check) {
                saveUser()
            }
        }
    }

    private fun saveUser() {
        // save data
        loginPrefsEditor.putBoolean(SAVE_USER, true)
        loginPrefsEditor.putString(WATER_INNEED, "2000")
        loginPrefsEditor.putString(CALO_INNEED, "2000")
        loginPrefsEditor.putString(SLEEP_TIME, "10:00:00")
        loginPrefsEditor.putString(WAKEUP_TIME, "06:00:00")
        loginPrefsEditor.commit()
        //
        binding.progessBarLogin.visibility = View.GONE
        canClick = true
        // go to home
        this.finish()
        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
    }


    override fun addEvent() {
        binding.view.setOnClickListener {
            if (canClick) {
                canClick = false
                val email = binding.edtEmail.text.toString()
                val password = binding.edtPassword.text.toString()
                if (email.isEmpty()) {
                    binding.tvEmail.error = "Vui lòng nhập"
                }
                if (password.isEmpty()) {
                    binding.tvPassword.error = "Vui lòng nhập"
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
                if (email.isEmpty() || password.isEmpty()) {

                } else {
                    binding.progessBarLogin.visibility = View.VISIBLE
                    login(email, password)
                }
            }
        }

        binding.textView3.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }
        binding.tvForgotPassword.setOnClickListener {
            this.finish()
            startActivity(Intent(this@LoginActivity, ForgotPasswordActivity::class.java))
        }
    }

    private fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Login success, update UI with the signed-in user's information
                    val user: FirebaseUser? = auth.currentUser
                    // Proceed with the authenticated user
                    // ...
                    mainViewModel.getUserData(user!!.email.toString())
                } else {
                    // Login failed, display a message to the user
                    Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    binding.progessBarLogin.visibility = View.GONE
                    canClick = true
                }
            }
            .addOnFailureListener {
                Toast.makeText(baseContext, it.localizedMessage.toString(), Toast.LENGTH_SHORT).show()
            }
    }
}