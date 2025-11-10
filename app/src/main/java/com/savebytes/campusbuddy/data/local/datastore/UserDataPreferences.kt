package com.savebytes.campusbuddy.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class UserDataPreferences<T>(
    private val dataStore: DataStore<Preferences>,
    private val key: Preferences.Key<T>,
    private val defaultValue: T
) : ReadWriteProperty<Any, T> {

    override fun getValue(thisRef: Any, property: KProperty<*>) = runBlocking {
        dataStore.data.map {
            it[key] ?: defaultValue
        }.first()
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        runBlocking {
            dataStore.edit { preferences ->
                preferences[key] = value
            }
        }
    }
}