package com.tta.fitnessapplication.view.login

import android.content.Context.MODE_PRIVATE
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
import com.tta.fitnessapplication.data.utils.Constant.PREF.WATER_INNEED
import com.tta.fitnessapplication.databinding.ActivityLoginBinding
import com.tta.fitnessapplication.view.base.BaseFragment

class LoginActivity : BaseFragment<ActivityLoginBinding>() {
    private var check = false
    private var saveLogin: Boolean = false

    override fun initView() {
        super.initView()
        binding.edtEmail.setText("theanh682001@gmail.com")
        binding.edtPassword.setText("123456")
        loginPreferences = requireActivity().getSharedPreferences(LOGIN_PREFS, MODE_PRIVATE)
        loginPrefsEditor = loginPreferences.edit();
        saveLogin = loginPreferences.getBoolean(SAVE_LOGIN, false);
        if (saveLogin) {
            binding.progessBarLogin.visibility = View.GONE
            findNavController().navigate(R.id.action_loginActivity_to_homeFragment)
        }
    }

    override fun getDataBinding(): ActivityLoginBinding {
        return ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun initViewModel() {
        super.initViewModel()
        mainViewModel.login.observe(viewLifecycleOwner) {
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
        loginPrefsEditor.putBoolean(SAVE_USER, true);
        loginPrefsEditor.putString(WATER_INNEED, "2000");
        loginPrefsEditor.commit();
        // go to home
        binding.progessBarLogin.visibility = View.GONE
        findNavController().navigate(R.id.action_loginActivity_to_homeFragment)
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