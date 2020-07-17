package com.kulloveth.covid19virustracker.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.kulloveth.covid19virustracker.data.Injection
import com.kulloveth.covid19virustracker.model.CountryStatus
import com.kulloveth.covid19virustracker.model.Failure
import com.kulloveth.covid19virustracker.model.Success

class StatusPeriodicWorker(context: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(context, workerParameters) {
    private val TAG = StatusPeriodicWorker::class.java.simpleName
    override suspend fun doWork(): Result {
        val status = fetchStatus()
        //insert status to database
        return if (status is Success) {
            Injection.db.getStatusDao().insert(status.data)
            makeStatusNotification("data inserted")
            Result.success()
        } else {
            Log.d(TAG, "error fetching status")
            Result.failure()
        }
    }

    private suspend fun fetchStatus(): com.kulloveth.covid19virustracker.model.Result<List<CountryStatus>> =
        try {
            val data = Injection.apiService.getStatusByCountry(200).data
            Success(data.status)
        } catch (error: Throwable) {
            Failure(error)
        }
}