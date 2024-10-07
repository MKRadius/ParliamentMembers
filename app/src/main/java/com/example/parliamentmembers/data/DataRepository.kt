package com.example.parliamentmembers.data

import com.example.parliamentmembers.model.ParliamentMember
import com.example.parliamentmembers.model.ParliamentMemberExtra
import kotlinx.coroutines.flow.Flow

interface DataRepository {
    suspend fun addParliamentMember(data: ParliamentMember)
    suspend fun addParliamentMembersExtra(data: ParliamentMemberExtra)
    fun getAllParliamentMembers(): Flow<List<ParliamentMember>>
    fun getAllParliamentMembersExtra(): Flow<List<ParliamentMemberExtra>>
    suspend fun fetchParliamentMembersData(): List<ParliamentMember>
    suspend fun fetchParliamentMembersExtraData(): List<ParliamentMemberExtra>
}