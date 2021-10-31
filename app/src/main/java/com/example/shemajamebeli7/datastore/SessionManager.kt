package com.example.shemajamebeli7.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.flow.map

object SessionManager {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("token")

    suspend fun saveToDataStore(context: Context, token: String, email: String) {
        context.dataStore.edit { sessionInfo ->
            sessionInfo[TOKEN_KEY] = token
            sessionInfo[EMAIL_KEY] = email
        }
    }

    suspend fun removeFromDataStore(context: Context) {
        context.dataStore.edit { sessionInfo ->
            sessionInfo.remove(TOKEN_KEY)
            sessionInfo.remove(EMAIL_KEY)
        }
    }

    fun readTokenFromDataStore(context: Context) = context.dataStore.data.map { session ->
        session[TOKEN_KEY] ?: DEFAULT_TOKEN
    }.asLiveData()

    fun readEmailFromDataStore(context: Context) = context.dataStore.data.map { session ->
        session[EMAIL_KEY] ?: "DefaultEmail"
    }.asLiveData()


    val TOKEN_KEY = stringPreferencesKey("token")
    const val DEFAULT_TOKEN = "DefaultToken"
    val EMAIL_KEY = stringPreferencesKey("email")


}