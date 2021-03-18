package com.komangss.repository

import com.komangss.datasource.network.service.AuthServices
import com.komangss.util.Resource

class AuthRepository private constructor(
    private val authServices: AuthServices
) {
    companion object {
        @Volatile
        private var instance : AuthRepository? = null

        fun getInstance(authServices: AuthServices) : AuthRepository =
            instance ?: AuthRepository(authServices)
    }

    suspend fun sendLoginRequest(username : String, password : String, role : String) : Resource<String> {
        return try {
            Resource.Success(data = authServices.login(username, password, role))
        } catch (e : Exception) {
            Resource.Error(e)
        }
    }
}