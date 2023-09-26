package com.tta.fitnessapplication.view.activity.tracker.calortracker.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tta.fitnessapplication.R
import com.tta.fitnessapplication.data.model.CategoryFood
import com.tta.fitnessapplication.databinding.ItemCategoryBinding

class CategoryMealAdapter :
    RecyclerView.Adapter<CategoryMealAdapter.ItemTodayMealViewHolder>() {
    private var listCategoryFood: List<CategoryFood> = listOf()
    private lateinit var context: Context

    fun setListCategoryFood(listCategoryFood: List<CategoryFood>, context: Context) {
        this.listCategoryFood = listCategoryFood
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
    ): ItemTodayMealViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCategoryBinding.inflate(inflater, parent, false)
        return ItemTodayMealViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemTodayMealViewHolder, position: Int) {
        var someThingToEat = listCategoryFood[position]
        if (someThingToEat == null) {

        } else {
            Glide.with(context)
                .load(someThingToEat.icon)
                .error(R.drawable.ic_breafast)
                .into(holder.binding.imgCategory);
            holder.binding.tvNameCategory.text = someThingToEat.name
            holder.binding.clItemCategory.setOnClickListener {
                onClickSendData?.let {
                    it(position)
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return listCategoryFood.size
    }

    class ItemTodayMealViewHolder(
        val binding: ItemCategoryBinding
    ) : RecyclerView.ViewHolder(binding.root)
}