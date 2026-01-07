package com.savebytes.campusbuddy.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.savebytes.campusbuddy.R
import com.savebytes.campusbuddy.databinding.ItemSearchCommunityBinding
import com.savebytes.campusbuddy.databinding.ItemSearchHeaderBinding
import com.savebytes.campusbuddy.databinding.ItemSearchUserBinding
import com.savebytes.campusbuddy.domain.model.Community
import com.savebytes.campusbuddy.domain.model.UserProfile

sealed class SearchItem {
    data class Header(val title: String) : SearchItem()
    data class UserItem(val user: UserProfile) : SearchItem()
    data class CommunityItem(val community: Community) : SearchItem()
}

class SearchAdapter(
    private val onUserClick: (UserProfile) -> Unit,
    private val onCommunityClick: (Community) -> Unit
) : ListAdapter<SearchItem, RecyclerView.ViewHolder>(SearchDiffCallback()) {

    companion object {
        private const val VIEW_TYPE_HEADER = 0
        private const val VIEW_TYPE_USER = 1
        private const val VIEW_TYPE_COMMUNITY = 2
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is SearchItem.Header -> VIEW_TYPE_HEADER
            is SearchItem.UserItem -> VIEW_TYPE_USER
            is SearchItem.CommunityItem -> VIEW_TYPE_COMMUNITY
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        
        return when (viewType) {
            VIEW_TYPE_HEADER -> {
                val binding = ItemSearchHeaderBinding.inflate(inflater, parent, false)
                HeaderViewHolder(binding)
            }
            VIEW_TYPE_USER -> {
                val binding = ItemSearchUserBinding.inflate(inflater, parent, false)
                UserViewHolder(binding, onUserClick)
            }
            else -> {
                val binding = ItemSearchCommunityBinding.inflate(inflater, parent, false)
                CommunityViewHolder(binding, onCommunityClick)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is SearchItem.Header -> (holder as HeaderViewHolder).bind(item)
            is SearchItem.UserItem -> (holder as UserViewHolder).bind(item.user)
            is SearchItem.CommunityItem -> (holder as CommunityViewHolder).bind(item.community)
        }
    }

    class HeaderViewHolder(
        private val binding: ItemSearchHeaderBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(header: SearchItem.Header) {
            binding.tvHeader.text = header.title
        }
    }

    class UserViewHolder(
        private val binding: ItemSearchUserBinding,
        private val onUserClick: (UserProfile) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(user: UserProfile) = with(binding) {
            tvUserName.text = user.name
            tvUsername.text = user.username
            
            viewOnlineStatus.visibility = if (user.isOnline) View.VISIBLE else View.GONE

            Glide.with(root.context)
                .load(user.profileImageUrl)
                .circleCrop()
                .into(ivAvatar)

            root.setOnClickListener { onUserClick(user) }
        }
    }

    class CommunityViewHolder(
        private val binding: ItemSearchCommunityBinding,
        private val onCommunityClick: (Community) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(community: Community) = with(binding) {
            tvCommunityName.text = community.name
            tvDescription.text = community.description
            tvMemberCount.text = "${community.memberCount} members"

            Glide.with(root.context)
                .load(community.avatarUrl)
                .circleCrop()
                .into(ivAvatar)

            // Add tags
            chipGroup.removeAllViews()
            community.tags.take(2).forEach { tag ->
                val chip = Chip(root.context).apply {
                    text = tag
                    isClickable = false
                    isCheckable = false
                    setChipBackgroundColorResource(R.color.chip_background)
                    setTextColor(root.context.getColor(R.color.chip_text))
                    setTextAppearance(R.style.ChipStyle)
                }
                chipGroup.addView(chip)
            }

            root.setOnClickListener { onCommunityClick(community) }
        }
    }

    class SearchDiffCallback : DiffUtil.ItemCallback<SearchItem>() {
        override fun areItemsTheSame(oldItem: SearchItem, newItem: SearchItem): Boolean {
            return when {
                oldItem is SearchItem.Header && newItem is SearchItem.Header -> 
                    oldItem.title == newItem.title
                oldItem is SearchItem.UserItem && newItem is SearchItem.UserItem -> 
                    oldItem.user.username == newItem.user.username
                oldItem is SearchItem.CommunityItem && newItem is SearchItem.CommunityItem -> 
                    oldItem.community.id == newItem.community.id
                else -> false
            }
        }

        override fun areContentsTheSame(oldItem: SearchItem, newItem: SearchItem): Boolean {
            return oldItem == newItem
        }
    }
}