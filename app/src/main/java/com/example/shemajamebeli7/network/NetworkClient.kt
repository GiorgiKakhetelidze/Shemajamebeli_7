package com.example.shemajamebeli7.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import kotlin.coroutines.coroutineContext

object NetworkClient {

    val authService by lazy { createAuthService() }

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private fun createAuthService(): AuthService {
        val retrofitBuilder = Retrofit.Builder()
        retrofitBuilder.baseUrl("https://reqres.in/api/")
        retrofitBuilder.client(
            OkHttpClient().newBuilder()
                .addInterceptor(loggingInterceptor)
                .build()
        )
        retrofitBuilder.addConverterFactory(moshConverter())
        return retrofitBuilder.build().create(AuthService::class.java)
    }


    private fun moshConverter() =
        MoshiConverterFactory.create(
            Moshi.Builder()
                .addLast(KotlinJsonAdapterFactory())
                .build()
        )
}