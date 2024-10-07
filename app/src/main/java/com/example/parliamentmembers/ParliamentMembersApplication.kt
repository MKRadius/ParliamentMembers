package com.example.parliamentmembers

import android.app.Application
import com.example.parliamentmembers.data.AppContainer
import com.example.parliamentmembers.data.AppDataContainer


class ParliamentMembersApplication: Application() {
   lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}

