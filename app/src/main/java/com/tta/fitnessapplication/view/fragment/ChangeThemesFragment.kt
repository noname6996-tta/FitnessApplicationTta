package com.tta.fitnessapplication.view.fragment

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.findNavController
import com.skydoves.balloon.ArrowPositionRules
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.BalloonSizeSpec
import com.skydoves.balloon.showAsDropDown
import com.tta.fitnessapplication.R
import com.tta.fitnessapplication.databinding.FragmentChangeThemesBinding
import com.tta.fitnessapplication.view.base.BaseFragment

class ChangeThemesFragment : BaseFragment<FragmentChangeThemesBinding>() {
    override fun getDataBinding(): FragmentChangeThemesBinding {
        return FragmentChangeThemesBinding.inflate(layoutInflater)
    }

    override fun initView() {
        super.initView()
        binding.switch1.isChecked = !isDarkModeOn()
    }

    override fun addEvent() {
        super.addEvent()
        with(binding) {
            switch1.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    changeToDarkMode()
                } else {
                    changeToLightMode()
                }
            }

            viewBack.setOnClickListener {
                findNavController().popBackStack()
            }

            val balloonWater = Balloon.Builder(requireContext())
                .setWidthRatio(1.0f)
                .setHeight(BalloonSizeSpec.WRAP)
                .setText("Change your app with your setting!")
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
                viewInfo.showAsDropDown(balloonWater)
            }
        }

    }

    // check if the app is in dark mode or not
    private fun isDarkModeOn(): Boolean {
        val nightModeFlags = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return nightModeFlags == Configuration.UI_MODE_NIGHT_YES
    }

    // If the App is in Dark Mode then
    // change it to Light Mode
    private fun changeToLightMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    private fun changeToDarkMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }
}