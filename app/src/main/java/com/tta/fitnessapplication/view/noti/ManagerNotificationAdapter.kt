package com.tta.fitnessapplication.view.noti;

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tta.fitnessapplication.R
import com.tta.fitnessapplication.data.model.noti.Notification
import com.tta.fitnessapplication.databinding.ItemNotificationBinding

class ManagerNotificationAdapter :
    RecyclerView.Adapter<ManagerNotificationAdapter.ItemTodayMealViewHolder>() {
    private var listCategoryFood: List<Notification> = listOf()
    private lateinit var context: Context

    fun setListNotification(listCategoryFood: List<Notification>, context: Context) {
        this.listCategoryFood = listCategoryFood
        this.context = context
        notifyDataSetChanged()
    }

    private var onClickSendData: ((i: Int) -> Unit)? = null
    fun editNoti(position: ((i: Int) -> Unit)) {
        onClickSendData = position
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemTodayMealViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemNotificationBinding.inflate(inflater, parent, false)
        return ItemTodayMealViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemTodayMealViewHolder, position: Int) {
        var notification = listCategoryFood[position]
        Glide.with(context)
            .load(notification.icon)
            .error(R.drawable.ic_breafast)
            .into(holder.binding.imgNoti)
        holder.binding.tvNameNotification.text = "${notification.hour}:${notification.min}"
        holder.binding.layoutNoti.setOnClickListener {
            onClickSendData?.let {
                it(position)
            }
        }
        holder.binding.switchNoti.isChecked = notification.enable

    }

    override fun getItemCount(): Int {
        return listCategoryFood.size
    }

    class ItemTodayMealViewHolder(
        val binding: ItemNotificationBinding
    ) : RecyclerView.ViewHolder(binding.root)
}
