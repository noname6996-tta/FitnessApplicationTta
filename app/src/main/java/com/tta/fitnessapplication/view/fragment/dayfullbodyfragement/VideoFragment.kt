package com.tta.fitnessapplication.view.fragment.dayfullbodyfragement

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tta.fitnessapplication.R
import com.tta.fitnessapplication.databinding.FragemntVideoBinding
import com.tta.fitnessapplication.view.base.BaseFragment

class VideoFragment : BaseFragment<FragemntVideoBinding>(){
    companion object {
        var urlExercise = ""
    }

    override fun getDataBinding(): FragemntVideoBinding {
        return FragemntVideoBinding.inflate(layoutInflater)
    }

}