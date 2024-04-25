package com.example.voyd.data.local.sharedPrefs

import com.dsofttech.dprefs.utils.DPrefs
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPrefsManagerImpl @Inject constructor() : SharedPrefsManager {

    private val sharedPrefObject: DPrefs = DPrefs

    override fun retrieveStringFromSharedPrefs(key: String, defaultValue: String): String =
        sharedPrefObject.getString(key, defaultValue)

    override fun retrieveIntFromSharedPrefs(key: String, defaultValue: Int?): Int =
        defaultValue?.let { sharedPrefObject.getInt(key, it) }
            ?: run { sharedPrefObject.getInt(key) }

    override fun saveStringToSharedPrefs(key: String, value: String) {
        sharedPrefObject.putString(key, value)
    }

    override fun saveIntToSharedPrefs(key: String, value: Int) {
        sharedPrefObject.putInt(key, value)
    }

    override fun removePref(key: String) {
        sharedPrefObject.removePref(key)
    }
}