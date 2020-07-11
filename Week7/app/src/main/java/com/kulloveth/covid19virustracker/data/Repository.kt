package com.kulloveth.covid19virustracker.data

import androidx.paging.*
import com.kulloveth.covid19virustracker.api.ApiService
import com.kulloveth.covid19virustracker.db.StatusDatabase
import com.kulloveth.covid19virustracker.model.CountryStatus
import kotlinx.coroutines.flow.Flow

class Repository( private val service: ApiService,
                        private val database: StatusDatabase
) {

    /**
     * Search repositories whose names match the query, exposed as a stream of data that will emit
     * every time we get more data from the network.
     */
    fun getStatus(): Flow<PagingData<CountryStatus>> {

        val pagingSourceFactory = { database.getStatusDao().statusByCountry() }

        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE),
            remoteMediator = StatusRemoteMediator(
                service,
                database
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 30
    }

}