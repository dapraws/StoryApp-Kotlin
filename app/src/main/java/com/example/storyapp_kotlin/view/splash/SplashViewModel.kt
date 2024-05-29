package com.example.storyapp_kotlin.view.splash

import androidx.lifecycle.ViewModel
import com.example.storyapp_kotlin.data.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val authRepository: UserRepository) :
    ViewModel() {

    fun getAuthToken(): Flow<String?> = authRepository.getUserToken()
}