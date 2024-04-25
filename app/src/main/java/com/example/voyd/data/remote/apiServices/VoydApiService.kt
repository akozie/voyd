package com.example.voyd.data.remote.apiServices

import com.example.voyd.data.remote.annotations.AuthenticatedRequest
import com.example.voyd.data.remote.apiResponses.ForgotPasswordResponse
import com.example.voyd.data.remote.apiResponses.LoginResponse
import com.example.voyd.data.remote.apiResponses.RegisterResponse
import com.example.voyd.data.remote.apiResponses.ResetPasswordResponse
import com.example.voyd.data.remote.dto.LoginRequestDTO
import com.example.voyd.data.remote.dto.RegisterUserDTO
import com.example.voyd.domain.models.ForgotPasswordDTO
import com.example.voyd.domain.models.ResetPasswordDTO
import com.example.voyd.domain.models.UpdateProfileDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface VoydApiService {
    @POST("v2/api/lambda/register")
    suspend fun registerUser(
        @Header("x-project") token: String,
        @Body registerUserRequest: RegisterUserDTO
    ): Response<RegisterResponse>

    @POST("v2/api/lambda/login")
    suspend fun login(
        @Header("x-project") token: String,
        @Body loginRequestDTO: LoginRequestDTO): Response<LoginResponse>

    @POST("v2/api/lambda/forgot")
    suspend fun forgotPassword(
        @Header("x-project") token: String,
        @Body forgotPasswordDTO: ForgotPasswordDTO): Response<ForgotPasswordResponse>

    @POST("v2/api/lambda/reset")
    suspend fun resetPassword(
        @Header("x-project") token: String,
        @Body resetPasswordDTO: ResetPasswordDTO): Response<ResetPasswordResponse>

    @POST("api/v1/base/profile")
    @AuthenticatedRequest
    suspend fun updateUserProfile(
        @Header("x-project") token: String,
        @Body updateProfileDTO: UpdateProfileDTO): Response<ResetPasswordResponse>
}