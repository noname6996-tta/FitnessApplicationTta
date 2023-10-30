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
import com.tta.fitnessapplication.databinding.FragmentRestexerciseBinding
import com.tta.fitnessapplication.view.activity.workout.doexercise.DoExerciseActivity
import com.tta.fitnessapplication.view.activity.workout.doexercise.DoExerciseActivity.Companion.numberExercise

class RestFragment : Fragment() {
    private lateinit var binding: FragmentRestexerciseBinding
    private lateinit var countdownTimer: CountDownTimer
    private val totalTimeInMillis: Long = 20000 // 20 seconds
    private var timeRemainingInMillis: Long = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRestexerciseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeCountdownTimer()
        initView()
        addEvent()
    }

    override fun onResume() {
        super.onResume()
        startCountdownTimer()
    }

    override fun onPause() {
        super.onPause()
        stopCountdownTimer()
    }

    private fun initializeCountdownTimer() {
        countdownTimer = object : CountDownTimer(totalTimeInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeRemainingInMillis = millisUntilFinished
                updateTimerText()
            }

            override fun onFinish() {
                stopCountdownTimer()
                findNavController().navigate(R.id.action_restFragment_to_doingExerciseFragment)
            }
        }
    }

    private fun startCountdownTimer() {
        countdownTimer.start()
    }

    private fun stopCountdownTimer() {
        countdownTimer.cancel()
    }

    private fun updateTimerText() {
        val seconds = (timeRemainingInMillis / 1000)
        binding.tvCountDown.text = String.format("%02d", seconds)
    }

    private fun initView() {
        val exercise = DoExerciseActivity.listExercise[numberExercise]
        if (exercise != null) {
            binding.tvNameExercise.text = exercise.name
            Glide.with(requireActivity())
                .load(exercise.image)
                .error(R.drawable.alarm_clock)
                .into(binding.imgExercise)
        }
    }

    private fun addEvent() {
        binding.btnBack.setOnClickListener {
            pauseCountdownTimer()
            AwesomeDialog.build(requireActivity())
                .title(
                    "Notification",
                    titleColor = ContextCompat.getColor(requireContext(), android.R.color.black)
                )
                .body(
                    "What is your problem?",
                    color = ContextCompat.getColor(requireContext(), android.R.color.black)
                )
                .icon(R.drawable.ic_logo)
                .background(R.drawable.bg_raduis_white_12dp)
                .onPositive(
                    "I just take a look",
                    buttonBackgroundColor = R.drawable.bg_raduis_white_12dp,
                    textColor = ContextCompat.getColor(requireContext(), android.R.color.darker_gray)
                ) {
                    requireActivity().onBackPressedDispatcher.onBackPressed()
                    requireActivity().finish()
                }
                .onNegative(
                    "Cancel",
                    buttonBackgroundColor = R.drawable.bg_raduis_white_12dp,
                    textColor = ContextCompat.getColor(requireContext(), android.R.color.darker_gray)
                ) {
                    resumeCountdownTimer()
                }
        }

        binding.imgNext.setOnClickListener {
            stopCountdownTimer()
            findNavController().navigate(R.id.action_restFragment_to_doingExerciseFragment)
        }
    }

    private fun pauseCountdownTimer() {
        countdownTimer.cancel()
    }

    private fun resumeCountdownTimer() {
        initializeCountdownTimer()
        startCountdownTimer()
    }
}