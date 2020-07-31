package com.kulloveth.covid19virustracker.di

import com.kulloveth.covid19virustracker.api.StatusApiService
import com.kulloveth.covid19virustracker.api.NewsApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val provideNetwork = module {
    single(named("NEWS_BASE_URL")) {
        "https://newsapi.org/"
    }

    single(named("STATUS_BASE_URL")) {
        "https://corona-virus-stats.herokuapp.com/api/"
    }
    single {
        http()
    }
    single {
        okHttpBuilder()
    }
    single {
        StatusApiService
    }
    single { NewsApiService }

}


fun http() = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}

fun okHttpBuilder() = OkHttpClient.Builder().addInterceptor(http())
    .build()

fun retrofit(base: String) =
    Retrofit.Builder()
        .client(okHttpBuilder())
        .baseUrl(base)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

fun buildStatusApiService(baseUrl: String) = retrofit(baseUrl).create(StatusApiService::class.java)

fun buildNewsApiService(baseUrl: String) = retrofit(baseUrl).create(NewsApiService::class.java)

val StatusApiService by lazy { buildStatusApiService("https://corona-virus-stats.herokuapp.com/api/") }

//build news api
val NewsApiService by lazy { buildNewsApiService("https://newsapi.org/") }