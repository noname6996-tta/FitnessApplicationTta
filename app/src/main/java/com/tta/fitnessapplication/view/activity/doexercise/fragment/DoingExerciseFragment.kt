package com.tta.fitnessapplication.view.activity.doexercise.fragment

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.tta.fitnessapplication.data.utils.Logger
import com.tta.fitnessapplication.databinding.FragmentDoingexerciseBinding
import com.tta.fitnessapplication.view.activity.DayFullBody.DayFullBodyViewModel
import com.tta.fitnessapplication.view.activity.doexercise.DoExerciseActivity.Companion.listExercise
import com.tta.fitnessapplication.view.activity.doexercise.DoExerciseActivity.Companion.numberExercise
import java.util.concurrent.TimeUnit

class DoingExerciseFragment : Fragment() {
    private val viewModel = DayFullBodyViewModel()
    private var _binding: FragmentDoingexerciseBinding? = null
    private var timeToPause = 0
    var bottomSheetFragment = ExerciseQuitBottomSheetFragment()
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDoingexerciseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("tta",numberExercise.toString()+listExercise[numberExercise])
        initUi()
        addEvent()

    }

    private fun initUi() {
        if (listExercise[numberExercise].type.toInt()==0){
            val seconds = listExercise[numberExercise].number.toLong()
            val duration = TimeUnit.SECONDS.toMillis(seconds)
            val timer = object: CountDownTimer(duration, TimeUnit.SECONDS.toMillis(1L)) {
                override fun onTick(millisUntilFinished: Long) {
                    binding.tvCountDown.text = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished).toString()+"s"
                    timeToPause = millisUntilFinished.toInt()
                }

                override fun onFinish() {
                    val action = DoingExerciseFragmentDirections.actionDoingExerciseFragmentToRestFragment()
                    findNavController().navigate(action)
                }
            }
            timer.start()
        } else {
            binding.tvCountDown.text = listExercise[numberExercise].number+"x"
        }

    }

    private fun addEvent() {
        binding.tvSkip.setOnClickListener {
            numberExercise++
            // check khi number == list.size thì hoàn thành bài tập
            Log.e("tta",numberExercise.toString()+listExercise[numberExercise])
            val action = DoingExerciseFragmentDirections.actionDoingExerciseFragmentToRestFragment()
            findNavController().navigate(action)
        }
        binding.tvPrevious.setOnClickListener {
            numberExercise--
            // check khi number == list.size thì hoàn thành bài tập
            Log.e("tta",numberExercise.toString()+listExercise[numberExercise])
            val action = DoingExerciseFragmentDirections.actionDoingExerciseFragmentToRestFragment()
            findNavController().navigate(action)
        }
        binding.btnPause.setOnClickListener {
            bottomSheetFragment.show(requireActivity().supportFragmentManager,bottomSheetFragment.tag)
        }
    }

//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
}