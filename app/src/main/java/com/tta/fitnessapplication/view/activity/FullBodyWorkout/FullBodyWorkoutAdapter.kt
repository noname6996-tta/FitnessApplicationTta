package com.tta.fitnessapplication.view.activity.FullBodyWorkout

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tta.fitnessapplication.R
import com.tta.fitnessapplication.data.model.Exercise
import com.tta.fitnessapplication.databinding.LayoutItemHomeworkoutBinding

class FullBodyWorkoutAdapter : RecyclerView.Adapter<FullBodyWorkoutAdapter.FullBodyWorkoutAdapterViewHolder>() {
    private var historysList: List<Exercise> = listOf()
    private lateinit var context: Context

    fun setListExercise(historysList: List<Exercise>, context: Context) {
        this.historysList = historysList
        this.context = context
        notifyDataSetChanged()
    }

    private var onClickSendData: ((i: Int) -> Unit)? = null
    fun setClcickSendData(position: ((i: Int) -> Unit)) {
        onClickSendData = position
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FullBodyWorkoutAdapterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LayoutItemHomeworkoutBinding.inflate(inflater, parent, false)
        return FullBodyWorkoutAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FullBodyWorkoutAdapterViewHolder, position: Int) {
        var history = historysList[position]
        if (history == null) {

        } else {
            Glide.with(context)
                .load(history.image)
                .error(R.drawable.alarm_clock)
                .into(holder.binding.imageView26);
            holder.binding.tvNameExercise.text = history.name
            if (history.type == "0") {
                holder.binding.tvTimesExercise.text = "00:${history.number}"
            } else if (history.type == "1") {
                holder.binding.tvTimesExercise.text = "x${history.number}"
            }
            holder.binding.imgShowInfo.setOnClickListener {
                onClickSendData?.let {
                    it(position)
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return historysList.size
    }

    class FullBodyWorkoutAdapterViewHolder(
        val binding: LayoutItemHomeworkoutBinding
    ) : RecyclerView.ViewHolder(binding.root) {}
}