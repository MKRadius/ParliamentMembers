package com.example.parliamentmembers.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.parliamentmembers.data.DataRepository
import com.example.parliamentmembers.model.ParliamentMember
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext

class FetchAndUpdatePMWorker(
    context: Context,
    params: WorkerParameters,
    private val dataRepo: DataRepository
): CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            val responsePM: List<ParliamentMember>

            try {
                responsePM = dataRepo.fetchParliamentMembersData().toMutableList()
            } catch (e: Exception) {
                Log.e("DBG", "Error fetching PM data: ${e.message}")
                return@withContext handleRetryOrFailure(e)
            }

            try {
//                responsePM.forEach { dataRepo.addParliamentMemberExtra(it) }
                responsePM.map {
                    async { dataRepo.addParliamentMember(it) }
                }.awaitAll()

            } catch (e: Exception) {
                Log.e("DBG", "Failed to add PM data to DB | Error: ${e.message}")
                return@withContext handleRetryOrFailure(e)
            }

            Log.d("DBG", "SUCCESS: Fetched and updated PM data")
            return@withContext Result.success()
        }
    }

    private fun handleRetryOrFailure(e: Exception): Result {
        return if (runAttemptCount < 2) {
            Log.e("DBG", "Retrying... attempt: $runAttemptCount | Error: ${e.message}")
            Result.retry()
        }
        else {
            Log.e("DBG", "Exceeded retries. Failing | Error: ${e.message}")
            Result.failure()
        }
    }
}
