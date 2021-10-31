package com.example.shemajamebeli7

fun String.checkEmail(): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}