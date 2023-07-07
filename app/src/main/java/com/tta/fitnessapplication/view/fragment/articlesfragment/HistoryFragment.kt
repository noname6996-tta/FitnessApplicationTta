package com.tta.fitnessapplication.view.fragment.articlesfragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.tta.fitnessapplication.data.model.Article
import com.tta.fitnessapplication.data.model.Video
import com.tta.fitnessapplication.databinding.FragmentHistoryBinding
import com.tta.fitnessapplication.view.activity.WebViewActivity
import com.tta.fitnessapplication.view.activity.chatGpt.ChatGPTActivity

class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private val adapterVideo = VideoAdapter()
    private val adapterArticle = ArticleAdapter()
    private val viewModel = ArticleViewModel()
    private var arrayArticle = ArrayList<Article>()
    private var arrayVideo = ArrayList<Video>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        binding.recyclerView.adapter = adapterVideo
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recArticles.adapter = adapterArticle
        binding.recArticles.layoutManager = GridLayoutManager(requireContext(), 2)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObsever()
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
            startActivity(Intent(requireContext(),ChatGPTActivity::class.java))
        }
    }

    private fun initObsever() {
        viewModel.getListVideo()
        viewModel.getListArticle()
        viewModel.listVideo.observe(viewLifecycleOwner) {
            arrayVideo.addAll(it)
            adapterVideo.setImageList(arrayVideo, requireContext())
        }
        viewModel.listArticle.observe(viewLifecycleOwner) {
            arrayArticle.addAll(it)
            adapterArticle.setImageList(arrayArticle, requireContext())
        }
        viewModel.message.observe(viewLifecycleOwner) {
            AlertDialog.Builder(requireContext())
                .setTitle("Thông báo")
                .setMessage(it)
                .setPositiveButton("OK",
                    DialogInterface.OnClickListener { _, _ ->
                    })
                .show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}