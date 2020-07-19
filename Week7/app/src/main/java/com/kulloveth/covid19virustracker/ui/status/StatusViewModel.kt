package com.kulloveth.covid19virustracker.ui.status

import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.kulloveth.covid19virustracker.data.Injection
import com.kulloveth.covid19virustracker.model.CountryStatus
import com.kulloveth.covid19virustracker.ui.base.BaseViewModel

class StatusViewModel : BaseViewModel() {


    val sstatusLiveData = MutableLiveData<CountryStatus>()
    //display status by paging to avoid overloading the adapter
    fun getStatus() = LivePagedListBuilder(
        Injection.db.getStatusDao().statusByCountry(), PagedList.Config.Builder()
            .setPageSize(PAGE_SIZE)
            .setEnablePlaceholders(ENABLE_PLACEHOLDER)
            .build()
    ).build()

init {
    Injection.provideRepository().fetchStatus()
}

    //setup status to pass between fragments
    fun setUpStatus(countryStatus: CountryStatus) {
        sstatusLiveData.value = countryStatus
    }



    companion object {
        private const val PAGE_SIZE = 30
         const val NEWS_PAGE_SIZE = 20
         const val ENABLE_PLACEHOLDER = true
    }

}