package com.savebytes.campusbuddy.presentation.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.savebytes.campusbuddy.R
import androidx.recyclerview.widget.RecyclerView
import com.savebytes.campusbuddy.core.constants.AppColor
import com.savebytes.campusbuddy.databinding.ItemOnboardingPageBinding

class OnboardingPagerAdapter : RecyclerView.Adapter<OnboardingPagerAdapter.OnboardingViewHolder>() {

    private val pages = listOf(
        OnboardingPage(
            iconRes = R.drawable.ic_community, // Replace with your icon
            title = "Discover communities in your college",
            description = "Find and join communities that match your interests, courses, and passions.",
            imageBg = AppColor.onbImageIconBg1,
            iconColor = AppColor.White
        ),
        OnboardingPage(
            iconRes = R.drawable.ic_message, // Replace with your icon
            title = "Chat, share & collaborate",
            description = "Connect with fellow students, share resources, and build together in one place.",
            imageBg = AppColor.onbImageIconBg2,
            iconColor = AppColor.White
        ),
        OnboardingPage(
            iconRes = R.drawable.ic_shield, // Replace with your icon
            title = "Private, student-only communities",
            description = "Safe and secure spaces designed exclusively for college students.",
            imageBg = AppColor.onbImageIconBg3,
            iconColor = AppColor.White
        )
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingViewHolder {
        val binding = ItemOnboardingPageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return OnboardingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OnboardingViewHolder, position: Int) {
        holder.bind(pages[position])
    }

    override fun getItemCount(): Int = pages.size

    class OnboardingViewHolder(
        private val binding: ItemOnboardingPageBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(page: OnboardingPage) {
            val bgDrawable = binding.imageViewBg.background.mutate()
            bgDrawable.setTint(page.imageBg)
            binding.ivIcon.setImageResource(page.iconRes)
            binding.tvTitle.text = page.title
            binding.tvDescription.text = page.description
        }
    }
}

data class OnboardingPage(
    val iconRes: Int,
    val title: String,
    val description: String,
    val iconColor: Int = Color.WHITE,
    val imageBg: Int = Color.WHITE,
)

