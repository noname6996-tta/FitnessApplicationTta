package com.tta.fitnessapplication.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tta.fitnessapplication.databinding.FragmentDiscoverBinding
import com.tta.fitnessapplication.databinding.FragmentSettingBinding
import com.tta.fitnessapplication.view.activity.login.LoginActivity
import com.tta.fitnessapplication.view.base.BaseFragment

class SettingFragment : Fragment() {
    private lateinit var binding : FragmentSettingBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSettingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        addEvent()
    }

    private fun addEvent() {
        binding.viewLogout.setOnClickListener {
            startActivity(Intent(activity,LoginActivity::class.java))
            activity?.finish()
        }
    }
}