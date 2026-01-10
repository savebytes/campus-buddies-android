package com.savebytes.campusbuddy.presentation.screens

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.savebytes.campusbuddy.databinding.FragmentProfileBinding
import com.savebytes.campusbuddy.domain.model.Community
import com.savebytes.campusbuddy.domain.model.UserProfile
import com.savebytes.campusbuddy.presentation.adapters.ProfileCommunitiesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var createdCommunitiesAdapter: ProfileCommunitiesAdapter
    private lateinit var joinedCommunitiesAdapter: ProfileCommunitiesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        setupRecyclerViews()
        loadUserProfile()
    }

    private fun setupViews() {
//        binding.ivSettings.setOnClickListener {
//            // Navigate to settings
//            val intent = Intent(requireContext(), SettingsActivity::class.java)
//            startActivity(intent)
//        }

        binding.btnLogout.setOnClickListener {
            handleLogout()
        }
    }

    private fun loadUserProfile() {
        // Sample user data - replace with actual data
        val userProfile = UserProfile(
            name = "Alex Chen",
            username = "@alex_chen",
            course = "Computer Science",
            college = "MIT",
            profileImageUrl = "https://images.unsplash.com/photo-1494790108377-be9c29b29330?q=80&w=1887&auto=format&fit=crop",
            isOnline = true
        )

        with(binding) {
            tvUserName.text = userProfile.name
            tvUserHandle.text = userProfile.username
            tvCourse.text = userProfile.course
            tvCollege.text = userProfile.college

            viewOnlineStatus.visibility = if (userProfile.isOnline) View.VISIBLE else View.GONE

            Glide.with(requireContext())
                .load(userProfile.profileImageUrl)
                .circleCrop()
                .into(ivProfileImage)
        }

        loadCreatedCommunities()
        loadJoinedCommunities()
    }

    private fun setupRecyclerViews() {
        createdCommunitiesAdapter = ProfileCommunitiesAdapter { community ->
            onCommunityClick(community)
        }

        joinedCommunitiesAdapter = ProfileCommunitiesAdapter { community ->
            onCommunityClick(community)
        }

        binding.rvCreatedCommunities.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = createdCommunitiesAdapter
            isNestedScrollingEnabled = false
        }

        binding.rvJoinedCommunities.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = joinedCommunitiesAdapter
            isNestedScrollingEnabled = false
        }
    }

    private fun loadCreatedCommunities() {
        val createdCommunities = listOf(
            Community(
                id = "2",
                name = "Campus Coders",
                description = "For developers building projects and sharing knowledge",
                imageUrl = "",
                avatarUrl = "https://images.unsplash.com/photo-1556761175-4b46a572b786?q=80&w=2074&auto=format&fit=crop",
                tags = listOf("Coding", "Projects", "Hackathons"),
                memberCount = 78,
                isJoined = true
            )
        )

        binding.tvCreatedCount.text = createdCommunities.size.toString()
        createdCommunitiesAdapter.submitList(createdCommunities)
    }

    private fun loadJoinedCommunities() {
        val joinedCommunities = listOf(
            Community(
                id = "1",
                name = "CS Study Group",
                description = "Collaborative learning space for Computer Science students",
                imageUrl = "",
                avatarUrl = "https://plus.unsplash.com/premium_photo-1685086785054-d047cdc0e525?q=80&w=2532&auto=format&fit=crop",
                tags = listOf("Computer Science", "Study", "Programming"),
                memberCount = 45,
                isJoined = true
            ),
            Community(
                id = "2",
                name = "Campus Coders",
                description = "For developers building projects and sharing knowledge",
                imageUrl = "",
                avatarUrl = "https://images.unsplash.com/photo-1556761175-4b46a572b786?q=80&w=2074&auto=format&fit=crop",
                tags = listOf("Coding", "Projects", "Hackathons"),
                memberCount = 78,
                isJoined = true
            ),
            Community(
                id = "3",
                name = "Engineering Society",
                description = "Connecting engineering students across all disciplines",
                imageUrl = "",
                avatarUrl = "https://images.unsplash.com/photo-1581092918056-0c4c3acd3789?q=80&w=2070&auto=format&fit=crop",
                tags = listOf("Engineering", "Networking", "Events"),
                memberCount = 120,
                isJoined = true
            ),
            Community(
                id = "4",
                name = "Startup Club",
                description = "For entrepreneurs and innovators building the next big thing",
                imageUrl = "",
                avatarUrl = "https://images.unsplash.com/photo-1556761175-5973dc0f32e7?q=80&w=2074&auto=format&fit=crop",
                tags = listOf("Startups", "Business", "Innovation"),
                memberCount = 34,
                isJoined = true
            )
        )

        binding.tvJoinedCount.text = joinedCommunities.size.toString()
        joinedCommunitiesAdapter.submitList(joinedCommunities)
    }

    private fun onCommunityClick(community: Community) {
        // Navigate to community details
    }

    private fun handleLogout() {
        // Handle logout logic
        // Clear user session
        // Navigate to login screen
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}