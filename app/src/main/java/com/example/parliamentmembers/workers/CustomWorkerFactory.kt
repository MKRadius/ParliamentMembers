/*
 * Date: 12th October 2024
 * Author: Khai Cao
 * Student ID: 2216586
 *
 * CustomWorkerFactory is a custom implementation of WorkerFactory for the Parliament Members Android application.
 * It provides a way to instantiate workers with a specific dependency (DataRepository) that is needed for
 * executing background tasks. The factory overrides the createWorker method to return an instance of
 * FetchAndUpdateDBWorker when requested. This allows workers to access shared resources or dependencies
 * while performing their tasks.
 */

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
            FetchAndUpdateDBWorker::class.java.name -> FetchAndUpdateDBWorker(context, workerParameters, dataRepo)
            else -> null
        }
    }
}