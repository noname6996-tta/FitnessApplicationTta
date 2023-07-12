package com.tta.fitnessapplication.view.fragment.articlesfragment

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tta.fitnessapplication.data.model.Video
import com.tta.fitnessapplication.databinding.LayoutItemVideoBinding

class VideoAdapter : RecyclerView.Adapter<VideoAdapter.VideoAdapterViewHolder>() {
    private var imageList: List<Video> = listOf()
    private lateinit var context: Context

    fun setImageList(imageList: List<Video>, context: Context) {
        this.imageList = imageList
        this.context = context
        notifyDataSetChanged()
    }

    private var onClickDeleteImage: ((i: Int) -> Unit)? = null
    fun setClickDeleteImage(position: ((i: Int) -> Unit)) {
        onClickDeleteImage = position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoAdapterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LayoutItemVideoBinding.inflate(inflater, parent, false)
        return VideoAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VideoAdapterViewHolder, position: Int) {
        val image = imageList[position]
        Glide.with(context)
            .load(image.image)
            .into(holder.binding.imgVideo)
        holder.binding.layoutItem.setOnClickListener {
            onClickDeleteImage?.let {
                it(position)
            }
        }

        holder.binding.tvVideoDesc.text = image.tittle
        holder.binding.tvVideoName.text = image.name
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    class VideoAdapterViewHolder(
        val binding: LayoutItemVideoBinding
    ) : RecyclerView.ViewHolder(binding.root)
}