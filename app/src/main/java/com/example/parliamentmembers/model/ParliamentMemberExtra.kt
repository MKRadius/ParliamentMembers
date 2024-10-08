package com.example.parliamentmembers.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "parliament_member_extra",
    foreignKeys = [
        ForeignKey(
            entity = ParliamentMember::class,
            parentColumns = ["heteka_id"],
            childColumns = ["heteka_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ParliamentMemberExtra(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "heteka_id")     val hetekaId: Int,
    @ColumnInfo(name = "twitter")       val twitter: String?,
    @ColumnInfo(name = "born_year")     val bornYear: Int,
    @ColumnInfo(name = "constituency")  val constituency: String,
)