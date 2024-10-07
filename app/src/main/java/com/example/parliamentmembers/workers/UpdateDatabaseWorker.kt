package com.example.parliamentmembers.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.parliamentmembers.data.DataRepository

class UpdateDatabaseWorker(
    context: Context,
    params: WorkerParameters,
    private val dataRepository: DataRepository
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        TODO("Not yet implemented")
    }
}