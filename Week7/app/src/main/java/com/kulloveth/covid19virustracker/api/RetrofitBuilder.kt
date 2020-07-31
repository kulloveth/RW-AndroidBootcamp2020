package com.kulloveth.covid19virustracker.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//client to log different server response
fun buildClient(): OkHttpClient = OkHttpClient.Builder()
    .addInterceptor(HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }).build()


//retrofit builder method with httpclient and gson converter
fun buildRetrofit(baseUrl: String): Retrofit {
    return Retrofit.Builder().client(buildClient()).baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun buildApiService(baseUrl: String) = buildRetrofit(baseUrl).create(StatusApiService::class.java)