package com.tta.fitnessapplication.view.activity.tracker.calortracker.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tta.fitnessapplication.R
import com.tta.fitnessapplication.databinding.ItemSomethingToEatBinding

class ItemFindSomethingToEatAdapter :
    RecyclerView.Adapter<ItemFindSomethingToEatAdapter.ItemFindSomethingToEatViewHolder>() {
    private var listSomeThingToEat: List<SomeThingToEat> = listOf()
    private lateinit var context: Context

    fun setListExercise(listSomeThingToEat: List<SomeThingToEat>, context: Context) {
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
    ): ItemFindSomethingToEatViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSomethingToEatBinding.inflate(inflater, parent, false)
        return ItemFindSomethingToEatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemFindSomethingToEatViewHolder, position: Int) {
        var someThingToEat = listSomeThingToEat[position]
        if (someThingToEat == null) {

        } else {
            Glide.with(context)
                .load(someThingToEat.image)
                .error(R.drawable.ic_breafast)
                .into(holder.binding.imgSomethingToEat);
            holder.binding.tvTimeSomethingToEat.text = someThingToEat.name
            holder.binding.tvNumberSomethingToEat.text = someThingToEat.number
            var number  = 1
            when(position){
                0 -> number = 1
                1 -> number = 2
                2 -> number = 3
            }
            holder.binding.appCompatButton3.setOnClickListener {
                onClickSendData?.let {
                    it(number)
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return listSomeThingToEat.size
    }

    class ItemFindSomethingToEatViewHolder(
        val binding: ItemSomethingToEatBinding
    ) : RecyclerView.ViewHolder(binding.root)

    data class SomeThingToEat(var name: String, var number: String, var image: Int)
}