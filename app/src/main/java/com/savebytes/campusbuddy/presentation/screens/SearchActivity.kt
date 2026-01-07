package com.savebytes.campusbuddy.presentation.screens

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.savebytes.campusbuddy.databinding.ActivitySearchBinding
import com.savebytes.campusbuddy.domain.model.Community
import com.savebytes.campusbuddy.domain.model.UserProfile
import com.savebytes.campusbuddy.presentation.adapters.SearchAdapter
import com.savebytes.campusbuddy.presentation.adapters.SearchItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var searchAdapter: SearchAdapter

    private var allUsers = listOf<UserProfile>()
    private var allCommunities = listOf<Community>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
        setupRecyclerView()
        loadData()
        setupSearch()
    }

    private fun setupViews() {
        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.ivClear.setOnClickListener {
            binding.etSearch.text?.clear()
        }

        binding.etSearch.requestFocus()
    }

    private fun setupRecyclerView() {
        searchAdapter = SearchAdapter(
            onUserClick = { user -> handleUserClick(user) },
            onCommunityClick = { community -> handleCommunityClick(community) }
        )

        binding.rvSearchResults.apply {
            layoutManager = LinearLayoutManager(this@SearchActivity)
            adapter = searchAdapter
            setHasFixedSize(false)
        }
    }

    private fun loadData() {
        // Load sample users
        allUsers = listOf(
            UserProfile(
                name = "Sarah",
                username = "@sarah_miller",
                course = "Computer Science",
                college = "MIT",
                profileImageUrl = "https://images.unsplash.com/photo-1494790108377-be9c29b29330?q=80&w=1887",
                isOnline = true
            ),
            UserProfile(
                name = "Mike",
                username = "@mike_johnson",
                course = "Engineering",
                college = "MIT",
                profileImageUrl = "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?q=80&w=1887",
                isOnline = false
            ),
            UserProfile(
                name = "Emma",
                username = "@emma_davis",
                course = "Computer Science",
                college = "MIT",
                profileImageUrl = "https://images.unsplash.com/photo-1438761681033-6461ffad8d80?q=80&w=1887",
                isOnline = true
            ),
            UserProfile(
                name = "Ryan",
                username = "@ryan_wilson",
                course = "Business",
                college = "MIT",
                profileImageUrl = "https://images.unsplash.com/photo-1500648767791-00dcc994a43e?q=80&w=1887",
                isOnline = false
            )
        )

        // Load sample communities
        allCommunities = listOf(
            Community(
                id = "1",
                name = "CS Study Group",
                description = "Collaborative learning space for Computer Science students",
                imageUrl = "https://images.unsplash.com/photo-1610563166150-b34df4f3bcd6?q=80&w=1976",
                avatarUrl = "https://plus.unsplash.com/premium_photo-1685086785054-d047cdc0e525?q=80&w=2532",
                tags = listOf("Computer Science", "Study"),
                memberCount = 45,
                isJoined = true
            ),
            Community(
                id = "2",
                name = "Campus Coders",
                description = "For developers building projects and sharing knowledge",
                imageUrl = "https://images.unsplash.com/photo-1510915228340-29c85a43dcfe?q=80&w=2070",
                avatarUrl = "https://images.unsplash.com/photo-1556761175-4b46a572b786?q=80&w=2074",
                tags = listOf("Coding", "Projects"),
                memberCount = 78,
                isJoined = true
            ),
            Community(
                id = "3",
                name = "Engineering Society",
                description = "Connect with fellow engineering students",
                imageUrl = "https://images.unsplash.com/photo-1581092160562-40aa08e78837?q=80&w=2070",
                avatarUrl = "https://images.unsplash.com/photo-1581092918056-0c4c3acd3789?q=80&w=2070",
                tags = listOf("Engineering", "Networking"),
                memberCount = 120,
                isJoined = false
            ),
            Community(
                id = "4",
                name = "ML Enthusiasts",
                description = "Machine Learning discussions, resources, and projects",
                imageUrl = "https://images.unsplash.com/photo-1677442136019-21780ecad995?q=80&w=2070",
                avatarUrl = "https://images.unsplash.com/photo-1620712943543-bcc4688e7485?q=80&w=2070",
                tags = listOf("Machine Learning", "AI"),
                memberCount = 62,
                isJoined = false
            ),
            Community(
                id = "5",
                name = "Startup Club",
                description = "For aspiring entrepreneurs and startup enthusiasts",
                imageUrl = "https://images.unsplash.com/photo-1556761175-4b46a572b786?q=80&w=2074",
                avatarUrl = "https://images.unsplash.com/photo-1556761175-5973dc0f32e7?q=80&w=2074",
                tags = listOf("Startups", "Business"),
                memberCount = 34,
                isJoined = false
            )
        )

        // Show initial state with all items
        showAllItems()
    }

    private fun setupSearch() {
        binding.etSearch.doAfterTextChanged { text ->
            val query = text?.toString()?.trim() ?: ""

            if (query.isEmpty()) {
                showAllItems()
            } else {
                performSearch(query)
            }

            // Show/hide clear button
            binding.ivClear.visibility = if (query.isEmpty()) View.GONE else View.VISIBLE
        }
    }

    private fun showAllItems() {
        val items = mutableListOf<SearchItem>()

        // Add Users section
        if (allUsers.isNotEmpty()) {
            items.add(SearchItem.Header("Users"))
            items.addAll(allUsers.map { SearchItem.UserItem(it) })
        }

        // Add Communities section
        if (allCommunities.isNotEmpty()) {
            items.add(SearchItem.Header("Communities"))
            items.addAll(allCommunities.map { SearchItem.CommunityItem(it) })
        }

        searchAdapter.submitList(items)
        updateEmptyState(false)
    }

    private fun performSearch(query: String) {
        val filteredUsers = allUsers.filter {
            it.name.contains(query, ignoreCase = true) ||
                    it.username.contains(query, ignoreCase = true) ||
                    it.course.contains(query, ignoreCase = true)
        }

        val filteredCommunities = allCommunities.filter {
            it.name.contains(query, ignoreCase = true) ||
                    it.description.contains(query, ignoreCase = true) ||
                    it.tags.any { tag -> tag.contains(query, ignoreCase = true) }
        }

        val items = mutableListOf<SearchItem>()

        // Add filtered users
        if (filteredUsers.isNotEmpty()) {
            items.add(SearchItem.Header("Users"))
            items.addAll(filteredUsers.map { SearchItem.UserItem(it) })
        }

        // Add filtered communities
        if (filteredCommunities.isNotEmpty()) {
            items.add(SearchItem.Header("Communities"))
            items.addAll(filteredCommunities.map { SearchItem.CommunityItem(it) })
        }

        searchAdapter.submitList(items)
        updateEmptyState(items.isEmpty())
    }

    private fun updateEmptyState(isEmpty: Boolean) {
        if (isEmpty) {
            binding.rvSearchResults.visibility = View.GONE
            binding.emptyStateLayout.visibility = View.VISIBLE
        } else {
            binding.rvSearchResults.visibility = View.VISIBLE
            binding.emptyStateLayout.visibility = View.GONE
        }
    }

    private fun handleUserClick(user: UserProfile) {
        // Navigate to user profile
        // TODO: Implement navigation
    }

    private fun handleCommunityClick(community: Community) {
        // Navigate to community details
        // TODO: Implement navigation
    }
}