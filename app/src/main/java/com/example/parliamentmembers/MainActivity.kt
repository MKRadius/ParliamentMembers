/*
 * Date: 12th October 2024
 * Author: Khai Cao
 * Student ID: 2216586
 *
 * MainActivity is the main activity of the Parliament Members Android application.
 * It serves as the entry point for the user interface and initializes the application theme
 * based on the user's preferences. The activity sets the content to the PMApp composable,
 * which contains the navigation graph for the application.
 */

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