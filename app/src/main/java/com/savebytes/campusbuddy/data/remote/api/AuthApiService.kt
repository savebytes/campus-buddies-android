package com.savebytes.campusbuddy.data.remote.api

import com.savebytes.campusbuddy.data.remote.dto.request.AuthRequest
import com.savebytes.campusbuddy.data.remote.dto.response.AuthResponse
import com.savebytes.campusbuddy.data.remote.dto.response.HomeResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApiService {

    @GET("film/home") suspend fun getAllMovies() : HomeResponse

    @POST("auth/signup") suspend fun signup(@Body request: AuthRequest): AuthResponse

    @POST("auth/signin") suspend fun signin(@Body request: AuthRequest): AuthResponse

    @POST("auth/verify-token") suspend fun verifyToken(@Body request: Map<String, String>): AuthResponse

}