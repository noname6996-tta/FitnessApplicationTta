package com.tta.fitnessapplication.view.fragment.dayfullbodyfragement

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tta.fitnessapplication.databinding.FragemntVideoBinding

class VideoFragment : Fragment(){
    private lateinit var binding : FragemntVideoBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragemntVideoBinding.inflate(layoutInflater)
        return binding.root
    }
}