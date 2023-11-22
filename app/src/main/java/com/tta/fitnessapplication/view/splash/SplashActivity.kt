package com.tta.fitnessapplication.view.splash

import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.tta.fitnessapplication.R
import com.tta.fitnessapplication.data.utils.Constant
import com.tta.fitnessapplication.databinding.ActivitySplashBinding
import com.tta.fitnessapplication.view.MainActivity
import com.tta.fitnessapplication.view.br.UninstallReceiver
import com.tta.fitnessapplication.view.login.LoginActivity
import com.tta.fitnessapplication.view.onboarding.OnBoardActivity

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private lateinit var loginPreferences: SharedPreferences
    private lateinit var loginPrefsEditor: SharedPreferences.Editor
    private var saveLogin: Boolean = false

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intentFilter = IntentFilter(Intent.ACTION_PACKAGE_FULLY_REMOVED)
        intentFilter.addDataScheme("package")
        registerReceiver(UninstallReceiver(), intentFilter)
        loginPreferences = getSharedPreferences(Constant.LOGIN_PREFS, MODE_PRIVATE)
        loginPrefsEditor = loginPreferences.edit()
        saveLogin = loginPreferences.getBoolean(Constant.SAVE_USER, false)
        if (saveLogin) {
            val auth = FirebaseAuth.getInstance()
            val currentUser = auth.currentUser

            if (currentUser != null) {
                // User is logged in
                // Perform any necessary actions for a logged-in user
                startActivity(Intent(this, MainActivity::class.java))
                this.finish()
            } else {
                // User is not logged in
                // Perform any necessary actions for a non-logged-in user
                loginPreferences =
                    this.getSharedPreferences(
                        Constant.LOGIN_PREFS,
                        MODE_PRIVATE
                    )
                loginPrefsEditor = loginPreferences.edit()
                loginPrefsEditor.remove(Constant.SAVE_USER)
                loginPrefsEditor.remove(Constant.PREF.IDUSER)
                loginPrefsEditor.remove(Constant.EMAIL_USER)
                loginPrefsEditor.commit()
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        val slideAnimation = AnimationUtils.loadAnimation(this, R.anim.side_slide)
        binding.splashLogo.startAnimation(slideAnimation)
        binding.tvTitleSplash.startAnimation(slideAnimation)
        binding.tvLogoTitle.startAnimation(slideAnimation)

        binding.view.setOnClickListener {
            val intent = Intent(this, OnBoardActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}