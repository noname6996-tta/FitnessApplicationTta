package com.tta.fitnessapplication.view.fragment

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate
import com.tta.fitnessapplication.databinding.FragmentChangeThemesBinding
import com.tta.fitnessapplication.view.base.BaseFragment

class ChangeThemesFragment : BaseFragment<FragmentChangeThemesBinding>() {
    override fun getDataBinding(): FragmentChangeThemesBinding {
        return FragmentChangeThemesBinding.inflate(layoutInflater)
    }

    override fun initView() {
        super.initView()
        if (isDarkModeOn()) {
            binding.switch1.isChecked = true
        } else {
            binding.switch1.isChecked = true
        }
    }

    override fun addEvent() {
        super.addEvent()
        binding.switch1.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                changeToDarkMode()
            } else {
                changeToLightMode()
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