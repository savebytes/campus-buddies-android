package com.savebytes.campusbuddy.data.local.datastore

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

private val Context.preferencesDataStore by preferencesDataStore(name = AppPrefKeys.prefType.toString())

@Singleton
class DataPrefManager @Inject constructor(
    @ApplicationContext context: Context,
) {

    private val userPrefDataStore = context.preferencesDataStore

    var token by UserDataPreferences<String>(
        dataStore = userPrefDataStore,
        key = AppPrefKeys.token,
        defaultValue = ""
    )

    var isLoggedIn by UserDataPreferences<Boolean>(
        dataStore = userPrefDataStore,
        key = AppPrefKeys.isLoggedIn,
        defaultValue = false
    )

    var appPreference by UserDataPreferences<String>(
        dataStore = userPrefDataStore,
        key = AppPrefKeys.appPreference,
        defaultValue = ""
    )

    suspend fun clearUserData() {
        Log.i(
            "DataPrefManager",
            "clearUserData: Clearing user data"
        )
        userPrefDataStore.edit {
            it.clear()
        }
    }
}