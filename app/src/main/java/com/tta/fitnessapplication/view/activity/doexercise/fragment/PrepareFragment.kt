package com.tta.fitnessapplication.view.activity.doexercise.fragment

import android.os.CountDownTimer
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.awesomedialog.AwesomeDialog
import com.example.awesomedialog.background
import com.example.awesomedialog.body
import com.example.awesomedialog.icon
import com.example.awesomedialog.onNegative
import com.example.awesomedialog.onPositive
import com.example.awesomedialog.title
import com.tta.fitnessapplication.R
import com.tta.fitnessapplication.databinding.FragmentPrepareBinding
import com.tta.fitnessapplication.view.activity.doexercise.DoExerciseActivity.Companion.listExercise
import com.tta.fitnessapplication.view.base.BaseFragment

class PrepareFragment : BaseFragment<FragmentPrepareBinding>() {
    private lateinit var countdownTimer: CountDownTimer
    private var totalTimeInMillis: Long = 20000 // 20 seconds
    private var timeRemainingInMillis: Long = totalTimeInMillis
    private var isTimerRunning: Boolean = false

    override fun getDataBinding(): FragmentPrepareBinding {
        return FragmentPrepareBinding.inflate(layoutInflater)
    }
    override fun addEvent() {
        super.addEvent()
        binding.imgNext.setOnClickListener {
            stopTimer()
            findNavController().navigate(R.id.action_prepareFragment_to_doingExerciseFragment)
        }

        binding.imgBack.setOnClickListener {
            pauseCountDown()
            AwesomeDialog.build(requireActivity())
                .title(
                    "Notification",
                    titleColor = ContextCompat.getColor(requireContext(), android.R.color.white)
                )
                .body(
                    "What is your problem?",
                    color = ContextCompat.getColor(requireContext(), android.R.color.white)
                )
                .icon(R.drawable.icon_bed)
                .background(R.drawable.bg_blue_linear_16)
                .onPositive(
                    "I just take a look",
                    buttonBackgroundColor = R.drawable.bg_pink_linear_16,
                    textColor = ContextCompat.getColor(requireContext(), android.R.color.black)
                ) {
                    requireActivity().onBackPressedDispatcher.onBackPressed()
                    requireActivity().finish()
                }
                .onNegative(
                    "Cancel",
                    buttonBackgroundColor = R.drawable.bg_pink_linear_16,
                    textColor = ContextCompat.getColor(requireContext(), android.R.color.black)
                ) {
                    resumeCountDown()
                }
        }
    }

    override fun initView() {
        super.initView()
        val exercise = listExercise[0]
        if (exercise != null) {
            binding.tvNameExercise.text = exercise.name
            Glide.with(requireActivity())
                .load(exercise.image)
                .error(R.drawable.alarm_clock)
                .into(binding.imgExercise);
        }
        // Initialize the timer
        countdownTimer = object : CountDownTimer(timeRemainingInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeRemainingInMillis = millisUntilFinished
                updateTimerText()
            }

            override fun onFinish() {
                // Do something when the countdown is complete
                stopTimer()
                findNavController().navigate(R.id.action_prepareFragment_to_doingExerciseFragment)
            }
        }
        startCountDown()
    }

    private fun updateTimerText() {
        val seconds = (timeRemainingInMillis / 1000)
        binding.tvCountDown.text = String.format("%02d", seconds)
    }

    private fun startCountDown() {
        if (!isTimerRunning) {
            isTimerRunning = true
            countdownTimer.start()
        }
    }

    private fun pauseCountDown() {
        if (isTimerRunning) {
            isTimerRunning = false
            countdownTimer.cancel()
        }
    }

    private fun resumeCountDown() {
        if (!isTimerRunning) {
            isTimerRunning = true
            countdownTimer = object : CountDownTimer(timeRemainingInMillis, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    timeRemainingInMillis = millisUntilFinished
                    updateTimerText()
                }

                override fun onFinish() {
                    // Do something when the countdown is complete
                    stopTimer()
                    findNavController().navigate(R.id.action_prepareFragment_to_doingExerciseFragment)
                }
            }
            countdownTimer.start()
        }
    }

    private fun stopTimer() {
        countdownTimer.cancel()
    }
}