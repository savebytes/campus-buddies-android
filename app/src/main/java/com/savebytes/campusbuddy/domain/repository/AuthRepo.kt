package com.savebytes.campusbuddy.domain.repository

import com.google.firebase.auth.FirebaseUser
import com.savebytes.campusbuddy.data.remote.dto.response.AuthResponse
import com.savebytes.campusbuddy.data.remote.dto.response.HomeResponse
import com.savebytes.campusbuddy.presentation.util.Resource

interface AuthRepo {

    suspend fun emailSignUp(
        email: String,
        password: String,
        name: String
    ): Resource<AuthResponse>

    suspend fun emailSignIn(
        email: String,
        password: String
    ): Resource<AuthResponse>

    suspend fun googleSignIn(
        idToken: String,
        email: String? = null
    ): Resource<AuthResponse>

    fun getCurrentUser(): FirebaseUser?

    suspend fun logout()

    suspend fun getMoviesData() : Resource<HomeResponse>

}

