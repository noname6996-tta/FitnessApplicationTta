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
import com.tta.fitnessapplication.data.model.Exercise
import com.tta.fitnessapplication.databinding.FragmentDoingexerciseBinding
import com.tta.fitnessapplication.databinding.FragmentHistoryBinding
import com.tta.fitnessapplication.view.activity.DayFullBody.ExerciseBottomSheetFragment
import com.tta.fitnessapplication.view.activity.doexercise.DoExerciseActivity.Companion.listExercise
import com.tta.fitnessapplication.view.activity.doexercise.DoExerciseActivity.Companion.numberExercise
import com.tta.fitnessapplication.view.base.BaseFragment

class DoingExerciseFragment : BaseFragment<FragmentDoingexerciseBinding>(){
    private lateinit var countdownTimer: CountDownTimer
    private var totalTimeInMillis: Long = 20000 // 20 seconds
    private var timeRemainingInMillis: Long = totalTimeInMillis
    private var isTimerRunning: Boolean = false
    private lateinit var exercise: Exercise
    override fun getDataBinding(): FragmentDoingexerciseBinding {
        return FragmentDoingexerciseBinding.inflate(layoutInflater)
    }
    override fun initView() {
        super.initView()
        // Initialize the timer
        countdownTimer = object : CountDownTimer(timeRemainingInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeRemainingInMillis = millisUntilFinished
                updateTimerText()
            }

            override fun onFinish() {
                // Do something when the countdown is complete
                stopTimer()
                numberExercise++
                findNavController().navigate(R.id.action_doingExerciseFragment_to_restFragment)
            }
        }
        exercise = listExercise[numberExercise]
        if (exercise != null) {
            binding.textView45.text = exercise.name
            Glide.with(requireContext())
                .load(exercise.image)
                .error(R.drawable.alarm_clock)
                .into(binding.imageView24)
            if (exercise.type == "0") {
                startCountDown()
                binding.btnPause.setOnClickListener {
                    pauseCountDown()
                    AwesomeDialog.build(requireActivity())
                        .title(
                            "Notification",
                            titleColor = ContextCompat.getColor(
                                requireContext(),
                                android.R.color.white
                            )
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
                            textColor = ContextCompat.getColor(
                                requireContext(),
                                android.R.color.black
                            )
                        ) {
                            requireActivity().onBackPressedDispatcher.onBackPressed()
                            requireActivity().finish()
                        }
                        .onNegative(
                            "Cancel",
                            buttonBackgroundColor = R.drawable.bg_pink_linear_16,
                            textColor = ContextCompat.getColor(
                                requireContext(),
                                android.R.color.black
                            )
                        ) {
                            resumeCountDown()
                        }
                }
            } else {
                binding.tvCountDown.text = exercise.number + "x"
                binding.btnPause.text = "Done"
                binding.btnPause.setIconResource(R.drawable.baseline_done_24)
                binding.btnPause.setOnClickListener {
                    numberExercise++
                    // check khi number == list.size thì hoàn thành bài tập
                    Log.e("tta", numberExercise.toString() + listExercise[numberExercise])
                    val action =
                        DoingExerciseFragmentDirections.actionDoingExerciseFragmentToRestFragment()
                    findNavController().navigate(action)
                }
            }
        }
    }


    override fun addEvent() {
        binding.tvSkip.setOnClickListener {
            stopTimer()
            numberExercise++
            // check khi number == list.size thì hoàn thành bài tập
            Log.e("tta", numberExercise.toString() + listExercise[numberExercise])
            val action = DoingExerciseFragmentDirections.actionDoingExerciseFragmentToRestFragment()
            findNavController().navigate(action)
        }
        binding.tvPrevious.setOnClickListener {
            stopTimer()
            numberExercise--
            // check khi number == list.size thì hoàn thành bài tập
            Log.e("tta", numberExercise.toString() + listExercise[numberExercise])
            val action = DoingExerciseFragmentDirections.actionDoingExerciseFragmentToRestFragment()
            findNavController().navigate(action)
        }
        binding.btnBack.setOnClickListener {
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
        binding.textView45.setOnClickListener {
            pauseCountDown()
//            val dialog = ExerciseBottomSheetFragment(exercise)
//            dialog.setDismissListener(this)
//            dialog.show(requireContext().supportFragmentManager, "FullScreenDialog")

        }
    }

    private fun updateTimerText() {
        val seconds = (timeRemainingInMillis / 1000)
        binding.tvCountDown.text = String.format("%02d s", seconds)
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
                    numberExercise++
                    findNavController().navigate(R.id.action_doingExerciseFragment_to_restFragment)
                }
            }
            countdownTimer.start()
        }
    }

    private fun stopTimer() {
        countdownTimer.cancel()
    }
}