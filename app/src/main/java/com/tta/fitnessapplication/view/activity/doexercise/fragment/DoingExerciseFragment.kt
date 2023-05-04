package com.tta.fitnessapplication.view.activity.doexercise.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.tta.fitnessapplication.data.utils.Logger
import com.tta.fitnessapplication.databinding.FragmentDoingexerciseBinding
import com.tta.fitnessapplication.view.activity.DayFullBody.DayFullBodyViewModel

class DoingExerciseFragment : Fragment() {
    private val viewModel = DayFullBodyViewModel()
    private var _binding: FragmentDoingexerciseBinding? = null
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
        binding.btnNext.setOnClickListener {
            Logger.logTest("${viewModel.dataExercise.value}")
            val action = DoingExerciseFragmentDirections.actionDoingExerciseFragmentToRestFragment()
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}