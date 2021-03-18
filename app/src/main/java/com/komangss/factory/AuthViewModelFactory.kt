package com.komangss.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.komangss.repository.AuthRepository
import com.komangss.ui.login.viewmodel.LoginViewModel

class AuthViewModelFactory private constructor(private val authRepository: AuthRepository) : ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var instance : AuthViewModelFactory? = null

        fun getInstance(authRepository: AuthRepository) : AuthViewModelFactory =
            instance ?: AuthViewModelFactory(authRepository)
    }
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(authRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel class : $modelClass")
        }
    }
}