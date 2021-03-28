package com.komangss.datasource.network.service

import okhttp3.MultipartBody
import retrofit2.http.Field
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface PengaduanServices {
    @Multipart
    @POST("ukaka/test/image")
    suspend fun createPengaduan(
        @Part image : MultipartBody.Part,
        @Field("isiPengaduan") isiPengaduan : String
    ) : String
}