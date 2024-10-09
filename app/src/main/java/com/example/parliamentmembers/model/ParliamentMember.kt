package com.example.parliamentmembers.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "parliament_member")
data class ParliamentMember(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "heteka_id")     val hetekaId: Int,
    @ColumnInfo(name = "seat_number")   val seatNumber: Int,
    @ColumnInfo(name = "last_name")     val lastname: String,
    @ColumnInfo(name = "first_name")    val firstname: String,
    @ColumnInfo(name = "party")         val party: String,
    @ColumnInfo(name = "minister")      val minister: Boolean,
    @ColumnInfo(name = "picture_url")   val pictureUrl: String?,
)