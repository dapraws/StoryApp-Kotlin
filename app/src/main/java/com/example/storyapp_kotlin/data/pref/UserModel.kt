package com.example.storyapp_kotlin.data.pref

data class UserModel(
    val email: String,
    val token: String,
    val isLogin: Boolean = false
)