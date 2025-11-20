package com.savebytes.campusbuddy.presentation.ui.auth.register

import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.lifecycle.Observer
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.savebytes.campusbuddy.BuildConfig
import com.savebytes.campusbuddy.databinding.ActivityRegisterBinding
import com.savebytes.campusbuddy.domain.model.UserData
import com.savebytes.campusbuddy.presentation.ui.auth.AuthState
import com.savebytes.campusbuddy.presentation.ui.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: AuthViewModel by viewModels()
    private lateinit var credentialManager: CredentialManager
    private lateinit var getCredentialLauncher: ActivityResultLauncher<GetCredentialRequest>

    private var mUserData : UserData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)


        credentialManager = CredentialManager.create(this)


        initView()
        getExtrasData()
        observer()

    }

    private fun observer() {
        viewModel.authState.observe(this, Observer { state ->
            when(state){
                is AuthState.Error ->{
                    mUserData = null
                }
                is AuthState.Idle -> {
                    mUserData = null
                }
                is AuthState.Loading -> {
                    mUserData = null
                }
                is AuthState.Success -> {
                    mUserData = state.user

                }
            }
        })
    }

    private fun getExtrasData() {


    }

    private fun signInWithGoogle() {
        val googleIdOption = GetGoogleIdOption.Builder()
            .setServerClientId(BuildConfig.GOOGLE_WEB_CLIENT_ID)
            .setFilterByAuthorizedAccounts(false)  // false for “any account / new signup”
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        getCredentialLauncher.launch(request)
    }

    private fun initView() {

        binding.btnRegister.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            val confirmPassword = binding.etConfirmPassword.text.toString().trim()

            if (validateInput(email, password, confirmPassword)) {
                // viewModel.signUpWithEmail(email, password)
            }
        }

        binding.btnGoogleSignIn.setOnClickListener {
            // viewModel.signInWithGoogle()
        }

        binding.tvLogin.setOnClickListener {
            finish()
        }
    }

    private fun validateInput(email: String, password: String, confirmPassword: String): Boolean {
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

        if (confirmPassword.isEmpty()) {
            binding.tilConfirmPassword.error = "Please confirm your password"
            return false
        }
        if (password != confirmPassword) {
            binding.tilConfirmPassword.error = "Passwords do not match"
            return false
        }
        binding.tilConfirmPassword.error = null

        return true
    }


    private fun handleCredentialResponse(response: GetCredentialResponse) {
        val credential = response.credential  // or response.getCredential()
        if (credential != null) {
            // A credential was retrieved
            if (credential is com.google.android.libraries.identity.googleid.GoogleIdTokenCredential) {
                val idToken = credential.idToken
                if (idToken != null) {
                    // viewModel.signInWithGoogle()
                } else {
                    Toast.makeText(this, "No ID token received", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Unexpected credential type: ${credential.type}", Toast.LENGTH_SHORT).show()
            }
        } else {
            // No credential retrieved — you can treat this as ‘cancelled’ or no‐credential scenario
            Toast.makeText(this, "No credential retrieved", Toast.LENGTH_SHORT).show()
        }
    }
}
