package com.example.parliamentmembers.data

import android.content.Context

interface AppContainer {
    val dataRepo: DataRepository
}

class AppDataContainer(context: Context): AppContainer {
    override val dataRepo: DataRepository by lazy {
        OfflineDataRepository(context)
    }
}