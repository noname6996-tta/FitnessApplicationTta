package com.tta.fitnessapplication.view.fragment.articlesfragment

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
import com.tta.fitnessapplication.view.base.BaseFragment

class HistoryFragment : Fragment() {
    private var _binding : FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private val adapterVideo = VideoAdapter()
    private val adapterArticle = ArticleAdapter()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHistoryBinding.inflate(inflater,container,false)
        binding.recyclerView.adapter = adapterVideo
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recArticles.adapter = adapterArticle
        binding.recArticles.layoutManager = GridLayoutManager(requireContext(), 2)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var arrayVideo = ArrayList<Video>()
        arrayVideo.add(Video(0,"demo1","https://www.youtube.com/watch?v=tu4HfcmMn1E&list=RD479nj6B6wAY&index=6","https://yt3.ggpht.com/lK_4iP2d9hyh3XwAOh49H1iGJZXPhIkTNObrP0CcJI22wtDy_cthZqYW94FEnCQfzSRSf0y22Q=s48-c-k-c0x00ffffff-no-nd-rj","cool"))
        arrayVideo.add(Video(0,"demo1","https://www.youtube.com/watch?v=tu4HfcmMn1E&list=RD479nj6B6wAY&index=6","https://yt3.ggpht.com/lK_4iP2d9hyh3XwAOh49H1iGJZXPhIkTNObrP0CcJI22wtDy_cthZqYW94FEnCQfzSRSf0y22Q=s48-c-k-c0x00ffffff-no-nd-rj","cool"))
        arrayVideo.add(Video(0,"demo1","https://www.youtube.com/watch?v=tu4HfcmMn1E&list=RD479nj6B6wAY&index=6","https://yt3.ggpht.com/lK_4iP2d9hyh3XwAOh49H1iGJZXPhIkTNObrP0CcJI22wtDy_cthZqYW94FEnCQfzSRSf0y22Q=s48-c-k-c0x00ffffff-no-nd-rj","cool"))
        arrayVideo.add(Video(0,"demo1","https://www.youtube.com/watch?v=tu4HfcmMn1E&list=RD479nj6B6wAY&index=6","https://yt3.ggpht.com/lK_4iP2d9hyh3XwAOh49H1iGJZXPhIkTNObrP0CcJI22wtDy_cthZqYW94FEnCQfzSRSf0y22Q=s48-c-k-c0x00ffffff-no-nd-rj","cool"))
        arrayVideo.add(Video(0,"demo1","https://www.youtube.com/watch?v=tu4HfcmMn1E&list=RD479nj6B6wAY&index=6","https://yt3.ggpht.com/lK_4iP2d9hyh3XwAOh49H1iGJZXPhIkTNObrP0CcJI22wtDy_cthZqYW94FEnCQfzSRSf0y22Q=s48-c-k-c0x00ffffff-no-nd-rj","cool"))
        arrayVideo.add(Video(0,"demo1","https://www.youtube.com/watch?v=tu4HfcmMn1E&list=RD479nj6B6wAY&index=6","https://yt3.ggpht.com/lK_4iP2d9hyh3XwAOh49H1iGJZXPhIkTNObrP0CcJI22wtDy_cthZqYW94FEnCQfzSRSf0y22Q=s48-c-k-c0x00ffffff-no-nd-rj","cool"))
        adapterVideo.setImageList(arrayVideo,requireContext())

        var arrayArticle = ArrayList<Article>()
        arrayArticle.add(Article(0,"tta reader","5 mins read","https://icdn.dantri.com.vn/zoom/300_200/2023/05/22/b9f30157647aba24e36b-crop-1684724294175.jpeg","https://dantri.com.vn/xa-hoi/can-giai-phap-manh-me-co-cau-lai-doanh-nghiep-thua-lo-kem-hieu-qua-20230523094746495.htm"))
        arrayArticle.add(Article(0,"tta reader","5 mins read","https://icdn.dantri.com.vn/zoom/300_200/2023/05/22/b9f30157647aba24e36b-crop-1684724294175.jpeg","https://dantri.com.vn/xa-hoi/can-giai-phap-manh-me-co-cau-lai-doanh-nghiep-thua-lo-kem-hieu-qua-20230523094746495.htm"))
        arrayArticle.add(Article(0,"tta reader","5 mins read","https://icdn.dantri.com.vn/zoom/300_200/2023/05/22/b9f30157647aba24e36b-crop-1684724294175.jpeg","https://dantri.com.vn/xa-hoi/can-giai-phap-manh-me-co-cau-lai-doanh-nghiep-thua-lo-kem-hieu-qua-20230523094746495.htm"))
        arrayArticle.add(Article(0,"tta reader","5 mins read","https://icdn.dantri.com.vn/zoom/300_200/2023/05/22/b9f30157647aba24e36b-crop-1684724294175.jpeg","https://dantri.com.vn/xa-hoi/can-giai-phap-manh-me-co-cau-lai-doanh-nghiep-thua-lo-kem-hieu-qua-20230523094746495.htm"))
        arrayArticle.add(Article(0,"tta reader","5 mins read","https://icdn.dantri.com.vn/zoom/300_200/2023/05/22/b9f30157647aba24e36b-crop-1684724294175.jpeg","https://dantri.com.vn/xa-hoi/can-giai-phap-manh-me-co-cau-lai-doanh-nghiep-thua-lo-kem-hieu-qua-20230523094746495.htm"))
        arrayArticle.add(Article(0,"tta reader","5 mins read","https://icdn.dantri.com.vn/zoom/300_200/2023/05/22/b9f30157647aba24e36b-crop-1684724294175.jpeg","https://dantri.com.vn/xa-hoi/can-giai-phap-manh-me-co-cau-lai-doanh-nghiep-thua-lo-kem-hieu-qua-20230523094746495.htm"))
        arrayArticle.add(Article(0,"tta reader","5 mins read","https://icdn.dantri.com.vn/zoom/300_200/2023/05/22/b9f30157647aba24e36b-crop-1684724294175.jpeg","https://dantri.com.vn/xa-hoi/can-giai-phap-manh-me-co-cau-lai-doanh-nghiep-thua-lo-kem-hieu-qua-20230523094746495.htm"))
        adapterArticle.setImageList(arrayArticle,requireContext())

        adapterArticle.setClickDeleteImage {
            var intent = Intent(requireContext(),WebViewActivity::class.java)
            intent.putExtra("url",arrayArticle[it].url)
            startActivity(intent)
        }

        adapterVideo.setClickDeleteImage {
            var intent = Intent(requireContext(),WebViewActivity::class.java)
            intent.putExtra("url",arrayVideo[it].url)
            startActivity(intent)
        }

        binding.cardView5.setOnClickListener {

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}