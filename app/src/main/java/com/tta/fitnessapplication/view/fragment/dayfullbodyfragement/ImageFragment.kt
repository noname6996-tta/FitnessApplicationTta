package com.tta.fitnessapplication.view.fragment.dayfullbodyfragement

import com.bumptech.glide.Glide
import com.tta.fitnessapplication.R
import com.tta.fitnessapplication.databinding.FragmentImageBinding
import com.tta.fitnessapplication.databinding.FragmentRestexerciseBinding
import com.tta.fitnessapplication.view.base.BaseFragment

class ImageFragment : BaseFragment<FragmentImageBinding>() {
    companion object {
        var image: String = ""
    }
    override fun getDataBinding(): FragmentImageBinding {
        return FragmentImageBinding.inflate(layoutInflater)
    }

    override fun initView() {
        super.initView()
        if (image != "") {
            Glide.with(requireContext())
                .load(image)
                .error(R.drawable.alarm_clock)
                .into(binding.imgExercise)
        }
    }

}