package com.kulloveth.covid19virustracker.worker

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.kulloveth.covid19virustracker.BuildConfig.API_KEY
import com.kulloveth.covid19virustracker.R
import com.kulloveth.covid19virustracker.api.NetworkStatusChecker
import com.kulloveth.covid19virustracker.data.Injection
import com.kulloveth.covid19virustracker.model.*

class NewsPeriodicWorker(val context: Context, workerParameters: WorkerParameters) :

CoroutineWorker(context, workerParameters) {
    private val TAG = NewsPeriodicWorker::class.java.simpleName
    override suspend fun doWork(): Result {
        val news = fetchNews()
        //insert news to database
        return if (news is Success) {
            Injection.db.getNewsDao().insert(news.data)
            makeStatusNotification(context.getString(R.string.news_message))
            Result.success()
        } else {
            Log.d(TAG, "error fetching news")
            Result.failure()
        }
    }

    private suspend fun fetchNews(): com.kulloveth.covid19virustracker.model.Result<List<Article>> =
        try {
            val data = Injection.newsApiService.getCovidNews("COVID", API_KEY)
            Success(data.articles)
        } catch (error: Throwable) {
            Failure(error)
        }
}