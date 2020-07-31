package com.kulloveth.covid19virustracker.data

import com.kulloveth.covid19virustracker.api.buildApiService

object Injection {

    // build status api
    val statusApiService by lazy { buildApiService(  "https://corona.lmao.ninja/") }


    //build news api
    val newsApiService by lazy { buildApiService("https://newsapi.org/") }





}