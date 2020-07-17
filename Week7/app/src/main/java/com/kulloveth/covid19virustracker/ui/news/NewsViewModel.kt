package com.kulloveth.covid19virustracker.ui.news

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.kulloveth.covid19virustracker.data.Repository
import com.kulloveth.covid19virustracker.model.Article
import com.kulloveth.covid19virustracker.ui.base.BaseViewModel
/**
 * News ViewModel extending from the [BaseViewModel]
 * */
class NewsViewModel(private val repository: Repository) : BaseViewModel(repository) {


    //fetch paged news from room
    fun getNews(): LiveData<PagedList<Article>> {
        return repository.getNews()
    }


}