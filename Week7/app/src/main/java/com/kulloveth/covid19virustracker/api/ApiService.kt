package com.kulloveth.covid19virustracker.api

import com.kulloveth.covid19virustracker.model.BaseResponse
import retrofit2.http.GET

interface ApiService {
    @GET("v1/cases/countries-search")
    suspend fun getStatusByCountry(): BaseResponse
}