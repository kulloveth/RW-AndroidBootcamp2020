package com.kulloveth.moviesapp

import android.app.Application
import com.facebook.stetho.Stetho

class MovieApplication: Application() {

    companion object{
        lateinit var instance:MovieApplication
        fun getContext() = instance.applicationContext


    }

    override fun onCreate() {
        instance = this
        super.onCreate()
        Stetho.initializeWithDefaults(this)

    }


}