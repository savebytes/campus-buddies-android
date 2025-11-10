package com.savebytes.campusbuddy.domain.model

data class UserData(
    val id: String,
    val email: String,
    val name: String? = null,
    val profilePic: String? = null
)