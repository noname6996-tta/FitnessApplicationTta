package com.tta.fitnessapplication.view.fragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.tta.fitnessapplication.data.utils.Constant
import com.tta.fitnessapplication.data.utils.Constant.Companion.BASE_URL
import com.tta.fitnessapplication.databinding.FragmentSettingBinding
import com.tta.fitnessapplication.view.activity.HistoryActivity.HistoryActivity
import com.tta.fitnessapplication.view.activity.WebViewActivity
import com.tta.fitnessapplication.view.activity.login.LoginActivity
import com.tta.fitnessapplication.view.base.BaseFragment

class SettingFragment : BaseFragment<FragmentSettingBinding>() {
    override fun getDataBinding(): FragmentSettingBinding {
        return FragmentSettingBinding.inflate(layoutInflater)
    }

    override fun addObservers() {
        super.addObservers()
        var token = loginPreferences.getString(Constant.EMAIL_USER, "").toString()
        if (token != "") {
            mainViewModel.getUserData(token)
        }
        mainViewModel.dataExercise.observe(viewLifecycleOwner) {
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

    override fun addEvent() {
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

        binding.viewAboutUs.setOnClickListener {
            val url = "$BASE_URL/fitnessweb/aboutUs"
            val intent = Intent(requireActivity(), WebViewActivity::class.java)
            intent.putExtra("url", url)
            startActivity(intent)
        }

        binding.viewContactUs.setOnClickListener {
            val url = "$BASE_URL/fitnessweb//fitnessweb/contact/"
            val intent = Intent(requireActivity(), WebViewActivity::class.java)
            intent.putExtra("url", url)
            startActivity(intent)
        }

        binding.viewPrivacy.setOnClickListener {
            val url = "$BASE_URL/fitnessweb/PrivacyPolicy"
            val intent = Intent(requireActivity(), WebViewActivity::class.java)
            intent.putExtra("url", url)
            startActivity(intent)
        }
    }
}