package com.example.voyd.utils

import android.util.Log
import com.example.voyd.presentation.viewState.Resource
import com.example.voyd.utils.AppConstants.showToast
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException

class NetworkUtils() {
    fun <T> getServerResponse(serverResponse: Response<T>): Resource<T?> {
        return when {
            serverResponse.code() in 200..299 -> {
                Resource.Success(serverResponse.body()!!)
            }

            serverResponse.code() in 400..499 -> {
                val errorBody = serverResponse.errorBody()?.string()
                val errorMessage = errorBody ?: "Unknown client error"
                Log.d("SERVER", errorMessage.toString())
                Resource.Failure(false, serverResponse.code(), "Client Error", errorMessage)

//                Resource.Failure(false, 403, "client", errorBody = null)
            }

            serverResponse.code() >= 500 -> {
                Resource.Failure(false, 500, "Server Error", null)
            }

            else -> {
                Resource.Failure(false, 500, "Error", null)
            }
        }
    }

//    fun handleError(e: Exception): Resource<String> =
//        when (e) {
//            is SocketTimeoutException -> {
//                Resource.Error(false, 500,"Time out")
//            }
//
//            is HttpException -> {
//                Resource.Error(false, 500, e.localizedMessage!!)
//            }
//
//            is IOException -> {
//                Resource.Error(false, 500, "Connection Error")
//            }
//
//            else -> {
//                Resource.Error(false, 500, e.localizedMessage!!)
//            }
//        }
}