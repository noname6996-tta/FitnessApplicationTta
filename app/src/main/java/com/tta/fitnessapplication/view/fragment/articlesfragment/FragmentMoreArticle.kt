package com.tta.fitnessapplication.view.fragment.articlesfragment

import android.content.Intent
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tta.fitnessapplication.data.model.Article
import com.tta.fitnessapplication.databinding.FragmentMoreArticleBinding
import com.tta.fitnessapplication.view.activity.WebViewActivity
import com.tta.fitnessapplication.view.base.BaseFragment

class FragmentMoreArticle : BaseFragment<FragmentMoreArticleBinding>() {
    private val adapterArticle = ArticleAdapter()
    private var arrayArticle = ArrayList<Article>()
    override fun getDataBinding(): FragmentMoreArticleBinding {
        return FragmentMoreArticleBinding.inflate(layoutInflater)
    }

    override fun addEvent() {
        super.addEvent()
        binding.apply {
            viewback.setOnClickListener {
                findNavController().popBackStack()
            }
        }
        adapterArticle.setClickDeleteImage {
            var intent = Intent(requireContext(), WebViewActivity::class.java)
            intent.putExtra("url", arrayArticle[it].url)
            startActivity(intent)
        }
    }

    override fun initViewModel() {
        super.initViewModel()
        mainViewModel.getArticle()
    }

    override fun initView() {
        super.initView()
        binding.recArticles.adapter = adapterArticle
        val layoutManager1 =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.recArticles.layoutManager = layoutManager1
    }

    override fun addObservers() {
        super.addObservers()
        mainViewModel.listArticle.observe(viewLifecycleOwner) {
            arrayArticle.clear()
            arrayArticle.addAll(it)
            adapterArticle.setImageList(arrayArticle, requireContext())
        }
    }
}