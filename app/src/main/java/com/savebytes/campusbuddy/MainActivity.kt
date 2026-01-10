package com.savebytes.campusbuddy

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import com.savebytes.campusbuddy.databinding.ActivityMainBinding
import com.savebytes.campusbuddy.presentation.screens.ChatsFragment
import com.savebytes.campusbuddy.presentation.screens.CommunitiesFragment
import com.savebytes.campusbuddy.presentation.screens.ProfileFragment
import com.savebytes.campusbuddy.presentation.screens.SearchActivity
import com.savebytes.campusbuddy.presentation.screens.SettingsActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            // Load Communities fragment by default
            loadFragment(CommunitiesFragment())
        }

        setupMainHeader()
        setupBottomNavigation()
    }

    private fun setupMainHeader() {
        binding.mainHeader.headerTitle.text = "Communities"
        binding.mainHeader.searchBar.setOnClickListener {
            openSearchActivity()
        }

        binding.mainHeader.headerIcon.setOnClickListener {
            openSettingsActivity()
        }
    }

    fun openSettingsActivity() {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }

    private fun setupBottomNavigation() {
        binding.bottomNav.selectedItemId = R.id.nav_communities

        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_communities -> {
                    binding.mainHeader.headerTitle.text = "Communities"
                    binding.mainHeader.searchBar.visibility = View.VISIBLE
                    binding.mainHeader.headerIcon.visibility = View.GONE
                    loadFragment(CommunitiesFragment())
                    true
                }
                R.id.nav_chats -> {
                    binding.mainHeader.headerTitle.text = "Chats"
                    binding.mainHeader.searchBar.visibility = View.VISIBLE
                    binding.mainHeader.headerIcon.visibility = View.GONE
                    loadFragment(ChatsFragment())
                    true
                }
                R.id.nav_profile -> {
                    binding.mainHeader.headerTitle.text = "Profile"
                    binding.mainHeader.searchBar.visibility = View.GONE
                    binding.mainHeader.headerIcon.visibility = View.VISIBLE
                    loadFragment(ProfileFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    private fun openSearchActivity() {
        val intent = Intent(this, SearchActivity::class.java)
        startActivity(intent)
    }
}