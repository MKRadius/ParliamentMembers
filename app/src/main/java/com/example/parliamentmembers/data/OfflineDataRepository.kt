package com.example.parliamentmembers.data

import android.content.Context
import com.example.parliamentmembers.database.DataDatabase
import com.example.parliamentmembers.model.ParliamentMember
import com.example.parliamentmembers.model.ParliamentMemberExtra
import com.example.parliamentmembers.model.ParliamentMemberLocal
import com.example.parliamentmembers.network.RetrofitInstance
import kotlinx.coroutines.flow.Flow

class OfflineDataRepository(context: Context): DataRepository {
    private val retrofitApi = RetrofitInstance.api
    private val dataDao = DataDatabase.getDatabase(context).dataDao()

    override suspend fun addParliamentMember(data: ParliamentMember) = dataDao.addParliamentMember(data)
    override suspend fun addParliamentMemberExtra(data: ParliamentMemberExtra) = dataDao.addParliamentMemberExtra(data)

    override fun getAllParliamentMembers(): Flow<List<ParliamentMember>> = dataDao.getAllParliamentMembers()
    override fun getAllParliamentMembersExtra(): Flow<List<ParliamentMemberExtra>> = dataDao.getAllParliamentMembersExtra()

    override suspend fun fetchParliamentMembersData() = retrofitApi.getParliamentMembers()
    override suspend fun fetchParliamentMembersExtraData() = retrofitApi.getParliamentMembersExtras()

    override fun getMemberWithId(id: Int): Flow<ParliamentMember> = dataDao.getMemberWithId(id)
    override fun getMemberExtraWithId(id: Int): Flow<ParliamentMemberExtra> = dataDao.getMemberExtraWithId(id)
    override fun getMemberLocalWithId(id: Int): Flow<ParliamentMemberLocal?> = dataDao.getMemberLocalWithId(id)

    override fun getParties(): Flow<List<String>> = dataDao.getParties()
    override fun getAllPMWithParty(party: String): Flow<List<ParliamentMember>> = dataDao.getAllPMWithParty(party)

    override suspend fun addParliamentLocal(data: ParliamentMemberLocal) = dataDao.addParliamentLocal(data)
    override fun getAllPMIds(): Flow<List<Int>> = dataDao.getAllPMIds()

    override suspend fun updateNoteWithId(id: Int, note: String?) = dataDao.updateNoteWithId(id, note)
    override suspend fun deleteNoteWithId(id: Int) = dataDao.deleteNoteWithId(id)

    override fun getFavoriteById(id: Int): Flow<Boolean> = dataDao.getFavoriteById(id)
    override suspend fun toggleFavorite(id: Int) = dataDao.toggleFavorite(id)
}