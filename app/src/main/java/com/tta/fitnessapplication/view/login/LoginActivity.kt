package com.tta.fitnessapplication.view.login

import android.content.Intent
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.tta.fitnessapplication.R
import com.tta.fitnessapplication.data.utils.Constant.Companion.EMAIL_USER
import com.tta.fitnessapplication.data.utils.Constant.Companion.LOGIN_PREFS
import com.tta.fitnessapplication.data.utils.Constant.Companion.SAVE_LOGIN
import com.tta.fitnessapplication.data.utils.Constant.Companion.SAVE_USER
import com.tta.fitnessapplication.data.utils.Constant.PREF.IDUSER
import com.tta.fitnessapplication.data.utils.Constant.PREF.SLEEP_TIME
import com.tta.fitnessapplication.data.utils.Constant.PREF.WAKEUP_TIME
import com.tta.fitnessapplication.data.utils.Constant.PREF.WATER_INNEED
import com.tta.fitnessapplication.databinding.ActivityLoginBinding
import com.tta.fitnessapplication.view.MainActivity
import com.tta.fitnessapplication.view.base.BaseActivity

class LoginActivity : BaseActivity<ActivityLoginBinding>() {
    private var check = false
    private var saveLogin: Boolean = false

    override fun initView() {
        super.initView()
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
        mainViewModel.login.observe(this) {
            if (it.isSuccessful) {
                check = true
                loginPrefsEditor.putString(IDUSER, it.body()?.id)
                loginPrefsEditor.commit()
                loginPrefsEditor.putString(EMAIL_USER, it.body()?.email)
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
        loginPrefsEditor.putString(SLEEP_TIME, "10:00:00")
        loginPrefsEditor.putString(WAKEUP_TIME, "06:00:00")
        loginPrefsEditor.commit()
        // go to home
        binding.progessBarLogin.visibility = View.GONE
        this.finish()
        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
    }


    override fun addEvent() {
        binding.view.setOnClickListener {
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
                mainViewModel.login(email, password)
            }
        }

        binding.textView3.setOnClickListener {
//            startActivity(Intent(this, SignUpActivity::class.java))
//            finish()
        }
    }
}