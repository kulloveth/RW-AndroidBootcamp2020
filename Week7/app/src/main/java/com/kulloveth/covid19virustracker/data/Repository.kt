package com.kulloveth.covid19virustracker.data

import com.kulloveth.covid19virustracker.api.ApiService
import com.kulloveth.covid19virustracker.utils.Resource

class Repository(val apiService: ApiService){

     suspend fun getStatusByCountry() = try{
        val data = apiService.getStatusByCountry()
        Resource.success(data.data.rows)
    }catch (error:Throwable){
        Resource.error("error getting countries",null)
    }

}