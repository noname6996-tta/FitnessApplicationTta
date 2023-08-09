package com.tta.fitnessapplication.view.activity.HistoryActivity

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tta.fitnessapplication.R
import com.tta.fitnessapplication.data.model.History
import com.tta.fitnessapplication.data.utils.layoutInflater
import com.tta.fitnessapplication.databinding.HistoryItemViewBinding

class HistoryAdapter() : RecyclerView.Adapter<HistoryAdapter.Example3EventsViewHolder>() {

    val events = mutableListOf<History>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Example3EventsViewHolder {
        return Example3EventsViewHolder(
            HistoryItemViewBinding.inflate(parent.context.layoutInflater, parent, false),
        )
    }

    override fun onBindViewHolder(viewHolder: Example3EventsViewHolder, position: Int) {
        viewHolder.bind(events[position])
    }

    override fun getItemCount(): Int = events.size

    inner class Example3EventsViewHolder(private val binding: HistoryItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(event: History) {
            binding.tvActivity.text = event.activity
            binding.tvValue.text = event.value
            binding.tvDateTime.text = "${event.date} - ${event.time}"
            when (event.type) {
                0 -> binding.imgType.setImageResource(R.drawable.ic_logo)
                1 -> binding.imgType.setImageResource(R.drawable.ic_cup)
                2 -> binding.imgType.setImageResource(R.drawable.ic_sleep)
                3 -> binding.imgType.setImageResource(R.drawable.ic_water_caculate)
            }
        }
    }
}