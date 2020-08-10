package com.kulloveth.covid19virustracker.di

import androidx.room.Room
import com.kulloveth.covid19virustracker.data.db.CovidDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val provideRoomDatabase = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            CovidDatabase::class.java,
            "covid_database"
        ).build()
    }
    single {
        get<CovidDatabase>().getStatusDao()
    }
    single {
        get<CovidDatabase>().getNewsDao()
    }
}

