package com.savebytes.campusbuddy.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.savebytes.campusbuddy.data.remote.api.AuthApiService
import com.savebytes.campusbuddy.data.remote.dto.request.AuthRequest
import com.savebytes.campusbuddy.data.remote.dto.response.AuthResponse
import com.savebytes.campusbuddy.data.remote.dto.response.HomeResponse
import com.savebytes.campusbuddy.domain.repository.AuthRepo
import com.savebytes.campusbuddy.presentation.util.Resource
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class AuthRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val authApiService: AuthApiService
) : AuthRepo {

    private val TAG = "AuthRepository"

    override suspend fun emailSignUp(
        email: String,
        password: String,
        name: String
    ): Resource<AuthResponse> {
        return try {
            val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val user = authResult.user ?: throw Exception("User creation failed")

            // Get Firebase ID token
            val token = user.getIdToken(false).await().token
                ?: throw Exception("Failed to get token")

            val response = authApiService.signup(
                AuthRequest(
                    email = email,
                    password = password,
                    firebaseToken = token
                )
            )
            if (response.success) {
                Resource.success(response)
            } else {
                Resource.error(response.message, null)
            }
        } catch (e: Exception) {
            Resource.error(e.message ?: "An unknown error occurred", null)
        }
    }

    override suspend fun emailSignIn(
        email: String,
        password: String
    ): Resource<AuthResponse> {
        return try {
            val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            val user = authResult.user ?: throw Exception("Sign in failed")

            val token = user.getIdToken(false).await().token
                ?: throw Exception("Failed to get token")

            val response = authApiService.signin(
                AuthRequest(email, password, token)
            )
            Resource.success(response)
        } catch (e: Exception) {
            Resource.error(e.message.toString(), null)
        }
    }

    override suspend fun googleSignIn(
        idToken: String,
        email: String?
    ): Resource<AuthResponse> {
        return try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val authResult = firebaseAuth.signInWithCredential(credential).await()
            val user = authResult.user ?: throw Exception("Google sign in failed")

            val firebaseToken = user.getIdToken(false).await().token
                ?: throw Exception("Failed to get token")

            val response = authApiService.signin(
                AuthRequest(
                    email = user.email ?: email ?: "",
                    password = "", // Not used for Google
                    firebaseToken = firebaseToken
                )
            )
            Resource.success(response)
        } catch (e: Exception) {
            Resource.error(msg = e.message.toString(), data = null)
        }
    }

    override fun getCurrentUser(): FirebaseUser? = firebaseAuth.currentUser
    override suspend fun logout() {
        firebaseAuth.signOut()
    }

    override suspend fun getMoviesData(): Resource<HomeResponse> {
        return try {
            val response = authApiService.getAllMovies()
            Resource.success(response)
        } catch (e: Exception) {
            Resource.error("Error fetching data", null)
        }
    }
}
