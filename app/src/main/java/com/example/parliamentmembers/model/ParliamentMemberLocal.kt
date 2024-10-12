/*
 * Date: 12th October 2024
 * Author: Khai Cao
 * Student ID: 2216586
 *
 * ParliamentMemberLocal is a data class representing the local storage
 * of a member of the parliament in the Room database. It defines the structure
 * of the parliament_member_local table, which stores the member's unique identifier
 * (hetekaId), a boolean indicating if the member is marked as a favorite,
 * and an optional note associated with the member.
 *
 * This class is linked to the ParliamentMember class through a foreign key relationship,
 * ensuring referential integrity between the two tables. Updates and deletions in the
 * parent table will cascade to this table.
 */

package com.example.parliamentmembers.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "parliament_member_local",
    foreignKeys = [
        ForeignKey(
            entity = ParliamentMember::class,
            parentColumns = ["heteka_id"],
            childColumns = ["heteka_id"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["heteka_id"])]
)
data class ParliamentMemberLocal(
    @PrimaryKey
    @ColumnInfo(name = "heteka_id") val hetekaId: Int,
    @ColumnInfo(name = "favorite") val favorite: Boolean,
    @ColumnInfo(name = "note") val note: String?,
)