package com.tta.fitnessapplication.view.fragment

import android.content.res.Configuration
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.awesomedialog.AwesomeDialog
import com.example.awesomedialog.background
import com.example.awesomedialog.body
import com.example.awesomedialog.icon
import com.example.awesomedialog.onNegative
import com.example.awesomedialog.onPositive
import com.example.awesomedialog.title
import com.skydoves.balloon.ArrowPositionRules
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.BalloonSizeSpec
import com.skydoves.balloon.showAsDropDown
import com.tta.fitnessapplication.R
import com.tta.fitnessapplication.databinding.FragmentChangeThemesBinding
import com.tta.fitnessapplication.view.activity.history.HistoryViewModel
import com.tta.fitnessapplication.view.base.BaseFragment

class ChangeThemesFragment : BaseFragment<FragmentChangeThemesBinding>() {
    private lateinit var viewModel: HistoryViewModel
    override fun getDataBinding(): FragmentChangeThemesBinding {
        return FragmentChangeThemesBinding.inflate(layoutInflater)
    }

    override fun initViewModel() {
        super.initViewModel()
        viewModel = ViewModelProvider(this)[HistoryViewModel::class.java]
    }

    override fun addObservers() {
        super.addObservers()
        mainViewModel.downloadHistory.observe(viewLifecycleOwner) { list ->
            hideLoading()
            if (list.isSuccessful && !list.body()?.data.isNullOrEmpty()) {
                for (item in list.body()?.data!!) {
                    viewModel.addHistory(item)
                }
            }
            Toast.makeText(requireContext(), "Download Success", Toast.LENGTH_SHORT).show()
        }
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

            viewChangePassword.setOnClickListener {
                findNavController().navigate(R.id.action_changeThemesFragment_to_changePasswordFragment)
            }

            viewUploadHistory.setOnClickListener {
                uploadHistory()
            }

            viewDownloadHistory.setOnClickListener {
                downloadHistory()
            }
        }

    }

    private fun downloadHistory() {
        showLoading()
        mainViewModel.getHistoryById(idUser)
    }

    private fun uploadHistory() {
        AwesomeDialog.build(requireActivity())
            .title(
                "Notification",
                titleColor = ContextCompat.getColor(requireContext(), android.R.color.black)
            )
            .body(
                "Are you sure about upload your local history to Clound?",
                color = ContextCompat.getColor(requireContext(), android.R.color.black)
            )
            .icon(R.drawable.baseline_info_24)
            .background(R.drawable.bg_raduis_white_12dp)
            .onPositive(
                "yes i change the phone",
                buttonBackgroundColor = R.drawable.bg_raduis_white_12dp,
                textColor = ContextCompat.getColor(requireContext(), android.R.color.black)
            ) {
                viewModel.readAllData.observe(viewLifecycleOwner) {
                    for (item in it) {
                        mainViewModel.uploadHistoryUser(
                            item.id_user.toString(),
                            item.date.toString(),
                            item.time.toString(),
                            item.activity.toString(),
                            item.type.toString(),
                            item.value.toString()
                        )
                    }
                }
            }
            .onNegative(
                "Cancel",
                buttonBackgroundColor = R.drawable.bg_raduis_white_12dp,
                textColor = ContextCompat.getColor(requireContext(), android.R.color.black)
            ) {

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