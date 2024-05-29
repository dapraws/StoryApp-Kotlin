package com.example.storyapp_kotlin.view.create

import androidx.lifecycle.ViewModel
import com.example.storyapp_kotlin.data.StoryRepository
import com.example.storyapp_kotlin.data.UserRepository
import com.example.storyapp_kotlin.data.api.FileUploadResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class CreateViewModel @Inject constructor(
    private val authRepository: UserRepository,
    private val storyRepository: StoryRepository
) : ViewModel() {

    fun getAuthToken(): Flow<String?> = authRepository.getUserToken()

    suspend fun uploadImage(
        token: String,
        file: MultipartBody.Part,
        description: RequestBody
    ): Flow<Result<FileUploadResponse>> =
        storyRepository.uploadImage(token, file, description)
}