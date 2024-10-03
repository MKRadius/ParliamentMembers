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
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addParliamentMember(data: ParliamentMember)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addParliamentMembersExtra(data: ParliamentMemberExtra)

    @Query("SELECT * FROM parliament_member")
    fun getAllParliamentMembers(): Flow<List<ParliamentMember>>

    @Query("SELECT * FROM parliament_member_extra")
    fun getAllParliamentMembersExtra(): Flow<List<ParliamentMemberExtra>>
}