package com.tta.fitnessapplication.view.fragment.articlesfragment

import android.content.Intent
import androidx.recyclerview.widget.GridLayoutManager
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
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recArticles.adapter = adapterArticle
        binding.recArticles.layoutManager = GridLayoutManager(requireContext(), 2)
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
            arrayVideo.addAll(it)
            adapterVideo.setImageList(arrayVideo, requireContext())
        }
        mainViewModel.listArticle.observe(viewLifecycleOwner) {
            arrayArticle.clear()
            arrayArticle.addAll(it)
            adapterArticle.setImageList(arrayArticle, requireContext())
        }
    }
}