package com.kulloveth.covid19virustracker.ui.status

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.kulloveth.covid19virustracker.data.Repository
import com.kulloveth.covid19virustracker.model.CountryStatus
import com.kulloveth.covid19virustracker.ui.BaseViewModel
import com.kulloveth.covid19virustracker.utils.ProgressListener

class StatusViewModel(private val repository: Repository) : BaseViewModel(repository) {
    private var progressListener: ProgressListener? = null

    fun setUpProgress(progressListener: ProgressListener) {
        this.progressListener = progressListener
    }

    private var statusResult: LiveData<PagedList<CountryStatus>> = MutableLiveData()
    val sstatusLiveData = MutableLiveData<CountryStatus>()

    fun getStatus(): LiveData<PagedList<CountryStatus>> {
        progressListener?.loading()
        statusResult = repository.getStatus()
        progressListener?.success()
        return statusResult
    }

    fun setUpStatus(countryStatus: CountryStatus) {
        sstatusLiveData.value = countryStatus
    }


}