package com.tta.fitnessapplication.view.activity.workout.FullBodyWorkout

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tta.fitnessapplication.R
import com.tta.fitnessapplication.data.model.Tool
import com.tta.fitnessapplication.data.utils.Constant.Companion.BASE_URL
import com.tta.fitnessapplication.databinding.LayoutItemToolBinding

class ToolAdapter : RecyclerView.Adapter<ToolAdapter.ToolAdapterViewHolder>() {
    private var historysList: List<Tool> = listOf()
    private lateinit var context: Context

    fun setListHistory(historysList: List<Tool>, context: Context) {
        this.historysList = historysList
        this.context = context
        notifyDataSetChanged()
    }

    private var onClickSendData: ((i: Int) -> Unit)? = null
    fun setClcickSendData(position: ((i: Int) -> Unit)) {
        onClickSendData = position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToolAdapterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LayoutItemToolBinding.inflate(inflater, parent, false)
        return ToolAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ToolAdapterViewHolder, position: Int) {
        var history = historysList[position]
        if (history == null) {

        } else {
            Glide.with(context)
                .load(BASE_URL+history.image)
                .error(R.drawable.alarm_clock)
                .into(holder.binding.imgTool);
            holder.binding.layoutTool.setOnClickListener {
                onClickSendData?.let {
                    it(position)
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return historysList.size
    }

    class ToolAdapterViewHolder(
        val binding: LayoutItemToolBinding
    ) : RecyclerView.ViewHolder(binding.root)
}