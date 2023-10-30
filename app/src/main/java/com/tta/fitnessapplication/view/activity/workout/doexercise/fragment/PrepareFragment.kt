package com.tta.fitnessapplication.view.activity.workout.doexercise.fragment

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
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
import com.tta.fitnessapplication.view.activity.workout.doexercise.DoExerciseActivity.Companion.listExercise

class PrepareFragment : Fragment() {
    private lateinit var binding: FragmentPrepareBinding
    private lateinit var countdownTimer: CountDownTimer
    private var totalTimeInMillis: Long = 20000 // 20 seconds
    private var timeRemainingInMillis: Long = totalTimeInMillis
    private var isTimerRunning: Boolean = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPrepareBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        addEvent()
    }

    private fun addEvent() {
        binding.imgNext.setOnClickListener {
            stopTimer()
            findNavController().navigate(R.id.action_prepareFragment_to_doingExerciseFragment)
        }

        binding.imgBack.setOnClickListener {
            pauseCountDown()
            AwesomeDialog.build(requireActivity())
                .title(
                    "Notification",
                    titleColor = ContextCompat.getColor(requireContext(), android.R.color.black)
                )
                .body(
                    "What is your problem?",
                    color = ContextCompat.getColor(requireContext(), android.R.color.black)
                )
                .icon(R.drawable.logo_title)
                .background(R.drawable.bg_raduis_white_12dp)
                .onPositive(
                    "I just take a look",
                    buttonBackgroundColor = R.drawable.bg_raduis_white_12dp,
                    textColor = ContextCompat.getColor(
                        requireContext(),
                        android.R.color.darker_gray
                    )
                ) {
                    requireActivity().onBackPressedDispatcher.onBackPressed()
                    requireActivity().finish()
                }
                .onNegative(
                    "Cancel",
                    buttonBackgroundColor = R.drawable.bg_raduis_white_12dp,
                    textColor = ContextCompat.getColor(
                        requireContext(),
                        android.R.color.darker_gray
                    )
                ) {
                    resumeCountDown()
                }
        }
    }

    private fun initView() {
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