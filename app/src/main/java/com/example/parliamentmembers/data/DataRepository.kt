/*
 * Date: 12th October 2024
 * Author: Khai Cao
 * Student ID: 2216586
 *
 * DataRepository interface defines the contract for data operations related
 * to Parliament Members, including theme management, CRUD operations for
 * members and their extra details, and querying functionalities for parties
 * and constituencies. It uses Kotlin Coroutines and Flow for asynchronous
 * data handling and provides methods for fetching, adding, updating, and
 * deleting records.
 */

package com.example.parliamentmembers.data

import com.example.parliamentmembers.model.ParliamentMember
import com.example.parliamentmembers.model.ParliamentMemberExtra
import com.example.parliamentmembers.model.ParliamentMemberLocal
import kotlinx.coroutines.flow.Flow

interface DataRepository {
    val isDarkThemeFlow: Flow<Boolean>

    suspend fun toggleTheme()

    suspend fun addParliamentMember(data: ParliamentMember)
    suspend fun addParliamentMemberExtra(data: ParliamentMemberExtra)

    fun getAllParliamentMembers(): Flow<List<ParliamentMember>>
    fun getAllParliamentMembersExtra(): Flow<List<ParliamentMemberExtra>>

    suspend fun fetchParliamentMembersData(): List<ParliamentMember>
    suspend fun fetchParliamentMembersExtraData(): List<ParliamentMemberExtra>

    fun getMemberWithId(id: Int): Flow<ParliamentMember>
    fun getMemberExtraWithId(id: Int): Flow<ParliamentMemberExtra>
    fun getMemberLocalWithId(id: Int): Flow<ParliamentMemberLocal?>

    fun getParties(): Flow<List<String>>
    fun getConstituencies(): Flow<List<String>>
    fun getAllPMWithParty(party: String): Flow<List<ParliamentMember>>
    fun getAllPMWithConstituency(constituency: String): Flow<List<ParliamentMember>>

    suspend fun addParliamentLocal(data: ParliamentMemberLocal)
    fun getAllPMIds(): Flow<List<Int>>

    suspend fun updateNoteWithId(id: Int, note: String?)
    suspend fun deleteNoteWithId(id: Int)

    fun getFavoriteById(id: Int): Flow<Boolean>
    suspend fun toggleFavorite(id: Int)
}