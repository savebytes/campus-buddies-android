package com.savebytes.campusbuddy

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.savebytes.campusbuddy.databinding.ActivityMainBinding
import com.savebytes.campusbuddy.domain.model.Community
import com.savebytes.campusbuddy.presentation.adapters.CommunitiesAdapter
import com.savebytes.campusbuddy.presentation.screens.OnboardingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var communitiesAdapter: CommunitiesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        setupRecyclerView()
        setupBottomNavigation()
        loadCommunities()

    }

    private fun initViews() {

    }

    private fun setupRecyclerView() {
        communitiesAdapter = CommunitiesAdapter { community ->
            onCommunityClick(community)
        }

        binding.rvCommunities.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = communitiesAdapter
            setHasFixedSize(true)
        }
    }

    private fun setupBottomNavigation() {
        binding.bottomNav.selectedItemId = R.id.nav_communities

        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_communities -> true
                R.id.nav_chats -> {
                    // Navigate to Chats
                    true
                }
                R.id.nav_profile -> {
                    // Navigate to Profile
                    true
                }
                else -> false
            }
        }
    }

    private fun loadCommunities() {
        // Sample data - replace with actual data from your repository/API
        val communities = listOf(
            Community(
                id = "1",
                name = "CS Study Group",
                description = "Collaborative learning space for Computer Science students",
                imageUrl = "https://images.unsplash.com/photo-1610563166150-b34df4f3bcd6?q=80&w=1976&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                avatarUrl = "https://plus.unsplash.com/premium_photo-1685086785054-d047cdc0e525?q=80&w=2532&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                tags = listOf("Computer Science", "Study", "Programming"),
                memberCount = 45,
                isJoined = true
            ),
            Community(
                id = "2",
                name = "Campus Coders",
                description = "For developers building projects and sharing knowledge",
                imageUrl = "https://images.unsplash.com/photo-1510915228340-29c85a43dcfe?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                avatarUrl = "https://plus.unsplash.com/premium_photo-1764687956131-b0db3dee20ef?q=80&w=2140&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                tags = listOf("Coding", "Projects", "Hackathons"),
                memberCount = 78,
                isJoined = true
            )
        )

        communitiesAdapter.submitList(communities)
    }


    private fun onCommunityClick(community: Community) {
        // Navigate to community details
        // startActivity(Intent(this, CommunityDetailsActivity::class.java).apply {
        //     putExtra("COMMUNITY_ID", community.id)
        // })
    }




    private fun debugInfo(){
        // Check build type
        if (BuildConfig.DEBUG) {
            Log.d("MyApp", "Debug mode")
        }

        binding.root.setOnClickListener{
            val intent = Intent(this, OnboardingActivity::class.java)
            startActivity(intent)
        }

        // Access custom fields
        val apiUrl = BuildConfig.BASE_URL
        val googleClientId = BuildConfig.GOOGLE_WEB_CLIENT_ID
        val loggingEnabled = BuildConfig.ENABLE_LOGGING

        Log.d("Config", "API: $apiUrl, Logging: $loggingEnabled, Google Client ID: $googleClientId")
    }
}