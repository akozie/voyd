package com.example.voyd.domain.interactors


interface SessionManager {
    fun saveToken(token: String)
    fun fetchToken(): String?
    fun deleteToken()
}