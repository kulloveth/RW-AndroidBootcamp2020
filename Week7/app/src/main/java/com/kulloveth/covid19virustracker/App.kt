package com.kulloveth.covid19virustracker

import android.app.Application
import com.facebook.stetho.Stetho

class App : Application() {

    companion object {

        private lateinit var instance: App

        fun getContext() = instance.applicationContext

    }

    override fun onCreate() {
        instance = this
        super.onCreate()
        Stetho.initializeWithDefaults(this)

    }
}