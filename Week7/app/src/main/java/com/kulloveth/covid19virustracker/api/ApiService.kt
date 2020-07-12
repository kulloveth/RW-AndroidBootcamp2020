package com.kulloveth.covid19virustracker.api

import com.kulloveth.covid19virustracker.model.NewsBaseResponse
import com.kulloveth.covid19virustracker.model.StatusBaseResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("v1/cases/countries-search")
    suspend fun getStatusByCountry( @Query("limit") limit: Int): StatusBaseResponse

    @GET("v2/top-headlines")
    suspend fun getCovidNews(@Query("q")topic:String,@Query("apiKey")apiKey:String):NewsBaseResponse
}