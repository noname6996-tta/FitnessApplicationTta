package com.tta.fitnessapplication.view.activity.tracker.calortracker.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tta.fitnessapplication.R
import com.tta.fitnessapplication.data.model.Meal
import com.tta.fitnessapplication.databinding.ItemMealPlannerBinding

class ItemTodayMealAdapter :
    RecyclerView.Adapter<ItemTodayMealAdapter.ItemTodayMealViewHolder>() {
    private var listSomeThingToEat: List<Meal> = listOf()
    private lateinit var context: Context

    fun setListMeal(listSomeThingToEat: List<Meal> , context: Context) {
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

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemTodayMealViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMealPlannerBinding.inflate(inflater, parent, false)
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
            holder.binding.tvTimeMeal.text = someThingToEat.desc
            if (someThingToEat.enable == true)
            {
                holder.binding.imgMoreDetails.visibility = View.VISIBLE
                holder.binding.btnEat.visibility = View.GONE
            } else {
                holder.binding.imgMoreDetails.visibility = View.GONE
                holder.binding.btnEat.visibility = View.VISIBLE
            }
            holder.binding.imgMoreDetails.setOnClickListener {
                onClickSendData?.let {
                    it(someThingToEat.id)
                }
            }
            holder.binding.btnEat.setOnClickListener {
                onClickUpdateData?.let {
                    it(someThingToEat.id_task)
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return listSomeThingToEat.size
    }

    class ItemTodayMealViewHolder(
        val binding: ItemMealPlannerBinding
    ) : RecyclerView.ViewHolder(binding.root)
}