package com.savebytes.campusbuddy.presentation.util

data class ApiResponse<T>(
    val status: Int,
    val message: String,
    val data: T?
)