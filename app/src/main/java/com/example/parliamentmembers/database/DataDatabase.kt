package com.example.parliamentmembers.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.parliamentmembers.ParliamentMembersApplication
import com.example.parliamentmembers.data.DataDao
import com.example.parliamentmembers.model.ParliamentMember
import com.example.parliamentmembers.model.ParliamentMemberExtra
import kotlin.concurrent.Volatile

@Database(entities = [ParliamentMember::class, ParliamentMemberExtra::class], version = 1, exportSchema = false)
abstract class DataDatabase : RoomDatabase() {
    abstract fun dataDao(): DataDao

    companion object {
        @Volatile
        private var Instance: DataDatabase? = null

        fun getDatabase(context: Context): DataDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, DataDatabase::class.java, "parliament_member_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}