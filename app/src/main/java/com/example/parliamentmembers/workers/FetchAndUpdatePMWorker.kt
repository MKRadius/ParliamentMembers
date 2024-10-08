package com.example.parliamentmembers.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.parliamentmembers.data.DataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FetchAndUpdatePMWorker(
    context: Context,
    params: WorkerParameters,
    private val dataRepo: DataRepository
): CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            try {
                val responsePM = dataRepo.fetchParliamentMembersData().toMutableList()

                while (responsePM.isNotEmpty()) {
                    val pm = responsePM.removeFirst()
                    Log.d("DBG", "PM: $pm")
                    dataRepo.addParliamentMember(pm)
                }

                Log.d("DBG", "SUCCESS: Fetched and updated PM data")
                Result.success()

            } catch (e: Exception) {
                Log.e("DBG", "Error: ${e.message}")
                if (runAttemptCount < 5) {
                    Log.e("DBG", "Retry attempt: $runAttemptCount")
                    Result.retry()
                } else {
                    Log.e("DBG", "Failure after retry: ${e.message}")
                    Result.failure()
                }
            }
        }
    }
}
