package com.kulloveth.covid19virustracker.ui.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.kulloveth.covid19virustracker.data.Repository
import com.kulloveth.covid19virustracker.model.Article
import com.kulloveth.covid19virustracker.model.CountryStatus
import com.kulloveth.covid19virustracker.ui.BaseViewModel

class NewsViewModel(private val repository: Repository) : BaseViewModel(repository) {



    fun getNews(): LiveData<PagedList<Article>> {
        return repository.getNews()
    }


}