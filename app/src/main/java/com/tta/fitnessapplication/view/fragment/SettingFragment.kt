package com.tta.fitnessapplication.view.fragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.tta.fitnessapplication.data.repository.RepositoryApi
import com.tta.fitnessapplication.data.utils.Constant
import com.tta.fitnessapplication.databinding.FragmentSettingBinding
import com.tta.fitnessapplication.view.activity.HistoryActivity.HistoryActivity
import com.tta.fitnessapplication.view.activity.MainActivity.MainViewModel
import com.tta.fitnessapplication.view.activity.MainActivity.MainViewModelFactory
import com.tta.fitnessapplication.view.activity.login.LoginActivity

class SettingFragment : Fragment() {
    private lateinit var binding: FragmentSettingBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var loginPreferences: SharedPreferences
    private lateinit var loginPrefsEditor: SharedPreferences.Editor
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
        loginPreferences = requireActivity().getSharedPreferences(
            Constant.LOGIN_PREFS,
            AppCompatActivity.MODE_PRIVATE
        )
        loginPrefsEditor = loginPreferences.edit();
        var token = loginPreferences.getString(Constant.EMAIL_USER, "").toString()
        if (token != "") {
            mainViewModel.getUserData(token)
        }
        mainViewModel.dataExercise.observe(requireActivity()) {
            if (it.isSuccessful) {
                var dataProfile = mainViewModel.dataExercise.value?.body()?.data
                binding.tvUserName.text =
                    "${dataProfile!![0].firstname} ${dataProfile!![0].lastname}"
                binding.tvGenderUser.text = "${dataProfile!![0].gender} "
                if (dataProfile!![0].tall == "" || dataProfile!![0].tall.isEmpty()) {
                    binding.tvHeight.text = "_"
                } else {
                    binding.tvHeight.text = "${dataProfile!![0].tall} "
                }
                if (dataProfile!![0].weight == "" || dataProfile!![0].weight.isEmpty()) {
                    binding.tvWeight.text = "_"
                } else {
                    binding.tvWeight.text = "${dataProfile!![0].weight} "
                }
                if (dataProfile!![0].age == 0) {
                    binding.tvAge.text = "_"
                } else {
                    binding.tvAge.text = "${dataProfile!![0].age} "
                }
            } else {
                Log.e("tta", it.errorBody().toString())
            }
        }

    }

    private fun addEvent() {
        binding.viewLogout.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Thông báo")
                .setMessage("Bạn có muốn đăng xuất không ?")
                .setPositiveButton("Có",
                    DialogInterface.OnClickListener { _, _ ->
                        loginPreferences =
                            requireActivity().getSharedPreferences(
                                Constant.LOGIN_PREFS,
                                AppCompatActivity.MODE_PRIVATE
                            )
                        loginPrefsEditor = loginPreferences.edit()
                        loginPrefsEditor.clear()
                        loginPrefsEditor.commit()
                        val intent = Intent(requireContext(), LoginActivity::class.java)
                        startActivity(intent)
                        requireActivity().finish()
                    })
                .setNegativeButton("Không", null)
                .show()
        }

        binding.viewHistory.setOnClickListener {
            requireActivity().startActivity(Intent(activity, HistoryActivity::class.java))
        }
    }
}