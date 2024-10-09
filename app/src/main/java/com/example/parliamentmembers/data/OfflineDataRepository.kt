package com.example.parliamentmembers.data

import android.content.Context
import android.util.Log
import androidx.room.Query
import androidx.work.WorkManager
import com.example.parliamentmembers.database.DataDatabase
import com.example.parliamentmembers.model.ParliamentMember
import com.example.parliamentmembers.model.ParliamentMemberExtra
import com.example.parliamentmembers.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext

class OfflineDataRepository(context: Context): DataRepository {
    private val retrofitApi = RetrofitInstance.api
    private val dataDao = DataDatabase.getDatabase(context).dataDao()
    val dbMutex = Mutex()

    override fun addAllPM(data: List<ParliamentMember>) {
        dataDao.addAllPM(data)
    }

    override fun addAllPME(data: List<ParliamentMemberExtra>) {
        dataDao.addAllPME(data)
    }

    override suspend fun addParliamentMember(data: ParliamentMember) {
        dbMutex.withLock {
            try {
                dataDao.addParliamentMember(data)
            } catch (e: Exception) {
                Log.e("DBG", "Error adding Parliament Member: $e")
            }
        }
    }
    override suspend fun addParliamentMemberExtra(data: ParliamentMemberExtra) {
        dbMutex.withLock {
            try {
                dataDao.addParliamentMemberExtra(data)
            } catch (e: Exception) {
                Log.e("DBG", "Error adding Parliament Member Extra: $e")
            }
        }
    }

    override fun getAllParliamentMembers(): Flow<List<ParliamentMember>> = dataDao.getAllParliamentMembers()
    override fun getAllParliamentMembersExtra(): Flow<List<ParliamentMemberExtra>> = dataDao.getAllParliamentMembersExtra()

    override suspend fun fetchParliamentMembersData() = retrofitApi.getParliamentMembers()
    override suspend fun fetchParliamentMembersExtraData() = retrofitApi.getParliamentMembersExtras()

    override fun getMemberWithId(id: Int): Flow<ParliamentMember> = dataDao.getMemberWithId(id)
    override fun getMemberExtraWithId(id: Int): Flow<ParliamentMemberExtra> = dataDao.getMemberExtraWithId(id)
}