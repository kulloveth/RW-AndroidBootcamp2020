package com.kulloveth.covid19virustracker.data

import androidx.lifecycle.ViewModelProvider
import com.kulloveth.covid19virustracker.api.buildApiService
import com.kulloveth.covid19virustracker.data.db.CovidDatabase
import com.kulloveth.covid19virustracker.ui.ViewModelFactory

object Injection {
    // build status api
    val statusApiService by lazy { buildApiService(  "https://corona.lmao.ninja/") }

    //build news api
    val newsApiService by lazy { buildApiService("https://newsapi.org/") }

    fun provideDb() = CovidDatabase.invoke()

    private fun provideRepository(): Repository {
        return Repository(provideDb().getStatusDao(), provideDb().getNewsDao())
    }

    fun provideViewModelFactory(): ViewModelProvider.Factory {
        return ViewModelFactory(provideRepository())
    }

}