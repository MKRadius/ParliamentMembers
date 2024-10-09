package com.example.parliamentmembers.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.parliamentmembers.model.ParliamentMember
import com.example.parliamentmembers.model.ParliamentMemberExtra
import kotlinx.coroutines.flow.Flow

@Dao
interface DataDao {
    @Insert(entity = ParliamentMember::class, onConflict = OnConflictStrategy.REPLACE)
    fun addAllPM(data: List<ParliamentMember>)

    @Insert(entity = ParliamentMemberExtra::class, onConflict = OnConflictStrategy.REPLACE)
    fun addAllPME(data: List<ParliamentMemberExtra>)

    @Insert(entity = ParliamentMember::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun addParliamentMember(data: ParliamentMember)

    @Insert(entity = ParliamentMemberExtra::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun addParliamentMemberExtra(data: ParliamentMemberExtra)

    @Query("SELECT * FROM parliament_member")
    fun getAllParliamentMembers(): Flow<List<ParliamentMember>>

    @Query("SELECT * FROM parliament_member_extra")
    fun getAllParliamentMembersExtra(): Flow<List<ParliamentMemberExtra>>

    @Query("SELECT * FROM parliament_member WHERE heteka_id = :id")
    fun getMemberWithId(id: Int): Flow<ParliamentMember>

    @Query("SELECT * FROM parliament_member_extra WHERE heteka_id = :id")
    fun getMemberExtraWithId(id: Int): Flow<ParliamentMemberExtra>
}