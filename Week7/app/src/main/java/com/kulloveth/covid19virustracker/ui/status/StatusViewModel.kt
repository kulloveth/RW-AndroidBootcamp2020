package com.kulloveth.covid19virustracker.ui.status

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.kulloveth.covid19virustracker.data.Repository
import com.kulloveth.covid19virustracker.model.CountryStatus
import com.kulloveth.covid19virustracker.ui.base.BaseViewModel

class StatusViewModel(private val repository: Repository) : BaseViewModel(repository) {



    private var statusResult: LiveData<PagedList<CountryStatus>> = MutableLiveData()
    val sstatusLiveData = MutableLiveData<CountryStatus>()

    //fetch paged status from room
    fun getStatus(): LiveData<PagedList<CountryStatus>> {
        statusResult = repository.getStatus()
        return statusResult
    }

    //setup status to pass between fragments
    fun setUpStatus(countryStatus: CountryStatus) {
        sstatusLiveData.value = countryStatus
    }


}