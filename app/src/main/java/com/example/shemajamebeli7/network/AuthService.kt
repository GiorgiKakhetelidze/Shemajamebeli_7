package com.example.shemajamebeli7.network

import com.example.shemajamebeli7.model.LoginSuccess
import com.example.shemajamebeli7.model.RegisterSuccess
import com.example.shemajamebeli7.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("login")
    suspend fun login(@Body user: User) : Response<LoginSuccess>

    @POST("register")
    suspend fun registration(@Body user: User) : Response<RegisterSuccess>
}