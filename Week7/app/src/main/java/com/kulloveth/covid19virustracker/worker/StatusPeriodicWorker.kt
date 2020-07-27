package com.kulloveth.covid19virustracker.worker

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.kulloveth.covid19virustracker.R
import com.kulloveth.covid19virustracker.data.Injection
import com.kulloveth.covid19virustracker.data.db.StatusEntity
import com.kulloveth.covid19virustracker.model.CountryStatus
import com.kulloveth.covid19virustracker.model.Failure
import com.kulloveth.covid19virustracker.model.Success

class StatusPeriodicWorker(val context: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(context, workerParameters) {

    private val TAG = StatusPeriodicWorker::class.java.simpleName
    override suspend fun doWork(): Result {
        val status = fetchStatus()
        //insert status to database
        return if (status is Success) {
            val newStatus = mutableListOf<StatusEntity>()
            status.data.forEach {
                val data = StatusEntity(
                    it.country,
                    it.country_abbreviation,
                    it.total_cases,
                    it.new_cases,
                    it.total_deaths,
                    it.new_deaths,
                    it.total_recovered,
                    it.active_cases,
                    it.serious_critical,
                    it.cases_per_mill_pop,
                    it.flag
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
    private suspend fun fetchStatus(): com.kulloveth.covid19virustracker.model.Result<List<CountryStatus>>? {
        var result: com.kulloveth.covid19virustracker.model.Result<List<CountryStatus>>? = null
        if (isNetworkAvailable(context)) {
            try {
                val data = Injection.apiService.getStatusByCountry(100).data
                result = Success(data.status)
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