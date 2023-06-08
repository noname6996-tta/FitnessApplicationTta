package com.tta.fitnessapplication.view.activity.HistoryActivity

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tta.fitnessapplication.data.utils.layoutInflater
import com.tta.fitnessapplication.databinding.HistoryItemViewBinding

class HistoryAdapter(val onClick: (Event) -> Unit) :
    RecyclerView.Adapter<HistoryAdapter.Example3EventsViewHolder>() {

    val events = mutableListOf<Event>()

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

        init {
            itemView.setOnClickListener {
                onClick(events[bindingAdapterPosition])
            }
        }

        fun bind(event: Event) {
            binding.itemEventText.text = event.text
        }
    }
}