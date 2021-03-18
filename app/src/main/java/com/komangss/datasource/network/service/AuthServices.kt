package com.komangss.datasource.network.service

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthServices {
    @FormUrlEncoded
    @POST("ukaka/auth/login")
    suspend fun login(
        @Field("username") username : String,
        @Field("password") password : String,
        @Field("role") role : String
    ) : String

    @FormUrlEncoded
    @POST("ukaka/auth/registerMasyarakat")
    suspend fun registerMasyarakat(
        @Field("username") username : String,
        @Field("password") password : String,
        @Field("nik") nik : String,
        @Field("name") name : String,
        @Field("telp") telp : String,
    ) : String


}