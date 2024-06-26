package com.tta.fitnessapplication.view.activity.tracker.calortracker.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tta.fitnessapplication.R
import com.tta.fitnessapplication.data.model.Food
import com.tta.fitnessapplication.databinding.ItemFoodRecommendBinding

class ItemRecommendFoodAdapter :
    RecyclerView.Adapter<ItemRecommendFoodAdapter.ItemTodayFoodViewHolder>() {
    private var listSomeThingToEat: List<Food> = listOf()
    private lateinit var context: Context

    fun setListExercise(listSomeThingToEat: List<Food>, context: Context) {
        this.listSomeThingToEat = listSomeThingToEat
        this.context = context
        notifyDataSetChanged()
    }

    private var onClickSendData: ((i: Int) -> Unit)? = null
    fun findSomethingToEat(position: ((i: Int) -> Unit)) {
        onClickSendData = position
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemTodayFoodViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFoodRecommendBinding.inflate(inflater, parent, false)
        return ItemTodayFoodViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemTodayFoodViewHolder, position: Int) {
        var someThingToEat = listSomeThingToEat[position]
        if (someThingToEat == null) {

        } else {
            Glide.with(context)
                .load(someThingToEat.image)
                .error(R.drawable.ic_breafast)
                .into(holder.binding.imageView40);
            holder.binding.tvNameFood.text = someThingToEat.name
            holder.binding.tvInfoFood.text = someThingToEat.desc
            holder.binding.appCompatButton3.setOnClickListener {
                onClickSendData?.let {
                    it(someThingToEat.id)
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return listSomeThingToEat.size
    }

    class ItemTodayFoodViewHolder(
        val binding: ItemFoodRecommendBinding
    ) : RecyclerView.ViewHolder(binding.root)
}