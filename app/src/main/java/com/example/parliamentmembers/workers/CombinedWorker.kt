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

class CombinedWorker(
    context: Context,
    params: WorkerParameters,
    private val dataRepo: DataRepository
): CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {

            var responsePM: List<ParliamentMember>
            var responsePME: List<ParliamentMemberExtra>

            launch {
                try {
                    responsePM = dataRepo.fetchParliamentMembersData().toMutableList()
                    responsePM.forEach {
                        dataRepo.addParliamentMember(it)
                    }
                }
                catch (e: Exception) {
                    Log.e("DBG", "Error fetching PM data: ${e.message}")
                }
            }.join()

            launch {
                try {
                    responsePME = dataRepo.fetchParliamentMembersExtraData().toMutableList()
                    responsePME.forEach {
                        dataRepo.addParliamentMemberExtra(it)
                    }
                } catch (e: Exception) {
                    Log.e("DBG", "Error fetching PM data: ${e.message}")
                }
            }.join()


            Log.d("DBG", "SUCCESS: DB updated")
            return@withContext Result.success()
        }
    }
}