package com.tta.fitnessapplication.view.activity.doexercise.fragment

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.tta.fitnessapplication.databinding.FragmentPrepareBinding

class PrepareFragment : Fragment() {
    private lateinit var binding : FragmentPrepareBinding
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
        val timer = object: CountDownTimer(20000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.tvDemo.text = millisUntilFinished.toString()
            }

            override fun onFinish() {
                val action  = PrepareFragmentDirections.actionPrepareFragmentToDoingExerciseFragment()
                findNavController().navigate(action)
            }
        }
        timer.start()
    }
}