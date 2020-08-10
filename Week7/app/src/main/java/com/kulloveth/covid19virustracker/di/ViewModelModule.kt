package com.kulloveth.covid19virustracker.di

import com.kulloveth.covid19virustracker.ui.news.NewsViewModel
import com.kulloveth.covid19virustracker.ui.status.StatusViewModel
import org.koin.dsl.module
import kotlin.math.sin


val provideViewModel = module {
    factory {
        NewsViewModel(get())
    }

    //just one instance so as to enable shared viewModel for the status detail
    single {
        StatusViewModel(get())
    }
}