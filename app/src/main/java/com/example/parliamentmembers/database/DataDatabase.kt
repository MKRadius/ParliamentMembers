package com.example.parliamentmembers.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.parliamentmembers.ParliamentMembersApplication
import com.example.parliamentmembers.data.DataDao
import com.example.parliamentmembers.model.ParliamentMember
import com.example.parliamentmembers.model.ParliamentMemberExtra
import com.example.parliamentmembers.model.ParliamentMemberLocal
import java.util.concurrent.Executors
import kotlin.concurrent.Volatile

@Database(entities = [ParliamentMember::class, ParliamentMemberExtra::class, ParliamentMemberLocal::class], version = 1, exportSchema = false)
abstract class DataDatabase : RoomDatabase() {
    abstract fun dataDao(): DataDao

    companion object {
        @Volatile
        private var Instance: DataDatabase? = null

        fun getDatabase(context: Context): DataDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, DataDatabase::class.java, "parliament_members_database")
//                    .setQueryCallback({ sqlQuery, bindArgs ->
//                        Log.d("RoomQuery", "SQL Query: $sqlQuery, Args: $bindArgs")
//                    }, Executors.newSingleThreadExecutor())
//                    .addCallback(object : Callback() {
//                        override fun onOpen(db: SupportSQLiteDatabase) {
//                            super.onOpen(db)
//                            // Enable foreign key constraints
//                            db.setForeignKeyConstraintsEnabled(true)
//                        }
//                    })
                    .build()
                    .also { Instance = it }
            }
        }
    }
}