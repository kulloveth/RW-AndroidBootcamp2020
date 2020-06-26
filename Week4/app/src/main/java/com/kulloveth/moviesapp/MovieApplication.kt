package com.kulloveth.moviesapp

import android.app.Application

class MovieApplication: Application() {

    companion object{
        lateinit var instance:MovieApplication
        fun getContext() = instance.applicationContext
    }

    override fun onCreate() {
        instance = this
        super.onCreate()

    }

}