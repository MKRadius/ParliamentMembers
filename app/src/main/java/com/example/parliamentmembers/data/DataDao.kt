/*
 * Date: 12th October 2024
 * Author: Khai Cao
 * Student ID: 2216586
 *
 * DataDao is a Data Access Object (DAO) interface for managing Parliament Member data
 * in the Room database. It provides methods for inserting, querying, updating, and
 * deleting records in the parliament_member, parliament_member_extra, and
 * parliament_member_local tables. Key functionalities include retrieving all members,
 * filtering by party or constituency, and managing favorite status and notes.
 * This interface uses Kotlin Coroutines and Flow for asynchronous data operations.
 */

package com.example.parliamentmembers.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.parliamentmembers.model.ParliamentMember
import com.example.parliamentmembers.model.ParliamentMemberExtra
import com.example.parliamentmembers.model.ParliamentMemberLocal
import kotlinx.coroutines.flow.Flow

@Dao
interface DataDao {
    @Insert(entity = ParliamentMember::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun addParliamentMember(data: ParliamentMember)

    @Insert(entity = ParliamentMemberExtra::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun addParliamentMemberExtra(data: ParliamentMemberExtra)

    @Insert(entity = ParliamentMemberLocal::class, onConflict = OnConflictStrategy.IGNORE)
    suspend fun addParliamentLocal(data: ParliamentMemberLocal)

    @Query("SELECT * FROM parliament_member")
    fun getAllParliamentMembers(): Flow<List<ParliamentMember>>

    @Query("SELECT * FROM parliament_member_extra")
    fun getAllParliamentMembersExtra(): Flow<List<ParliamentMemberExtra>>

    @Query("SELECT * FROM parliament_member WHERE heteka_id = :id")
    fun getMemberWithId(id: Int): Flow<ParliamentMember>

    @Query("SELECT * FROM parliament_member_extra WHERE heteka_id = :id")
    fun getMemberExtraWithId(id: Int): Flow<ParliamentMemberExtra>

    @Query("SELECT * FROM parliament_member_local WHERE heteka_id = :id")
    fun getMemberLocalWithId(id: Int): Flow<ParliamentMemberLocal?>

    @Query("SELECT DISTINCT party FROM parliament_member ORDER BY party ASC")
    fun getParties(): Flow<List<String>>

    @Query("SELECT DISTINCT constituency FROM parliament_member_extra ORDER BY constituency ASC")
    fun getConstituencies(): Flow<List<String>>

    @Query("SELECT * FROM parliament_member WHERE party = :party")
    fun getAllPMWithParty(party: String): Flow<List<ParliamentMember>>

    @Transaction
    @Query("""
        SELECT pm.*
        FROM parliament_member pm
        INNER JOIN parliament_member_extra pme ON pm.heteka_id = pme.heteka_id
        WHERE pme.constituency = :constituency
        ORDER BY pm.heteka_id ASC
    """)
    fun getAllPMWithConstituency(constituency: String): Flow<List<ParliamentMember>>

    @Query("SELECT heteka_id FROM parliament_member")
    fun getAllPMIds(): Flow<List<Int>>

    @Query("UPDATE parliament_member_local SET note = :note WHERE heteka_id = :id")
    suspend fun updateNoteWithId(id: Int, note: String?)

    @Query("UPDATE parliament_member_local SET note = NULL WHERE heteka_id = :id")
    suspend fun deleteNoteWithId(id: Int)

    @Query("SELECT favorite FROM parliament_member_local WHERE heteka_id = :id")
    fun getFavoriteById(id: Int): Flow<Boolean>

    @Query("UPDATE parliament_member_local SET favorite = NOT favorite WHERE heteka_id = :id")
    suspend fun toggleFavorite(id: Int)
}