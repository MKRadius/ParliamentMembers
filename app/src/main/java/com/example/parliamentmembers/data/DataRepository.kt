package com.example.parliamentmembers.data

import androidx.room.Query
import com.example.parliamentmembers.model.ParliamentMember
import com.example.parliamentmembers.model.ParliamentMemberExtra
import kotlinx.coroutines.flow.Flow

interface DataRepository {
    fun addAllPM(data: List<ParliamentMember>)
    fun addAllPME(data: List<ParliamentMemberExtra>)

    suspend fun addParliamentMember(data: ParliamentMember)
    suspend fun addParliamentMemberExtra(data: ParliamentMemberExtra)

    fun getAllParliamentMembers(): Flow<List<ParliamentMember>>
    fun getAllParliamentMembersExtra(): Flow<List<ParliamentMemberExtra>>

    suspend fun fetchParliamentMembersData(): List<ParliamentMember>
    suspend fun fetchParliamentMembersExtraData(): List<ParliamentMemberExtra>

    fun getMemberWithId(id: Int): Flow<ParliamentMember>
    fun getMemberExtraWithId(id: Int): Flow<ParliamentMemberExtra>
}