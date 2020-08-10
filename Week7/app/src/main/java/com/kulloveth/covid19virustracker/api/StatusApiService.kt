package com.kulloveth.covid19virustracker.api

import com.kulloveth.covid19virustracker.model.NewsBaseResponse
import com.kulloveth.covid19virustracker.model.StatusResponse
import retrofit2.http.GET
import retrofit2.http.Query
//This class setsup result from endpoint
interface StatusApiService {
    /**
     * get a certain no of status from api
     */
    @GET("v2/countries")
    suspend fun getStatus(): List<StatusResponse>
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