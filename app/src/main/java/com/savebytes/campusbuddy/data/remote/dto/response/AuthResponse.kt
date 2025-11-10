package com.savebytes.campusbuddy.data.remote.dto.response

import com.savebytes.campusbuddy.domain.model.UserData

data class AuthResponse(
    val success: Boolean,
    val message: String,
    val user: UserData? = null,
    val token: String? = null
)