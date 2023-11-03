package com.tta.fitnessapplication.view.fragment.articlesfragment

import android.content.Intent
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tta.fitnessapplication.data.model.Video
import com.tta.fitnessapplication.databinding.FragmentMoreVideoBinding
import com.tta.fitnessapplication.view.activity.WebViewActivity
import com.tta.fitnessapplication.view.base.BaseFragment

class FragmentMoreVideo : BaseFragment<FragmentMoreVideoBinding>() {
    private val adapterVideo = VideoAdapter()
    private var arrayVideo = ArrayList<Video>()
    override fun getDataBinding(): FragmentMoreVideoBinding {
        return FragmentMoreVideoBinding.inflate(layoutInflater)
    }

    override fun addEvent() {
        super.addEvent()
        binding.apply {
            viewback.setOnClickListener {
                findNavController().popBackStack()
            }
        }
        adapterVideo.setClickDeleteImage {
            var intent = Intent(requireContext(), WebViewActivity::class.java)
            intent.putExtra("url", arrayVideo[it].link)
            startActivity(intent)
        }
    }

    override fun initViewModel() {
        super.initViewModel()
        mainViewModel.getVideo()
    }

    override fun initView() {
        super.initView()
        binding.recyclerView.adapter = adapterVideo
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.layoutManager = layoutManager
    }

    override fun addObservers() {
        super.addObservers()
        mainViewModel.listVideo.observe(viewLifecycleOwner) {
            arrayVideo.clear()
            arrayVideo.addAll(it)
            adapterVideo.setImageList(arrayVideo, requireContext())
        }
    }
}