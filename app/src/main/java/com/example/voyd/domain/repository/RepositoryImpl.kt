package com.example.voyd.domain.repository

import com.example.voyd.data.remote.apiResponses.ForgotPasswordResponse
import com.example.voyd.data.remote.apiResponses.LoginResponse
import com.example.voyd.data.remote.apiResponses.RegisterResponse
import com.example.voyd.data.remote.apiResponses.ResetPasswordResponse
import com.example.voyd.data.remote.apiServices.VoydApiService
import com.example.voyd.data.remote.dto.LoginRequestDTO
import com.example.voyd.data.remote.dto.RegisterUserDTO
import com.example.voyd.domain.interactors.SessionManager
import com.example.voyd.domain.models.ForgotPasswordDTO
import com.example.voyd.domain.models.ResetPasswordDTO
import com.example.voyd.domain.models.UpdateProfileDTO
import com.example.voyd.presentation.viewState.Resource
import com.example.voyd.utils.NetworkUtils
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryImpl @Inject constructor(
    private val voydApiService: VoydApiService,
    private val networkUtils: NetworkUtils,
    private val sessionManager: SessionManager
) : Repository {
    override suspend fun registerUser(
        token: String,
        registerUserRequest: RegisterUserDTO
    ): Resource<RegisterResponse?> =
        voydApiService.registerUser(token, registerUserRequest).let {
            networkUtils.getServerResponse(it)
        }

    override suspend fun login(
        token: String,
        loginRequestDTO: LoginRequestDTO
    ): Resource<LoginResponse?> {
        // Make the API call to login
        val response = voydApiService.login(token, loginRequestDTO)
        // Get the server response
        val resource = networkUtils.getServerResponse(response)
        // Check if the response is successful
        if (resource is Resource.Success) {
            // Extract the token from the response body
            val token = resource.value?.token
            // Save the token using your preferred method
            if (token != null) {
                sessionManager.saveToken(token)
            }
        }
        return resource
    }

    override suspend fun forgotPassword(
        token: String,
        forgotPasswordDTO: ForgotPasswordDTO
    ): Resource<ForgotPasswordResponse?> =
        voydApiService.forgotPassword(token, forgotPasswordDTO).let {
            networkUtils.getServerResponse(it)
        }

    override suspend fun resetPassword(
        token: String,
        resetPasswordDTO: ResetPasswordDTO
    ): Resource<ResetPasswordResponse?> =
        voydApiService.resetPassword(token, resetPasswordDTO).let {
            networkUtils.getServerResponse(it)
        }

    override suspend fun updateUserProfile(
        token: String,
        updateProfileDTO: UpdateProfileDTO
    ): Resource<ResetPasswordResponse?> =
        voydApiService.updateUserProfile(token, updateProfileDTO).let {
            networkUtils.getServerResponse(it)
        }


}