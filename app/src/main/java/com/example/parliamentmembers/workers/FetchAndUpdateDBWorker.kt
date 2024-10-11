package com.example.parliamentmembers.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.parliamentmembers.data.DataRepository
import com.example.parliamentmembers.model.ParliamentMember
import com.example.parliamentmembers.model.ParliamentMemberExtra
import com.example.parliamentmembers.model.ParliamentMemberLocal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext

class FetchAndUpdateDBWorker(
    context: Context,
    params: WorkerParameters,
    private val dataRepo: DataRepository
): CoroutineWorker(context, params) {
    private lateinit var responsePM: List<ParliamentMember>
    private lateinit var responsePME: List<ParliamentMemberExtra>
    private lateinit var responsePair: List<Pair<ParliamentMember, ParliamentMemberExtra?>>

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            responsePM = dataRepo.fetchParliamentMembersData().toMutableList()
            responsePME = dataRepo.fetchParliamentMembersExtraData().toMutableList()
        }
        catch (e: Exception) {
            Log.e("DBG", "Error fetching data: ${e.message}")
            return@withContext handleRetryOrFailure(e)
        }

        Log.d("DBG", "Got PM : $responsePM")
        Log.d("DBG", "Got PME: $responsePME")

        val pmeMap = responsePME.associateBy { it.hetekaId }

        responsePair = responsePM.map { member ->
            pmeMap[member.hetekaId]?.let { extra ->
                Pair(member, extra)
            } ?: Pair(member, null)
        }

        responsePair.forEach { data ->
            try {
                dataRepo.addParliamentMember(data.first)
                if (data.second != null) dataRepo.addParliamentMemberExtra(data.second!!)

                val existingEntry = dataRepo.getEntryById(data.first.hetekaId).firstOrNull()
                val newEntry = ParliamentMemberLocal(
                    hetekaId = data.first.hetekaId,
                    favorite = existingEntry?.favorite ?: false,
                    note = existingEntry?.note
                )

                dataRepo.addParliamentLocal(newEntry)
            }
            catch (e: Exception) {
                Log.e("DBG", "Failed to add entries: $data | Error: ${e.message}")
            }
        }

        Log.d("DBG", "SUCCESS: Fetched and updated data")
        return@withContext Result.success()
    }

    private fun handleRetryOrFailure(e: Exception): Result {
        return if (runAttemptCount < 3) {
            Log.e("DBG", "Retry attempt: $runAttemptCount | Error in work ${this.javaClass.name}: ${e.message}")
            Result.retry()
        }
        else {
            Log.e("DBG", "Exceeded retries. Work failed | Error in work ${this.javaClass.name}: ${e.message}")
            Result.failure()
        }
    }
}