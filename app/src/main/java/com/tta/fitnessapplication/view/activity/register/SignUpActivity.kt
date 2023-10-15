package com.tta.fitnessapplication.view.activity.register

import android.content.Intent
import androidx.core.widget.doOnTextChanged
import com.example.awesomedialog.AwesomeDialog
import com.example.awesomedialog.body
import com.example.awesomedialog.icon
import com.example.awesomedialog.onPositive
import com.example.awesomedialog.title
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.tta.fitnessapplication.R
import com.tta.fitnessapplication.databinding.ActivitySignUpBinding
import com.tta.fitnessapplication.view.base.BaseActivity
import com.tta.fitnessapplication.view.login.LoginActivity

class SignUpActivity : BaseActivity<ActivitySignUpBinding>() {
    private lateinit var auth: FirebaseAuth
    override fun getDataBinding(): ActivitySignUpBinding {
        return ActivitySignUpBinding.inflate(layoutInflater)
    }

    override fun initView() {
        super.initView()
        // Initialize Firebase Auth
//        auth = Firebase.auth
    }

    override fun initViewModel() {
        super.initViewModel()
        mainViewModel.login.observe(this) {
            if (it.isSuccessful) {
                if (it.body()?.success == 1) {
                    AwesomeDialog.build(this)
                        .title("Notification !")
                        .body("Register success, but i need some information for your profiles")
                        .icon(R.drawable.alarm_clock)
                        .onPositive("ok") {
                            startActivity(Intent(this, AfterSigUpActivity::class.java))
                            finish()
                        }
                }
            }
        }
    }


    override fun addEvent() {
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
            if (email.isEmpty() || password.isEmpty() || firstName.isEmpty() || lastName.isEmpty()) {

            } else {
                registerByFirebase(email, password, firstName, lastName)

            }
        }
        binding.textView3.setOnClickListener {
            this.finish()
            startActivity(Intent(this@SignUpActivity, LoginActivity::class.java))
        }
    }

    private fun registerByFirebase(
        email: String,
        password: String,
        firstName: String,
        lastName: String
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    mainViewModel.register(email, password, firstName, lastName)
                } else {
                    // If sign in fails, display a message to the user.
//                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                }
            }
    }
}