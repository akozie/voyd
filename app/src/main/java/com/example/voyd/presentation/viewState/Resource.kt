package com.example.voyd.presentation.viewState

//sealed class Resource<T>(val data: T? = null, val message: String? = null) {
//    class Loading<T> : Resource<T>()
//    class Success<T>(data: T) : Resource<T>(data)
//    class Error<T>(errorMessage: String) : Resource<T>(message = errorMessage)
//}

sealed class Resource<out T> {
    data class Success<out T>(val value : T) : Resource<T>()
    data class Failure(
        val isNetworkError : Boolean,
        val errorCode : Int?,
        val message : String?,
        val errorBody : String?
    ) : Resource<Nothing>()
    object Loading: Resource<Nothing>()
}