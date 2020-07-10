package com.kulloveth.covid19virustracker.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun buildClient(): OkHttpClient = OkHttpClient.Builder()
    .addInterceptor(HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }).build()

fun buildRetrofit(): Retrofit {
    val BASE_URL = "https://corona-virus-stats.herokuapp.com/api/"
    return Retrofit.Builder().client(buildClient()).baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun buildApiService() = buildRetrofit().create(ApiService::class.java)