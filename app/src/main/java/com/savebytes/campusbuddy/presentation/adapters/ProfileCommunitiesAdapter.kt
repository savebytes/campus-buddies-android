package com.savebytes.campusbuddy.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.savebytes.campusbuddy.databinding.ItemProfileCommunityBinding
import com.savebytes.campusbuddy.domain.model.Community

class ProfileCommunitiesAdapter(
    private val onCommunityClick: (Community) -> Unit
) : ListAdapter<Community, ProfileCommunitiesAdapter.CommunityViewHolder>(CommunityDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommunityViewHolder {
        val binding = ItemProfileCommunityBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CommunityViewHolder(binding, onCommunityClick)
    }

    override fun onBindViewHolder(holder: CommunityViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class CommunityViewHolder(
        private val binding: ItemProfileCommunityBinding,
        private val onCommunityClick: (Community) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(community: Community) = with(binding) {
            tvCommunityName.text = community.name
            tvMemberCount.text = "${community.memberCount} members"

            Glide.with(root.context)
                .load(community.avatarUrl)
                .fitCenter()
                .into(ivAvatar)

            root.setOnClickListener { onCommunityClick(community) }
            ivChevron.setOnClickListener { onCommunityClick(community) }
        }
    }

    class CommunityDiffCallback : DiffUtil.ItemCallback<Community>() {
        override fun areItemsTheSame(oldItem: Community, newItem: Community): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Community, newItem: Community): Boolean {
            return oldItem == newItem
        }
    }
}