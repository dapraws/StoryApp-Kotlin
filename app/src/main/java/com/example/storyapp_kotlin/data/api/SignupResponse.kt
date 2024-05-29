package com.example.storyapp_kotlin.data.api

import com.google.gson.annotations.SerializedName

class SignupResponse {
    @field:SerializedName("error")
    val error: Boolean? = null

    @field:SerializedName("message")
    val message: String? = null
}