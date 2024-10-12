package com.example.parliamentmembers

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.parliamentmembers.data.DataRepository
import com.example.parliamentmembers.ui.theme.ParliamentMembersTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

class MainActivity : ComponentActivity() {
    private lateinit var dataRepo: DataRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataRepo = (application as ParliamentMembersApplication).container.dataRepo

        setContent {
            val isDarkTheme by dataRepo.isDarkThemeFlow.collectAsState(initial = false)

            ParliamentMembersTheme(darkTheme = isDarkTheme) {
                PMApp()
            }
        }
    }
}