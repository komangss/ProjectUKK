package com.komangss.datasource.network.service

import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.POST

interface AuthServices {
    @POST("/auth/login")
    suspend fun login(
        @Field("username") username : String,
        @Field("password") password : String,
        @Field("role") role : String
    ) : String
}