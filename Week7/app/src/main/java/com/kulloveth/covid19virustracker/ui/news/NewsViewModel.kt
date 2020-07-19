package com.kulloveth.covid19virustracker.ui.news

import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.kulloveth.covid19virustracker.data.Injection
import com.kulloveth.covid19virustracker.ui.base.BaseViewModel
import com.kulloveth.covid19virustracker.ui.status.StatusViewModel

/**
 * News ViewModel extending from the [BaseViewModel]
 * */
class NewsViewModel : BaseViewModel() {

    init {
                   Injection.provideRepository().fetchNews()
    }
    //display news by paging to avoid overloading the adapter
    fun getNews() = LivePagedListBuilder(
        Injection.db.getNewsDao().covidNews(), PagedList.Config.Builder()
            .setPageSize(StatusViewModel.NEWS_PAGE_SIZE)
            .setEnablePlaceholders(StatusViewModel.ENABLE_PLACEHOLDER)
            .build()
    ).build()


}