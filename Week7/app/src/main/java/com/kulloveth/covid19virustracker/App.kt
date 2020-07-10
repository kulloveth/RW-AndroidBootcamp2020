package com.kulloveth.covid19virustracker

import android.app.Application
import com.kulloveth.covid19virustracker.api.buildApiService
import com.kulloveth.covid19virustracker.data.Repository

class App : Application() {

    companion object {

        private lateinit var instance: App

        private val apiService by lazy { buildApiService() }

        val remoteApi by lazy { Repository(apiService) }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}