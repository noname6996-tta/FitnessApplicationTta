package com.tta.fitnessapplication.view.activity.tracker.calortracker.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tta.fitnessapplication.R
import com.tta.fitnessapplication.data.model.Food
import com.tta.fitnessapplication.data.model.Meal
import com.tta.fitnessapplication.databinding.ItemMealRecommendBinding

class ItemPopularFoodAdapter :
    RecyclerView.Adapter<ItemPopularFoodAdapter.ItemTodayMealViewHolder>() {
    private var listSomeThingToEat: List<Food> = listOf()
    private lateinit var context: Context

    fun setListMeal(listSomeThingToEat: List<Food>, context: Context) {
        this.listSomeThingToEat = listSomeThingToEat
        this.context = context
        notifyDataSetChanged()
    }

    private var onClickSendData: ((i: Int) -> Unit)? = null
    fun findSomethingToEat(position: ((i: Int) -> Unit)) {
        onClickSendData = position
    }

    private var onClickUpdateData: ((i: Int) -> Unit)? = null
    fun updateData(position: ((i: Int) -> Unit)) {
        onClickUpdateData = position
    }

    fun clearList() {
        listSomeThingToEat = emptyList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemTodayMealViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMealRecommendBinding.inflate(inflater, parent, false)
        return ItemTodayMealViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemTodayMealViewHolder, position: Int) {
        var someThingToEat = listSomeThingToEat[position]
        if (someThingToEat == null) {

        } else {
            Glide.with(context)
                .load(someThingToEat.image)
                .error(R.drawable.ic_breafast)
                .into(holder.binding.imgFood);
            holder.binding.tvNameMeal.text = someThingToEat.name
            holder.binding.tvMealDesc.text = someThingToEat.desc
            holder.binding.imgMoreDetails.setOnClickListener {
                onClickSendData?.let {
                    it(someThingToEat.id)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return listSomeThingToEat.size
    }

    class ItemTodayMealViewHolder(
        val binding: ItemMealRecommendBinding
    ) : RecyclerView.ViewHolder(binding.root)
}