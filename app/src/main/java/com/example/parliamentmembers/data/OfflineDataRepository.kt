package com.example.parliamentmembers.data

import android.content.Context
import android.util.Log
import androidx.work.WorkManager
import com.example.parliamentmembers.database.DataDatabase
import com.example.parliamentmembers.model.ParliamentMember
import com.example.parliamentmembers.model.ParliamentMemberExtra
import com.example.parliamentmembers.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class OfflineDataRepository(context: Context): DataRepository {
    private val retrofitApi = RetrofitInstance.api
    private val dataDao = DataDatabase.getDatabase(context).dataDao()

    override suspend fun addParliamentMember(data: ParliamentMember) {
        try {
            dataDao.addParliamentMember(data)
        }
        catch (e: Exception) {
            Log.e("DBG", "PM Error: $e")
        }
    }
    override suspend fun addParliamentMemberExtra(data: ParliamentMemberExtra) {
        try {
            dataDao.addParliamentMemberExtra(data)
        }
        catch (e: Exception) {
            Log.e("DBG", "PME Error: $e")
        }
    }

    override fun getAllParliamentMembers(): Flow<List<ParliamentMember>> = dataDao.getAllParliamentMembers()
    override fun getAllParliamentMembersExtra(): Flow<List<ParliamentMemberExtra>> = dataDao.getAllParliamentMembersExtra()

    override suspend fun fetchParliamentMembersData() = retrofitApi.getParliamentMembers()
    override suspend fun fetchParliamentMembersExtraData() = retrofitApi.getParliamentMembersExtras()
}