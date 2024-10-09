package com.example.parliamentmembers.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker.Result
import androidx.work.WorkerParameters
import com.example.parliamentmembers.data.DataRepository
import com.example.parliamentmembers.model.ParliamentMember
import com.example.parliamentmembers.model.ParliamentMemberExtra
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext

class FetchAndUpdatePMExtraWorker(
    context: Context,
    params: WorkerParameters,
    private val dataRepo: DataRepository
): CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            val responsePME: List<ParliamentMemberExtra>

            try {
                responsePME = dataRepo.fetchParliamentMembersExtraData().toMutableList()
            }
            catch (e: Exception) {
                Log.e("DBG", "Error fetching PM data: ${e.message}")
                return@withContext handleRetryOrFailure(e)
            }

            try {
//                responsePME.forEach { dataRepo.addParliamentMemberExtra(it) }
                responsePME.map {
                    async { dataRepo.addParliamentMemberExtra(it) }
                }.awaitAll()
            }
            catch (e: Exception) {
                Log.e("DBG", "Failed to add PME data to DB | Error: ${e.message}")
                return@withContext handleRetryOrFailure(e)
            }

            Log.d("DBG", "SUCCESS: Fetched and updated PME data")
            return@withContext Result.success()
        }
    }

    private fun handleRetryOrFailure(e: Exception): Result {
        return if (runAttemptCount < 5) {
            Log.e("DBG", "Retrying... attempt: $runAttemptCount | Error: ${e.message}")
            Result.retry()
        }
        else {
            Log.e("DBG", "Exceeded retries. Failing | Error: ${e.message}")
            Result.failure()
        }
    }
}