package com.kulloveth.covid19virustracker.data

import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.kulloveth.covid19virustracker.Injection
import com.kulloveth.covid19virustracker.data.db.CovidDatabase

class Repository(private val database: CovidDatabase) {


    //fetch status from the internet
    suspend fun fetchStatus() = Injection.apiService.getStatusByCountry(200).data.status

    //fetch news from the internet
    suspend fun fetchNews() =
        Injection.newsApiService.getCovidNews("COVID", API_KEY).articles


    //display data by paging to avoid overloading the adapter
    fun getStatus() = LivePagedListBuilder(
        database.getStatusDao().statusByCountry(), PagedList.Config.Builder()
            .setPageSize(PAGE_SIZE)
            .setEnablePlaceholders(ENABLE_PLACEHOLDER)
            .build()
    ).build()

    fun getNews() = LivePagedListBuilder(
        database.getNewsDao().covidNews(), PagedList.Config.Builder()
            .setPageSize(PAGE_SIZE)
            .setEnablePlaceholders(ENABLE_PLACEHOLDER)
            .build()
    ).build()

    companion object {
        val API_KEY: String = com.kulloveth.covid19virustracker.BuildConfig.API_KEY
        private const val PAGE_SIZE = 30
        private const val ENABLE_PLACEHOLDER = true
    }


}