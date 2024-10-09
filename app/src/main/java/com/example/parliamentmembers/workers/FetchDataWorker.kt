package com.example.parliamentmembers.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import com.example.parliamentmembers.data.DataRepository
import com.example.parliamentmembers.model.ParliamentMember
import com.example.parliamentmembers.model.ParliamentMemberExtra
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FetchDataWorker(
    context: Context,
    params: WorkerParameters,
    private val dataRepo: DataRepository
): CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            val responsePM: List<ParliamentMember>
            val responsePME: List<ParliamentMemberExtra>

            try {
                responsePM = dataRepo.fetchParliamentMembersData().toMutableList()
            } catch (e: Exception) {
                Log.e("DBG", "Error fetching PM data: ${e.message}")
                return@withContext handleRetryOrFailure(e)
            }

            try {
                responsePME = dataRepo.fetchParliamentMembersExtraData().toMutableList()
            } catch (e: Exception) {
                Log.e("DBG", "Error fetching PM data: ${e.message}")
                return@withContext handleRetryOrFailure(e)
            }

            val gson = Gson()
            val pmJson = gson.toJson(responsePM)
            val pmeJson = gson.toJson(responsePME)

            val responseData = Data.Builder()
                .putString("pm_data", pmJson)
                .putString("pme_data", pmeJson)
                .build()

            Log.d("DBG", "SUCCESS: Fetched data")
            return@withContext Result.success(responseData)
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