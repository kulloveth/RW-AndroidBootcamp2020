package com.kulloveth.covid19virustracker.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.kulloveth.covid19virustracker.data.Repository
import com.kulloveth.covid19virustracker.model.Article
import com.kulloveth.covid19virustracker.model.CountryStatus

class NewsViewModel(private val repository: Repository) : ViewModel() {



    fun getNews(): LiveData<PagedList<Article>> {
        return repository.getNews()
    }


}