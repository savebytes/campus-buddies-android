package com.savebytes.campusbuddy.presentation.screens

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.savebytes.campusbuddy.databinding.FragmentCommunitiesBinding
import com.savebytes.campusbuddy.domain.model.Community
import com.savebytes.campusbuddy.presentation.adapters.CommunitiesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommunitiesFragment : Fragment() {

    private var _binding: FragmentCommunitiesBinding? = null
    private val binding get() = _binding!!

    private lateinit var communitiesAdapter: CommunitiesAdapter
    private var allCommunities = listOf<Community>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCommunitiesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        loadCommunities()
    }

    private fun setupRecyclerView() {
        communitiesAdapter = CommunitiesAdapter(
            onCommunityClick = { community -> onCommunityClick(community) },
            onSearchClick = { openSearchActivity() }
        )

        binding.rvCommunities.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = communitiesAdapter
            setHasFixedSize(true)
        }
    }

    private fun loadCommunities() {
        allCommunities = listOf(
            Community(
                id = "1",
                name = "CS Study Group",
                description = "Collaborative learning space for Computer Science students",
                imageUrl = "https://images.unsplash.com/photo-1610563166150-b34df4f3bcd6?q=80&w=1976&auto=format&fit=crop",
                avatarUrl = "https://plus.unsplash.com/premium_photo-1685086785054-d047cdc0e525?q=80&w=2532&auto=format&fit=crop",
                tags = listOf("Computer Science", "Study", "Programming"),
                memberCount = 45,
                isJoined = true
            ),
            Community(
                id = "2",
                name = "Campus Coders",
                description = "For developers building projects and sharing knowledge",
                imageUrl = "https://images.unsplash.com/photo-1510915228340-29c85a43dcfe?q=80&w=2070&auto=format&fit=crop",
                avatarUrl = "https://plus.unsplash.com/premium_photo-1764687956131-b0db3dee20ef?q=80&w=2140&auto=format&fit=crop",
                tags = listOf("Coding", "Projects", "Hackathons"),
                memberCount = 78,
                isJoined = true
            ),
            Community(
                id = "3",
                name = "Engineering Society",
                description = "Connecting engineering students across all disciplines",
                imageUrl = "https://images.unsplash.com/photo-1581092160562-40aa08e78837?q=80&w=2070&auto=format&fit=crop",
                avatarUrl = "https://images.unsplash.com/photo-1581092918056-0c4c3acd3789?q=80&w=2070&auto=format&fit=crop",
                tags = listOf("Engineering", "Networking", "Events"),
                memberCount = 120,
                isJoined = false
            ),
            Community(
                id = "4",
                name = "Startup Club",
                description = "For entrepreneurs and innovators building the next big thing",
                imageUrl = "https://images.unsplash.com/photo-1556761175-4b46a572b786?q=80&w=2074&auto=format&fit=crop",
                avatarUrl = "https://images.unsplash.com/photo-1556761175-5973dc0f32e7?q=80&w=2074&auto=format&fit=crop",
                tags = listOf("Startups", "Business", "Innovation"),
                memberCount = 34,
                isJoined = false
            )
        )

        communitiesAdapter.submitList(allCommunities)
    }

    private fun openSearchActivity() {
        val intent = Intent(requireContext(), SearchActivity::class.java)
        startActivity(intent)
    }

    private fun onCommunityClick(community: Community) {
        // Navigate to community details
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}