/*
 * Date: 12th October 2024
 * Author: Khai Cao
 * Student ID: 2216586
 *
 * ParliamentMemberExtra is a data class representing additional information
 * related to a member of the parliament. It defines the structure of the
 * parliament_member_extra table in the Room database, containing fields for
 * the member's unique identifier (hetekaId), their Twitter handle, year of birth,
 * and constituency.
 *
 * This class is linked to the ParliamentMember class through a
 * foreign key relationship, ensuring referential integrity between the two tables.
 * Updates and deletions in the parent table will cascade to this table.
 */

package com.example.parliamentmembers.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "parliament_member_extra",
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
data class ParliamentMemberExtra(
    @PrimaryKey
    @ColumnInfo(name = "heteka_id")     val hetekaId: Int,
    @ColumnInfo(name = "twitter")       val twitter: String?,
    @ColumnInfo(name = "born_year")     val bornYear: Int,
    @ColumnInfo(name = "constituency")  val constituency: String,
)