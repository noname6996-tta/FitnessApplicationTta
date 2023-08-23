package com.tta.fitnessapplication.view.noti

import com.skydoves.balloon.ArrowPositionRules
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.BalloonSizeSpec
import com.skydoves.balloon.showAsDropDown
import com.tta.fitnessapplication.R
import com.tta.fitnessapplication.databinding.ActivityNotificationBinding
import com.tta.fitnessapplication.view.base.BaseActivity

class NotificationActivity(var type: Int) : BaseActivity<ActivityNotificationBinding>() {
    override fun getDataBinding(): ActivityNotificationBinding {
        return ActivityNotificationBinding.inflate(layoutInflater)
    }

    override fun addEvent() {
        with(binding) {
            viewBack.setOnClickListener {
                this@NotificationActivity.finish()
                onBackPressedDispatcher.onBackPressed()
            }

            val ballonInfo = Balloon.Builder(this@NotificationActivity)
                .setWidthRatio(1.0f)
                .setHeight(BalloonSizeSpec.WRAP)
                .setText("Once you add your schedule we will notify you when it's time you choose!")
                .setTextColorResource(R.color.white)
                .setTextSize(15f)
                .setArrowPositionRules(ArrowPositionRules.ALIGN_BALLOON)
                .setArrowSize(10)
                .setArrowPosition(0.5f)
                .setPadding(12)
                .setCornerRadius(8f)
                .setBackgroundColorResource(R.color.text)
                .setBalloonAnimation(BalloonAnimation.ELASTIC)
                .build()
            viewInfo.setOnClickListener {
                viewInfo.showAsDropDown(ballonInfo)
            }
        }
    }

}