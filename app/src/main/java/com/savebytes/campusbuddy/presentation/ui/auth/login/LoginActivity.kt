package com.savebytes.campusbuddy.presentation.ui.auth.login

import android.os.Bundle
import android.util.Patterns
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.savebytes.campusbuddy.databinding.ActivityLoginBinding
import com.savebytes.campusbuddy.presentation.ui.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        observer()
    }


    private fun initView() {

        binding.btnLogin.setOnClickListener{
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (validateInput(email, password)) {
                // Login Action Function
            }
        }

        viewModel.getMovie()

        binding.btnGoogleSignIn.setOnClickListener {
            signInWithGoogle()
        }

    }


    private fun observer() {
        // Observer ViewModel

    }


    private fun validateInput(email: String, password: String): Boolean {
        if (email.isEmpty()) {
            binding.tilEmail.error = "Email is required"
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.tilEmail.error = "Invalid email format"
            return false
        }
        binding.tilEmail.error = null

        if (password.isEmpty()) {
            binding.tilPassword.error = "Password is required"
            return false
        }
        if (password.length < 6) {
            binding.tilPassword.error = "Password must be at least 6 characters"
            return false
        }
        binding.tilPassword.error = null

        return true
    }

    private fun signInWithGoogle() {
        viewModel.signInWithGoogle()
    }
}


