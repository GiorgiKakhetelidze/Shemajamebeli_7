package com.example.shemajamebeli7.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginSuccess(
    @Json(name = "token")
    val token: String
)