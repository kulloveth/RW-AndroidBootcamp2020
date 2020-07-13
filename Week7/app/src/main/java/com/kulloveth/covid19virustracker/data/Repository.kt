package com.kulloveth.covid19virustracker.data

import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.kulloveth.covid19virustracker.data.db.CovidDatabase
import com.kulloveth.covid19virustracker.model.*

class Repository(private val database: CovidDatabase) {


    //fetch status from the internet
    suspend fun fetchStatus():Result<List<CountryStatus>> = try {
        val data = Injection.apiService.getStatusByCountry(200).data
        Success(data.status)
    }catch (error: Throwable) {
        Failure(error)
    }

    //fetch news from the internet
    suspend fun fetchNews():Result<List<Article>> = try{
        val data = Injection.newsApiService.getCovidNews("COVID", API_KEY).articles
        Success(data)
    }catch (error:Throwable){
        Failure(error)
    }


    //display status by paging to avoid overloading the adapter
    fun getStatus() = LivePagedListBuilder(
        database.getStatusDao().statusByCountry(), PagedList.Config.Builder()
            .setPageSize(PAGE_SIZE)
            .setEnablePlaceholders(ENABLE_PLACEHOLDER)
            .build()
    ).build()

    //display news by paging to avoid overloading the adapter
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