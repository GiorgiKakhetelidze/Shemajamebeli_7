package com.example.shemajamebeli7.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.asLiveData
import com.example.shemajamebeli7.app.App.Companion.appContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.map

object SessionManager {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "_Data")

    suspend fun saveToDataStore(token: String, email: String) {
        appContext?.dataStore?.edit { sessionInfo ->
            sessionInfo[tokenKey] = token
            sessionInfo[emailKey] = email
        }
    }

    suspend fun removeFromDataStore() {
        appContext?.dataStore?.edit { sessionInfo ->
            sessionInfo.remove(tokenKey)
            sessionInfo.remove(emailKey)
        }
    }

    suspend fun readTokenFromDataStore() = appContext?.dataStore?.data?.map { session ->
        session[tokenKey] ?: "DefaultToken"
    }?.first()

    suspend fun readEmailFromDataStore() = appContext?.dataStore?.data?.map { session ->
        session[emailKey] ?: "DefaultEmail"
    }?.first()


    private val tokenKey = stringPreferencesKey("token")
    private val emailKey = stringPreferencesKey("email")

}