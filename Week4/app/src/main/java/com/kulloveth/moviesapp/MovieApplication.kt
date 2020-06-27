package com.kulloveth.moviesapp

import android.app.Application
import com.facebook.stetho.Stetho
import com.kulloveth.moviesapp.repository.Injection
import com.kulloveth.moviesapp.room.MovieDatabse

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