package com.example.storyapp_kotlin.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.storyapp_kotlin.data.StoryRepository
import com.example.storyapp_kotlin.data.UserRepository
import com.example.storyapp_kotlin.data.api.StoryResponse
import com.example.storyapp_kotlin.data.pref.UserModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authRepository: UserRepository,
    private val storyRepository: StoryRepository
) : ViewModel() {

    fun saveUserToken(token: String) {
        viewModelScope.launch {
            authRepository.saveUserToken(token)
        }
    }

    suspend fun getAllStory(token: String): Flow<Result<StoryResponse>> =
        storyRepository.getAllStory(token, null, null)
}