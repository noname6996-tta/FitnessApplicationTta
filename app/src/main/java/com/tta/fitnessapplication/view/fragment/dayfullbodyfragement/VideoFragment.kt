package com.tta.fitnessapplication.view.fragment.dayfullbodyfragement

import android.content.Intent
import com.tta.fitnessapplication.databinding.FragemntVideoBinding
import com.tta.fitnessapplication.view.activity.WebViewActivity
import com.tta.fitnessapplication.view.base.BaseFragment

class VideoFragment : BaseFragment<FragemntVideoBinding>() {
    companion object {
        var urlExercise = ""
    }

    override fun getDataBinding(): FragemntVideoBinding {
        return FragemntVideoBinding.inflate(layoutInflater)
    }

    override fun initView() {
        super.initView()
        if (!urlExercise.isNullOrEmpty()) {
            val intent = Intent(requireActivity(), WebViewActivity::class.java)
            intent.putExtra("url", urlExercise)
            startActivity(intent)
        }
    }

}