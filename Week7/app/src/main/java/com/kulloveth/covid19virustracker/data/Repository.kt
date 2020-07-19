package com.kulloveth.covid19virustracker.data

import androidx.work.*
import com.kulloveth.covid19virustracker.App
import com.kulloveth.covid19virustracker.worker.NOTIFICATION_CHANNEL_NAME
import com.kulloveth.covid19virustracker.worker.NewsPeriodicWorker
import com.kulloveth.covid19virustracker.worker.StatusPeriodicWorker
import java.util.concurrent.TimeUnit

private const val TAG_SYNC_DATA = "TAG_SYNC_DATA"
private const val NEWS_TAG_SYNC_DATA = "NEWS_SYNC_DATA"

class Repository{
    // Create Network constraint
    val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()
    val workManager = WorkManager.getInstance(App.getContext())

//init {
//    fetchStatus()
//    fetchNews()
//}
    //build and enqeue status work
    fun fetchStatus() {
        val periodicSyncDataWork =
            PeriodicWorkRequest.Builder(StatusPeriodicWorker::class.java, 16, TimeUnit.MINUTES)
                .addTag(TAG_SYNC_DATA)
                .setConstraints(constraints) // setting a backoff on case the work needs to retry
                .setBackoffCriteria(BackoffPolicy.LINEAR, PeriodicWorkRequest.MIN_BACKOFF_MILLIS, TimeUnit.MILLISECONDS)
                .build()
        workManager.enqueueUniquePeriodicWork("Status", ExistingPeriodicWorkPolicy.KEEP,  //Existing Periodic Work policy
            periodicSyncDataWork //work request
        )
    }


    //build and engueue news work
    fun fetchNews() {
        val periodicSynDataWork =
            PeriodicWorkRequest.Builder(NewsPeriodicWorker::class.java, 20, TimeUnit.MINUTES)
                .addTag(NEWS_TAG_SYNC_DATA)
                .setConstraints(constraints) // setting a backoff on case the work needs to retry
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
}