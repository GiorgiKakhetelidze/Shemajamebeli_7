package com.example.shemajamebeli7.ui.viewmodel

import android.util.Log.d
import androidx.lifecycle.*
import com.example.shemajamebeli7.datastore.SessionManager
import com.example.shemajamebeli7.model.LoginSuccess
import com.example.shemajamebeli7.model.RegisterSuccess
import com.example.shemajamebeli7.model.User
import com.example.shemajamebeli7.network.NetworkClient
import com.example.shemajamebeli7.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class AuthViewModel : ViewModel() {

    private val _registerFinished = MutableLiveData<Resource<RegisterSuccess>>()
    val registerFinished: LiveData<Resource<RegisterSuccess>> get() = _registerFinished

    private val _loginFinished = MutableLiveData<Resource<LoginSuccess>>()
    val loginFinished: LiveData<Resource<LoginSuccess>> get() = _loginFinished


    fun register(user: User) {
        try {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    val response = NetworkClient.authService.registration(user = user)
                    val result = response.body()
                    if (response.isSuccessful && result != null)
                        _registerFinished.postValue(Resource.Success(data = result))
                    else
                        _registerFinished.postValue(Resource.Error(message = response.message()))
                }
            }
        } catch (e: Exception) {
            _registerFinished.postValue(Resource.Error(message = e.message.toString()))
        } finally {
            _registerFinished.postValue(Resource.Loading(load = false))
        }

    }

    fun login(user: User) {
        try {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    val response = NetworkClient.authService.login(user = user)
                    val result = response.body()
                    if (response.isSuccessful && result != null)
                        _loginFinished.postValue(Resource.Success(data = result))
                    else
                        _loginFinished.postValue(Resource.Error(message = response.message()))
                }
            }
        } catch (e: Exception) {
            _loginFinished.postValue(Resource.Error(message = e.message.toString()))
        } finally {
            _loginFinished.postValue(Resource.Loading(load = false))
        }

    }

    fun setSession(token: String, email: String, isChecked: Boolean) {
        if (isChecked) {
            viewModelScope.launch(Dispatchers.IO) {
                SessionManager.saveToDataStore(
                    token = token,
                    email = email
                )
                d("saved", readTokenFromDatastore().toString())
            }

        }
    }

    suspend fun readEmailFromDataStore(): String? {
        return SessionManager.readEmailFromDataStore()
    }

    suspend fun readTokenFromDatastore(): String? {
        return SessionManager.readTokenFromDataStore()
    }

    suspend fun removeTokenFromDataStore() {
        SessionManager.removeFromDataStore()
        d("removed", readTokenFromDatastore().toString())
    }


}