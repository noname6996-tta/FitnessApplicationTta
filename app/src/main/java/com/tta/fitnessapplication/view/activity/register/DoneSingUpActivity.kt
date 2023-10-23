package com.tta.fitnessapplication.view.activity.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tta.fitnessapplication.R
import com.tta.fitnessapplication.databinding.ActivityDoneSingUpBinding
import com.tta.fitnessapplication.view.base.BaseActivity
import com.tta.fitnessapplication.view.login.LoginActivity

class DoneSingUpActivity : BaseActivity<ActivityDoneSingUpBinding>() {

    override fun getDataBinding(): ActivityDoneSingUpBinding {
        return ActivityDoneSingUpBinding.inflate(layoutInflater)
    }

    override fun addEvent() {
        super.addEvent()
        binding.view.setOnClickListener {
            startActivity(Intent(this@DoneSingUpActivity,LoginActivity::class.java))
            this.finish()
        }
    }
}