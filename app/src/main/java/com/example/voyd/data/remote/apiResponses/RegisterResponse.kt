package com.example.voyd.data.remote.apiResponses

data class RegisterResponse(
    val error: Boolean,
    val expire_at: Int,
    val role: String,
    val token: String,
    val user_id: Int
)