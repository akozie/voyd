package com.example.voyd.data.remote.apiResponses

data class ForgotPasswordResponse(
    val code: String,
    val error: Boolean,
    val message: String,
    val token: String
)