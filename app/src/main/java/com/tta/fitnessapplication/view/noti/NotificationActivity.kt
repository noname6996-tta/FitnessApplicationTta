package com.tta.fitnessapplication.view.noti

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tta.fitnessapplication.R

class NotificationActivity(var type : Int) : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)
    }
}