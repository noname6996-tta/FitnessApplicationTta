package com.tta.fitnessapplication.view.activity.doexercise.fragment

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.tta.fitnessapplication.databinding.FragmentRestexerciseBinding

class RestFragment : Fragment() {
    private var _binding: FragmentRestexerciseBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRestexerciseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val timer = object: CountDownTimer(20000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.tvCountDown.text = millisUntilFinished.toString()
            }

            override fun onFinish() {
                val action  = RestFragmentDirections.actionRestFragmentToDoingExerciseFragment()
                findNavController().navigate(action)
            }
        }
        timer.start()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}