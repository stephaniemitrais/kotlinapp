package com.stephanie.kotlinapp.model

data class LoginResponse(
    val success: Boolean,
    val message: String,
    val name: String?,
    val token: String?
)