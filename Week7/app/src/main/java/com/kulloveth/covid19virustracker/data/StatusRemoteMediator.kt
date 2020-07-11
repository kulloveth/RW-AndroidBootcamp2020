package com.kulloveth.covid19virustracker.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.kulloveth.covid19virustracker.api.ApiService
import com.kulloveth.covid19virustracker.db.RemoteKey
import com.kulloveth.covid19virustracker.db.StatusDatabase
import com.kulloveth.covid19virustracker.model.CountryStatus
import retrofit2.HttpException
import java.io.IOException
import java.io.InvalidObjectException

private const val STATUS_STARTING_PAGE_INDEX = 1

@OptIn(ExperimentalPagingApi::class)
class StatusRemoteMediator(
    private val service: ApiService,
    private val statusDatabase: StatusDatabase
) : RemoteMediator<Int, CountryStatus>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CountryStatus>
    ): MediatorResult {

        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: STATUS_STARTING_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                    ?: // The LoadType is PREPEND so some data was loaded before,
                    // so we should have been able to get remote keys
                    // If the remoteKeys are null, then we're an invalid state and we have a bug
                    throw InvalidObjectException("Remote key and the prevKey should not be null")
                // If the previous key is null, then we can't request more data
                val prevKey = remoteKeys.prevKey
                if (prevKey == null) {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                remoteKeys.prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                if (remoteKeys?.nextKey == null) {
                    throw InvalidObjectException("Remote key should not be null for $loadType")
                }
                remoteKeys.nextKey
            }

        }

        try {
            val apiResponse = service.getStatusByCountry(state.config.pageSize, page)

            val status = apiResponse.data.rows
            val endOfPaginationReached = status.isEmpty()
            statusDatabase.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    statusDatabase.remoteKeysDao().clearRemoteKeys()
                    statusDatabase.getStatusDao().clearStatus()
                }
                val prevKey = if (page == STATUS_STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = status.map {
                    RemoteKey(statusId = it.country, prevKey = prevKey, nextKey = nextKey)
                }
                statusDatabase.remoteKeysDao().insertAll(keys)
                statusDatabase.getStatusDao().insert(status)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, CountryStatus>): RemoteKey? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { status ->
                // Get the remote keys of the last item retrieved
                statusDatabase.remoteKeysDao().remoteKeysStatusIdId(status.country)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, CountryStatus>): RemoteKey? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { status ->
                // Get the remote keys of the first items retrieved
                statusDatabase.remoteKeysDao().remoteKeysStatusIdId(status.country)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, CountryStatus>
    ): RemoteKey? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.country?.let { statusId ->
                statusDatabase.remoteKeysDao().remoteKeysStatusIdId(statusId)
            }
        }
    }

}