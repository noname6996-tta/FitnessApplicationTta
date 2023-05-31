package com.tta.fitnessapplication.view.splash

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.Manifest.permission.ACTIVITY_RECOGNITION
import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi
import com.tta.fitnessapplication.view.onboarding.OnBoardActivity
import com.tta.fitnessapplication.R
import com.tta.fitnessapplication.data.utils.Constant
import com.tta.fitnessapplication.databinding.ActivitySplashBinding
import com.tta.fitnessapplication.view.activity.MainActivity.MainActivity

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private lateinit var loginPreferences: SharedPreferences
    private lateinit var loginPrefsEditor: SharedPreferences.Editor
    private var saveLogin: Boolean = false
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginPreferences = getSharedPreferences(Constant.LOGIN_PREFS, MODE_PRIVATE)
        loginPrefsEditor = loginPreferences.edit()
        saveLogin = loginPreferences.getBoolean(Constant.SAVE_USER, false)
        if (saveLogin) {
            startActivity(Intent(this, MainActivity::class.java))
            this.finish()
        } else {

        }
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        // HERE WE ARE TAKING THE REFERENCE OF OUR IMAGE
        // SO THAT WE CAN PERFORM ANIMATION USING THAT IMAGE

        val slideAnimation = AnimationUtils.loadAnimation(this, R.anim.side_slide)
        binding.splashLogo.startAnimation(slideAnimation)
        binding.tvTitleSplash.startAnimation(slideAnimation)
        binding.tvLogoTitle.startAnimation(slideAnimation)

        if (ContextCompat.checkSelfPermission(this, ACTIVITY_RECOGNITION)
            != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            ActivityCompat.requestPermissions(this,
                arrayOf(ACTIVITY_RECOGNITION),
                1)
        }

        binding.view.setOnClickListener {
            val intent = Intent(this, OnBoardActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}