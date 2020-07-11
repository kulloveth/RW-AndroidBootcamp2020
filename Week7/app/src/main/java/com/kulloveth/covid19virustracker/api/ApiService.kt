package com.kulloveth.covid19virustracker.api

import com.kulloveth.covid19virustracker.model.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("v1/cases/countries-search")
    suspend fun getStatusByCountry( @Query("limit") limit: Int, @Query("page") page: Int): BaseResponse
}