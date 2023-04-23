package com.tta.fitnessapplication.view.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.tta.fitnessapplication.data.repository.RepositoryApi
import com.tta.fitnessapplication.databinding.FragmentDiscoverBinding
import com.tta.fitnessapplication.databinding.FragmentSettingBinding
import com.tta.fitnessapplication.view.activity.MainActivity.MainViewModel
import com.tta.fitnessapplication.view.activity.MainActivity.MainViewModelFactory
import com.tta.fitnessapplication.view.activity.login.LoginActivity
import com.tta.fitnessapplication.view.base.BaseFragment

class SettingFragment : Fragment() {
    private lateinit var binding : FragmentSettingBinding
    private lateinit var mainViewModel : MainViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSettingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        addDataProfile()
        addEvent()
    }

    private fun addDataProfile() {
        val repositoryApi = RepositoryApi()
        val viewModelFactory = MainViewModelFactory(repositoryApi)
        mainViewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        mainViewModel.dataExercise.observe(requireActivity()) {
            if (it.isSuccessful) {
                var dataProfile = mainViewModel.dataExercise.value?.body()?.data
                binding.textView23.text = "${dataProfile!![0].firstname} ${dataProfile!![0].lastname}"
                binding.textView24.text = "${dataProfile!![0].gender} "
            } else {
                Log.e("tta", it.errorBody().toString())
            }
        }

    }

    private fun addEvent() {
        binding.viewLogout.setOnClickListener {
            startActivity(Intent(activity,LoginActivity::class.java))
            activity?.finish()
        }
    }
}