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
import com.savebytes.campusbuddy.databinding.ItemCommunityCardBinding
import com.savebytes.campusbuddy.databinding.ItemSearchBarBinding
import com.savebytes.campusbuddy.domain.model.Community

class CommunitiesAdapter(
    private val onCommunityClick: (Community) -> Unit
) : ListAdapter<Community, RecyclerView.ViewHolder>(CommunityDiffCallback()) {

    companion object {
        private const val VIEW_TYPE_SEARCH = 0
        private const val VIEW_TYPE_COMMUNITY = 1
    }

    private var showSearchBar = true

    override fun getItemCount(): Int {
        return if (showSearchBar) super.getItemCount() + 1 else super.getItemCount()
    }

    override fun getItemViewType(position: Int): Int {
        return if (showSearchBar && position == 0) VIEW_TYPE_SEARCH else VIEW_TYPE_COMMUNITY
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            VIEW_TYPE_SEARCH -> {
                val binding = ItemSearchBarBinding.inflate(inflater, parent, false)
                SearchViewHolder(binding)
            }
            else -> {
                val binding = ItemCommunityCardBinding.inflate(inflater, parent, false)
                CommunityCardViewHolder(binding, onCommunityClick)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SearchViewHolder -> holder.bind()
            is CommunityCardViewHolder -> {
                val actualPosition = if (showSearchBar) position - 1 else position
                holder.bind(getItem(actualPosition))
            }
        }
    }

    // -------------------------
    // ViewHolders
    // -------------------------

    class SearchViewHolder(
        private val binding: ItemSearchBarBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            binding.root.setOnClickListener {
                // Open search / expand search
            }
        }
    }

    class CommunityCardViewHolder(
        private val binding: ItemCommunityCardBinding,
        private val onCommunityClick: (Community) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(community: Community) = with(binding) {
            tvCommunityName.text = community.name
            tvDescription.text = community.description
            tvMemberCount.text = "${community.memberCount} members"

            cvJoinedBadge.visibility =
                if (community.isJoined) View.VISIBLE else View.GONE

            // Image loading (example)
            Glide.with(root.context).load(community.imageUrl).into(ivCommunityBanner)
            Glide.with(root.context).load(community.avatarUrl).into(ivAvatar)

            // Tags
            chipGroup.removeAllViews()
            community.tags.forEach { tag ->
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
            tvViewDetails.setOnClickListener { onCommunityClick(community) }
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
