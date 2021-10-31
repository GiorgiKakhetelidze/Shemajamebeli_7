package com.example.shemajamebeli7.ui.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shemajamebeli7.model.RegisterSuccess
import com.example.shemajamebeli7.model.User
import com.example.shemajamebeli7.network.NetworkClient
import com.example.shemajamebeli7.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class RegistrationViewModel : ViewModel() {

    private val _registerFinished = MutableLiveData<Resource<RegisterSuccess>>()
    val registerFinished: LiveData<Resource<RegisterSuccess>> get() = _registerFinished

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
        }
        finally {
            _registerFinished.postValue(Resource.Loading(load = false))
        }

    }

}