package com.example.parliamentmembers.data

import androidx.room.Query
import com.example.parliamentmembers.model.ParliamentMember
import com.example.parliamentmembers.model.ParliamentMemberExtra
import com.example.parliamentmembers.model.ParliamentMemberLocal
import kotlinx.coroutines.flow.Flow

interface DataRepository {
    suspend fun addParliamentMember(data: ParliamentMember)
    suspend fun addParliamentMemberExtra(data: ParliamentMemberExtra)

    fun getAllParliamentMembers(): Flow<List<ParliamentMember>>
    fun getAllParliamentMembersExtra(): Flow<List<ParliamentMemberExtra>>

    suspend fun fetchParliamentMembersData(): List<ParliamentMember>
    suspend fun fetchParliamentMembersExtraData(): List<ParliamentMemberExtra>

    fun getMemberWithId(id: Int): Flow<ParliamentMember>
    fun getMemberExtraWithId(id: Int): Flow<ParliamentMemberExtra>
    fun getMemberLocalWithId(id: Int): Flow<ParliamentMemberLocal>

    fun getParties(): Flow<List<String>>
    fun getAllPMWithParty(party: String): Flow<List<ParliamentMember>>

    fun getEntryById(id: Int): Flow<ParliamentMemberLocal?>
    suspend fun addEntry(data: ParliamentMemberLocal)
    fun getAllPMIds(): Flow<List<Int>>

    suspend fun updateNoteWithId(id: Int, note: String?)
    suspend fun deleteNoteWithId(id: Int)
}