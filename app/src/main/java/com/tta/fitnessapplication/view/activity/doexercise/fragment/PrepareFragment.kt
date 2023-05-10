package com.tta.fitnessapplication.view.activity.doexercise.fragment

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.tta.fitnessapplication.R
import com.tta.fitnessapplication.databinding.FragmentPrepareBinding
import com.tta.fitnessapplication.view.activity.DayFullBody.ExerciseBottomSheetFragment
import com.tta.fitnessapplication.view.activity.doexercise.DoExerciseActivity
import com.tta.fitnessapplication.view.activity.doexercise.DoExerciseActivity.Companion.listExercise
import java.util.concurrent.TimeUnit

class PrepareFragment : Fragment() {
    companion object{
        var pause = false
    }
    private lateinit var binding : FragmentPrepareBinding
    private var timeToPause = 0
    var bottomSheetFragment = ExerciseQuitBottomSheetFragment()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPrepareBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var exercise = listExercise[0]
        val seconds = 20L // duration in seconds
        val duration = TimeUnit.SECONDS.toMillis(seconds)
        val timer = object: CountDownTimer(duration, TimeUnit.SECONDS.toMillis(1L)) {
            override fun onTick(millisUntilFinished: Long) {
                binding.tvCountDown.text = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished).toString()+"s"
                timeToPause = millisUntilFinished.toInt()
            }

            override fun onFinish() {
                val action  = PrepareFragmentDirections.actionPrepareFragmentToDoingExerciseFragment()
                findNavController().navigate(action)
            }
        }
        timer.start()

        binding.imgNext.setOnClickListener {
            val action  = PrepareFragmentDirections.actionPrepareFragmentToDoingExerciseFragment()
            findNavController().navigate(action)
            timer.cancel()
        }

        if (exercise!=null){
            binding.tvNameExercise.text = exercise.name
            Glide.with(requireActivity())
                .load(exercise.image)
                .error(R.drawable.alarm_clock)
                .into(binding.imgExercise);
            binding.imgShowInfo.setOnClickListener {
                pause = true
                timer.cancel()
                bottomSheetFragment.show(requireActivity().supportFragmentManager,bottomSheetFragment.tag)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (!pause){
            if (timeToPause!=0){
                val timer2 = object: CountDownTimer(timeToPause.toLong(), TimeUnit.SECONDS.toMillis(1L)) {
                    override fun onTick(millisUntilFinished: Long) {
                        binding.tvCountDown.text =  TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished).toString()+"s"
                        timeToPause = millisUntilFinished.toInt()
                    }

                    override fun onFinish() {
                        val action  = PrepareFragmentDirections.actionPrepareFragmentToDoingExerciseFragment()
                        findNavController().navigate(action)
                    }
                }
                timer2.start()
            }

        }
    }
}