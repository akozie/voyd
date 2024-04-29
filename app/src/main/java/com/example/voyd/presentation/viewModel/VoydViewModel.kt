package com.example.voyd.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.voyd.data.local.sharedPrefs.SharedPrefsManager
import com.example.voyd.data.remote.apiResponses.ForgotPasswordResponse
import com.example.voyd.data.remote.apiResponses.LoginResponse
import com.example.voyd.data.remote.apiResponses.RegisterResponse
import com.example.voyd.data.remote.apiResponses.ResetPasswordResponse
import com.example.voyd.data.remote.dto.LoginRequestDTO
import com.example.voyd.data.remote.dto.RegisterUserDTO
import com.example.voyd.domain.interactors.SessionManager
import com.example.voyd.domain.models.ForgotPasswordDTO
import com.example.voyd.domain.models.ResetPasswordDTO
import com.example.voyd.domain.models.UpdateProfileDTO
import com.example.voyd.domain.repository.Repository
import com.example.voyd.presentation.viewState.Resource
import com.example.voyd.utils.AppConstants.FORGOT_PASSWORD_X_PROJECT_URL
import com.example.voyd.utils.AppConstants.LOGIN_X_PROJECT_URL
import com.example.voyd.utils.AppConstants.REGISTER_X_PROJECT_URL
import com.example.voyd.utils.AppConstants.RESET_PASSWORD_X_PROJECT_URL
import com.example.voyd.utils.AppConstants.TEMP_USER_DETAILS
import com.example.voyd.utils.AppConstants.UPDATE_PROFILE_X_PROJECT_URL
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class VoydViewModel @Inject constructor(
    private val repository: Repository,
    private val sharedPrefsManager: SharedPrefsManager,
    private val gson: Gson,
    private val ioDispatcher: CoroutineContext
) : ViewModel() {

    private val _loginResponse: MutableLiveData<Resource<LoginResponse?>> = MutableLiveData()
    val loginResponse: LiveData<Resource<LoginResponse?>> get() = _loginResponse

    private val _registerResponse: MutableLiveData<Resource<RegisterResponse?>> = MutableLiveData()
    val registerResponse: LiveData<Resource<RegisterResponse?>> get() = _registerResponse

    private val _forgotPasswordResponse: MutableLiveData<Resource<ForgotPasswordResponse?>> = MutableLiveData()
    val forgotPasswordResponse: LiveData<Resource<ForgotPasswordResponse?>> get() = _forgotPasswordResponse

    private val _resetPasswordResponse: MutableLiveData<Resource<ResetPasswordResponse?>> = MutableLiveData()
    val resetPasswordResponse: LiveData<Resource<ResetPasswordResponse?>> get() = _resetPasswordResponse

    private val _userDetailsResponse: MutableLiveData<Resource<ResetPasswordResponse?>> = MutableLiveData()
    val userDetailsResponse: LiveData<Resource<ResetPasswordResponse?>> get() = _userDetailsResponse


    fun login(email: String, password: String) {
        _loginResponse.postValue( Resource.Loading)
        viewModelScope.launch(ioDispatcher) {
            if (email.contains("admin")){
                val loginRequestDTO = LoginRequestDTO(
                    email = email,
                    password = password,
                    role = "admin"
                )
                _loginResponse.postValue(repository.login(LOGIN_X_PROJECT_URL, loginRequestDTO))
            }else {
                val loginRequestDTO = LoginRequestDTO(
                    email = email,
                    password = password,
                    role = "member"
                )
                _loginResponse.postValue(repository.login(LOGIN_X_PROJECT_URL, loginRequestDTO))
            }
        }
    }

    fun forgotPassword(email: String) {
        _forgotPasswordResponse.postValue(Resource.Loading)
        viewModelScope.launch(ioDispatcher) {
            val forgotPasswordDTO = ForgotPasswordDTO(
                email = email
            )
            _forgotPasswordResponse.postValue(repository.forgotPassword(FORGOT_PASSWORD_X_PROJECT_URL, forgotPasswordDTO))
        }
    }

    fun resetPassword(code: String, password: String, token: String) {
        _resetPasswordResponse.postValue(Resource.Loading)
        viewModelScope.launch(ioDispatcher) {
            val forgotPasswordDTO = ResetPasswordDTO(
                code = code,
                password = password,
                token = token
            )
            _resetPasswordResponse.postValue(repository.resetPassword(RESET_PASSWORD_X_PROJECT_URL, forgotPasswordDTO))
        }
    }


    fun register(registerUserDTO: RegisterUserDTO) {
        _registerResponse.postValue(Resource.Loading)
        viewModelScope.launch(ioDispatcher) {
            sharedPrefsManager.saveStringToSharedPrefs(
                TEMP_USER_DETAILS,
                gson.toJson(registerUserDTO.copy(
                    password = ""
                ))
            )
            _registerResponse.postValue(repository.registerUser(REGISTER_X_PROJECT_URL, registerUserDTO))
        }
    }

    fun updateUserProfile(updateProfileDTO: UpdateProfileDTO) {
        _userDetailsResponse.postValue(Resource.Loading)
        viewModelScope.launch(ioDispatcher) {
            _userDetailsResponse.postValue(repository.updateUserProfile(UPDATE_PROFILE_X_PROJECT_URL, updateProfileDTO))
        }
    }

}