package com.komangss.datasource.network.instance

import com.komangss.datasource.network.service.AuthServices
import com.komangss.datasource.network.service.PengaduanServices
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Multipart

object RetrofitBuilder {
    private const val BASE_URL = "http://192.168.42.113"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val authServices = retrofit.create(AuthServices::class.java)
    val pengaduanServices = retrofit.create(PengaduanServices::class.java)
}