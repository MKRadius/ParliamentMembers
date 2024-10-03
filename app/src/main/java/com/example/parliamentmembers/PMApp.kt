package com.example.parliamentmembers

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.parliamentmembers.ui.navigation.ParliamentMembersNavHost

@Composable
fun PMApp(navCtrl: NavHostController = rememberNavController()) {
    ParliamentMembersNavHost(navCtrl)
}