package com.example.parliamentmembers

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.parliamentmembers.ui.theme.ParliamentMembersTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ParliamentMembersTheme {
                PMApp()
            }
        }
    }
}