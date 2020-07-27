package com.kulloveth.covid19virustracker.ui.news

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.kulloveth.covid19virustracker.data.Injection
import com.kulloveth.covid19virustracker.data.Repository
import com.kulloveth.covid19virustracker.data.db.NewsEntity
import com.kulloveth.covid19virustracker.ui.base.BaseViewModel
import com.kulloveth.covid19virustracker.ui.status.StatusViewModel

/**
 * News ViewModel extending from the [BaseViewModel]
 * */
class NewsViewModel(repository: Repository) : BaseViewModel(repository) {

    // val loadingStaus = MutableLiveData<Boolean>()
    init {
        repository.fetchNews()
    }

    //display news by paging to avoid overloading the adapter
    fun getNews(): LiveData<PagedList<NewsEntity>> {
        //loadingStaus.postValue(true)
        val news: LiveData<PagedList<NewsEntity>> = LivePagedListBuilder(
            Injection.provideDb().getNewsDao().covidNews(), PagedList.Config.Builder()
                .setPageSize(StatusViewModel.NEWS_PAGE_SIZE)
                .setEnablePlaceholders(StatusViewModel.ENABLE_PLACEHOLDER)
                .build()
        ).build()
        // loadingStaus.postValue(false)
        return news
    }


}