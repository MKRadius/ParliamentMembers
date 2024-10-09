package com.example.parliamentmembers

import android.app.Application
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.work.BackoffPolicy
import androidx.work.Configuration
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.parliamentmembers.data.AppContainer
import com.example.parliamentmembers.data.AppDataContainer
import com.example.parliamentmembers.workers.CombinedWorker
import com.example.parliamentmembers.workers.CustomWorkerFactory
import com.example.parliamentmembers.workers.DelayWorker
import com.example.parliamentmembers.workers.FetchAndUpdatePMExtraWorker
import com.example.parliamentmembers.workers.FetchAndUpdatePMWorker
import com.example.parliamentmembers.workers.FetchDataWorker
import com.example.parliamentmembers.workers.UpdateDBWorker
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

//        val fetchAndUpdatePMWorkRequest = OneTimeWorkRequestBuilder<FetchAndUpdatePMWorker>()
//            .setConstraints(constraints)
//            .build()
//
//        val delayWorkRequest = OneTimeWorkRequestBuilder<DelayWorker>()
//            .build()
//
//        val fetchAndUpdatePMExtraWorkRequest = OneTimeWorkRequestBuilder<FetchAndUpdatePMExtraWorker>()
//            .setConstraints(constraints)
//            .setBackoffCriteria(
//                BackoffPolicy.LINEAR,
//                30,
//                TimeUnit.SECONDS
//            )
//            .build()
//
//        WorkManager.getInstance(this)
//            .beginWith(fetchAndUpdatePMWorkRequest)
////            .then(delayWorkRequest)
//            .then(fetchAndUpdatePMExtraWorkRequest)
//            .enqueue()

//        val fetchDataWorkRequest = OneTimeWorkRequestBuilder<FetchDataWorker>()
//            .setConstraints(constraints)
//            .build()
//
//        val updateDataWorkRequest = OneTimeWorkRequestBuilder<UpdateDBWorker>()
//            .setConstraints(constraints)
//            .build()

        val combinedWorkRequest = OneTimeWorkRequestBuilder<CombinedWorker>()
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this)
            .enqueue(combinedWorkRequest)

        Handler(Looper.getMainLooper()).postDelayed({
            schedulePeriodicFetchAndUpdateWork()
        }, TimeUnit.MINUTES.toMillis(1))
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setMinimumLoggingLevel(Log.DEBUG)
            .setWorkerFactory(CustomWorkerFactory(container.dataRepo))
            .build()
}

