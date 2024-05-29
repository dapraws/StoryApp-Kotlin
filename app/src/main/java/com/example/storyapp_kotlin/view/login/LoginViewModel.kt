package com.example.storyapp_kotlin.view.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storyapp_kotlin.data.UserRepository
import com.example.storyapp_kotlin.data.pref.UserModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: UserRepository
) : ViewModel() {
    suspend fun userLogin(email: String, password: String) =
        authRepository.userLogin(email, password)

    fun saveUserToken(token: String) {
        viewModelScope.launch {
            authRepository.saveUserToken(token)
        }
    }
}