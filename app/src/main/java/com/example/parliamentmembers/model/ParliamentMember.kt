/*
 * Date: 12th October 2024
 * Author: Khai Cao
 * Student ID: 2216586
 *
 * ParliamentMember is a data class representing a member of the parliament.
 * It defines the structure of the parliament_member table in the Room database,
 * with properties corresponding to the member's information. Each member has
 * a unique identifier (hetekaId), seat number, last name, first name, party affiliation,
 * a boolean indicating if they are a minister, and an optional URL for their picture.
 */

package com.example.parliamentmembers.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "parliament_member",
    indices = [Index(value = ["heteka_id"], unique = true)]
)
data class ParliamentMember(
    @PrimaryKey
    @ColumnInfo(name = "heteka_id")     val hetekaId: Int,
    @ColumnInfo(name = "seat_number")   val seatNumber: Int,
    @ColumnInfo(name = "last_name")     val lastname: String,
    @ColumnInfo(name = "first_name")    val firstname: String,
    @ColumnInfo(name = "party")         val party: String,
    @ColumnInfo(name = "minister")      val minister: Boolean,
    @ColumnInfo(name = "picture_url")   val pictureUrl: String?,
)