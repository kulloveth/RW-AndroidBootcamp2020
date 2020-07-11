package com.kulloveth.covid19virustracker

import androidx.lifecycle.ViewModelProvider
import com.kulloveth.covid19virustracker.api.buildApiService
import com.kulloveth.covid19virustracker.data.Repository
import com.kulloveth.covid19virustracker.db.StatusDatabase
import com.kulloveth.covid19virustracker.ui.ViewModelFactory

object Injection {
    private val apiService by lazy { buildApiService() }
    val db = StatusDatabase.invoke()
    // val remoteApi by lazy { Repository(apiService,db) }

    private fun provideRepository():Repository{
        return Repository(apiService,db)
    }

    fun provideViewModelFactory():ViewModelProvider.Factory{
        return ViewModelFactory(provideRepository())
    }
}