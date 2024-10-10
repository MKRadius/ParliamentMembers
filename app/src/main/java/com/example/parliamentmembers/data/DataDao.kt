package com.example.parliamentmembers.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
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

    @Insert(entity = ParliamentMemberLocal::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun addEntry(data: ParliamentMemberLocal)

    @Query("SELECT * FROM parliament_member_local WHERE heteka_id = :id LIMIT 1")
    fun getEntryById(id: Int): Flow<ParliamentMemberLocal?>

    @Query("SELECT * FROM parliament_member")
    fun getAllParliamentMembers(): Flow<List<ParliamentMember>>

    @Query("SELECT * FROM parliament_member_extra")
    fun getAllParliamentMembersExtra(): Flow<List<ParliamentMemberExtra>>

    @Query("SELECT * FROM parliament_member WHERE heteka_id = :id")
    fun getMemberWithId(id: Int): Flow<ParliamentMember>

    @Query("SELECT * FROM parliament_member_extra WHERE heteka_id = :id")
    fun getMemberExtraWithId(id: Int): Flow<ParliamentMemberExtra>

    @Query("SELECT * FROM parliament_member_local WHERE heteka_id = :id")
    fun getMemberLocalWithId(id: Int): Flow<ParliamentMemberLocal>

    @Query("SELECT DISTINCT party FROM parliament_member")
    fun getParties(): Flow<List<String>>

    @Query("SELECT * FROM parliament_member WHERE party = :party")
    fun getAllPMWithParty(party: String): Flow<List<ParliamentMember>>

    @Query("SELECT heteka_id FROM parliament_member")
    fun getAllPMIds(): Flow<List<Int>>

    @Query("UPDATE parliament_member_local SET note = :note WHERE heteka_id = :id")
    suspend fun updateNoteWithId(id: Int, note: String?)

    @Query("UPDATE parliament_member_local SET note = NULL WHERE heteka_id = :id")
    suspend fun deleteNoteWithId(id: Int)
}