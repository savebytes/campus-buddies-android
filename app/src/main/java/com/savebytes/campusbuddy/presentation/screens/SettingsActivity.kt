package com.savebytes.campusbuddy.presentation.screens

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.savebytes.campusbuddy.databinding.ActivitySettingsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
        setupClickListeners()
    }

    private fun setupViews() {
        // Set initial switch states (you can load from preferences)
        binding.switchPushNotifications.isChecked = true
        binding.switchMessageNotifications.isChecked = true
        binding.switchJoinRequestNotifications.isChecked = false
    }

    private fun setupClickListeners() {
        // Back button
        binding.ivBack.setOnClickListener {
            finish()
        }

        // Account Section
        binding.layoutEditProfile.setOnClickListener {
            navigateToEditProfile()
        }

        binding.layoutPrivacySecurity.setOnClickListener {
            navigateToPrivacySecurity()
        }

        // Notification Switches
        binding.switchPushNotifications.setOnCheckedChangeListener { _, isChecked ->
            handlePushNotifications(isChecked)
        }

        binding.switchMessageNotifications.setOnCheckedChangeListener { _, isChecked ->
            handleMessageNotifications(isChecked)
        }

        binding.switchJoinRequestNotifications.setOnCheckedChangeListener { _, isChecked ->
            handleJoinRequestNotifications(isChecked)
        }

        // Support Section
        binding.layoutHelpSupport.setOnClickListener {
            navigateToHelpSupport()
        }

        binding.layoutAbout.setOnClickListener {
            navigateToAbout()
        }
    }

    private fun navigateToEditProfile() {
        val intent = Intent(this, EditProfileScreen::class.java)
        startActivity(intent)
    }

    private fun navigateToPrivacySecurity() {
        // TODO: Navigate to Privacy & Security screen
        // val intent = Intent(this, PrivacySecurityActivity::class.java)
        // startActivity(intent)
    }

    private fun navigateToHelpSupport() {
        // TODO: Navigate to Help & Support screen
        // val intent = Intent(this, HelpSupportActivity::class.java)
        // startActivity(intent)
    }

    private fun navigateToAbout() {
        // TODO: Navigate to About screen
        // val intent = Intent(this, AboutActivity::class.java)
        // startActivity(intent)
    }

    private fun handlePushNotifications(isEnabled: Boolean) {
        // TODO: Save preference and update notification settings
        // If disabled, disable all other notifications too
        if (!isEnabled) {
            binding.switchMessageNotifications.isChecked = false
            binding.switchJoinRequestNotifications.isChecked = false
        }
    }

    private fun handleMessageNotifications(isEnabled: Boolean) {
        // TODO: Save preference
    }

    private fun handleJoinRequestNotifications(isEnabled: Boolean) {
        // TODO: Save preference
    }
}