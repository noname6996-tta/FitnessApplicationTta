package com.tta.fitnessapplication.view.activity.tracker.calortracker.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tta.fitnessapplication.R
import com.tta.fitnessapplication.data.model.Meal
import com.tta.fitnessapplication.databinding.ItemFoodRecommendBinding
import com.tta.fitnessapplication.databinding.ItemMealPlannerBinding

class ItemRecommendMealAdapter :
    RecyclerView.Adapter<ItemRecommendMealAdapter.ItemTodayMealViewHolder>() {
    private var listSomeThingToEat: List<Meal> = listOf()
    private lateinit var context: Context

    fun setListExercise(listSomeThingToEat: List<Meal> , context: Context) {
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
    ): ItemTodayMealViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFoodRecommendBinding.inflate(inflater, parent, false)
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
            holder.binding.tvTimeMeal.text = someThingToEat.time
            holder.binding.btnEat.setOnClickListener {
                onClickSendData?.let {
                    it(position)
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return listSomeThingToEat.size
    }

    class ItemTodayMealViewHolder(
        val binding: ItemFoodRecommendBinding
    ) : RecyclerView.ViewHolder(binding.root)
}