package com.savebytes.campusbuddy.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.savebytes.campusbuddy.databinding.ItemChatBinding
import com.savebytes.campusbuddy.databinding.ItemSearchBarBinding
import com.savebytes.campusbuddy.domain.model.ChatItem

class ChatsAdapter(
    private val onChatClick: (ChatItem) -> Unit,
    private val onSearchClick: () -> Unit
) : ListAdapter<ChatItem, RecyclerView.ViewHolder>(ChatDiffCallback()) {

    companion object {
        private const val VIEW_TYPE_SEARCH = 0
        private const val VIEW_TYPE_CHAT = 1
    }

    private var showSearchBar = false

    override fun getItemCount(): Int {
        return if (showSearchBar) super.getItemCount() + 1 else super.getItemCount()
    }

    override fun getItemViewType(position: Int): Int {
        return if (showSearchBar && position == 0) VIEW_TYPE_SEARCH else VIEW_TYPE_CHAT
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            VIEW_TYPE_SEARCH -> {
                val binding = ItemSearchBarBinding.inflate(inflater, parent, false)
                SearchViewHolder(binding, onSearchClick)
            }
            else -> {
                val binding = ItemChatBinding.inflate(inflater, parent, false)
                ChatViewHolder(binding, onChatClick)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SearchViewHolder -> holder.bind()
            is ChatViewHolder -> {
                val actualPosition = if (showSearchBar) position - 1 else position
                holder.bind(getItem(actualPosition))
            }
        }
    }

    class SearchViewHolder(
        private val binding: ItemSearchBarBinding,
        private val onSearchClick: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            binding.root.setOnClickListener {
                onSearchClick()
            }
        }
    }

    class ChatViewHolder(
        private val binding: ItemChatBinding,
        private val onChatClick: (ChatItem) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(chat: ChatItem) = with(binding) {
            tvCommunityName.text = chat.communityName
            tvLastMessage.text = chat.lastMessage
            tvTime.text = chat.lastMessageTime

            if (chat.unreadCount > 0) {
                tvUnreadBadge.visibility = View.VISIBLE
                tvUnreadBadge.text = chat.unreadCount.toString()
            } else {
                tvUnreadBadge.visibility = View.GONE
            }

            Glide.with(root.context)
                .load(chat.communityAvatar)
                .circleCrop()
                .into(ivAvatar)

            root.setOnClickListener { onChatClick(chat) }
        }
    }

    class ChatDiffCallback : DiffUtil.ItemCallback<ChatItem>() {
        override fun areItemsTheSame(oldItem: ChatItem, newItem: ChatItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ChatItem, newItem: ChatItem): Boolean {
            return oldItem == newItem
        }
    }
}