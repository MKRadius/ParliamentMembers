package com.example.parliamentmembers.data

import com.example.parliamentmembers.model.ParliamentMember
import com.example.parliamentmembers.model.ParliamentMemberExtra
import com.example.parliamentmembers.network.RetrofitInstance
import kotlinx.coroutines.flow.Flow

class OfflineDataRepository(private val dataDao: DataDao): DataRepository {
    private val retrofitApi = RetrofitInstance.api

    override suspend fun addParliamentMember(data: ParliamentMember) = dataDao.addParliamentMember(data)
    override suspend fun addParliamentMembersExtra(data: ParliamentMemberExtra) = dataDao.addParliamentMembersExtra(data)
    override fun getAllParliamentMembers(): Flow<List<ParliamentMember>> = dataDao.getAllParliamentMembers()
    override fun getAllParliamentMembersExtra(): Flow<List<ParliamentMemberExtra>> = dataDao.getAllParliamentMembersExtra()

    override suspend fun fetchParliamentMembersData() = retrofitApi.getParliamentMembers()
    override suspend fun fetchParliamentMembersExtraData() = retrofitApi.getParliamentMemberExtraInfo()
}