package com.kulloveth.covid19virustracker.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun buildClient(): OkHttpClient = OkHttpClient.Builder()
    .addInterceptor(HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }).build()

fun buildRetrofit(baseUrl:String): Retrofit {
    return Retrofit.Builder().client(buildClient()).baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun buildApiService(baseUrl: String) = buildRetrofit(baseUrl).create(ApiService::class.java)