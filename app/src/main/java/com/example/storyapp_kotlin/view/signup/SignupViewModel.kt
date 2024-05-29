package com.example.storyapp_kotlin.view.signup

import androidx.lifecycle.ViewModel
import com.example.storyapp_kotlin.data.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    suspend fun userSignup(name: String, email: String, password: String) =
        userRepository.userSignup(name, email, password)
}