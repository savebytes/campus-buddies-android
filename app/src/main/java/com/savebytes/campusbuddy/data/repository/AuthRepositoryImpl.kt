package com.savebytes.campusbuddy.data.repository

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.savebytes.campusbuddy.data.remote.api.AuthApiService
import com.savebytes.campusbuddy.data.remote.dto.request.AuthRequest
import com.savebytes.campusbuddy.data.remote.dto.response.AuthResponse
import com.savebytes.campusbuddy.domain.repository.AuthRepo
import com.savebytes.campusbuddy.presentation.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject


class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val authApiService: AuthApiService
) : AuthRepo {

    override suspend fun signUpWithEmail(
        email: String,
        password: String
    ): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun signInWithEmail(email: String, password: String): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun signInWithGoogle(credential: AuthCredential): Result<Unit> = withContext(Dispatchers.IO) {

    }

    override suspend fun getMovieData(): Result<Unit>  = withContext(Dispatchers.IO){
        try {
            authApiService.getAllMovies()
            Result.success(Unit)
        } catch (e: Exception){
            Result.failure(e)
        }
    }

    override suspend fun emailSignUp(
        email: String,
        password: String,
        name: String
    ): Resource<AuthResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun emailSignIn(
        email: String,
        password: String
    ): Resource<AuthResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun googleSignIn(
        idToken: String,
        email: String?
    ): Resource<AuthResponse> = try {
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
        Result.failure(e)
    }

    override fun getCurrentUser(): FirebaseUser? = firebaseAuth.currentUser
    override suspend fun logout() {
        firebaseAuth.signOut()
    }

    override fun signOut() {

    }
}