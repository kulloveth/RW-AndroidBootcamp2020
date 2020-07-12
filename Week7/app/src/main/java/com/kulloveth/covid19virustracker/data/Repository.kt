package com.kulloveth.covid19virustracker.data

import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.kulloveth.covid19virustracker.Injection
import com.kulloveth.covid19virustracker.data.db.StatusDatabase

class Repository(private val database: StatusDatabase) {


    //fetch status from the internet
    suspend fun fetchStatus() = Injection.apiService.getStatusByCountry(200).data.status


    //display data by paging to avoid overloading the adapter
    fun getStatus() = LivePagedListBuilder(
        database.getStatusDao().statusByCountry(), PagedList.Config.Builder()
            .setPageSize(PAGE_SIZE)
            .setEnablePlaceholders(ENABLE_PLACEHOLDER)
            .build()
    ).build()

    companion object {
        private const val PAGE_SIZE = 30
        private const val ENABLE_PLACEHOLDER = true
    }



}