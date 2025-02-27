package com.yudhi.data.data.api


import com.chuckerteam.chucker.api.ChuckerInterceptor

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    const val BASE_URL = Constants.BASE_URL

    private lateinit var contextProvider: ContextProvider

    fun initialize(contextProvider: ContextProvider) {
        this.contextProvider = contextProvider
    }

    private val logging: HttpLoggingInterceptor
        get() {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            return httpLoggingInterceptor.apply {
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            }
        }
    private val client: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(ChuckerInterceptor(contextProvider.getContext()))
            .build()
    }

    val instance: com.yudhi.data.data.remote.ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        retrofit.create(com.yudhi.data.data.remote.ApiService::class.java)
    }

}