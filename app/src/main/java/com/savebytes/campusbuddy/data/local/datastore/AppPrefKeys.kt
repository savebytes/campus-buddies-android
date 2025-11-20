package com.savebytes.campusbuddy.data.local.datastore

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.Job

object AppPrefKeys {

    val prefType = stringPreferencesKey(Keys.PREF_TYPE)
    val token = stringPreferencesKey(Keys.TOKEN)
    val isLoggedIn = booleanPreferencesKey(Keys.IS_LOGGED_IN)
    val appPreference = stringPreferencesKey(Keys.APP_PREFERENCE)
    val userData = stringPreferencesKey(Keys.USER_DATA)
    
}

object Keys {
    const val PREF_TYPE = "users_session_preference"
    const val TOKEN = "token"
    const val IS_LOGGED_IN = "is_logged_in"
    const val APP_PREFERENCE = "com.sport.appPref"

    const val USER_DATA = "user_data"
    
    
}