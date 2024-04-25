package com.example.voyd.domain.models

data class ResetPasswordDTO(
    val code: String,
    val password: String,
    val token: String
)