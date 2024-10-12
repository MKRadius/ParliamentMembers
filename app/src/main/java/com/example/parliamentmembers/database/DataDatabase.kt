/*
 * Date: 12th October 2024
 * Author: Khai Cao
 * Student ID: 2216586
 *
 * DataDatabase is an abstract class that serves as the main database for
 * the ParliamentMembers application, managing the Room database instance.
 * It defines the database schema using the ParliamentMember, ParliamentMemberExtra,
 * and ParliamentMemberLocal entities. The class includes a singleton instance
 * for accessing the database and provides an abstract method to obtain
 * the DataDao for performing CRUD operations on the database.
 */

package com.example.parliamentmembers.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.parliamentmembers.data.DataDao
import com.example.parliamentmembers.model.ParliamentMember
import com.example.parliamentmembers.model.ParliamentMemberExtra
import com.example.parliamentmembers.model.ParliamentMemberLocal
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
                    .build()
                    .also { Instance = it }
            }
        }
    }
}