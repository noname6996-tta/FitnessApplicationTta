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

    private var onClickSendData: ((position: Notification, isChecked: Boolean) -> Unit)? = null
    fun editNotification(position: ((position: Notification, isChecked: Boolean) -> Unit)) {
        onClickSendData = position
    }

    private var onClickDeleteData: ((position: Notification) -> Unit)? = null
    fun deleteNotification(position: ((position: Notification) -> Unit)) {
        onClickDeleteData = position
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
        holder.binding.textView78.text = notification.text
        holder.binding.switchNoti.isChecked = notification.enable
        holder.binding.switchNoti.setOnCheckedChangeListener { buttonView, isChecked ->
            onClickSendData?.let {
                it(notification, isChecked)
            }
        }
        holder.binding.imgEditNoti.setOnClickListener {
            onClickDeleteData?.let {
                it(notification)
            }
        }
    }

    override fun getItemCount(): Int {
        return listCategoryFood.size
    }

    class ItemTodayMealViewHolder(
        val binding: ItemNotificationBinding
    ) : RecyclerView.ViewHolder(binding.root)
}
