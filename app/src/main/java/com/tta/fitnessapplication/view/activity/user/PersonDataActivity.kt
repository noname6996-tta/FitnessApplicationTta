package com.tta.fitnessapplication.view.activity.user

import com.tta.fitnessapplication.databinding.ActivityPersonDataBinding
import com.tta.fitnessapplication.view.base.BaseActivity

class PersonDataActivity : BaseActivity<ActivityPersonDataBinding>() {
    override fun getDataBinding(): ActivityPersonDataBinding {
        return ActivityPersonDataBinding.inflate(layoutInflater)
    }
}