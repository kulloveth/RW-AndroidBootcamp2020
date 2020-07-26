package com.kulloveth.covid19virustracker.data

import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.paging.toLiveData
import androidx.work.*
import com.kulloveth.covid19virustracker.App
import com.kulloveth.covid19virustracker.data.db.StatusDao
import com.kulloveth.covid19virustracker.data.db.StatusEntity
import com.kulloveth.covid19virustracker.ui.status.StatusViewModel
import com.kulloveth.covid19virustracker.ui.status.StatusViewModel.Companion.PAGE_SIZE
import com.kulloveth.covid19virustracker.worker.NOTIFICATION_CHANNEL_NAME
import com.kulloveth.covid19virustracker.worker.NewsPeriodicWorker
import com.kulloveth.covid19virustracker.worker.StatusPeriodicWorker
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.toList
import java.util.concurrent.TimeUnit

private const val TAG_SYNC_DATA = "TAG_SYNC_DATA"
private const val NEWS_TAG_SYNC_DATA = "NEWS_SYNC_DATA"

open class Repository(private val statusDao: StatusDao){
    // Create Network constraint
    val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .setRequiresBatteryNotLow(true)
        .build()
    val workManager = WorkManager.getInstance(App.getContext())

    //build and enqeue status work
    fun fetchStatus() {
        val periodicSyncDataWork =
            PeriodicWorkRequest.Builder(StatusPeriodicWorker::class.java, 1, TimeUnit.HOURS)
                .addTag(TAG_SYNC_DATA)
                .setConstraints(constraints)
                // setting a backoff on case the work needs to retry
                .setBackoffCriteria(BackoffPolicy.LINEAR, PeriodicWorkRequest.MIN_BACKOFF_MILLIS, TimeUnit.MILLISECONDS)
                .build()
        workManager.enqueueUniquePeriodicWork("Status", ExistingPeriodicWorkPolicy.KEEP,  //Existing Periodic Work policy
            periodicSyncDataWork //work request
        )
    }


    //build and engueue news work
    fun fetchNews() {
        val periodicSynDataWork =
            PeriodicWorkRequest.Builder(NewsPeriodicWorker::class.java, 15, TimeUnit.MINUTES)
                .addTag(NEWS_TAG_SYNC_DATA)
                .setConstraints(constraints)
                // setting a backoff on case the work needs to retry
                .setBackoffCriteria(
                    BackoffPolicy.LINEAR,
                    PeriodicWorkRequest.MIN_BACKOFF_MILLIS,
                    TimeUnit.MILLISECONDS
                )
                .build()
        workManager.enqueueUniquePeriodicWork(
           "News",
            ExistingPeriodicWorkPolicy.KEEP,  //Existing Periodic Work policy
            periodicSynDataWork //work request
        )
    }

    @FlowPreview
    suspend fun fetchStatusFromRoom()=
        statusDao.getNewStatus().flatMapConcat { it.asFlow() }.toList()
}