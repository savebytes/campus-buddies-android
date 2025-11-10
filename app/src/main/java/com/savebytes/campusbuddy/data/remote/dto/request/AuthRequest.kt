package com.savebytes.campusbuddy.data.remote.dto.request

data class AuthRequest(
    val email: String,
    val password: String,
    val firebaseToken: String
)