package com.kulloveth.covid19virustracker

import android.app.Application
import com.facebook.stetho.Stetho
import com.kulloveth.covid19virustracker.di.provideNetwork
import com.kulloveth.covid19virustracker.di.provideRepository
import com.kulloveth.covid19virustracker.di.provideRoomDatabase
import com.kulloveth.covid19virustracker.di.provideViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {

    companion object {

        private lateinit var instance: App

        fun getContext() = instance.applicationContext

    }

    override fun onCreate() {
        instance = this
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(listOf(provideRoomDatabase, provideRepository, provideViewModel, provideNetwork))
        }
        Stetho.initializeWithDefaults(this)
    }
}