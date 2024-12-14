package com.intern.aifortodo.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.intern.aifortodo.R
import com.intern.aifortodo.ui.Message.Message

class ChatAdapter : RecyclerView.Adapter<ChatAdapter.MessageViewHolder>() {
    private val messages = mutableListOf<Message>()

    class MessageViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val leftContainer: LinearLayout = view.findViewById(R.id.leftMessageContainer)
        private val rightContainer: LinearLayout = view.findViewById(R.id.rightMessageContainer)
        private val leftMessageText: TextView = view.findViewById(R.id.leftMessageText)
        private val rightMessageText: TextView = view.findViewById(R.id.rightMessageText)

        fun bind(message: Message) {
            if (message.isFromAI) {
                // AI 消息显示在左侧
                leftContainer.visibility = View.VISIBLE
                rightContainer.visibility = View.GONE
                leftMessageText.text = message.content
            } else {
                // 用户消息显示在右侧
                leftContainer.visibility = View.GONE
                rightContainer.visibility = View.VISIBLE
                rightMessageText.text = message.content
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_message, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(messages[position])
    }

    override fun getItemCount() = messages.size

    fun addMessage(message: Message) {
        messages.add(message)
        notifyItemInserted(messages.size - 1)
    }
}
