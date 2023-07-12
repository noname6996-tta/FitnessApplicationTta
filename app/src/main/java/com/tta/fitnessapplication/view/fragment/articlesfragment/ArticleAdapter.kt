package com.tta.fitnessapplication.view.fragment.articlesfragment

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tta.fitnessapplication.data.model.Article
import com.tta.fitnessapplication.databinding.LayoutItemArticleBinding

class ArticleAdapter : RecyclerView.Adapter<ArticleAdapter.ArticleAdapterViewHolder>() {
    private var imageList: List<Article> = listOf()
    private lateinit var context: Context

    fun setImageList(imageList: List<Article>, context: Context) {
        this.imageList = imageList
        this.context = context
        notifyDataSetChanged()
    }

    private var onClickDeleteImage: ((i: Int) -> Unit)? = null
    fun setClickDeleteImage(position: ((i: Int) -> Unit)) {
        onClickDeleteImage = position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleAdapterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LayoutItemArticleBinding.inflate(inflater, parent, false)
        return ArticleAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleAdapterViewHolder, position: Int) {
        var image = imageList[position]
        Glide.with(context)
            .load(image.image)
            .into(holder.binding.imgArticle);
        holder.binding.btnRead.setOnClickListener {
            onClickDeleteImage?.let {
                it(position)
            }
        }

        holder.binding.tvNameArticle.text = image.name
        holder.binding.tvDescArticle.text = image.time
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    class ArticleAdapterViewHolder(
        val binding: LayoutItemArticleBinding
    ) : RecyclerView.ViewHolder(binding.root)
}