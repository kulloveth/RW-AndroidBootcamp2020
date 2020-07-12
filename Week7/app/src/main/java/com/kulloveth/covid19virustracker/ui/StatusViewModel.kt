package com.kulloveth.covid19virustracker.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.kulloveth.covid19virustracker.data.Repository
import com.kulloveth.covid19virustracker.model.CountryStatus
import kotlinx.coroutines.flow.Flow

class StatusViewModel(private val repository: Repository) : ViewModel() {

    private var statusResult: Flow<PagingData<CountryStatus>>? = null
    val sstatusLiveData = MutableLiveData<CountryStatus>()

    fun getStatus(): Flow<PagingData<CountryStatus>> {
        val lastResult = statusResult
        if (lastResult != null) {
            return lastResult
        }
        val newResult = repository.getStatus().cachedIn(viewModelScope)
        statusResult = newResult
        return newResult
    }

    fun setUpStatus(countryStatus: CountryStatus) {
        sstatusLiveData.value = countryStatus
    }


}