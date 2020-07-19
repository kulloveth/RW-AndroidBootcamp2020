package com.kulloveth.covid19virustracker.data

import androidx.lifecycle.ViewModelProvider
import com.kulloveth.covid19virustracker.api.buildApiService
import com.kulloveth.covid19virustracker.data.db.CovidDatabase
import com.kulloveth.covid19virustracker.ui.ViewModelFactory

object Injection {
    // build status api
    val apiService by lazy { buildApiService("https://corona-virus-stats.herokuapp.com/api/") }
    //build news api
    val newsApiService by lazy { buildApiService("https://newsapi.org/") }

     val db = CovidDatabase.invoke()

     fun provideRepository():Repository{
        return Repository()
    }

    fun provideViewModelFactory():ViewModelProvider.Factory{
        return ViewModelFactory()
    }

}