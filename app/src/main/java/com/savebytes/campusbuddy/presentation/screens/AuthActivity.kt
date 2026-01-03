package com.savebytes.campusbuddy.presentation.screens

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.savebytes.campusbuddy.MainActivity
import com.savebytes.campusbuddy.R
import com.savebytes.campusbuddy.databinding.ActivityAuthBinding
import com.savebytes.campusbuddy.presentation.dialogs.UsernameBottomSheet

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // This will adjust the screen when keyboard appears
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        setupClickListeners()
        setupKeyboardHandling()
    }

    private fun setupClickListeners() {
        binding.btnGoogleSignIn.setOnClickListener {
            // Handle Google Sign In
            showUsernameBottomSheet()
            handleGoogleSignIn()
                // startActivity(Intent(this, MainActivity::class.java))
        }

        binding.btnEmailSignIn.setOnClickListener {
            // Handle Email Sign In
            handleEmailSignIn()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun setupKeyboardHandling() {
        // Handle window insets for better keyboard behavior
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
            val imeInsets = insets.getInsets(WindowInsetsCompat.Type.ime())
            val systemBarsInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            // Add bottom padding when keyboard is visible
            view.setPadding(
                view.paddingLeft,
                view.paddingTop,
                view.paddingRight,
                imeInsets.bottom
            )

            insets
        }
    }

    private fun handleGoogleSignIn() {
        //
    }

    private fun handleEmailSignIn() {
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()


        if (email.isEmpty()) {
            binding.emailInputLayout.error = "Email is required"
            return
        }

        if (password.isEmpty()) {
            binding.passwordInputLayout.error = "Password is required"
            return
        }

        // Clear errors
        binding.emailInputLayout.error = null
        binding.passwordInputLayout.error = null

    }


    private fun showUsernameBottomSheet() {
        val bottomSheet = UsernameBottomSheet.newInstance()

        bottomSheet.setOnUsernameSelectedListener { username ->
            // Handle the selected username
            Toast.makeText(this, "Username: $username", Toast.LENGTH_SHORT).show()
            // Save username or proceed with next step
            startActivity(Intent(this, EditProfileScreen::class.java))
        }

        bottomSheet.show(supportFragmentManager, UsernameBottomSheet.TAG)
    }
}