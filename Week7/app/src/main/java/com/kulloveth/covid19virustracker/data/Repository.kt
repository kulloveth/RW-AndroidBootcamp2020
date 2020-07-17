package com.kulloveth.covid19virustracker.data

import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.work.*
import com.kulloveth.covid19virustracker.App
import com.kulloveth.covid19virustracker.data.db.CovidDatabase
import com.kulloveth.covid19virustracker.model.Article
import com.kulloveth.covid19virustracker.model.Failure
import com.kulloveth.covid19virustracker.model.Result
import com.kulloveth.covid19virustracker.model.Success
import com.kulloveth.covid19virustracker.worker.STATUS_NOTIFICATION_CHANNEL_NAME
import com.kulloveth.covid19virustracker.worker.StatusPeriodicWorker
import java.util.concurrent.TimeUnit

private const val TAG_SYNC_DATA = "TAG_SYNC_DATA"

class Repository(private val database: CovidDatabase) {

     fun fetchStatus() {

        // Create Network constraint
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val workManager = WorkManager.getInstance(App.getContext())


        val periodicSyncDataWork =
            PeriodicWorkRequest.Builder(StatusPeriodicWorker::class.java, 16, TimeUnit.MINUTES)
                .addTag(TAG_SYNC_DATA)
                .setConstraints(constraints) // setting a backoff on case the work needs to retry
                .setBackoffCriteria(
                    BackoffPolicy.LINEAR,
                    PeriodicWorkRequest.MIN_BACKOFF_MILLIS,
                    TimeUnit.MILLISECONDS
                )
                .build()
        workManager.enqueueUniquePeriodicWork(
            STATUS_NOTIFICATION_CHANNEL_NAME,
            ExistingPeriodicWorkPolicy.KEEP,  //Existing Periodic Work policy
            periodicSyncDataWork //work request
        )
    }


    //fetch news from the internet
    suspend fun fetchNews(): Result<List<Article>> = try {
        val data = Injection.newsApiService.getCovidNews("COVID", API_KEY).articles
        Success(data)
    } catch (error: Throwable) {
        Failure(error)
    }


    //display status by paging to avoid overloading the adapter
    fun getStatus() = LivePagedListBuilder(
        database.getStatusDao().statusByCountry(), PagedList.Config.Builder()
            .setPageSize(PAGE_SIZE)
            .setEnablePlaceholders(ENABLE_PLACEHOLDER)
            .build()
    ).build()

    //display news by paging to avoid overloading the adapter
    fun getNews() = LivePagedListBuilder(
        database.getNewsDao().covidNews(), PagedList.Config.Builder()
            .setPageSize(PAGE_SIZE)
            .setEnablePlaceholders(ENABLE_PLACEHOLDER)
            .build()
    ).build()

    companion object {
        val API_KEY: String = com.kulloveth.covid19virustracker.BuildConfig.API_KEY
        private const val PAGE_SIZE = 30
        private const val ENABLE_PLACEHOLDER = true
    }


}