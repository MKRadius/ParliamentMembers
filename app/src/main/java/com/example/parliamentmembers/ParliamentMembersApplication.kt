package com.example.parliamentmembers

import android.app.Application
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.work.Configuration
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.parliamentmembers.data.AppContainer
import com.example.parliamentmembers.data.AppDataContainer
import com.example.parliamentmembers.workers.CustomWorkerFactory
import com.example.parliamentmembers.workers.FetchAndUpdateDBWorker
import java.util.concurrent.TimeUnit


class ParliamentMembersApplication: Application(), Configuration.Provider {
   lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
        WorkManager.initialize(this, workManagerConfiguration)
        schedulePeriodicFetchAndUpdateWork()
    }

    private fun schedulePeriodicFetchAndUpdateWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .build()

//        val fetchAndUpdateDBWorker = PeriodicWorkRequestBuilder<FetchAndUpdateDBWorker>(60, TimeUnit.MINUTES)
//            .setConstraints(constraints)
//            .build()
//
//        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
//            "FetchAndUpdateDBWorker",
//            ExistingPeriodicWorkPolicy.KEEP,
//            fetchAndUpdateDBWorker
//        )

        val fetchAndUpdateDBWorker = OneTimeWorkRequestBuilder<FetchAndUpdateDBWorker>()
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueue(fetchAndUpdateDBWorker)

        Handler(Looper.getMainLooper()).postDelayed({
            schedulePeriodicFetchAndUpdateWork()
        }, TimeUnit.MINUTES.toMillis(10))
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setMinimumLoggingLevel(Log.DEBUG)
            .setWorkerFactory(CustomWorkerFactory(container.dataRepo))
            .build()
}

