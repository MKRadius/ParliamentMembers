package com.example.parliamentmembers.data

import android.content.Context
import com.example.parliamentmembers.database.DataDatabase

interface AppContainer {
    val dataRepo: DataRepository
}

class AppDataContainer(private val context: Context): AppContainer {
    override val dataRepo: DataRepository by lazy {
        OfflineDataRepository(DataDatabase.getDatabase(context).dataDao())
    }
}