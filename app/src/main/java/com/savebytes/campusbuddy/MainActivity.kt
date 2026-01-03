package com.savebytes.campusbuddy

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.savebytes.campusbuddy.databinding.ActivityMainBinding
import com.savebytes.campusbuddy.presentation.screens.OnboardingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

    private fun goToMain(){

    }
}