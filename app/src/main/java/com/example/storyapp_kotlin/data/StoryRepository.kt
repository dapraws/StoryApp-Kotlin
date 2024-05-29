package com.example.storyapp_kotlin.data

import com.example.storyapp_kotlin.data.api.ApiService
import com.example.storyapp_kotlin.data.api.FileUploadResponse
import com.example.storyapp_kotlin.data.api.StoryResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject


class StoryRepository @Inject constructor(
    private val apiService: ApiService
) {

    private fun generateBearerToken(token: String): String {
        return "Bearer $token"
    }

    suspend fun getAllStory(
        token: String,
        page: Int? = null,
        size: Int? = null
    ): Flow<Result<StoryResponse>> = flow {
        try {
            val bearerToken = generateBearerToken(token)
            val response = apiService.getAllStory(bearerToken, page, size)
            emit(Result.success(response))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun uploadImage(
        token: String,
        file: MultipartBody.Part,
        description: RequestBody
    ): Flow<Result<FileUploadResponse>> = flow {
        try {
            val bearerToken = generateBearerToken(token)
            val response = apiService.uploadImage(bearerToken, file, description)
            emit(Result.success(response))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.failure(e))
        }
    }


}