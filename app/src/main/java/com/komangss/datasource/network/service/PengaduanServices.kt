package com.komangss.datasource.network.service

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.http.Field
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface PengaduanServices {
    @Multipart
    @POST("ukaka/test/image")
    suspend fun createPengaduan(
        @Part image : MultipartBody.Part,
        @Part("isiPengaduan") isiPengaduan : RequestBody
    ) : String
}