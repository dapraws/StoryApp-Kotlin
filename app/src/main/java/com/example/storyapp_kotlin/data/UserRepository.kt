package com.example.storyapp_kotlin.data

import com.example.storyapp_kotlin.data.api.ApiService
import com.example.storyapp_kotlin.data.api.LoginResponse
import com.example.storyapp_kotlin.data.api.SignupResponse
import com.example.storyapp_kotlin.data.pref.UserModel
import com.example.storyapp_kotlin.data.pref.UserPreference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val apiService: ApiService,
    private val userPreference: UserPreference
) {

    suspend fun userLogin(email: String, password: String): Flow<Result<LoginResponse>> = flow {
        try {
            val response = apiService.userLogin(email, password)
            emit(Result.success(response))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun userSignup(
        name: String,
        email: String,
        password: String
    ): Flow<Result<SignupResponse>> = flow {
        try {
            val response = apiService.userSignup(name, email, password)
            emit(Result.success(response))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun saveUserToken(token: String) {
        userPreference.saveUserToken(token)
    }

    fun getUserToken(): Flow<String?> = userPreference.getSessions()
}