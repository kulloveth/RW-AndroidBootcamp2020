package com.kulloveth.covid19virustracker.api

import com.kulloveth.covid19virustracker.model.NewsBaseResponse
import com.kulloveth.covid19virustracker.model.StatusBaseResponse
import retrofit2.http.GET
import retrofit2.http.Query
//This class setsup result from endpoint
interface ApiService {
    /**
     * get a certain no of status from api using [limit]
     */
    @GET("v1/cases/countries-search")
    suspend fun getStatusByCountry(@Query("limit") limit: Int): StatusBaseResponse

    /**
     *   get news headlines for covid by [topic]
     *   using your personal [apiKey]
     */
    @GET("v2/top-headlines")
    suspend fun getCovidNews(
        @Query("q") topic: String,
        @Query("apiKey") apiKey: String
    ): NewsBaseResponse
}