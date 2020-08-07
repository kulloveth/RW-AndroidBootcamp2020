package com.kulloveth.covid19virustracker.di

import com.kulloveth.covid19virustracker.api.NewsApiService
import com.kulloveth.covid19virustracker.api.StatusApiService
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
            "https://corona.lmao.ninja/"
    }
    single {
        httpLoggingInterceptor()
    }
    single {
        okHttpBuilder()
    }
    single {
        buildStatusApiService(get(named("STATUS_BASE_URL")))
    }
    single { buildNewsApiService(get(named("NEWS_BASE_URL"))) }

}


private fun httpLoggingInterceptor() = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}

private fun okHttpBuilder() = OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor())
    .build()

private fun retrofit(base: String) =
    Retrofit.Builder()
        .client(okHttpBuilder())
        .baseUrl(base)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

private fun buildStatusApiService(baseUrl: String) =
    retrofit(baseUrl).create(StatusApiService::class.java)

private fun buildNewsApiService(baseUrl: String) =
    retrofit(baseUrl).create(NewsApiService::class.java)
