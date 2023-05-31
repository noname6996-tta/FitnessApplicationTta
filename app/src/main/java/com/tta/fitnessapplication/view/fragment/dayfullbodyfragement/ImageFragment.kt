package com.tta.fitnessapplication.view.fragment.dayfullbodyfragement

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.tta.fitnessapplication.R
import com.tta.fitnessapplication.databinding.FragmentImageBinding

class ImageFragment : Fragment() {
    companion object {
        var image: String = ""
    }

    private lateinit var binding: FragmentImageBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentImageBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (image != "") {
            Glide.with(requireContext())
                .load(image)
                .error(R.drawable.alarm_clock)
                .into(binding.imgExercise)
        }

    }
}