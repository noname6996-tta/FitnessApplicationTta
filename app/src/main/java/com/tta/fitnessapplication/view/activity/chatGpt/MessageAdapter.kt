package com.tta.fitnessapplication.view.activity.chatGpt

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tta.fitnessapplication.R
import com.tta.fitnessapplication.view.activity.chatGpt.MessageAdapter.MyViewHolder

class MessageAdapter(var messageList: List<Message>) : RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val chatView =
            LayoutInflater.from(parent.context).inflate(R.layout.chat_item, null)
        return MyViewHolder(chatView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val message = messageList[position]
        if (message.sentBy == Message.SENT_BY_ME) {
            holder.leftChatView.visibility = View.GONE
            holder.rightChatView.visibility = View.VISIBLE
            holder.rightTextView.text = message.message
        } else {
            holder.rightChatView.visibility = View.GONE
            holder.leftChatView.visibility = View.VISIBLE
            holder.leftTextView.text = message.message
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var leftChatView: LinearLayout
        var rightChatView: LinearLayout
        var leftTextView: TextView
        var rightTextView: TextView

        init {
            leftChatView = itemView.findViewById(R.id.left_chat_view)
            rightChatView = itemView.findViewById(R.id.right_chat_view)
            leftTextView = itemView.findViewById(R.id.left_chat_text_view)
            rightTextView = itemView.findViewById(R.id.right_chat_text_view)
        }
    }
}