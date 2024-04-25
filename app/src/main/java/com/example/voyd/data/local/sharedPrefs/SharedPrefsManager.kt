package com.example.voyd.data.local.sharedPrefs

interface SharedPrefsManager {
    fun retrieveStringFromSharedPrefs(
        key: String,
        defaultValue: String,
    ): String
    fun retrieveIntFromSharedPrefs(key: String, defaultValue: Int? = null): Int
    fun saveStringToSharedPrefs(key: String, value: String)
    fun saveIntToSharedPrefs(key: String, value: Int)
    fun removePref(key: String)
}