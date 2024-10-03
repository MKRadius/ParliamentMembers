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

//    companion object {
//        lateinit var appContext: Context
//            private set
//    }
//
//    override fun onCreate() {
//        super.onCreate()
//        appContext = applicationContext
//    }


//    init {
//        instance = this
//    }
//
//    companion object {
//        private var instance: ParliamentMembersApplication? = null
//
//        fun appContext() : Context {
//            return instance!!.applicationContext
//        }
//    }
//
//    override fun onCreate() {
//        super.onCreate()
//
//        val appContext: Context = appContext()
//    }
}

