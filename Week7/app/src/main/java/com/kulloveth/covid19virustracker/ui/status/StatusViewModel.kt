package com.kulloveth.covid19virustracker.ui.status

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.kulloveth.covid19virustracker.data.Injection
import com.kulloveth.covid19virustracker.data.Repository
import com.kulloveth.covid19virustracker.data.db.StatusEntity
import com.kulloveth.covid19virustracker.ui.base.BaseViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch

class StatusViewModel(private val repository: Repository) : BaseViewModel(repository) {


    val sstatusLiveData = MutableLiveData<StatusEntity>()
    private val newStatusLiveData = MutableLiveData<List<StatusEntity>>()


    //display status by paging to avoid overloading the adapter
    fun getStatus() =LivePagedListBuilder(
        Injection.provideDb().getStatusDao().statusByCountry(), PagedList.Config.Builder()
            .setPageSize(StatusViewModel.PAGE_SIZE)
            .setEnablePlaceholders(StatusViewModel.ENABLE_PLACEHOLDER)
            .build()
    ).build()
    init {
        repository.fetchStatus()
    }

    @FlowPreview
    fun getNewStatus():LiveData<List<StatusEntity>>{
        viewModelScope.launch {
          val status =  repository.fetchStatusFromRoom()
            newStatusLiveData.postValue(status)
        }
        return newStatusLiveData
    }

    //setup status to pass between fragments
    fun setUpStatus(statusEntity: StatusEntity) {
        sstatusLiveData.value = statusEntity
    }


    companion object {
         const val PAGE_SIZE = 30
        const val NEWS_PAGE_SIZE = 20
        const val ENABLE_PLACEHOLDER = true
    }

}