package com.example.voyd.domain.repository

import com.example.voyd.data.remote.apiResponses.*
import com.example.voyd.data.remote.dto.LoginRequestDTO
import com.example.voyd.data.remote.dto.RegisterUserDTO
import com.example.voyd.domain.models.ForgotPasswordDTO
import com.example.voyd.domain.models.ResetPasswordDTO
import com.example.voyd.domain.models.UpdateProfileDTO
import com.example.voyd.presentation.viewState.Resource

interface Repository {
    suspend fun registerUser(token: String, registerUserRequest: RegisterUserDTO): Resource<RegisterResponse?>
    suspend fun login(token: String, loginRequestDTO: LoginRequestDTO): Resource<LoginResponse?>
    suspend fun forgotPassword(token: String, forgotPasswordDTO: ForgotPasswordDTO): Resource<ForgotPasswordResponse?>
    suspend fun resetPassword(token: String, resetPasswordDTO: ResetPasswordDTO): Resource<ResetPasswordResponse?>
    suspend fun updateUserProfile(token: String, updateProfileDTO: UpdateProfileDTO): Resource<ResetPasswordResponse?>
}