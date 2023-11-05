package com.tta.fitnessapplication.view.fragment.articlesfragment

import android.content.Intent
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tta.fitnessapplication.R
import com.tta.fitnessapplication.data.model.Article
import com.tta.fitnessapplication.data.model.Video
import com.tta.fitnessapplication.databinding.FragmentHistoryBinding
import com.tta.fitnessapplication.view.activity.WebViewActivity
import com.tta.fitnessapplication.view.activity.map.MapsActivity
import com.tta.fitnessapplication.view.base.BaseFragment

class ArticleFragment : BaseFragment<FragmentHistoryBinding>() {
    private val adapterVideo = VideoAdapter()
    private val adapterArticle = ArticleAdapter()
    private var arrayArticle = ArrayList<Article>()
    private var arrayVideo = ArrayList<Video>()
    override fun getDataBinding(): FragmentHistoryBinding {
        return FragmentHistoryBinding.inflate(layoutInflater)
    }

    override fun initView() {
        super.initView()
        binding.recyclerView.adapter = adapterVideo
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.layoutManager = layoutManager
        binding.recArticles.adapter = adapterArticle
        val layoutManager1 =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.recArticles.layoutManager = layoutManager1
    }

    override fun addEvent() {
        super.addEvent()
        adapterArticle.setClickDeleteImage {
            var intent = Intent(requireContext(), WebViewActivity::class.java)
            intent.putExtra("url", arrayArticle[it].url)
            startActivity(intent)
        }
        adapterVideo.setClickDeleteImage {
            var intent = Intent(requireContext(), WebViewActivity::class.java)
            intent.putExtra("url", arrayVideo[it].link)
            startActivity(intent)
        }
        binding.cardView5.setOnClickListener {
            var intent = Intent(requireContext(), MapsActivity::class.java)
            startActivity(intent)
        }
        binding.tvSeeMoreArticle.setOnClickListener {
            findNavController().navigate(R.id.action_historyFragment_to_fragmentMoreArticle)
        }
        binding.tvSeeMoreVideo.setOnClickListener {
            findNavController().navigate(R.id.action_historyFragment_to_fragmentMoreVideo)
        }
    }

    override fun initViewModel() {
        super.initViewModel()
        mainViewModel.getVideo()
        mainViewModel.getArticle()
    }

    override fun addObservers() {
        super.addObservers()
        mainViewModel.listVideo.observe(viewLifecycleOwner) {
            arrayVideo.clear()
            arrayVideo.addAll(it.take(5))
            adapterVideo.setImageList(arrayVideo, requireContext())
        }
        mainViewModel.listArticle.observe(viewLifecycleOwner) {
            arrayArticle.clear()
            arrayArticle.addAll(it.take(5))
            adapterArticle.setImageList(arrayArticle, requireContext())
        }
    }
}