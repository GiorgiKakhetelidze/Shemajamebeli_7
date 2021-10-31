package com.example.shemajamebeli7.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RegisterSuccess(
    @Json(name = "id")
    val id: Int,
    @Json(name = "token")
    val token: String
)