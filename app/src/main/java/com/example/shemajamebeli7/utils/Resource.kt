package com.example.shemajamebeli7.utils

sealed class Resource<out T>(
    val data: T? = null,
    val message: String? = null,
    val loading: Boolean = false
) {
    class Success<T>(data: T, loadState: Boolean = false) :
        Resource<T>(data = data, loading = loadState)

    class Error<T>(message: String, data: T? = null) : Resource<T>(data = data, message = message)
    class Loading<T>(load: Boolean) : Resource<T>(loading = load)
}
