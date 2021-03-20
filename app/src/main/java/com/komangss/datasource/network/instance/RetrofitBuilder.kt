package com.komangss.datasource.network.instance

import com.komangss.datasource.network.service.AuthServices
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    private const val BASE_URL = "http://192.168.0.102"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val authServices = retrofit.create(AuthServices::class.java)
}