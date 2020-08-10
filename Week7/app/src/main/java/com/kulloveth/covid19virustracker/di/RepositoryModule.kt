package com.kulloveth.covid19virustracker.di

import com.kulloveth.covid19virustracker.data.Repository
import org.koin.dsl.module

val provideRepository = module {
    single {
        Repository(get(),get())
    }
}