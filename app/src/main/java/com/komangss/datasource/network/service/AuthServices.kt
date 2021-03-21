package com.komangss.datasource.network.service

import okhttp3.MultipartBody
import retrofit2.http.*

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
        @Field("nama") name : String,
        @Field("telp") telp : String,
    ) : String

    @Multipart
    @POST("ukaka/test/image")
    suspend fun uploadImage(
        @Part image : MultipartBody.Part
    ) : String
}