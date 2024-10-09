package com.example.parliamentmembers.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.parliamentmembers.data.DataRepository
import com.example.parliamentmembers.model.ParliamentMember
import com.example.parliamentmembers.model.ParliamentMemberExtra
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UpdateDBWorker(
    context: Context,
    params: WorkerParameters,
    private val dataRepo: DataRepository
): CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
//            try {
//                responsePM = dataRepo.fetchParliamentMembersData().toMutableList()
//            } catch (e: Exception) {
//                Log.e("DBG", "Error fetching PM data: ${e.message}")
//                return@withContext handleRetryOrFailure(e)
//            }
//
//            try {
//                responsePME = dataRepo.fetchParliamentMembersExtraData().toMutableList()
//            } catch (e: Exception) {
//                Log.e("DBG", "Error fetching PM data: ${e.message}")
//                return@withContext handleRetryOrFailure(e)
//            }
//
//            val gson = Gson()
//            val pmJson = gson.toJson(responsePM)
//            val pmeJson = gson.toJson(responsePME)
//
//            val responsePMData = Data.Builder()
//                .putString("pm_data", pmJson)
//                .putString("pme_data", pmeJson)
//                .build()

            val pmJson = inputData.getString("pm_data")
            val pmeJson = inputData.getString("pme_data")

            // Check if the JSON strings are null or empty
            if (pmJson.isNullOrEmpty() || pmeJson.isNullOrEmpty()) {
                return@withContext Result.failure() // Handle the failure case
            }

            // Define the types for deserialization
            val pmListType = object : TypeToken<List<ParliamentMember>>() {}.type
            val pmeListType = object : TypeToken<List<ParliamentMemberExtra>>() {}.type

            // Parse the JSON strings back to List<ParliamentMember> and List<ParliamentMemberExtra>
            val parliamentMembers: List<ParliamentMember> = Gson().fromJson(pmJson, pmListType)
            val parliamentMembersExtra: List<ParliamentMemberExtra> = Gson().fromJson(pmeJson, pmeListType)

            // Now you can use the lists of ParliamentMember and ParliamentMemberExtra objects
            launch {
                parliamentMembers.forEach {
                    dataRepo.addParliamentMember(it)
                }
            }.join()

            launch {
                parliamentMembersExtra.forEach {
                    dataRepo.addParliamentMemberExtra(it)
                }
            }.join()

            // Return success
            Result.success()

            Log.d("DBG", "SUCCESS: DB updated")
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