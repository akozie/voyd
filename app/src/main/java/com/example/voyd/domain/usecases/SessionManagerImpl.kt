package com.example.voyd.domain.usecases

import com.example.voyd.data.local.sharedPrefs.SharedPrefsManager
import com.example.voyd.domain.interactors.SessionManager
import com.example.voyd.utils.AppConstants.STRING_SESSION_TOKEN_SHARED_PREFS_KEY
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManagerImpl @Inject constructor(
    private val appSharedPref: SharedPrefsManager,
) : SessionManager {
    override fun saveToken(token: String) {
        appSharedPref.saveStringToSharedPrefs(STRING_SESSION_TOKEN_SHARED_PREFS_KEY, token)
    }

    override fun fetchToken(): String? =
        appSharedPref.retrieveStringFromSharedPrefs(STRING_SESSION_TOKEN_SHARED_PREFS_KEY, "").let {
            if (it.isNotEmpty()) it else null
        }

    override fun deleteToken() {
        appSharedPref.removePref(STRING_SESSION_TOKEN_SHARED_PREFS_KEY)
    }
}

