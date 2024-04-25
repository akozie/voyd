package com.example.voyd.data.remote.dto

data class RegisterUserDTO(
    val email: String,
    val password: String,
    val role: String,
    val is_refresh: Boolean
)
