package com.example.parliamentmembers.workers

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.example.parliamentmembers.data.DataRepository

class CustomWorkerFactory(
    private val dataRepo: DataRepository
) : WorkerFactory() {
    override fun createWorker(
        context: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return when (workerClassName) {
            FetchAndUpdatePMWorker::class.java.name         -> FetchAndUpdatePMWorker(context, workerParameters, dataRepo)
            FetchAndUpdatePMExtraWorker::class.java.name    -> FetchAndUpdatePMExtraWorker(context, workerParameters, dataRepo)
            DelayWorker::class.java.name                    -> DelayWorker(context, workerParameters)
            else -> null
        }
    }
}