package com.example.voyd.data.remote.dto

data class LoginRequestDTO(
    val email: String,
    val password: String,
    val role: String
)

