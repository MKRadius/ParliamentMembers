package com.example.parliamentmembers.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.parliamentmembers.data.DataRepository

class FetchDataWorker(
    context: Context,
    params: WorkerParameters,
    private val dataRepo: DataRepository
): CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        return try {
            dataRepo.fetchParliamentMembersData()
            Result.success()
        }
        catch (e: Exception) {
            Log.e("DBG", "Unable to fetch Parliament Members data from network: $e")
            Result.retry()
        }
    }
}