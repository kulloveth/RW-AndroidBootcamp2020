package com.kulloveth.covid19virustracker.worker

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.kulloveth.covid19virustracker.App
import com.kulloveth.covid19virustracker.R
import com.kulloveth.covid19virustracker.data.Injection
import com.kulloveth.covid19virustracker.data.db.CountryInfoEntity
import com.kulloveth.covid19virustracker.data.db.StatusEntity
import com.kulloveth.covid19virustracker.model.*

class StatusPeriodicWorker(val context: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(context, workerParameters) {

    private val TAG = StatusPeriodicWorker::class.java.simpleName
    override suspend fun doWork(): Result {
        val status = fetchStatus()
        //insert status to database
        return if (status is Success) {
            val newStatus = mutableListOf<StatusEntity>()
            status.data.forEach {
                val countryInfoEntity = CountryInfoEntity(it.countryInfo._id,it.countryInfo.flag)
                val data = StatusEntity(
                    it.country,
                    countryInfoEntity,
                    it.cases,
                    it.todayCases,
                    it.deaths,
                    it.todayDeaths,
                    it.recovered,
                    it.todayRecovered,
                    it.critical,
                    it.active
                )
                newStatus.add(data)
            }

            Injection.provideDb().getStatusDao().insert(newStatus)
            makeStatusNotification(context.getString(R.string.status_message))
            Result.success()
        } else {
            Log.d(TAG, "error fetching status")
            Result.failure()
        }
    }

    //check for internet connection and fetch status from api
    private suspend fun fetchStatus(): com.kulloveth.covid19virustracker.model.Result<List<StatusResponse>>? {
        var result: com.kulloveth.covid19virustracker.model.Result<List<StatusResponse>>? = null
        if (isNetworkAvailable(context)) {
            try {
                val data = Injection.statusApiService.getStatus()
                Log.d("workers","$data")
                result = Success(data)
            } catch (error: Throwable) {
                result = Failure(error)
            }
        } else {
            Toast.makeText(
                context,
                context.resources.getString(R.string.no_internet),
                Toast.LENGTH_SHORT
            ).show()
        }
        return result
    }
}