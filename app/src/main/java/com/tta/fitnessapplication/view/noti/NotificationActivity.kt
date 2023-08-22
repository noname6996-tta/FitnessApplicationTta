package com.tta.fitnessapplication.view.noti

import com.tta.fitnessapplication.databinding.ActivityNotificationBinding
import com.tta.fitnessapplication.view.base.BaseActivity

class NotificationActivity(var type: Int) : BaseActivity<ActivityNotificationBinding>() {
    override fun getDataBinding(): ActivityNotificationBinding {
        return ActivityNotificationBinding.inflate(layoutInflater)
    }

}