package com.savebytes.campusbuddy.domain.model

data class UserProfile(
    val name: String,
    val username: String,
    val course: String,
    val college: String,
    val profileImageUrl: String,
    val isOnline: Boolean
)