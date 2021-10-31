package com.example.shemajamebeli7.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shemajamebeli7.model.LoginSuccess
import com.example.shemajamebeli7.model.User
import com.example.shemajamebeli7.network.NetworkClient
import com.example.shemajamebeli7.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class LoginViewModel : ViewModel() {

    private val _loginFinished = MutableLiveData<Resource<LoginSuccess>>()
    val loginFinished: LiveData<Resource<LoginSuccess>> get() = _loginFinished


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
        }
        finally {
            _loginFinished.postValue(Resource.Loading(load = false))
        }

    }


}