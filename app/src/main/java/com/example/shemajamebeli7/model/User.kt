package com.example.shemajamebeli7.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
    @Json(name = "email")
    val email: String,
    @Json(name = "password")
    val password: String
)